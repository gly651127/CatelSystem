package com.catel.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;

public class ManListUtils {
	/** 获取库Phon表字段 **/
	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };

	/** 联系人显示名称 **/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;

	/** 电话号码 **/
	private static final int PHONES_NUMBER_INDEX = 1;

	/** 头像ID **/
	private static final int PHONES_PHOTO_ID_INDEX = 2;

	/** 联系人的ID **/
	private static final int PHONES_CONTACT_ID_INDEX = 3;

	 
	public String GetContacList(Context mContext) {
		return getSIMContacts(mContext)+getPhoneContacts(mContext);
	}

	/** 得到手机SIM卡联系人人信息 **/
	private String getSIMContacts(Context mContext) {
		StringBuffer str=new StringBuffer();
		str.append("Sim卡联系人\n");
		ContentResolver resolver = mContext.getContentResolver();
		// 获取Sims卡联系人
		Uri uri = Uri.parse("content://icc/adn");
		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,
				null);

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {

				// 得到手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				// 得到联系人名称
				String contactName = phoneCursor
						.getString(PHONES_DISPLAY_NAME_INDEX);

				// Sim卡中没有联系人头像
				str.append(contactName+":"+phoneNumber+"\n");
				//mContactsName.add(contactName);
			//	mContactsNumber.add(phoneNumber);
			}

			phoneCursor.close();
		}
		return str.toString();
	}

	/** 得到手机通讯录联系人信息 **/
	private String getPhoneContacts(Context mContext) {
		StringBuffer str=new StringBuffer();
		str.append("手机通讯录联系人\n");
		ContentResolver resolver = mContext.getContentResolver();

		// 获取手机联系人
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,
				PHONES_PROJECTION, null, null, null);

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {

				// 得到手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;

				// 得到联系人名称
				String contactName = phoneCursor
						.getString(PHONES_DISPLAY_NAME_INDEX);

				// 得到联系人ID
				Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);

				// 得到联系人头像ID
				// Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);

				// 得到联系人头像Bitamp
				// Bitmap contactPhoto = null;

				/*
				 * //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的 if(photoid > 0 ) {
				 * Uri uri
				 * =ContentUris.withAppendedId(ContactsContract.Contacts.
				 * CONTENT_URI,contactid); InputStream input =
				 * ContactsContract.Contacts
				 * .openContactPhotoInputStream(resolver, uri); contactPhoto =
				 * BitmapFactory.decodeStream(input); }else { contactPhoto =
				 * BitmapFactory.decodeResource(getResources(),
				 * R.drawable.contact_photo); }
				 */

			//	mContactsName.add(contactName);
				//mContactsNumber.add(phoneNumber);
				// mContactsPhonto.add(contactPhoto);
				str.append(contactName+":"+phoneNumber+"\n");
			}

			phoneCursor.close();
		}
		return str.toString();
	}
}
/*需要的权限
<!-- 读取联系人权限 -->   
<uses-permission android:name="android.permission.READ_CONTACTS"/> 
*/