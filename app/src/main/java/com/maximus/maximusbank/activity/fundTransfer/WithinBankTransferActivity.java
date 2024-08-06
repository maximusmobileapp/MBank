package com.maximus.maximusbank.activity.fundTransfer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;
import com.maximus.maximusbank.adapter.WithinBankAdapter;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WithinBankTransferActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WithinBankAdapter adapter;
    private Button proceedButton;
    private List<String> steps = Arrays.asList("Pay From", "Pay To", "Amount", "Remarks");
    private List<String> yourSpinnerData = Arrays.asList("Select from account no", "95551132115", "95512141151", "9525454842"); // Add your spinner data
    TextView headerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_party_transfer);

        headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.third_party);
        recyclerView = findViewById(R.id.step_recycler_view);
        ImageView backPressImg = findViewById(R.id.backButton);
        Utils.onBackPress(this, backPressImg);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WithinBankAdapter(steps);
        recyclerView.setAdapter(adapter);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yourSpinnerData);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setSpinnerAdapter(spinnerAdapter);

        proceedButton = findViewById(R.id.proceedButton);
        proceedButton.setOnClickListener(v -> {
            Map<String, String> stepDataMap = new HashMap<>();
            stepDataMap.put("fromAccount", adapter.getStepData().get(0));
            stepDataMap.put("payTo", adapter.getStepData().get(1));
            stepDataMap.put("amount", adapter.getStepData().get(2));


            Intent intent = new Intent(WithinBankTransferActivity.this, FT_Confirmation.class);
            intent.putExtra("stepData", new HashMap<>(stepDataMap));
            intent.putExtra("refNo", generateDeviceReferenceNo());
            intent.putExtra("paymentTime", getCurrentDateTime());
            //intent.putExtra("fromAccount","From");
            intent.putExtra("bankType", "Within Bank");
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
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

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void showProceedButton() {
        proceedButton.setVisibility(View.VISIBLE);
    }

}