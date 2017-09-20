package com.catel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.catel.system.R;
import com.catel.modle.ControlInfo;
import com.catel.util.baseadapter.CommonAdapter;
import com.catel.util.baseadapter.ViewHolder;
import com.catel.views.SureDialog;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/8/21.
 */

public class ControlFragment extends BaseFragment {


    @BindView(R.id.lv_message)
    ListView lvMessage;
    private List<ControlInfo> datas;
    private CommonAdapter<ControlInfo> adapter;


    public void setData(List<ControlInfo> datas) {
        this.datas = datas;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getView(inflater, R.layout.fragment_control, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initList();
    }


    private void initList() {
        adapter = new CommonAdapter<ControlInfo>(mContext, datas, R.layout.list_control_item) {
            @Override
            public void convert(ViewHolder helper, final ControlInfo item) {
                TextView tv_place = helper.getView(R.id.tv_place);
                TextView tv_code = helper.getView(R.id.tv_code);

                tv_code.setText(item.getCode());
                tv_place.setText(item.getPlace());

                helper.setOnclickLinstener(R.id.btn_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new SureDialog(mContext).setContentText(getResources().getString(R.string.sure_is_delete)).setOnChooseLinstener(new SureDialog.onChooseLinstener() {
                            @Override
                            public void onSure() {
                                datas.remove(item);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void cancle() {

                            }
                        }).show();

                    }
                });
            }
        };
        lvMessage.setAdapter(adapter);
    }

    @Override
    public void refresh() {
        adapter.notifyDataSetChanged();
    }
}
