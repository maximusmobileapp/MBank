package com.maximus.maximusbank;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

public class MPIN extends AppCompatActivity {
    public Button buttonlogin;
    private EditText mpin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpin);

        LinearLayout rootlayout = new LinearLayout(this);
        rootlayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
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
                            Intent intent = new Intent(MPIN.this, ForgetPassword.class);
                            startActivity(intent);
                        }
                    });
                    innerLayout.addView(forgettext);
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
                if (validateFields()) {
                    Toast.makeText(MPIN.this, "No Screen", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MPIN.this, MainActivity.class));
                } else {
                    Toast.makeText(MPIN.this, "Please fill all details", Toast.LENGTH_SHORT).show();
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