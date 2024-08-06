package com.maximus.maximusbank.Utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.maximus.maximusbank.activity.sharedPref.PreferenceManager;

import java.util.Locale;

public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        PreferenceManager preferenceManager = new PreferenceManager(base);
        String language = preferenceManager.getValue("language", "en");
        super.attachBaseContext(updateLocale(base, language));
    }

    private Context updateLocale(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        return context.createConfigurationContext(config);

    }
}

