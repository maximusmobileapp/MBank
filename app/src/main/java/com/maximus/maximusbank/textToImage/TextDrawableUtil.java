package com.maximus.maximusbank.textToImage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class TextDrawableUtil {

    public static Bitmap getTextDrawable(String text, int width, int height, int backgroundColor, int textColor, int borderWidth, int borderColor) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawOval(new RectF(0, 0, width, height), paint);

        if (borderWidth > 0) {
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(borderWidth);
            canvas.drawOval(new RectF(0, 0, width, height), paint);
        }

        paint.setColor(textColor);
        paint.setTextSize(50);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);

        float xPos = canvas.getWidth() / 2;
        float yPos = (canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2);

        canvas.drawText(text, xPos, yPos, paint);

        return bitmap;
    }
}

