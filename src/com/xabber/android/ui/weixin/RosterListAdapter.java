/**
 * Copyright (c) 2013, Redsolution LTD. All rights reserved.
 * 
 * This file is part of Xabber project; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License, Version 3.
 * 
 * Xabber is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License,
 * along with this program. If not, see http://www.gnu.org/licenses/.
 */
package com.xabber.android.ui.weixin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xabber.android.data.account.AccountItem;
import com.xabber.android.data.account.AccountManager;
import com.xabber.android.data.connection.ConnectionState;
import com.xabber.android.data.extension.avatar.AvatarManager;
import com.xabber.android.data.extension.muc.RoomContact;
import com.xabber.android.data.roster.RosterContact;
import com.xabber.android.ui.AccountList;
import com.xabber.androiddev.R;

/**
 * Adapter for the list of accounts for {@link AccountList}.
 * 
 * @author alexander.ivanov
 * 
 */
public class RosterListAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private RosterContact[] contacts;
	
	public RosterListAdapter(Activity context) {
		mInflater = context.getLayoutInflater();
	}

	public void setContacts(Collection<RosterContact> contacts)
	{
		Log.d("RosterListAdapter", contacts.toString());
		this.contacts = (RosterContact[]) contacts.toArray();
	}
	public int getCount() {
		return contacts.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = mInflater.inflate(R.layout.main_tab_address_cell, null);
			holder = new ViewHolder();
			holder.nameView = (TextView) convertView
					.findViewById(R.id.friend_name);
			
			convertView.setTag(holder);
		}
		final RosterContact contact = contacts[position];
		holder.nameView.setText( contact.getName());
		return convertView;
	}

	class ViewHolder {
		TextView nameView;
	}

}
