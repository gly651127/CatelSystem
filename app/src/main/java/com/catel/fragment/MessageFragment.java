package com.catel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.catel.system.R;
import com.catel.modle.MessageInfo;
import com.catel.util.baseadapter.CommonAdapter;
import com.catel.util.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/8/21.
 */

public class MessageFragment extends BaseFragment {

    @BindView(R.id.lv_message)
    ListView lvMessage;
    private List<MessageInfo> datas = new ArrayList<>();
    private CommonAdapter<MessageInfo> adapter;

    public void setData(List<MessageInfo> datas) {
        this.datas = datas;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getView(inflater, R.layout.fragment_message, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initList();
    }

    private void initList() {

        adapter = new CommonAdapter<MessageInfo>(mContext, datas, R.layout.list_message_item) {
            @Override
            public void convert(ViewHolder helper, MessageInfo item) {
                TextView tv_issure = helper.getView(R.id.tv_issure);
                TextView tv_name = helper.getView(R.id.tv_name);
                TextView tv_message = helper.getView(R.id.tv_message);
                TextView tv_place = helper.getView(R.id.tv_place);
                ImageView img_song = helper.getView(R.id.img_song);

                tv_name.setText(item.getName());
                tv_message.setText(item.getMessage());
                tv_place.setText(item.getPlace());
                if (item.isSure()) {
                    tv_issure.setText("已确认");
                    img_song.setImageResource(R.drawable.song_unsure);
                    int colorSure = getResources().getColor(R.color.tv_sure);
                    tv_name.setTextColor(colorSure);
                    tv_message.setTextColor(colorSure);
                    tv_place.setTextColor(colorSure);
                    tv_issure.setTextColor(colorSure);
                } else {
                    tv_issure.setText("未确认");
                    img_song.setImageResource(R.drawable.song_sure);
                    int colorUnsure = getResources().getColor(R.color.tv_unsure);
                    tv_name.setTextColor(colorUnsure);
                    tv_message.setTextColor(colorUnsure);
                    tv_place.setTextColor(colorUnsure);
                    tv_issure.setTextColor(colorUnsure);
                }
            }
        };
        lvMessage.setAdapter(adapter);
    }


    @Override
    public void refresh() {
        adapter.notifyDataSetChanged();
    }
}
