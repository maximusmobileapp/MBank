package com.maximus.maximusbank;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maximus.maximusbank.Utils.Utils;
import com.maximus.maximusbank.adapter.TransactionsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NestedScrollView scrollView;
    private LinearLayout rootView;
    private LinearLayout accountBalanceCard;
    private GridLayout gridLayout;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    private LinearLayout drawerContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the root view
        scrollView = new NestedScrollView(this);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        rootView = new LinearLayout(this);
        rootView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        rootView.setOrientation(LinearLayout.VERTICAL);

        // Set up the header
        setupHeader();

        // Set up the account balance card
        setupAccountBalanceCard();

        // Set up the grid layout for modules
        setupGridLayout();

        // Set up the recent transactions RecyclerView
        setupRecyclerView();

        // Add root view to scroll view
        scrollView.addView(rootView);

        // Set content view to DrawerLayout
        setContentView(drawerLayout);

        // Add scroll view to DrawerLayout
        drawerLayout.addView(scrollView);
    }

    private void setupHeader() {
        // DrawerLayout
        drawerLayout = new DrawerLayout(this);
        DrawerLayout.LayoutParams drawerLayoutParams = new DrawerLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        drawerLayout.setLayoutParams(drawerLayoutParams);

        // Navigation Drawer
        drawerContent = new LinearLayout(this);
        DrawerLayout.LayoutParams drawerListParams = new DrawerLayout.LayoutParams(
                600, // Set width for drawer
                ViewGroup.LayoutParams.MATCH_PARENT);
        drawerListParams.gravity = Gravity.LEFT;
        drawerContent.setLayoutParams(drawerListParams);
        drawerContent.setOrientation(LinearLayout.VERTICAL);
        drawerContent.setBackgroundColor(Color.WHITE);
        drawerLayout.addView(drawerContent);

        // Header layout
        LinearLayout headerLayout = new LinearLayout(this);
        headerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        headerLayout.setBackgroundColor(Color.parseColor("#1976D2"));
        headerLayout.setPadding(16, 16, 16, 16);
        headerLayout.setOrientation(LinearLayout.HORIZONTAL);
        headerLayout.setGravity(Gravity.CENTER_VERTICAL);

        // Drawer icon
        ImageView drawerIcon = new ImageView(this);
        drawerIcon.setImageResource(R.drawable.ic_drawer); // Replace with your drawer icon resource
        drawerIcon.setPadding(8, 8, 8, 8);
        drawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        // Header title
        TextView headerTitle = new TextView(this);
        headerTitle.setText("Banking App");
        headerTitle.setTextColor(Color.WHITE);
        headerTitle.setTextSize(24);
        headerTitle.setPadding(16, 0, 16, 0);

        // Notification icon
        ImageView notificationIcon = new ImageView(this);
        notificationIcon.setImageResource(R.drawable.ic_notification); // Replace with your notification icon resource
        notificationIcon.setPadding(8, 8, 8, 8);
        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open notification activity
                /*Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);*/
                Toast.makeText(MainActivity.this, "Notification icon clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // Add icons and title to header layout
        headerLayout.addView(drawerIcon);
        headerLayout.addView(headerTitle);
        headerLayout.addView(notificationIcon);

        // Add header layout to root view
        rootView.addView(headerLayout);

        // Setup drawer items
        setupDrawerItems();
    }

    private void setupAccountBalanceCard() {
        // Create the card layout
        CardView accountBalanceCard = new CardView(this);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(16, 16, 16, 0); // Set margins (left, top, right, bottom)
        accountBalanceCard.setLayoutParams(cardParams);
        accountBalanceCard.setRadius(16); // Set corner radius
        accountBalanceCard.setCardBackgroundColor(Color.parseColor("#2196F3")); // Set card background color
        accountBalanceCard.setCardElevation(8); // Set card elevation

        // Create a vertical LinearLayout for the card content
        LinearLayout cardContentLayout = new LinearLayout(this);
        cardContentLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        cardContentLayout.setOrientation(LinearLayout.VERTICAL);
        cardContentLayout.setPadding(32, 24, 32, 24); // Set padding (left, top, right, bottom)

        // Account Balance title TextView
        TextView accountBalanceTitle = new TextView(this);
        accountBalanceTitle.setText("Account Balance");
        accountBalanceTitle.setTextColor(Color.WHITE);
        accountBalanceTitle.setTextSize(20);
        accountBalanceTitle.setTypeface(null, Typeface.BOLD);
        accountBalanceTitle.setPadding(0, 0, 0, 16); // Add bottom padding

        // Example values for account number and balance (you can replace with actual values)
        String accountNumber = "1234 XXXX XXXX 5678";
        String accountBalance = "$10,000.00";

        // Account Number TextView
        TextView accountNumberTextView = new TextView(this);
        accountNumberTextView.setText("Account Number: " + accountNumber);
        accountNumberTextView.setTextColor(Color.WHITE);
        accountNumberTextView.setTextSize(16);
        accountNumberTextView.setPadding(0, 0, 0, 8); // Add bottom padding

        // Account Balance TextView
        TextView accountBalanceTextView = new TextView(this);
        accountBalanceTextView.setText("Balance: " + accountBalance);
        accountBalanceTextView.setTextColor(Color.WHITE);
        accountBalanceTextView.setTextSize(24);

        // ToggleButton to toggle between showing actual and masked values
        ToggleButton toggleButton = new ToggleButton(this);
        toggleButton.setTextOff("Show ****"); // Display "Show ****" when toggled off
        toggleButton.setTextOn("Show Actual"); // Display "Show Actual" when toggled on
        toggleButton.setChecked(false); // Start with masked values
        toggleButton.setTextColor(Color.WHITE); // Set text color

        // ToggleButton listener to switch between showing actual and masked values
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Show actual values
                    accountNumberTextView.setText("Account Number: " + accountNumber);
                    accountBalanceTextView.setText("Balance: " + accountBalance);
                } else {
                    // Show masked values (or alternative display)
                    accountNumberTextView.setText("Account Number: **** **** **** 5678");
                    accountBalanceTextView.setText("Balance: ****");
                }
            }
        });

        // Add views to the card content layout
        cardContentLayout.addView(accountBalanceTitle);
        cardContentLayout.addView(accountNumberTextView);
        cardContentLayout.addView(accountBalanceTextView);
        cardContentLayout.addView(toggleButton);

        // Add card content layout to the CardView
        accountBalanceCard.addView(cardContentLayout);

        // Add the CardView to the root layout
        rootView.addView(accountBalanceCard);
    }



    private void setupGridLayout() {
        gridLayout = new GridLayout(this);
        gridLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)); // Adjust height as needed
        int numberOfColumns = 3; // You can change this to 2 or any other number
        gridLayout.setColumnCount(numberOfColumns);
        gridLayout.setPadding(8, 8, 8, 8);

        // Get the screen width
        int screenWidth = getResources().getDisplayMetrics().widthPixels;

        // Calculate the available width for each CardView
        int totalPadding = gridLayout.getPaddingLeft() + gridLayout.getPaddingRight();
        int totalMargin = 8 * (numberOfColumns + 1); // Assuming 16dp margin between items
        int availableWidth = screenWidth - totalPadding - totalMargin;
        int cardWidth = availableWidth / numberOfColumns;

        try {
            JSONObject dashboardJson = Utils.loadJsonFromRaw(getResources(), R.raw.dashboard_json);
            JSONArray gridModules = dashboardJson.getJSONObject("dashboard").getJSONArray("grid_modules");

            for (int i = 0; i < gridModules.length(); i++) {
                JSONObject module = gridModules.getJSONObject(i);

                String title = module.getString("title");
                String icon = module.getString("icon");
                String background = module.getString("background");
                int id = module.getInt("id");

                // Inflate grid module item layout
                View gridItemView = LayoutInflater.from(this).inflate(R.layout.item_grid_module, null);

                // Set background color
                CardView cardView = gridItemView.findViewById(R.id.card_view);
                cardView.setCardBackgroundColor(Color.parseColor(background));

                // Bind views
                ImageView moduleIcon = gridItemView.findViewById(R.id.card_icon);
                TextView moduleTitle = gridItemView.findViewById(R.id.card_title);

                // Set icon and title
                moduleIcon.setImageResource(getResources().getIdentifier(icon, "drawable", getPackageName()));
                moduleTitle.setText(title);

                // Add grid item to GridLayout
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = cardWidth;
                params.height = 150;
                params.setMargins(8, 8, 8, 8);
                gridItemView.setLayoutParams(params);
                gridLayout.addView(gridItemView);

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (1 == id) {
                            startActivity(new Intent(MainActivity.this, FundTransferActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "" + title, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        TextView headerTitle = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(16, 0, 16, 0);
        headerTitle.setText("Quick Access");
        headerTitle.setTextColor(Color.BLACK);
        headerTitle.setTextSize(18);
        headerTitle.setLayoutParams(params);
        headerTitle.setPadding(4, 0, 4, 0);

        rootView.addView(headerTitle);
        rootView.addView(gridLayout);
    }

    private void setupRecyclerView() {
        recyclerView = new RecyclerView(this);
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Replace with actual data or adapter setup for transactions
        List<String> transactionList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            transactionList.add("Transaction " + (i + 1));
        }
        TransactionsAdapter adapter = new TransactionsAdapter(transactionList);
        recyclerView.setAdapter(adapter);

        TextView headerTitle = new TextView(this);
        headerTitle.setText("Recent Transaction");
        headerTitle.setTextColor(Color.BLACK);
        headerTitle.setTextSize(20);
        headerTitle.setPadding(16, 0, 16, 0);

        rootView.addView(headerTitle);
        rootView.addView(recyclerView);
    }

    private void setupDrawerItems() {
        try {
            JSONObject dashboardJson = Utils.loadJsonFromRaw(getResources(), R.raw.dashboard_json);
            JSONArray drawerItems = dashboardJson.getJSONObject("drawer").getJSONArray("items");

            for (int i = 0; i < drawerItems.length(); i++) {
                JSONObject item = drawerItems.getJSONObject(i);
                String title = item.getString("title");
                String icon = item.getString("icon");

                // Create CardView for each drawer item
                CardView drawerCardView = new CardView(this);
                LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                cardParams.setMargins(16, 16, 16, 0); // Set margins (left, top, right, bottom)
                drawerCardView.setLayoutParams(cardParams);
                drawerCardView.setRadius(8); // Set corner radius
                drawerCardView.setCardElevation(4); // Set card elevation

                // Create LinearLayout for card content
                LinearLayout drawerItemLayout = new LinearLayout(this);
                drawerItemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                drawerItemLayout.setOrientation(LinearLayout.HORIZONTAL);
                drawerItemLayout.setPadding(16, 16, 16, 16);
                drawerItemLayout.setGravity(Gravity.CENTER_VERTICAL);

                // Create ImageView for icon
                ImageView itemIcon = new ImageView(this);
                LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                iconParams.setMarginEnd(16); // Margin between icon and text
                itemIcon.setLayoutParams(iconParams);
                itemIcon.setImageResource(getResources().getIdentifier(icon, "drawable", getPackageName()));

                // Create TextView for title
                TextView itemTitle = new TextView(this);
                itemTitle.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                itemTitle.setText(title);
                itemTitle.setTextSize(16); // Adjust text size as needed

                // Add icon and title to drawer item layout
                drawerItemLayout.addView(itemIcon);
                drawerItemLayout.addView(itemTitle);

                // Add drawer item layout to CardView
                drawerCardView.addView(drawerItemLayout);

                // Set click listener for CardView


                // Add CardView to drawer content view
                drawerContent.addView(drawerCardView);

                drawerCardView.setOnClickListener(v -> {
                    // Handle click event (e.g., navigate to corresponding screen)
                    Log.d("itemTitle", title);
                    Toast.makeText(MainActivity.this, "Clicked: " + title, Toast.LENGTH_SHORT).show();
//                        drawerLayout.closeDrawer(GravityCompat.START); // Close drawer after item is clicked
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    private JSONObject loadJsonFromRaw(int resId) throws JSONException {
        try {
            InputStream inputStream = getResources().openRawResource(resId);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            return new JSONObject(new String(buffer, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
