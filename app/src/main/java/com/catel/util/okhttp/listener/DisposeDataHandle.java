package com.catel.util.okhttp.listener;

/**
 * @author vision
 */
public class DisposeDataHandle {
    public DisposeDataListener mListener = null;
    public String mSource = null;


    public DisposeDataHandle(DisposeDataListener listener) {
        this.mListener = listener;
    }

    public DisposeDataHandle(DisposeDataListener listener, String source) {
        this.mListener = listener;
        this.mSource = source;
    }
}