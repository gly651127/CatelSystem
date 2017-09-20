package com.catel.util;

import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressWarnings("unused")
public class SizeUtil {
	public static int getTotalHeightofListView(ListView listView) {
		ListAdapter mAdapter = listView.getAdapter();
		if (mAdapter == null) {
			return 0;
		}
		int totalHeight = 0;
		boolean aaa = false;
		for (int i = 0; i < mAdapter.getCount(); i++) {
			View mView = mAdapter.getView(i, null, listView);
			mView.measure(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			// mView.measure(0, 0);
			totalHeight += mView.getMeasuredHeight();
			if (!aaa && i == 1) {
				i--;
				aaa = true;
			}
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (mAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
		return totalHeight;
	}

	public static int measureView(View child) {
		ViewGroup.LayoutParams lp = child.getLayoutParams();
		if (lp == null) {
			lp = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		// headerView�Ŀ����??
		int childMeasureWidth = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
		int childMeasureHeight;
		if (lp.height > 0) {
			childMeasureHeight = MeasureSpec.makeMeasureSpec(lp.height,
					MeasureSpec.EXACTLY);
		} else {
			childMeasureHeight = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);// δָ??
		}
		child.measure(childMeasureWidth, childMeasureHeight);
		return child.getMeasuredHeight();
	}

	public static int getViewHeight(View view) {
		int w = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		int h = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		int height = view.getMeasuredHeight();
		int width = view.getMeasuredWidth();
		return height;
	}

	public static int getLineCount(TextView textView, float width) {
		Paint textPaint = textView.getPaint();
		Rect bounds = new Rect();
		String s = textView.getText().toString();
		textPaint.getTextBounds(s, 0, s.length(), bounds);
		float bw = bounds.width();

		float a = bw / width;
		String bString = (String) (a + "").subSequence(
				(a + "").indexOf(".") + 1, (a + "").length());
		int count = (int) a;
		if (Integer.parseInt(bString) > 0) {
			count += 1;
		}
		return count;
	}
}
