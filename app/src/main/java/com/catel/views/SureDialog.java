package com.catel.views;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.catel.system.R;
import com.catel.application.MyApplication;


/**
 * @author gly
 * @Description:自定义对话框
 */
public class SureDialog extends Dialog {
    public static SureDialog eloadingDialog;
    private final TextView tv_content;
    private onChooseLinstener onChooseLinstener;

//    public static void ShowDialog(Context mContext) {
//        if (null != eloadingDialog) {
//            if (null != eloadingDialog.getWindow() && eloadingDialog.isShowing()) {
//                eloadingDialog.cancel();
//                eloadingDialog = null;
//            }
//        }
//        eloadingDialog = new SureDialog(mContext.getApplicationContext());
//        eloadingDialog.setCancelable(true);
//        eloadingDialog.show();
//    }

    public SureDialog setContentText(String str) {
        tv_content.setText(str);
        return this;
    }

    public static void cancle() {
        if (null != eloadingDialog && eloadingDialog.isShowing()) {
            eloadingDialog.dismiss();
            eloadingDialog = null;
        }
    }

    public SureDialog(Context mContext) {
        super(mContext, R.style.sure_dialog);
        float density = mContext.getResources().getDisplayMetrics().density;


//        Window dialogWindow = getWindow();
//        dialogWindow.setGravity(Gravity.CENTER);
//        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.width = (int) (MyApplication.size[0] * 0.8);
//        p.height = (int) (p.width * 0.37);
//        dialogWindow.setAttributes(p);

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.sure_dialog, null);
        tv_content = inflate.findViewById(R.id.tv_content);
        int width = (int) (MyApplication.size[0] * 0.8);
        int height = (int) (width * 0.37);
        setContentView(inflate, new RelativeLayout.LayoutParams(width, height));
        findViewById(R.id.btn_sure).setOnClickListener(clickListener);
        findViewById(R.id.btn_cancle).setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (null != onChooseLinstener) {
                switch (view.getId()) {
                    case R.id.btn_cancle:
                        onChooseLinstener.cancle();
                        break;
                    case R.id.btn_sure:
                        onChooseLinstener.onSure();
                        break;
                    default:
                        break;
                }
            }
            dismiss();
        }
    };

    public SureDialog setOnChooseLinstener(onChooseLinstener onChooseLinstener) {
        this.onChooseLinstener = onChooseLinstener;
        return this;
    }

    public interface onChooseLinstener {
        void onSure();

        void cancle();
    }

}
