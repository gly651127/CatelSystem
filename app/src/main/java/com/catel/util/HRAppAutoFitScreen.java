package com.catel.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

/**
 * 
 * @author android 自适应屏幕工具类
 * 
 */
public class HRAppAutoFitScreen {
	public static float scaleX, scaleY,screenwidth,screenheight;
	static {
		scaleX = 1.0f;
		scaleY = 1.0f;
	}

	public static void initDate(Context mContext) {
		DisplayMetrics display = mContext.getResources().getDisplayMetrics();
		 screenwidth = display.widthPixels;
		 screenheight = display.heightPixels;
		scaleX = screenwidth / 720f;
		scaleY = screenheight / 1280f;
	}

	public static float autofitX(float pixValue) {
		return scaleX * pixValue;
	}

	public static float autofitY(float pixValue) {
		return scaleY * pixValue;
	}

	public static int autofitX(int pixValue) {
		return (int) (scaleX * pixValue);
	}

	public static int autofitY(int pixValue) {
		return (int) (scaleY * pixValue);
	}

	public static ViewGroup.LayoutParams getAutoFitLayoutParams(
			ViewGroup.LayoutParams params) {
		int width = params.width;
		int height = params.height;
		if (width == ViewGroup.LayoutParams.FILL_PARENT
				|| width == ViewGroup.LayoutParams.MATCH_PARENT
				|| width == ViewGroup.LayoutParams.WRAP_CONTENT) {

		} else {
			params.width = (int) (params.width * scaleX);
		}
		if (height == ViewGroup.LayoutParams.FILL_PARENT
				|| height == ViewGroup.LayoutParams.MATCH_PARENT
				|| height == ViewGroup.LayoutParams.WRAP_CONTENT) {

		} else {

			params.height = (int) (params.height * scaleY);
		}
		if (width >= 1 && params.width == 0) {
			params.width = 1;
		}
		if (height >= 1 && params.height == 0) {
			params.height = 1;
		}
		if (params instanceof ViewGroup.MarginLayoutParams) {
			((ViewGroup.MarginLayoutParams) params).leftMargin = (int) (scaleX * ((ViewGroup.MarginLayoutParams) params).leftMargin);
			((ViewGroup.MarginLayoutParams) params).rightMargin = (int) (scaleX * ((ViewGroup.MarginLayoutParams) params).rightMargin);
			((ViewGroup.MarginLayoutParams) params).topMargin = (int) (scaleY * ((ViewGroup.MarginLayoutParams) params).topMargin);
			((ViewGroup.MarginLayoutParams) params).bottomMargin = (int) (scaleY * ((ViewGroup.MarginLayoutParams) params).bottomMargin);
		}
		return params;
	}

}
