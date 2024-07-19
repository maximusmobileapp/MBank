package com.maximus.maximusbank;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MPIN extends AppCompatActivity {
    public Button buttonlogin;
    private EditText mpin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpin);

        RelativeLayout rootLayout = new RelativeLayout(this);
        rootLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        rootLayout.setBackgroundColor(Color.WHITE);
        rootLayout.setPadding(32, 32, 32, 32);

        LinearLayout centeredLayout = new LinearLayout(this);
        RelativeLayout.LayoutParams centeredLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        centeredLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        centeredLayout.setLayoutParams(centeredLayoutParams);
        centeredLayout.setOrientation(LinearLayout.VERTICAL);
        centeredLayout.setGravity(Gravity.CENTER);
        centeredLayout.setBackgroundColor(Color.WHITE);

        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        cardView.setPadding(8, 8, 8, 8);
        cardViewParams.setMargins(8, 8, 8, 8);
        cardView.setLayoutParams(cardViewParams);
        cardView.setCardBackgroundColor(Color.WHITE);
        cardView.setCardElevation(16.0f);
        cardView.setRadius(24.0f);
        centeredLayout.addView(cardView);

        LinearLayout innerLayout = new LinearLayout(this);
        innerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        innerLayout.setOrientation(LinearLayout.VERTICAL);
        innerLayout.setGravity(Gravity.CENTER);
        cardView.addView(innerLayout);

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.mpin);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonStr = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject screen = jsonObject.getJSONObject("screen");
            JSONArray components = screen.getJSONArray("components");

            List<View> viewsToFocus = new ArrayList<>();
            for (int i = 0; i < components.length(); i++) {
                JSONObject component = components.getJSONObject(i);

                String type = component.getString("type");
                if ("text".equals(type)) {
                    TextView textView = new TextView(this);
                    textView.setText(component.getString("value"));
                    textView.setTextColor(Color.parseColor(component.getString("textColor")));
                    textView.setGravity(Gravity.CENTER);
                    if ("header".equals(component.getString("style"))) {
                        textView.setTextSize(24);
                    }
                    innerLayout.addView(textView);
                }
                else if ("text_New".equals(type)){
                    TextView text_New = new TextView(this);
                    text_New.setText(component.getString("value"));
                    text_New.setPadding(30,30,30,30);
                    text_New.setTextColor(Color.parseColor(component.getString("textColor")));
                    innerLayout.addView(text_New);
                }
                else if ("edit_MPIN".equals(type)) {
                    EditText edit_New = new EditText(this);
                    edit_New.setHint(component.getString("hint"));
                    edit_New.setTextColor(Color.parseColor(component.getString("textColor")));
                    InputFilter[] filters = new InputFilter[] { new InputFilter.LengthFilter(component.getInt("maxlength")) };
                    edit_New.setFilters(filters);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(30, 0, 30, 0);
                    edit_New.setLayoutParams(params);
                    edit_New.setInputType(getInputType(component.getString("input_type")));
                    applyBorder(edit_New);
                    innerLayout.addView(edit_New);
                    viewsToFocus.add(edit_New);

                    if ("Enter MPIN".equals(component.getString("hint"))) {
                        mpin = edit_New;
                    }
                }
                else if ("buttonlogin".equals(type)){
//                    View buttonView = createButton(component);
                    Button button = new Button(this);
                    button.setPadding(20,20,20,30);
                    LinearLayout.LayoutParams paramss = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    paramss.setMargins(50, 50, 50, 50);
                    button.setLayoutParams(paramss);
                    button.setText(component.getString("text"));
                    button.setTextColor(Color.parseColor(component.getString("textColor")));
                    button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    button.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.rounded_button_background));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String newPin = mpin.getText().toString();
                            Intent intent = getIntent();
                            String previousPin = intent.getStringExtra("PIN");
                            if (!previousPin.equals(newPin)) {
                                mpin.setError("Enter Valid Pin");
                            } else {
                                if (validateFields()) {
                                    startActivity(new Intent(MPIN.this, MainActivity.class));
                                } else {
                                    Toast.makeText(MPIN.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                    innerLayout.addView(button);
                }
                else if ("forget_text".equals(type)) {
                    TextView forgettext = new TextView(this);
                    forgettext.setText(component.getString("value"));
                    forgettext.setTextSize(component.getInt("textSize"));
                    forgettext.setPadding(30,30,30,30);
                    forgettext.setTextColor(Color.parseColor(component.getString("textColor")));
                    forgettext.setGravity(Gravity.CENTER);
                    forgettext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MPIN.this, ForgetPassword.class);
                            startActivity(intent);
                        }
                    });
                    innerLayout.addView(forgettext);
                }
            }

            rootLayout.addView(centeredLayout);

            // Footer layout
            RelativeLayout footerLayout = new RelativeLayout(this);
            RelativeLayout.LayoutParams footerLayoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            footerLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            footerLayout.setLayoutParams(footerLayoutParams);

            BottomNavigationView bottomNavigationView = new BottomNavigationView(this);
            bottomNavigationView.inflateMenu(R.menu.menu_navigation);
            bottomNavigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));
            bottomNavigationView.setBackgroundColor(Color.WHITE);
            footerLayout.addView(bottomNavigationView);

            rootLayout.addView(footerLayout);

            //This will show the complete view of page
            setContentView(rootLayout);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private View createButton(JSONObject component) throws JSONException {
        ShimmerFrameLayout shimmerFrameLayout = new ShimmerFrameLayout(this);
        LinearLayout.LayoutParams shimmerParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        shimmerParams.setMargins(0, 16, 0, 16);
        shimmerFrameLayout.setLayoutParams(shimmerParams);

        buttonlogin = new Button(this);
        buttonlogin.setText(component.getString("text"));
        buttonlogin.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        buttonlogin.setBackground(getResources().getDrawable(R.drawable.rounded_button_bg));

        shimmerFrameLayout.addView(buttonlogin);
        Shimmer shimmer = new Shimmer.AlphaHighlightBuilder()
                .setDuration(1500) // Duration of shimmer animation
                .setBaseAlpha(0.7f) // Alpha of the base color
                .setHighlightAlpha(1f) // Alpha of the highlight color
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT) // Animation direction
                .build();
        shimmerFrameLayout.setShimmer(shimmer);
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPin = mpin.getText().toString();
                Intent intent = getIntent();
                String previousPin = intent.getStringExtra("PIN");
                if (!previousPin.equals(newPin)) {
                    mpin.setError("Enter Valid Pin");
                } else {
                    if (validateFields()) {
                        startActivity(new Intent(MPIN.this, MainActivity.class));
                    } else {
                        Toast.makeText(MPIN.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return shimmerFrameLayout;
    }

    private boolean validateFields() {
        boolean isValid = true;
        if (mpin == null || mpin.getText().toString().trim().isEmpty()) {
            mpin.setError("Enter valid mpin");
            isValid = false;
        }
        return isValid;
    }

    private void applyBorder(View view) throws JSONException {
        view.setBackgroundResource(R.drawable.edit_text_border);
        view.setPadding(16, 16, 16, 16);
    }

    private int getInputType(String inputType) {
        switch (inputType) {
            case "textMPIN":
                return InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD;
            default:
                return InputType.TYPE_TEXT_VARIATION_PASSWORD;
        }
    }
}