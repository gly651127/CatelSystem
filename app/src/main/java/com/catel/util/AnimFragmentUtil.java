package com.catel.util;

/**
 * Created by lm806 on 2016/12/15.
 */

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.catel.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("Recycle")
public class AnimFragmentUtil {
    private FragmentManager fragmentManager;
    private int layoutId;
    private List<BaseFragment> fragments = new ArrayList<>();

    public AnimFragmentUtil(FragmentManager fragmentManager, int layoutId) {
        super();
        this.fragmentManager = fragmentManager;
        this.layoutId = layoutId;
    }

    public void refreshAll() {
        for (BaseFragment fr : fragments) {
            fr.refresh();
        }
    }

    public void selectFragment(BaseFragment fragment) {
        fragmentManager.popBackStackImmediate();
        addFragment(fragment);
    }

    private Fragment addFragment(BaseFragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.setCustomAnimations(arg0, arg1, arg2, arg3);
        // Bundle bundle = new Bundle();
        // bundle.putSerializable("message", message);
        // fragment.setArguments(bundle);
        fragments.add(fragment);
        ft.add(layoutId, fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
        return fragment;
    }

    @SuppressWarnings("unchecked")
    private <T> T getInstance(Class<T> theClass) {
        T o = null;
        try {
            o = (T) Class.forName(theClass.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

}
