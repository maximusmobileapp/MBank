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
public class SetMpin extends AppCompatActivity {
    public Button buttonsubmit;
    private EditText newmpin,confmpin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_mpin);

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
                LinearLayout.LayoutParams.WRAP_CONTENT);

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
                LinearLayout.LayoutParams.MATCH_PARENT));
        innerLayout.setOrientation(LinearLayout.VERTICAL);
        innerLayout.setGravity(Gravity.CENTER);
        cardView.addView(innerLayout);

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.setmpin);
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
                else if ("text_Confirm".equals(type)){
                    TextView text_Confirm = new TextView(this);
                    text_Confirm.setText(component.getString("value"));
                    text_Confirm.setPadding(30,30,30,30);
                    text_Confirm.setTextColor(Color.parseColor(component.getString("textColor")));
                    innerLayout.addView(text_Confirm);
                }
                else if ("edit_New".equals(type)) {
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
                        newmpin = edit_New;
                    } else if ("Enter Confirm MPIN".equals(component.getString("hint"))) {
                        confmpin = edit_New;
                    }
                }
                else if ("buttonnext".equals(type)){
                    View buttonView = createButton(component);
                    buttonView.setPadding(20,20,20,30);
                    innerLayout.addView(buttonView);
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

        buttonsubmit = new Button(this);
        buttonsubmit.setText(component.getString("text"));
        buttonsubmit.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        buttonsubmit.setBackground(getResources().getDrawable(R.drawable.rounded_button_bg));

        shimmerFrameLayout.addView(buttonsubmit);
        Shimmer shimmer = new Shimmer.AlphaHighlightBuilder()
                .setDuration(1500) // Duration of shimmer animation
                .setBaseAlpha(0.7f) // Alpha of the base color
                .setHighlightAlpha(1f) // Alpha of the highlight color
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT) // Animation direction
                .build();
        shimmerFrameLayout.setShimmer(shimmer);
        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    Intent intent = new Intent(SetMpin.this, MPIN.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SetMpin.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return shimmerFrameLayout;
    }

    private boolean validateFields() {
        boolean isValid = true;
        if (newmpin == null || newmpin.getText().toString().trim().isEmpty()) {
            newmpin.setError("Enter valid mpin");
            isValid = false;
        } else if (newmpin.getText().toString().trim().length() != 4) {
            newmpin.setError("MPIN must be 4 characters");
            isValid = false;
        }
        else if (confmpin == null || confmpin.getText().toString().trim().isEmpty()) {
            confmpin.setError("Enter valid mpin");
            isValid = false;
        } else if (confmpin.getText().toString().trim().length() != 4) {
            confmpin.setError("MPIN must be 4 characters");
            isValid = false;
        }

        if (newmpin != null && confmpin != null) {
            String mpin = newmpin.getText().toString().trim();
            String confirmMpin = confmpin.getText().toString().trim();
            if (!mpin.equals(confirmMpin)) {
                confmpin.setError("MPINs do not match");
                isValid = false;
            }
        }
        return isValid;
    }

    private int getInputType(String inputType) {
        switch (inputType) {
            case "textMPIN":
                return InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD;
            case "textConfMPIN":
                return InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD;
            default:
            return InputType.TYPE_CLASS_NUMBER;
        }
    }

    private void applyBorder(View view) throws JSONException {
        view.setBackgroundResource(R.drawable.edit_text_border);
        view.setPadding(16, 16, 16, 16);
    }
}