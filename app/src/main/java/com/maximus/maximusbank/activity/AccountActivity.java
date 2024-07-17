package com.maximus.maximusbank.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.adapter.ViewPagerAdapter;

public class AccountActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TextView headerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        headerTextView = findViewById(R.id.headerTextView);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Accounts");
                    break;
                case 1:
                    tab.setText("Deposit");
                    break;
                case 2:
                    tab.setText("Loans");
                    break;
            }
        }).attach();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateHeaderText(position);
            }
        });

    }

    private void updateHeaderText(int position) {
        switch (position) {
            case 0:
                headerTextView.setText("Accounts");
                break;
            case 1:
                headerTextView.setText("Deposit");
                break;
            case 2:
                headerTextView.setText("Loans");
                break;
        }
    }
}