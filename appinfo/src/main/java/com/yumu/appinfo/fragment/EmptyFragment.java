package com.yumu.appinfo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yumu.appinfo.R;

/**
 * Date :  2020-03-28.
 * Time :  15:39.
 * Created by sunan.
 */
public class EmptyFragment extends BaseFragment {

    private String tile;

    public EmptyFragment() {
    }

    public static  EmptyFragment getInstance(String title) {
        EmptyFragment emptyFragment = new EmptyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("params", title);
        emptyFragment.setArguments(bundle);
        return emptyFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = View.inflate(getContext(), R.layout.fragment_empty, null);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tile = getArguments().getString("params");
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText(tile);

    }
}
