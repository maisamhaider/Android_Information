package com.dragster.android.information.system.my.android.pushnotification;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceConnector {
	
	public static final String PREF_NAME = "Setting_PREFERENCES";
	public static final int MODE = Context.MODE_PRIVATE;


	// Settings Saved
	public static final String message = "message";
	public static final String title_Notifi = "title";
	public static final String packageName = "packageName";
	public static final String mode = "mode";
	public static final String isTokenSaved = "isTokenSaved";



	public static void writeBoolean(Context context, String key, boolean value) {
		getEditor(context).putBoolean(key, value).commit();
	}

	public static boolean readBoolean(Context context, String key, boolean defValue) {
		return getPreferences(context).getBoolean(key, defValue);
	}

	public static void writeInteger(Context context, String key, int value) {
		getEditor(context).putInt(key, value).commit();

	}

	public static int readInteger(Context context, String key, int defValue) {
		return getPreferences(context).getInt(key, defValue);
	}

	public static void writeString(Context context, String key, String selectedEquipmentGroupList) {
		getEditor(context).putString(key, selectedEquipmentGroupList).commit();

	}
	
	 
	public static String readString(Context context, String key, String defValue) {
		return getPreferences(context).getString(key, defValue);
	}
	
	public static void writeFloat(Context context, String key, float value) {
		getEditor(context).putFloat(key, value).commit();
	}

	public static float readFloat(Context context, String key, float defValue) {
		return getPreferences(context).getFloat(key, defValue);
	}
	
	public static void writeLong(Context context, String key, long value) {
		getEditor(context).putLong(key, value).commit();
	}

	public static long readLong(Context context, String key, long defValue) {
		return getPreferences(context).getLong(key, defValue);
	}

	public static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(PREF_NAME, MODE);
	}

	public static Editor getEditor(Context context) {
		return getPreferences(context).edit();
	}


}
