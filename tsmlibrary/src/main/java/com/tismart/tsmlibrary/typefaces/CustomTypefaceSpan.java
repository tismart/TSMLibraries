package com.tismart.tsmlibrary.typefaces;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

/**
 * Created by luis.burgos on 23/03/2015.
 *
 * Clase TypefaceSpan customizada para los diferentes tipos de typeface que se puede setear a un span.
 */
@SuppressWarnings("NullableProblems,unused")
public class CustomTypefaceSpan extends TypefaceSpan {

    private final Typeface newType;

    public CustomTypefaceSpan(Typeface type) {
        super("sans-serif");
        newType = type;
    }

    private static void applyCustomTypeface(Paint paint, Typeface tf) {
        int oldStyle;
        Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }
        int fake = oldStyle & ~tf.getStyle();
        if ((fake & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(tf);

    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        applyCustomTypeface(ds, newType);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        super.updateMeasureState(paint);
        applyCustomTypeface(paint, newType);
    }
}
