package com.maximus.maximusbank.activity.ChequeBook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;
import com.maximus.maximusbank.activity.splitBill.SplitAmountActivity;
import com.maximus.maximusbank.common.CommonBottomSheetDialogFragment;


public class ChequeBookActivity extends AppCompatActivity {
    TextView headerTextView,txtcommadd,txtstate,txtcity,txtloc,txtadd;
    ImageView backButton;
    RadioButton rdcommadd,rdpickup;
    Button btnclear,btnconfirm;
    Spinner spinstate,spincity,spinloc,spinadd,SpinnerAcc,Spinnerpages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheque_book);
        ImageView backPressImg = findViewById(R.id.backButton);
        Utils.onBackPress(this, backPressImg);
        TextView headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.cheque_book);
        headerTextView = findViewById(R.id.headerTextView);
        backButton = findViewById(R.id.backButton);
        headerTextView.setText(R.string.cheque_book);
        rdcommadd = findViewById(R.id.rdcommadd);
        rdpickup = findViewById(R.id.rdpickup);
        txtcommadd = findViewById(R.id.txtcommadd);
        btnclear = findViewById(R.id.btnclear);
        txtstate = findViewById(R.id.txtstate);
        spinstate = findViewById(R.id.spinstate);
        txtcity = findViewById(R.id.txtcity);
        spincity = findViewById(R.id.spincity);
        txtloc = findViewById(R.id.txtloc);
        spinloc = findViewById(R.id.spinloc);
        txtadd = findViewById(R.id.txtadd);
        spinadd = findViewById(R.id.spinadd);
        btnconfirm = findViewById(R.id.btnconfirm);
        SpinnerAcc = findViewById(R.id.SpinnerAcc);
        Spinnerpages = findViewById(R.id.Spinnerpages);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        rdcommadd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rdcommadd.isChecked()){
                    txtcommadd.setVisibility(View.VISIBLE);
                    rdpickup.setChecked(false);
                    txtstate.setVisibility(View.GONE);
                    spinstate.setVisibility(View.GONE);
                    txtcity.setVisibility(View.GONE);
                    spincity.setVisibility(View.GONE);
                    txtloc.setVisibility(View.GONE);
                    spinloc.setVisibility(View.GONE);
                    txtadd.setVisibility(View.GONE);
                    spinadd.setVisibility(View.GONE);
                } else {
                    txtcommadd.setVisibility(View.GONE);
                }
            }
        });


        rdpickup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rdpickup.isChecked()) {
                    txtstate.setVisibility(View.VISIBLE);
                    spinstate.setVisibility(View.VISIBLE);
                    txtcity.setVisibility(View.VISIBLE);
                    spincity.setVisibility(View.VISIBLE);
                    txtloc.setVisibility(View.VISIBLE);
                    spinloc.setVisibility(View.VISIBLE);
                    txtadd.setVisibility(View.VISIBLE);
                    spinadd.setVisibility(View.VISIBLE);
                    rdcommadd.setChecked(false);
                    txtcommadd.setVisibility(View.GONE);
                } else {
                    txtstate.setVisibility(View.GONE);
                    spinstate.setVisibility(View.GONE);
                    txtcity.setVisibility(View.GONE);
                    spincity.setVisibility(View.GONE);
                    txtloc.setVisibility(View.GONE);
                    spinloc.setVisibility(View.GONE);
                    txtadd.setVisibility(View.GONE);
                    spinadd.setVisibility(View.GONE);
                }
            }
        });

        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdcommadd.setChecked(false);
                rdpickup.setChecked(false);
//                SpinnerAcc.setAdapter(null);
//                Spinnerpages.setAdapter(null);
            }
        });

        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatefields()){
                    confirmbottom();
                } else {
                    Toast.makeText(ChequeBookActivity.this, "Fill all the details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validatefields(){
        if (SpinnerAcc.getSelectedItemPosition() == 0 ) {
            Toast.makeText(this, "Account number is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Spinnerpages.getSelectedItemPosition() == 0 ) {
            Toast.makeText(this, "Number of pages is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (rdcommadd.isChecked()) {
        }
        else if (rdpickup.isChecked()) {
            if (spinstate.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "State is required", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (spincity.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "City is required", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (spinloc.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Location is required", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (spinadd.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Address is required", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, "Please select a delivery method", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
    }
    private void confirmbottom() {
        String myString = "Request Sent Successfully";
        CommonBottomSheetDialogFragment bottomSheet = CommonBottomSheetDialogFragment.newInstance(myString, ChequeBookActivity.this);
        bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
            /*View viewstatus = LayoutInflater.from(this).inflate(R.layout.bottomsheetchequebook, null);
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(viewstatus);
            bottomSheetDialog.show();*/
    }
}