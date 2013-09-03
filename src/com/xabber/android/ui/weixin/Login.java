package com.xabber.android.ui.weixin;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.xabber.android.data.Application;
import com.xabber.android.data.NetworkException;
import com.xabber.android.data.account.AccountManager;
import com.xabber.android.data.account.AccountType;
import com.xabber.android.data.intent.AccountIntentBuilder;
import com.xabber.android.ui.AccountAdd;
import com.xabber.android.ui.OAuthActivity;
import com.xabber.android.ui.adapter.AccountTypeAdapter;
import com.xabber.android.ui.helper.ManagedActivity;
import com.xabber.androiddev.R;
public class Login extends ManagedActivity implements
View.OnClickListener, OnItemSelectedListener  {
	private EditText mUser; // ’ ∫≈±‡º≠øÚ
	private EditText mPassword; // √‹¬Î±‡º≠øÚ

	private static final String SAVED_ACCOUNT_TYPE = "com.xabber.android.ui.AccountAdd.ACCOUNT_TYPE";

	private static final int OAUTH_WML_REQUEST_CODE = 1;

	private Spinner accountTypeView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFinishing())
			return;

		setContentView(R.layout.login);

        mUser = (EditText)findViewById(R.id.login_user_edit);
        mPassword = (EditText)findViewById(R.id.login_passwd_edit);
        
		accountTypeView = (Spinner) findViewById(R.id.account_type);
		accountTypeView.setAdapter(new AccountTypeAdapter(this));
		accountTypeView.setOnItemSelectedListener(this);

		String accountType;
		if (savedInstanceState == null)
			accountType = null;
		else
			accountType = savedInstanceState.getString(SAVED_ACCOUNT_TYPE);
		accountTypeView.setSelection(0);
		for (int position = 0; position < accountTypeView.getCount(); position++)
			if (((AccountType) accountTypeView.getItemAtPosition(position))
					.getName().equals(accountType)) {
				accountTypeView.setSelection(position);
				break;
			}

		((Button) findViewById(R.id.ok)).setOnClickListener(this);
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(findViewById(R.id.ok)
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(SAVED_ACCOUNT_TYPE,
				((AccountType) accountTypeView.getSelectedItem()).getName());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == OAUTH_WML_REQUEST_CODE) {
			if (resultCode == RESULT_OK && !OAuthActivity.isInvalidated(data)) {
				String token = OAuthActivity.getToken(data);
				if (token == null) {
					Application.getInstance().onError(
							R.string.AUTHENTICATION_FAILED);
				} else {
					String account;
					try {
						account = AccountManager.getInstance()
								.addAccount(
										null,
										token,
										(AccountType) accountTypeView
												.getSelectedItem(),
										false,//syncableView.isChecked(),
										true//storePasswordView.isChecked()
										);
					} catch (NetworkException e) {
						Application.getInstance().onError(e);
						return;
					}
					setResult(RESULT_OK,
							createAuthenticatorResult(this, account));
					finish();
				}
			}
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ok:
			AccountType accountType = (AccountType) accountTypeView
					.getSelectedItem();
			if (accountType.getProtocol().isOAuth()) {
				startActivityForResult(
						OAuthActivity.createIntent(this,
								accountType.getProtocol()),
						OAUTH_WML_REQUEST_CODE);
			} else {
				String account;
				try {
					account = AccountManager.getInstance().addAccount(
							mUser.getText().toString(),
							mPassword.getText().toString(), accountType,
							false,//syncableView.isChecked(),
							true//storePasswordView.isChecked()
							);
				} catch (NetworkException e) {
					Application.getInstance().onError(e);
					return;
				}

		      	Intent intent = new Intent();
				intent.setClass(this,MainWeixin.class);
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view,
			int position, long id) {
//		AccountType accountType = (AccountType) accountTypeView
//				.getSelectedItem();
//		if (accountType.getProtocol().isOAuth())
//			findViewById(R.id.auth_panel).setVisibility(View.GONE);
//		else
//			findViewById(R.id.auth_panel).setVisibility(View.VISIBLE);
//		((TextView) findViewById(R.id.account_user_name)).setHint(accountType
//				.getHint());
//		((TextView) findViewById(R.id.account_help)).setText(accountType
//				.getHelp());
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {
		accountTypeView.setSelection(0);
	}

	public static Intent createIntent(Context context) {
		return new Intent(context, AccountAdd.class);
	}

	private static Intent createAuthenticatorResult(Context context,
			String account) {
		return new AccountIntentBuilder(null, null).setAccount(account).build();
	}

	public static String getAuthenticatorResultAccount(Intent intent) {
		return AccountIntentBuilder.getAccount(intent);
	}

	
    public void login_mainweixin(View v) {
    	if("buaa".equals(mUser.getText().toString()) && "123".equals(mPassword.getText().toString()))   //≈–∂œ ’ ∫≈∫Õ√‹¬Î
        {
             Intent intent = new Intent();
             intent.setClass(Login.this,LoadingActivity.class);
             startActivity(intent);
             //Toast.makeText(getApplicationContext(), "µ«¬º≥…π¶", Toast.LENGTH_SHORT).show();
          }
        else if("".equals(mUser.getText().toString()) || "".equals(mPassword.getText().toString()))   //≈–∂œ ’ ∫≈∫Õ√‹¬Î
        {
        	new AlertDialog.Builder(Login.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("µ«¬º¥ÌŒÛ")
			.setMessage("Œ¢–≈’ ∫≈ªÚ’ﬂ√‹¬Î≤ªƒ‹Œ™ø’£¨\n«Î ‰»Î∫Û‘Ÿµ«¬º£°")
			.create().show();
         }
        else{
           
        	new AlertDialog.Builder(Login.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("µ«¬º ß∞‹")
			.setMessage("Œ¢–≈’ ∫≈ªÚ’ﬂ√‹¬Î≤ª’˝»∑£¨\n«ÎºÏ≤È∫Û÷ÿ–¬ ‰»Î£°")
			.create().show();
        }
    	
    	//µ«¬º∞¥≈•
    	/*
      	Intent intent = new Intent();
		intent.setClass(Login.this,Whatsnew.class);
		startActivity(intent);
		Toast.makeText(getApplicationContext(), "µ«¬º≥…π¶", Toast.LENGTH_SHORT).show();
		this.finish();*/
      }  
    public void login_back(View v) {     //±ÍÃ‚¿∏ ∑µªÿ∞¥≈•
//      	this.finish();
      }  
    public void login_pw(View v) {     //Õ¸º«√‹¬Î∞¥≈•
//    	Uri uri = Uri.parse("http://3g.qq.com"); 
//    	Intent intent = new Intent(Intent.ACTION_VIEW, uri); 
//    	startActivity(intent);
    	//Intent intent = new Intent();
    	//intent.setClass(Login.this,Whatsnew.class);
        //startActivity(intent);
      }  
}
