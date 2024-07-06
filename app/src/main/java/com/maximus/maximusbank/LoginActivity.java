package com.maximus.maximusbank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    public Button button;
    private EditText AccnoEditText,mobEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setGravity(Gravity.CENTER);
        rootLayout.setBackgroundColor(Color.WHITE);
        rootLayout.setPadding(32, 32, 32, 32);

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
        rootLayout.addView(cardView);

        LinearLayout innerLayout = new LinearLayout(this);
        innerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        innerLayout.setOrientation(LinearLayout.VERTICAL);
        innerLayout.setGravity(Gravity.CENTER);
        cardView.addView(innerLayout);

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.login);
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
                else if ("edittext_acc".equals(type)) {
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

                    if ("Enter your account number".equals(component.getString("hint"))) {
                        AccnoEditText = editText;
                    } else if ("Enter your mobile number".equals(component.getString("hint"))) {
                        mobEditText = editText;
                    }
                }
                else if ("edittext_mob".equals(type)) {
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

                    if ("Enter your account number".equals(component.getString("hint"))) {
                        AccnoEditText = editText;
                    } else if ("Enter your mobile number".equals(component.getString("hint"))) {
                        mobEditText = editText;
                    }
                }
               /* else if ("text_email".equals(type)) {
                    TextView text_email = new TextView(this);
                    text_email.setText(component.getString("value"));
                    text_email.setPadding(30,30,30,30);
                    text_email.setTextColor(Color.parseColor(component.getString("textColor")));
                    innerLayout.addView(text_email);
                }*/
                else if ("text_componets".equals(type)) {
                    TextView text_pass = new TextView(this);
                    text_pass.setText(component.getString("value"));
                    text_pass.setPadding(30,30,30,30);
                    text_pass.setTextColor(Color.parseColor(component.getString("textColor")));
                    innerLayout.addView(text_pass);
                }
                else if ("button".equals(type)) {
                    View buttonView = createButton(component);
                    buttonView.setPadding(20,20,20,30);
                    innerLayout.addView(buttonView);
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
                            Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
                            startActivity(intent);
                        }
                    });
                    innerLayout.addView(forgettext);
                }

                else if ("register_text".equals(type)) {
                    TextView registertextView = new TextView(this);
                    registertextView.setText(component.getString("value"));
                    registertextView.setTextSize(component.getInt("textSize"));
                    registertextView.setPadding(30,30,30,30);
                    registertextView.setTextColor(Color.parseColor(component.getString("textColor")));
                    registertextView.setGravity(Gravity.CENTER);
                    registertextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(LoginActivity.this, "Helllo", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                            startActivity(intent);
                        }
                    });
                    innerLayout.addView(registertextView);
                }
            }
            setContentView(rootLayout);

            if (!viewsToFocus.isEmpty()) {
                viewsToFocus.get(0).requestFocus();
            }
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

        button = new Button(this);
        button.setText(component.getString("text"));
        button.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        button.setBackground(getResources().getDrawable(R.drawable.rounded_button_bg));

        shimmerFrameLayout.addView(button);
        Shimmer shimmer = new Shimmer.AlphaHighlightBuilder()
                .setDuration(1500) // Duration of shimmer animation
                .setBaseAlpha(0.7f) // Alpha of the base color
                .setHighlightAlpha(1f) // Alpha of the highlight color
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT) // Animation direction
                .build();
        shimmerFrameLayout.setShimmer(shimmer);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
//                    showOtpDialog();

                    Intent intent = new Intent(LoginActivity.this, OTP.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return shimmerFrameLayout;
    }

    private void showOtpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("OTP");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 20, 40, 20);
        layout.setGravity(Gravity.CENTER);

        final EditText otpInput = new EditText(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(0, 10, 0, 10);
        otpInput.setLayoutParams(params);
        otpInput.setBackgroundColor(Color.WHITE);
        otpInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        otpInput.setHint("Enter OTP");
        otpInput.setPadding(32, 32, 32, 32);
        otpInput.setBackgroundResource(R.drawable.rounded_edittext_bg);

        builder.setView(otpInput);
        builder.setPositiveButton("Verify", null);

        builder.setPositiveButton("Verify", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String otp = otpInput.getText().toString().trim();
                if (otp.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "OTP Verified!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (button != null) {
                    button.setVisibility(View.VISIBLE);
                }
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean validateFields() {
        boolean isValid = true;

        if (AccnoEditText == null || AccnoEditText.getText().toString().trim().isEmpty()) {
            AccnoEditText.setError("Invalid email");
            isValid = false;
        }

        if (mobEditText == null || mobEditText.getText().toString().trim().isEmpty()) {
            mobEditText.setError("Enter Valid Mobile No");
            isValid = false;
        }

        return isValid;
    }

    private int getInputType(String inputType) {
        switch (inputType) {
            case "textmobileno":
                return InputType.TYPE_CLASS_NUMBER;
            case "textaccno":
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
            default:
                return InputType.TYPE_CLASS_TEXT;
        }
    }

    private void applyBorder(View view) throws JSONException {
        view.setBackgroundResource(R.drawable.edit_text_border);
        view.setPadding(16, 16, 16, 16);
    }
}
