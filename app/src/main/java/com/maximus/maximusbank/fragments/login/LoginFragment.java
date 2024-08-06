package com.maximus.maximusbank.fragments.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.activity.landing.OTPActivity;


public class LoginFragment extends Fragment {

    private ViewPager2 viewPager;
    Button btnSignUp, btnSignIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        viewPager = getActivity().findViewById(R.id.viewPager);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        btnSignIn = view.findViewById(R.id.btnSignIn);
        btnSignUp.setOnClickListener(v -> viewPager.setCurrentItem(1));
        btnSignIn.setOnClickListener(v -> startActivity(new Intent(getActivity(), OTPActivity.class)));

        return view;
    }
}