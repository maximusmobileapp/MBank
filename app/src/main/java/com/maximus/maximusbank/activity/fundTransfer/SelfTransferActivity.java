package com.maximus.maximusbank.activity.fundTransfer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;
import com.maximus.maximusbank.adapter.SelfTranfStepAdapter;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SelfTransferActivity extends AppCompatActivity {
    private EditText etamu;
    TextView headerTextView;
    private RecyclerView step_recycler_view_self;
    private SelfTranfStepAdapter adapterselftransf;
    private Button proceedButtonSelf;
    private List<String> steps = Arrays.asList("Pay From", "Available Balance", "Pay To", "Amount", "Remarks");
    private List<String> SpinnerDataSelf = Arrays.asList("Select from account no", "95551132115", "95512141151", "9525454842");
    private List<String> SpinnerDataSelfto = Arrays.asList("Select to account no", "123223232323", "4234343434343", "32443433434");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_transfer);

        headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.self_transfer);

        ImageView backPressImg = findViewById(R.id.backButton);
        Utils.onBackPress(this, backPressImg);
        step_recycler_view_self = findViewById(R.id.step_recycler_view_self);

        step_recycler_view_self.setLayoutManager(new LinearLayoutManager(this));
        adapterselftransf = new SelfTranfStepAdapter(steps);
        step_recycler_view_self.setAdapter(adapterselftransf);


        // Set up different spinners for different steps
        ArrayAdapter<String> payFromAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SpinnerDataSelf);
        payFromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterselftransf.setPayFromAdapter(payFromAdapter);

        ArrayAdapter<String> payToAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SpinnerDataSelfto);
        payToAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterselftransf.setPayToAdapter(payToAdapter);


        proceedButtonSelf = findViewById(R.id.proceedButtonSelf);
        proceedButtonSelf.setOnClickListener(v -> {

            Map<String, String> stepDataMap = new HashMap<>();
            stepDataMap.put("fromAccount", adapterselftransf.getStepData().get(0));
            stepDataMap.put("payTo", adapterselftransf.getStepData().get(1));
            stepDataMap.put("amount", adapterselftransf.getStepData().get(3));

            Intent intent = new Intent(SelfTransferActivity.this, FT_Confirmation.class);
            intent.putExtra("stepData", new HashMap<>(stepDataMap));
            intent.putExtra("refNo", generateDeviceReferenceNo());
            intent.putExtra("paymentTime", getCurrentDateTime());
            intent.putExtra("bankType", "Other Bank");
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    private String generateDeviceReferenceNo() {
        String deviceId = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // For Android 10 and above
            deviceId = Settings.Secure.ANDROID_ID;
        } else {
            // For older Android versions
            deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        return deviceId.substring(0, Math.min(deviceId.length(), 10)) + "_" + generateRandomNumber();
    }

    private String generateRandomNumber() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }



    public void showProceedButtonSelf() {
        proceedButtonSelf.setVisibility(View.VISIBLE);
    }
}