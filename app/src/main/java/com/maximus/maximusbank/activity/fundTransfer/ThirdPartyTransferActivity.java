package com.maximus.maximusbank.activity.fundTransfer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.adapter.StepAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThirdPartyTransferActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StepAdapter adapter;
    private Button proceedButton;
    private List<String> steps = Arrays.asList("Pay From", "Pay To", "Amount", "Remarks");
    private List<String> yourSpinnerData = Arrays.asList("Select from account no", "95551132115", "95512141151","9525454842"); // Add your spinner data
    TextView headerTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_party_transfer);

        headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.third_party);
        recyclerView = findViewById(R.id.step_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new StepAdapter(steps);
        recyclerView.setAdapter(adapter);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yourSpinnerData);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setSpinnerAdapter(spinnerAdapter);

        proceedButton = findViewById(R.id.proceedButton);
        proceedButton.setOnClickListener(v -> {
            Intent intent = new Intent(ThirdPartyTransferActivity.this, PreviewActivity.class);
            intent.putStringArrayListExtra("stepData", new ArrayList<>(adapter.getStepData()));
            startActivity(intent);
        });

    }

    public void showProceedButton() {
        proceedButton.setVisibility(View.VISIBLE);
    }

}