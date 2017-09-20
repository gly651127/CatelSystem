package com.catel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.catel.system.R;

/**
 * Created by Administrator on 2017/8/21.
 */

public class SetCenterFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getView(inflater, R.layout.fragment_center, null);
    }

    @Override
    public void refresh() {

    }
}
