package com.maximus.maximusbank.textToImage;

import java.util.Random;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.maximus.maximusbank.R;

public class ColorGenerator {
    private final int[] colors;
    private final Random random;
    private static final int[] MATERIAL_COLORS = {
            Color.rgb(244, 67, 54), Color.rgb(233, 30, 99), Color.rgb(156, 39, 176),
            Color.rgb(103, 58, 183), Color.rgb(63, 81, 181), Color.rgb(33, 150, 243),
            Color.rgb(3, 169, 244), Color.rgb(0, 188, 212), Color.rgb(0, 150, 136),
            Color.rgb(76, 175, 80), Color.rgb(139, 195, 74), Color.rgb(205, 220, 57),
            Color.rgb(255, 235, 59), Color.rgb(255, 193, 7)
    };

    public ColorGenerator(Context context) {
        colors = new int[]{
                ContextCompat.getColor(context, R.color.gray),
                ContextCompat.getColor(context, R.color.lightgray),
                ContextCompat.getColor(context, R.color.colorPrimary),
                ContextCompat.getColor(context, R.color.lightblue),
                ContextCompat.getColor(context, R.color.colorPrimarfy),
                ContextCompat.getColor(context, R.color.colorPrimaryDark),
                ContextCompat.getColor(context, R.color.buttonPrimaryColor),
                ContextCompat.getColor(context, R.color.transparent),
                ContextCompat.getColor(context, R.color.disable_forground_grey),
                ContextCompat.getColor(context, R.color.disable_thumb_grey),
                ContextCompat.getColor(context, R.color.enable_thumb_green),
                ContextCompat.getColor(context, R.color.primaryColor),
                ContextCompat.getColor(context, R.color.primaryVariantColor),
                ContextCompat.getColor(context, R.color.secondaryColor),
                ContextCompat.getColor(context, R.color.backgroundColor),
                ContextCompat.getColor(context, R.color.surfaceColor),
                ContextCompat.getColor(context, R.color.textColor),
                ContextCompat.getColor(context, R.color.viewBackgroundLight),
                ContextCompat.getColor(context, R.color.viewBackgroundDark),
                ContextCompat.getColor(context, R.color.primaryDarkColor),
                ContextCompat.getColor(context, R.color.primaryVariantDarkColor),
                ContextCompat.getColor(context, R.color.secondaryDarkColor),
                ContextCompat.getColor(context, R.color.backgroundDarkColor),
                ContextCompat.getColor(context, R.color.surfaceDarkColor),
                ContextCompat.getColor(context, R.color.textDarkColor),
                ContextCompat.getColor(context, R.color.viewBackgroundDarkLight),
                ContextCompat.getColor(context, R.color.viewBackgroundDarkColor)
        };
        random = new Random();
    }

    private static final Random RANDOM = new Random();

    public int getRandomColor() {
        return colors[random.nextInt(colors.length)];
//        return MATERIAL_COLORS[RANDOM.nextInt(MATERIAL_COLORS.length)];
    }

    public int getColor(Object key) {
        return MATERIAL_COLORS[Math.abs(key.hashCode()) % MATERIAL_COLORS.length];
    }
}

