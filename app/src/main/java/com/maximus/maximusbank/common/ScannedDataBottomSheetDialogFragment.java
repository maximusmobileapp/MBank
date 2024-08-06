package com.maximus.maximusbank.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.activity.scanNpay.ScannerActivity;

public class ScannedDataBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private static final String ARG_BARCODE_VALUE = "barcode_value";

    public static ScannedDataBottomSheetDialogFragment newInstance(String barcodeValue) {
        ScannedDataBottomSheetDialogFragment fragment = new ScannedDataBottomSheetDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BARCODE_VALUE, barcodeValue);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_scanned_data, container, false);

        TextView tvBarcodeValue = view.findViewById(R.id.tv_barcode_value);
        Button btnOk = view.findViewById(R.id.btn_ok);

        if (getArguments() != null) {
            String barcodeValue = getArguments().getString(ARG_BARCODE_VALUE);
            tvBarcodeValue.setText(barcodeValue);
        }

        btnOk.setOnClickListener(v -> {
                    dismiss();
            ((ScannerActivity) getActivity()).resumeScanning();
                }
        );

        return view;
    }
}

