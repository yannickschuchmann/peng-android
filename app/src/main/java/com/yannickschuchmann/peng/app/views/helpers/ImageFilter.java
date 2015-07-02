package com.yannickschuchmann.peng.app.views.helpers;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by yannick on 01.07.15.
 */
public class ImageFilter {
    public static Drawable applyFiltersForColor(Context context, int res, int color) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), res);
        BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);

        ColorMatrix greyscaleMatrix = new ColorMatrix();
        greyscaleMatrix.setSaturation(0);

        ColorMatrix colorizeMatrix = new ColorMatrix();
        color = context.getResources().getColor(color);
        colorizeMatrix.setScale(Color.red(color) / 255.0f,
                Color.green(color) / 255.0f,
                Color.blue(color) / 255.0f,
                1);

        ColorMatrix contrastMatrix = getContrastMatrix(0.4f);

        ColorMatrix brightnessMatrix = new ColorMatrix();
        brightnessMatrix.setScale(1.7f,1.7f,1.7f,1);

        greyscaleMatrix.postConcat(colorizeMatrix);
        greyscaleMatrix.postConcat(contrastMatrix);
        greyscaleMatrix.postConcat(brightnessMatrix);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(greyscaleMatrix);

        drawable.setColorFilter(filter);
        return drawable;
    }

    private static ColorMatrix getContrastMatrix(float contrast) {
        float scale = contrast + 1.f;
        float translate = (-.5f * scale + .5f) * 255.f;
        float[] array = new float[] {
                scale, 0, 0, 0, translate,
                0, scale, 0, 0, translate,
                0, 0, scale, 0, translate,
                0, 0, 0, 1, 0};
        ColorMatrix matrix = new ColorMatrix(array);
        return matrix;
    }
}
