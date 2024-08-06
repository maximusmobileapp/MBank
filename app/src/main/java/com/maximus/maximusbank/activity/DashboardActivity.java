package com.maximus.maximusbank.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.maximus.maximusbank.Model.GridModel;
import com.maximus.maximusbank.Model.RecentTransactionModel;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.TransactionHistory;
import com.maximus.maximusbank.Utils.ExpandableHeightGridView;
import com.maximus.maximusbank.Utils.Utils;
import com.maximus.maximusbank.activity.ChequeBook.ChequeBookRequest;
import com.maximus.maximusbank.activity.account.AccountActivity;
import com.maximus.maximusbank.activity.beneficiary.AddBeneficiaryActivity;
import com.maximus.maximusbank.activity.cardcontrol.CardControlActivity;
import com.maximus.maximusbank.activity.fundTransfer.FundTransferDashboard;
import com.maximus.maximusbank.activity.landing.PasscodeActivity;
import com.maximus.maximusbank.activity.requestmoney.RequestMoney;
import com.maximus.maximusbank.activity.scanNpay.ScannerActivity;
import com.maximus.maximusbank.activity.share.ShareQR;
import com.maximus.maximusbank.activity.sharedPref.PreferenceManager;
import com.maximus.maximusbank.activity.splitBill.SpiltBillAmount;
import com.maximus.maximusbank.adapter.GridAdapter;
import com.maximus.maximusbank.adapter.RecentTransactionAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    private TextView txtAccountNumber;
    private TextView txtAccountBalance,txtSeeAll;
    private ImageView toggleAccountNumber, toggleAccountBalance;
    private boolean isAccountNumberVisible = false;
    private boolean isAccountBalanceVisible = false;
    ExpandableHeightGridView gridView;
    RecyclerView recyclerView;
    List<RecentTransactionModel> transactionList;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar topAppBar;
    PreferenceManager preferenceManager;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        preferenceManager = new PreferenceManager(this);
        String language = preferenceManager.getValue("language", "en");
        setLocale(language);
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

         bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.mHome) {
                    if (!(DashboardActivity.this instanceof DashboardActivity)) {
                        startActivity(new Intent(DashboardActivity.this, DashboardActivity.class));
                        finish(); // Optional: finish current activity to prevent stack buildup
                    }
                    return true;
                } else if (itemId == R.id.mplaceholder) {
                    startActivity(new Intent(DashboardActivity.this, ScannerActivity.class));
                    return true;
                } else if (itemId == R.id.mSetting) {
                    startActivity(new Intent(DashboardActivity.this, SettingActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.mHome);
        Utils.setLocale(this);
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        preferenceManager.setValue("language", lang);

    }

    private void nevigationSetupUp() {
        topAppBar.setNavigationOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.scanpay) {
                    startActivity(new Intent(DashboardActivity.this, ScannerActivity.class));
                } else if (item.getItemId() == R.id.item_home) {
                    Toast.makeText(DashboardActivity.this, R.string.home, Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.item_accounts) {
                    startActivity(new Intent(DashboardActivity.this, AccountActivity.class));
                } else if (item.getItemId() == R.id.item_recent_transactions) {
                    startActivity(new Intent(DashboardActivity.this, TransactionHistory.class));
                } else if (item.getItemId() == R.id.item_third_party_transfer) {
                    startActivity(new Intent(DashboardActivity.this, FundTransferDashboard.class));
                } else if (item.getItemId() == R.id.item_beneficiaries) {
                    startActivity(new Intent(DashboardActivity.this, AddBeneficiaryActivity.class));
                } else if (item.getItemId() == R.id.item_settings) {
                    startActivity(new Intent(DashboardActivity.this, SettingActivity.class));
                } else if (item.getItemId() == R.id.item_about_us) {
                    Toast.makeText(DashboardActivity.this, R.string.about_us, Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.item_help) {
                    Toast.makeText(DashboardActivity.this, R.string.help, Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.item_share) {
                    startActivity(new Intent(DashboardActivity.this, ShareQR.class));
                } else if (item.getItemId() == R.id.item_app_info) {
                    Toast.makeText(DashboardActivity.this, R.string.app_info, Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.item_logout) {
                    startActivity(new Intent(DashboardActivity.this, PasscodeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void init() {
        drawerLayout = findViewById(R.id.drawer_layout);
        txtSeeAll = findViewById(R.id.txtSeeAll);
        topAppBar = findViewById(R.id.topAppBar);
        gridView = findViewById(R.id.idGVcourses);
        recyclerView = findViewById(R.id.recyclerView);
        txtAccountNumber = findViewById(R.id.txtAccountNumber);
        txtAccountBalance = findViewById(R.id.txtAccountBalance);
        toggleAccountNumber = findViewById(R.id.toggleAccountNumber);
        toggleAccountBalance = findViewById(R.id.toggleAccountBalance);
        txtSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, TransactionHistory.class));
            }
        });
    }

    private void gridViewSetup() {
        ArrayList<GridModel> gridModelArrayList = new ArrayList<>();
        gridModelArrayList.add(new GridModel(R.string.accounts, R.drawable.ic_open_account));
        gridModelArrayList.add(new GridModel(R.string.fund_transfer, R.drawable.ic_fund_transfer));
        gridModelArrayList.add(new GridModel(R.string.card_control, R.drawable.ic_card_control));
        gridModelArrayList.add(new GridModel(R.string.recent_transaction, R.drawable.ic_recent_transactions));
        gridModelArrayList.add(new GridModel(R.string.split_bill, R.drawable.ic_loan));
        gridModelArrayList.add(new GridModel(R.string.cheque_book, R.drawable.checkdeposit));
//        gridModelArrayList.add(new GridModel("Cheque Deposit",R.drawable.checkdeposit));
        gridModelArrayList.add(new GridModel(R.string.add_benef, R.drawable.ic_beneficiaries_48px));
        gridModelArrayList.add(new GridModel(R.string.request, R.drawable.ic_request));

        GridAdapter adapter = new GridAdapter(this, gridModelArrayList);
        gridView.setAdapter(adapter);
        gridView.setExpanded(true);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            GridModel selectedItem = (GridModel) parent.getItemAtPosition(position);
            handleGridItemClick(position);
        });
    }

    @Override
    public void onBackPressed() {
        showLogoutDialog();
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(DashboardActivity.this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DashboardActivity.this, PasscodeActivity.class);
                        startActivity(intent);
                        Toast.makeText(DashboardActivity.this, "Logging out...", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void handleGridItemClick(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, AccountActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, FundTransferDashboard.class));
                break;
            case 2:
                startActivity(new Intent(this, CardControlActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, TransactionHistory.class));
                break;
            case 4:
                startActivity(new Intent(this, SpiltBillAmount.class));
                break;
            case 5:
                startActivity(new Intent(this, ChequeBookRequest.class));
                break;
            case 6:
                startActivity(new Intent(this, AddBeneficiaryActivity.class));
                break;
            case 7:
                startActivity(new Intent(this, RequestMoney.class));
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