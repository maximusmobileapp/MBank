package com.maximus.maximusbank.Utils;

import android.content.res.Resources;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class Utils {

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
}

