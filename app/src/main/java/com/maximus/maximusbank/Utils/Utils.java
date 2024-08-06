package com.maximus.maximusbank.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.maximus.maximusbank.activity.sharedPref.PreferenceManager;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class Utils {

    public static void showSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbarView.setBackgroundColor(Color.RED);
        snackbar.show();
    }

    public static void onBackPress(Activity context, ImageView imageView){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.onBackPressed();
            }
        });
    }

    public static JSONObject loadJsonFromRaw(Resources resources, int resId) throws Exception {
        InputStream inputStream = resources.openRawResource(resId);
        String jsonString;
        try {
            jsonString = convertStreamToString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new JSONObject(jsonString);
    }

    private static String convertStreamToString(InputStream is) throws IOException {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static void setLocale(Context context) {
        PreferenceManager preferenceManager = new PreferenceManager(context);
        String language = preferenceManager.getValue("language", "en");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        preferenceManager.setValue("language", language);

    }
}

