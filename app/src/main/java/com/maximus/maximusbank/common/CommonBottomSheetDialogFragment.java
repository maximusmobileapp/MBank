package com.maximus.maximusbank.common;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.activity.DashboardActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.content.Context;


public class CommonBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private static final String ARG_STRING = "arg_string";
    private String myString;
    private Context context;

    public static CommonBottomSheetDialogFragment newInstance(String myString, Context context) {
        CommonBottomSheetDialogFragment fragment = new CommonBottomSheetDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STRING, myString);
        fragment.setArguments(args);
        fragment.setContext(context);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        TextView textView = view.findViewById(R.id.txtMsg);
        Button buttonAction = view.findViewById(R.id.button_action);

        if (getArguments() != null) {
            myString = getArguments().getString(ARG_STRING);
        }

        textView.setText(myString);

        buttonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, DashboardActivity.class));
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        if (dialog != null) {
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            View bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setHideable(false);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }
}


