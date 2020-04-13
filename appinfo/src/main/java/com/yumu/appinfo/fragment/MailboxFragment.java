package com.yumu.appinfo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yumu.appinfo.R;
import com.yumu.appinfo.adapter.CirculationViewAdapter;
import com.yumu.appinfo.views.CustomLinerLayoutManager;
import com.yumu.appinfo.views.LuckyDrawView;

import java.util.Random;

/**
 * Date :  2020-03-28.
 * Time :  15:39.
 * Created by sunan.
 */
public class MailboxFragment extends Fragment implements View.OnClickListener {
    private CirculationViewAdapter adapterOne, adapterTwo, adapterThree,adapterFour;
    private RecyclerView recyclerViewOne, recyclerViewTwo, recyclerViewThree,recyclerViewFour;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = View.inflate(getContext(), R.layout.fragment_mailbox, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewOne = view.findViewById(R.id.recyclerViewOne);
        recyclerViewTwo = view.findViewById(R.id.recyclerViewTwo);
        recyclerViewThree = view.findViewById(R.id.recyclerViewThree);
        recyclerViewFour = view.findViewById(R.id.recyclerViewFour);
        initRecyclerView();
        addViewAction();
    }

    public void initRecyclerView() {

        // x 正方向 循环滚动
        CustomLinerLayoutManager layoutManagerOne = new CustomLinerLayoutManager(getActivity());
        layoutManagerOne.setSpeedSlow();
        layoutManagerOne.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewOne.setLayoutManager(layoutManagerOne);
        recyclerViewOne.setAdapter(adapterOne = new CirculationViewAdapter(getActivity(), 1));

        // x 负方向 循环滚动
        CustomLinerLayoutManager layoutManagerTwo = new CustomLinerLayoutManager(getActivity());
        layoutManagerTwo.setSpeedSlow();
        layoutManagerTwo.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewTwo.setLayoutManager(layoutManagerTwo);
        recyclerViewTwo.setAdapter(adapterTwo = new CirculationViewAdapter(getActivity(), 2));

        //  x 正方向 无限滚动
        CustomLinerLayoutManager layoutManagerThree = new CustomLinerLayoutManager(getActivity());
        layoutManagerThree.setSpeedSlow();
        layoutManagerThree.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewThree.setLayoutManager(layoutManagerThree);
        recyclerViewThree.setAdapter(adapterThree = new CirculationViewAdapter(getActivity(), 3));
        recyclerViewThree.smoothScrollToPosition(Integer.MAX_VALUE / 2);

        //  x 负方向 无限滚动
        CustomLinerLayoutManager layoutManagerFour = new CustomLinerLayoutManager(getActivity());
        layoutManagerFour.setSpeedSlow();
        layoutManagerFour.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewFour.setLayoutManager(layoutManagerFour);
        recyclerViewFour.setAdapter(adapterFour = new CirculationViewAdapter(getActivity(), 3));
        recyclerViewFour.smoothScrollToPosition(0);

    }


    private void addViewAction() {
        recyclerViewTwo.scrollToPosition(14);
        recyclerViewOne.addOnScrollListener(onScrollListenerOne);
        recyclerViewTwo.addOnScrollListener(onScrollListenerTwo);
    }

    RecyclerView.OnScrollListener onScrollListenerOne = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            isVisBottom(recyclerView, recyclerViewTwo);
        }
    };

    RecyclerView.OnScrollListener onScrollListenerTwo = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            isVisBottom(recyclerView, recyclerViewOne);
        }
    };

    public void isVisBottom(RecyclerView recyclerView, RecyclerView recyclerViewOther) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (!recyclerView.canScrollHorizontally(-1)) {//当前在顶部
            recyclerView.smoothScrollToPosition(linearLayoutManager.getItemCount() - 1);
            recyclerViewOther.smoothScrollToPosition(0);
        }
        if (!recyclerView.canScrollHorizontally(1)) {//当前在底部
            recyclerView.smoothScrollToPosition(0);
            recyclerViewOther.smoothScrollToPosition(linearLayoutManager.getItemCount() - 1);

        }
    }

    @Override
    public void onClick(View view) {

    }
}
