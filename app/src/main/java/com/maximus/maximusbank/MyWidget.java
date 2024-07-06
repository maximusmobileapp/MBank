package com.maximus.maximusbank;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyWidget {
    static TextView textView(Context context, String textOntextview , String textClr, String headerView) {
        TextView textView = new TextView(context);
        textView.setText(textOntextview);
        textView.setTextColor(Color.parseColor(textClr));
        textView.setGravity(Gravity.CENTER);
        if ("header".equals(headerView)) {
            textView.setTextSize(24);
        }
        return textView;
    }

    static EditText editText(Context context, String texthint, String textClr, int InputType,int max_length) {
        EditText editText = new EditText(context);
        editText.setHint(texthint);
        editText.setTextColor(Color.parseColor(textClr));
        editText.setInputType(InputType);

        if (max_length > 0) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max_length)});
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(30, 0, 30, 0);
        editText.setLayoutParams(params);
        applyBorder(editText);

        return editText;
    }

    static TextView tvcomponent(Context context, String textview, String textClr) {
        TextView textView = new TextView(context);
        textView.setText(textview);
        textView.setTextColor(Color.parseColor(textClr));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(30, 30, 30, 30);
        textView.setLayoutParams(params);
        return textView;
    }

//    static Button button(Context context, String buttonview, String Padding) {
//        Button button = new Button(context);
//        button = createButton(buttonview);
//
//        button.setText();
//        return button;
//    }


    public static void applyBorder(View view)  {
        view.setBackgroundResource(R.drawable.edit_text_border);
        view.setPadding(16, 16, 16, 16);
    }

}
