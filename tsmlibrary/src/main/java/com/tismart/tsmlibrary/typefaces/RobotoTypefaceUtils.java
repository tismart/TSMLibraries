package com.tismart.tsmlibrary.typefaces;

import android.content.Context;

/**
 * Created by luis.burgos on 20/03/2015.
 * <p/>
 * Clase de typeface especial para Roboto.
 */
public class RobotoTypefaceUtils extends TypefaceUtils {
    private static final String REGULAR = "Roboto-Regular.ttf";
    private static final String BOLD = "Roboto-Bold.ttf";
    private static final String LIGHT = "Roboto-Light.ttf";
    private static final String MEDIUM = "Roboto-Medium.ttf";
    private static final String THIN = "Roboto-Thin.ttf";
    private static final String BLACK = "Roboto-Black.ttf";

    private static RobotoTypefaceUtils mInstance;

    private RobotoTypefaceUtils(Context context) {
        super(context, REGULAR, BOLD, LIGHT, MEDIUM, THIN, BLACK);
    }

    public static RobotoTypefaceUtils newInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RobotoTypefaceUtils(context);
        }
        return mInstance;
    }


}
