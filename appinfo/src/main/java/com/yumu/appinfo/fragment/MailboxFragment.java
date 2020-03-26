package com.yumu.appinfo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yumu.appinfo.R;
import com.yumu.appinfo.views.LuckyDrawView;

import java.util.Random;


public class MailboxFragment extends Fragment implements View.OnClickListener {

    private TextView tv_action_all, tv_action_border, tv_reset;
    private LuckyDrawView luckyDrawView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = View.inflate(getContext(), R.layout.fragment_mailbox, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_action_all = view.findViewById(R.id.tv_action_all);
        tv_action_border = view.findViewById(R.id.tv_action_border);
        luckyDrawView = view.findViewById(R.id.lucky_panel);
        tv_reset = view.findViewById(R.id.tv_reset);
        addViewAction();
    }

    private void addViewAction() {
        tv_action_border.setOnClickListener(this);
        tv_action_all.setOnClickListener(this);
        tv_reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_action_all) {
            if (!luckyDrawView.isGameRunning()) {
                luckyDrawView.startGame();
                int stayIndex = new Random().nextInt(6);//随机数
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        luckyDrawView.tryToStop(stayIndex);//停止的时候的索引
                    }
                }, 5000);
            }
        } else if (view.getId() == R.id.tv_action_border) {
            luckyDrawView.startReversal();
        } else if (view.getId() == R.id.tv_reset) {
            luckyDrawView.resetAdapter();
        }
    }

}
