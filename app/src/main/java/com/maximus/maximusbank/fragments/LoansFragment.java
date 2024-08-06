package com.maximus.maximusbank.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.activity.account.LoanCalculatorActivity;
import com.maximus.maximusbank.activity.account.LoanEnquiryActivity;
import com.maximus.maximusbank.activity.account.LoanRepaymentActivity;
import com.maximus.maximusbank.activity.account.OpenLoanActivity;

public class LoansFragment extends Fragment {

    public LoansFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loans, container, false);

        // Initialize your buttons
        MaterialButton openLoanAgainstFDButton = view.findViewById(R.id.openLoanAgainstFDButton);
        MaterialButton requestInterestCertificateButton = view.findViewById(R.id.loanCalculator);
        MaterialButton loanEnquiryButton = view.findViewById(R.id.loanEnquiryButton);
        MaterialButton loanRepaymentButton = view.findViewById(R.id.loanRepaymentButton);

        // Set click listeners for each button
        openLoanAgainstFDButton.setOnClickListener(v -> startActivity(new Intent(getActivity(), OpenLoanActivity.class)));
        requestInterestCertificateButton.setOnClickListener(v -> startActivity(new Intent(getActivity(), LoanCalculatorActivity.class)));
        loanEnquiryButton.setOnClickListener(v -> startActivity(new Intent(getActivity(), LoanEnquiryActivity.class)));
        loanRepaymentButton.setOnClickListener(v -> startActivity(new Intent(getActivity(), LoanRepaymentActivity.class)));

        return view;
    }
}
