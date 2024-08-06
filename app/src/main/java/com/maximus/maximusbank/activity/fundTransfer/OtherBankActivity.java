package com.maximus.maximusbank.activity.fundTransfer;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;
import com.maximus.maximusbank.adapter.OtherbkStepAdapter;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OtherBankActivity extends AppCompatActivity {
    TextView headerTextView;

    private RecyclerView step_recycler_view_other;
    private OtherbkStepAdapter adapterotherbk;
    private Button proceedButtonOther;
    private List<String> steps = Arrays.asList("Pay From", "Pay To", "Bank Name", "Account No", "IFSC", "Amount", "Remarks");
    private List<String> SpinnerDataOther = Arrays.asList("Select from account no", "95551132115", "95512141151", "9525454842");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_bank);

        headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.other_bank);
        ImageView backPressImg = findViewById(R.id.backButton);
        Utils.onBackPress(this, backPressImg);
        step_recycler_view_other = findViewById(R.id.step_recycler_view_other);

        step_recycler_view_other.setLayoutManager(new LinearLayoutManager(this));
        adapterotherbk = new OtherbkStepAdapter(steps);
        step_recycler_view_other.setAdapter(adapterotherbk);


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SpinnerDataOther);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterotherbk.setSpinnerAdapter(spinnerAdapter);

        proceedButtonOther = findViewById(R.id.proceedButtonOther);
        proceedButtonOther.setOnClickListener(v -> {
            Map<String, String> stepDataMap = new HashMap<>();
            stepDataMap.put("fromAccount", adapterotherbk.getStepData().get(0));
            stepDataMap.put("payTo", adapterotherbk.getStepData().get(1));
            stepDataMap.put("amount", adapterotherbk.getStepData().get(5));

            Intent intent = new Intent(OtherBankActivity.this, FT_Confirmation.class);
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

    public void showProceedButtonOther() {
        proceedButtonOther.setVisibility(View.VISIBLE);
    }
}