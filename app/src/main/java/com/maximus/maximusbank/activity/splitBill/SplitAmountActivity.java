package com.maximus.maximusbank.activity.splitBill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;
import com.maximus.maximusbank.adapter.SplitBillAdapter;
import com.maximus.maximusbank.Model.Contact;

import com.maximus.maximusbank.common.CommonBottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class SplitAmountActivity extends AppCompatActivity {

    private RecyclerView selectedContactsRecyclerView;
    private SplitBillAdapter selectedContactsAdapter;
    private List<Contact> selectedContacts = new ArrayList<>();
    private int totalBillAmount;
    TextView totalAmountTextView;
    Button sendRequestButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_amount);
        ImageView backPressImg = findViewById(R.id.backButton);
        Utils.onBackPress(this, backPressImg);
        TextView headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.split_bill);
        selectedContactsRecyclerView = findViewById(R.id.selectedContactsRecyclerView);
        sendRequestButton = findViewById(R.id.sendRequestButton);
        totalAmountTextView = findViewById(R.id.totalAmountTextView);

        selectedContacts = (List<Contact>) getIntent().getSerializableExtra("selectedContacts");
        totalBillAmount = getIntent().getIntExtra("totalBillAmount", 0);
        totalAmountTextView.setText("₹ "+totalBillAmount);
        selectedContactsAdapter = new SplitBillAdapter(SplitAmountActivity.this, selectedContacts, totalBillAmount);
        selectedContactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectedContactsRecyclerView.setAdapter(selectedContactsAdapter);

        sendRequestButton.setOnClickListener(v -> {
            String myString = "Request Sent Successfully";
            CommonBottomSheetDialogFragment bottomSheet = CommonBottomSheetDialogFragment.newInstance(myString, SplitAmountActivity.this);
            bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
    }
}