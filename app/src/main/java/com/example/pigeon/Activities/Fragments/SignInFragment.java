package com.example.pigeon.Activities.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pigeon.R;

public class SignInFragment extends Fragment {


    public static SignInFragment newInstance(int position){
        Bundle args = new Bundle();
        args.putInt("ARG_PAGE", position);
        SignInFragment signInFragment = new SignInFragment();
        signInFragment.setArguments(args);
        return signInFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_in_content, container, false);
        return view;
    }
}
