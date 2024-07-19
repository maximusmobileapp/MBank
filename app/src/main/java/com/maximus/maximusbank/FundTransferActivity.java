package com.maximus.maximusbank;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.maximus.maximusbank.Utils.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FundTransferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the root LinearLayout for the activity
        LinearLayout rootView = new LinearLayout(this);
        rootView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.setGravity(Gravity.CENTER);
        rootView.setBackgroundColor(Color.WHITE); // Set background color if needed

        try {
            // Load JSON configuration
            JSONObject fundTransferJson = Utils.loadJsonFromRaw(getResources(),R.raw.fund_transfer);

            // Set activity title
            String activityTitle = fundTransferJson.getString("activity_title");
            setTitle(activityTitle);

            // Retrieve cards array
            JSONArray cardsArray = fundTransferJson.getJSONArray("cards");

            // Iterate through each card configuration
            for (int i = 0; i < cardsArray.length(); i++) {
                JSONObject cardObject = cardsArray.getJSONObject(i);

                // Extract card details
                String type = cardObject.getString("type");
                String title = cardObject.getString("title");
                String icon = cardObject.getString("icon");
                String bgColor = cardObject.getString("background_color");
                String action = cardObject.getString("action");

                // Create CardView for each fund transfer option
                CardView cardView = new CardView(this);
                LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                cardParams.setMargins(16, 16, 16, 0); // Set margins
                cardView.setLayoutParams(cardParams);
                cardView.setRadius(8); // Set corner radius
                cardView.setCardBackgroundColor(Color.parseColor(bgColor)); // Set background color
                cardView.setCardElevation(4); // Set card elevation

                // Create TextView for card title
                TextView textView = new TextView(this);
                LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(textParams);
                textView.setText(title);
                textView.setTextSize(18);
                textView.setTextColor(Color.WHITE);
                textView.setPadding(32, 32, 32, 32); // Set padding
                textView.setGravity(Gravity.CENTER);

                cardView.addView(textView);

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle click event
                        Toast.makeText(FundTransferActivity.this, "Clicked: " + title, Toast.LENGTH_SHORT).show();
                        handleFundTransferAction(action);
                    }
                });

                // Add CardView to the root layout
                rootView.addView(cardView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Set the root view for the activity
        setContentView(rootView);
    }

    private void handleFundTransferAction(String action) {
        switch (action) {
            case "initiate_within_bank_transfer":
                break;
            case "initiate_other_bank_transfer":
                // Handle other-bank transfer action
                break;
            default:
                break;
        }
    }

}

