package com.maximus.maximusbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.ExpandableHeightGridView;
import com.maximus.maximusbank.activity.cardcontrol.CardControlActivity;
import com.maximus.maximusbank.activity.fundTransfer.FundTransferDashboard;
import com.maximus.maximusbank.activity.fundTransfer.ThirdPartyTransferActivity;
import com.maximus.maximusbank.adapter.GridAdapter;
import com.maximus.maximusbank.adapter.RecentTransactionAdapter;
import com.maximus.maximusbank.model.GridModel;
import com.maximus.maximusbank.model.RecentTransactionModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private TextView txtAccountNumber;
    private TextView txtAccountBalance;
    private ImageView toggleAccountNumber, toggleAccountBalance;
    private boolean isAccountNumberVisible = false;
    private boolean isAccountBalanceVisible = false;
    ExpandableHeightGridView gridView;
    RecyclerView recyclerView;
    List<RecentTransactionModel> transactionList;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        init();

        nevigationSetupUp();

        gridViewSetup();

        setupRecyclerView();

        toggleAccountNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(txtAccountNumber, isAccountNumberVisible, toggleAccountNumber);
                isAccountNumberVisible = !isAccountNumberVisible;
            }
        });

        toggleAccountBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(txtAccountBalance, isAccountBalanceVisible, toggleAccountBalance);
                isAccountBalanceVisible = !isAccountBalanceVisible;
            }
        });
    }

    private void nevigationSetupUp() {
        topAppBar.setNavigationOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
       navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_home) {
                    Toast.makeText(DashboardActivity.this, "Home", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.item_accounts) {
                    Toast.makeText(DashboardActivity.this, "Account", Toast.LENGTH_SHORT).show();
                }else if (item.getItemId() == R.id.item_recent_transactions) {
                    Toast.makeText(DashboardActivity.this, "Recent transaction", Toast.LENGTH_SHORT).show();
                }else if (item.getItemId() == R.id.item_third_party_transfer) {
                    Toast.makeText(DashboardActivity.this, "Third party transfer", Toast.LENGTH_SHORT).show();
                }else if (item.getItemId() == R.id.item_beneficiaries) {
                    Toast.makeText(DashboardActivity.this, "Beneficiary", Toast.LENGTH_SHORT).show();
                }else if (item.getItemId() == R.id.item_settings) {
                    Toast.makeText(DashboardActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                }else if (item.getItemId() == R.id.item_about_us) {
                    Toast.makeText(DashboardActivity.this, "About Us", Toast.LENGTH_SHORT).show();
                }else if (item.getItemId() == R.id.item_help) {
                    Toast.makeText(DashboardActivity.this, "Help", Toast.LENGTH_SHORT).show();
                }else if (item.getItemId() == R.id.item_share) {
                    Toast.makeText(DashboardActivity.this, "Share", Toast.LENGTH_SHORT).show();
                }else if (item.getItemId() == R.id.item_app_info) {
                    Toast.makeText(DashboardActivity.this, "App Info", Toast.LENGTH_SHORT).show();
                }else if (item.getItemId() == R.id.item_logout){
                    Toast.makeText(DashboardActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void init(){
        drawerLayout = findViewById(R.id.drawer_layout);
        topAppBar = findViewById(R.id.topAppBar);
        gridView = findViewById(R.id.idGVcourses);
        recyclerView = findViewById(R.id.recyclerView);
        txtAccountNumber = findViewById(R.id.txtAccountNumber);
        txtAccountBalance = findViewById(R.id.txtAccountBalance);
        toggleAccountNumber = findViewById(R.id.toggleAccountNumber);
        toggleAccountBalance = findViewById(R.id.toggleAccountBalance);
    }

    private void gridViewSetup() {
        ArrayList<GridModel> gridModelArrayList = new ArrayList<GridModel>();
        gridModelArrayList.add(new GridModel("Accounts", R.drawable.ic_open_account));
        gridModelArrayList.add(new GridModel("Fund Transfer", R.drawable.ic_fund_transfer));
        gridModelArrayList.add(new GridModel("Card Control", R.drawable.ic_card_control));
        gridModelArrayList.add(new GridModel("Recent Transaction", R.drawable.ic_recent_transactions));
        gridModelArrayList.add(new GridModel("Loan", R.drawable.ic_loan));
        gridModelArrayList.add(new GridModel("Bill Payments", R.drawable.ic_bill_payments));
        gridModelArrayList.add(new GridModel("Add Beneficiary", R.drawable.ic_beneficiaries_48px));
        gridModelArrayList.add(new GridModel("Request", R.drawable.ic_request));

        GridAdapter adapter = new GridAdapter(this, gridModelArrayList);
        gridView.setAdapter(adapter);
        gridView.setExpanded(true);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            GridModel selectedItem = (GridModel) parent.getItemAtPosition(position);
            handleGridItemClick(selectedItem);
        });
    }

    private void handleGridItemClick(GridModel selectedItem) {
        switch (selectedItem.grid_name) {
            case "Accounts":
                startActivity(new Intent(this, AccountActivity.class));
                break;
            case "Fund Transfer":
                startActivity(new Intent(this, FundTransferDashboard.class));
                break;
            case "Card Control":
                startActivity(new Intent(this, CardControlActivity.class));
                // Navigate to Card Control activity or perform desired action
                break;
            case "Recent Transaction":
                Toast.makeText(this, "Recent Transaction Clicked", Toast.LENGTH_SHORT).show();
                // Navigate to Recent Transaction activity or perform desired action
                break;
            case "Loan":
                Toast.makeText(this, "Loan Clicked", Toast.LENGTH_SHORT).show();
                // Navigate to Loan activity or perform desired action
                break;
            case "Bill Payments":
                Toast.makeText(this, "Bill Payments Clicked", Toast.LENGTH_SHORT).show();
                // Navigate to Bill Payments activity or perform desired action
                break;
            case "Add Beneficiary":
                Toast.makeText(this, "Add Beneficiary Clicked", Toast.LENGTH_SHORT).show();
                // Navigate to Add Beneficiary activity or perform desired action
                break;
            case "Request":
                Toast.makeText(this, "Request Clicked", Toast.LENGTH_SHORT).show();
                // Navigate to Request activity or perform desired action
                break;
            default:
                Toast.makeText(this, "Unknown Item Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void setupRecyclerView() {
        transactionList = new ArrayList<>();
        transactionList.add(new RecentTransactionModel("Kiran Patole", "14 July 2024 at 12:27 pm", "Rs. 750"));
        transactionList.add(new RecentTransactionModel("Suraj Pable", "14 July 2024 at 1:00 pm", "Rs. 1500"));
        transactionList.add(new RecentTransactionModel("Nidhi Tripathi", "14 July 2024 at 1:00 pm", "Rs. 1500"));
        // Add more transactions as needed

        RecentTransactionAdapter adapter = new RecentTransactionAdapter(transactionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                adjustRecyclerViewPadding(dy);
            }
        });
    }

    private void adjustRecyclerViewPadding(int dy) {
        if (dy > 0) {
            // Scrolling down
            recyclerView.setPadding(0, 0, 0, 0);
        } else {
            // Scrolling up
            recyclerView.setPadding(0, 0, 0, (int) getResources().getDimension(R.dimen.fab_margin));
        }
    }

    private void toggleVisibility(TextView textView, boolean isVisible, ImageView imageView) {
        if (isVisible) {
            textView.setTransformationMethod(PasswordTransformationMethod.getInstance());
            imageView.setImageResource(R.drawable.ic_eye_off);
        } else {
            textView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            imageView.setImageResource(R.drawable.baseline_visibility_24);
        }
    }
}