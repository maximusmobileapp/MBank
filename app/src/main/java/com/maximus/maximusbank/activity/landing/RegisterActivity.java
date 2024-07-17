package com.maximus.maximusbank.activity.landing;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.maximus.maximusbank.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    public Button buttonregister;
    private EditText namet,mobilet,emailet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        LinearLayout rootlayout = new LinearLayout(this);
        rootlayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        rootlayout.setOrientation(LinearLayout.VERTICAL);
        rootlayout.setGravity(Gravity.CENTER);
        rootlayout.setBackgroundColor(Color.WHITE);
        rootlayout.setPadding(32, 32, 32, 32);

        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardView.setPadding(8, 8, 8, 8);
        cardViewParams.setMargins(8, 8, 8, 8);
        cardView.setLayoutParams(cardViewParams);
        cardView.setCardBackgroundColor(Color.WHITE);
        cardView.setCardElevation(16.0f);
        cardView.setRadius(24.0f);
        rootlayout.addView(cardView);

        LinearLayout innerLayout = new LinearLayout(this);
        innerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        innerLayout.setOrientation(LinearLayout.VERTICAL);
        innerLayout.setGravity(Gravity.CENTER);
        cardView.addView(innerLayout);

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.register);
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
                else if ("text_name".equals(type)){
                    TextView text_name = new TextView(this);
                    text_name.setText(component.getString("value"));
                    text_name.setPadding(30,30,30,30);
                    text_name.setTextColor(Color.parseColor(component.getString("textColor")));
                    innerLayout.addView(text_name);
                }
                else if ("text_mobile".equals(type)){
                    TextView text_mobile = new TextView(this);
                    text_mobile.setText(component.getString("value"));
                    text_mobile.setPadding(30,30,30,30);
                    text_mobile.setTextColor(Color.parseColor(component.getString("textColor")));
                    innerLayout.addView(text_mobile);
                }

                else if ("text_email".equals(type)){
                    TextView text_email = new TextView(this);
                    text_email.setText(component.getString("value"));
                    text_email.setPadding(30,30,30,30);
                    text_email.setTextColor(Color.parseColor(component.getString("textColor")));
                    innerLayout.addView(text_email);
                }

               /* else if ("text_pass".equals(type)){
                    TextView text_pass = new TextView(this);
                    text_pass.setText(component.getString("value"));
                    text_pass.setPadding(30,30,30,30);
                    text_pass.setTextColor(Color.parseColor(component.getString("textColor")));
                    innerLayout.addView(text_pass);
                }*/

               /* else if ("text_confirmpass".equals(type)) {
                    TextView text_confirmpass = new TextView(this);
                    text_confirmpass.setText(component.getString("value"));
                    text_confirmpass.setPadding(30,30,30,30);
                    text_confirmpass.setTextColor(Color.parseColor(component.getString("textColor")));
                    innerLayout.addView(text_confirmpass);
                }*/

                else if ("edit_text".equals(type)) {
                    EditText editText = new EditText(this);
                    editText.setHint(component.getString("hint"));
                    editText.setTextColor(Color.parseColor(component.getString("textColor")));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(30, 0, 30, 0);
                    editText.setLayoutParams(params);
                    editText.setInputType(getInputType(component.getString("input_type")));
                    applyBorder(editText);
                    innerLayout.addView(editText);
                    viewsToFocus.add(editText);

                    if ("Enter Name".equals(component.getString("hint"))) {
                        namet = editText;
                    } else if ("Enter Mobile No.".equals(component.getString("hint"))) {
                        mobilet = editText;
                    } else if ("Enter Email".equals(component.getString("hint"))) {
                        emailet = editText;
                    }
                }
/*
                else if ("editpass".equals(type)) {
                    EditText editText = new EditText(this);
                    editText.setHint(component.getString("hint"));
                    editText.setTextColor(Color.parseColor(component.getString("textColor")));
                    InputFilter[] filters = new InputFilter[] { new InputFilter.LengthFilter(component.getInt("max_length")) };
                    editText.setFilters(filters);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(30, 0, 30, 0);
                    editText.setLayoutParams(params);
                    editText.setInputType(getInputType(component.getString("input_type")));
                    applyBorder(editText);
                    innerLayout.addView(editText);
                    viewsToFocus.add(editText);

                    if ("Enter Name".equals(component.getString("hint"))) {
                        namet = editText;
                    } else if ("Enter Mobile No.".equals(component.getString("hint"))) {
                        mobilet = editText;
                    } else if ("Enter Email".equals(component.getString("hint"))) {
                        emailet = editText;
                    } else if ("Enter Password".equals(component.getString("hint"))) {
                        passet = editText;
                    } else if ("Enter Confirm Password".equals(component.getString("hint"))) {
                        confirmpasset = editText;
                    }
                }
*/

                else if ("editmobile".equals(type)) {
                    EditText editText = new EditText(this);
                    editText.setHint(component.getString("hint"));
                    editText.setTextColor(Color.parseColor(component.getString("textColor")));
                    InputFilter[] filters = new InputFilter[] { new InputFilter.LengthFilter(component.getInt("max_length")) };
                    editText.setFilters(filters);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(30, 0, 30, 0);
                    editText.setLayoutParams(params);
                    editText.setInputType(getInputType(component.getString("input_type")));
                    applyBorder(editText);
                    innerLayout.addView(editText);
                    viewsToFocus.add(editText);

                    if ("Enter Name".equals(component.getString("hint"))) {
                        namet = editText;
                    } else if ("Enter Mobile No.".equals(component.getString("hint"))) {
                        mobilet = editText;
                    } else if ("Enter Email".equals(component.getString("hint"))) {
                        emailet = editText;
                    }
                }

/*
                else if ("editconf".equals(type)) {
                    EditText editText = new EditText(this);
                    editText.setHint(component.getString("hint"));
                    editText.setTextColor(Color.parseColor(component.getString("textColor")));
                    InputFilter[] filters = new InputFilter[] { new InputFilter.LengthFilter(component.getInt("max_length")) };
                    editText.setFilters(filters);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(30, 0, 30, 0);
                    editText.setLayoutParams(params);
                    editText.setInputType(getInputType(component.getString("input_type")));
                    applyBorder(editText);
                    innerLayout.addView(editText);
                    viewsToFocus.add(editText);

                    if ("Enter Name".equals(component.getString("hint"))) {
                        namet = editText;
                    } else if ("Enter Mobile No.".equals(component.getString("hint"))) {
                        mobilet = editText;
                    } else if ("Enter Email".equals(component.getString("hint"))) {
                        emailet = editText;
                    } else if ("Enter Password".equals(component.getString("hint"))) {
                        passet = editText;
                    } else if ("Enter Confirm Password".equals(component.getString("hint"))) {
                        confirmpasset = editText;
                    }
                }
*/

                else if ("buttonregister".equals(type)){
                    View buttonView = createButton(component);
                    buttonView.setPadding(20,20,20,30);
                    innerLayout.addView(buttonView);
                }

                else if ("login_text".equals(type)){
                    TextView logintextView = new TextView(this);
                    logintextView.setText(component.getString("value"));
                    logintextView.setTextSize(component.getInt("textSize"));
                    logintextView.setPadding(30,30,30,30);
                    logintextView.setTextColor(Color.parseColor(component.getString("textColor")));
                    logintextView.setGravity(Gravity.CENTER);
                    logintextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(RegisterActivity.this, "Login Page", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    innerLayout.addView(logintextView);
                }
            }
            //This will show the complete view of page
            setContentView(rootlayout);

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

        buttonregister = new Button(this);
        buttonregister.setText(component.getString("text"));
        buttonregister.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        buttonregister.setBackground(getResources().getDrawable(R.drawable.rounded_button_bg));

        shimmerFrameLayout.addView(buttonregister);
        Shimmer shimmer = new Shimmer.AlphaHighlightBuilder()
                .setDuration(1500) // Duration of shimmer animation
                .setBaseAlpha(0.7f) // Alpha of the base color
                .setHighlightAlpha(1f) // Alpha of the highlight color
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT) // Animation direction
                .build();
        shimmerFrameLayout.setShimmer(shimmer);

        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFieldss()) {
                    Intent intent = new Intent(RegisterActivity.this, OTPActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return shimmerFrameLayout;
    }

    private boolean validateFieldss() {
        boolean isValid = true;

        if (namet == null || namet.getText().toString().trim().isEmpty() ) {
            namet.setError("Enter valid name");
            isValid = false;
        }
        else if (mobilet == null || mobilet.getText().toString().trim().isEmpty()) {
            mobilet.setError("Mobile no required");
            isValid = false;
        }
        else if (emailet == null || emailet.getText().toString().trim().isEmpty() || !isValidEmail(emailet.getText().toString().trim())) {
            emailet.setError("Invalid email");
            isValid = false;
        }
        return isValid;
    }

    private boolean isValidEmail(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void applyBorder(View view) throws JSONException {
        view.setBackgroundResource(R.drawable.edit_text_border);
        view.setPadding(16, 16, 16, 16);
    }

    private int getInputType(String inputType) {
        switch (inputType) {
            case "textName":
                return InputType.TYPE_CLASS_TEXT;
            case "textMobile":
                return InputType.TYPE_CLASS_NUMBER;
            case "textEmail" :
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
            default:
                return InputType.TYPE_CLASS_TEXT;
        }
    }
}