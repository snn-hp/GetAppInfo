package com.yumu.appinfo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yumu.appinfo.R;



public class PersonFragment extends Fragment {
    public static String NAME = "NAME";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = View.inflate(getContext(), R.layout.fragment_holder, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText("1234");

//        textView.setText(getArguments().getString(NAME));
    }

    public static PersonFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(NAME, text);
        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
