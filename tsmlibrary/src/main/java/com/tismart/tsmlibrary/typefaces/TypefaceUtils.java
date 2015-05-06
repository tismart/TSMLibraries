package com.tismart.tsmlibrary.typefaces;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by luis.burgos on 20/03/2015.
 * <p/>
 * Clase padre para generación de typeface customizados. Se puede usar la clase @RobotoTypefaceUtils o crear una nueva usando esta clase padre.
 */
@SuppressWarnings("SameParameterValue,unused")
public abstract class TypefaceUtils {

    private final String strRegular;
    private final String strBold;
    private final String strLight;
    private final String strMedium;
    private final String strThin;
    private final String strBlack;
    private final Context context;
    private Typeface tf_regular;
    private Typeface tf_bold;
    private Typeface tf_light;
    private Typeface tf_medium;
    private Typeface tf_thin;
    private Typeface tf_black;


    public TypefaceUtils(Context context, String regular, String bold, String light, String medium, String thin, String black) {
        this.context = context;

        strRegular = regular;
        strBold = bold;
        strLight = light;
        strMedium = medium;
        strThin = thin;
        strBlack = black;
    }

    Typeface getRegular() {
        if (tf_regular == null && strRegular != null) {
            tf_regular = Typeface.createFromAsset(context.getAssets(), strRegular);
        }
        return tf_regular;
    }

    Typeface getBold() {
        if (tf_bold == null && strBold != null) {
            tf_bold = Typeface.createFromAsset(context.getAssets(), strBold);
        }
        return tf_bold == null ? getRegular() : tf_bold;
    }

    Typeface getLight() {
        if (tf_light == null && strLight != null) {
            tf_light = Typeface.createFromAsset(context.getAssets(), strLight);
        }
        return tf_light == null ? getRegular() : tf_light;
    }

    Typeface getMedium() {
        if (tf_medium == null && strMedium != null) {
            tf_medium = Typeface.createFromAsset(context.getAssets(), strMedium);
        }
        return tf_medium == null ? getRegular() : tf_medium;
    }

    Typeface getThin() {
        if (tf_thin == null && strThin != null) {
            tf_thin = Typeface.createFromAsset(context.getAssets(), strThin);
        }
        return tf_thin == null ? getRegular() : tf_thin;
    }

    Typeface getBlack() {
        if (tf_black == null && strBlack != null) {
            tf_black = Typeface.createFromAsset(context.getAssets(), strBlack);
        }
        return tf_black == null ? getRegular() : tf_black;
    }

    public Typeface getTypeface(TypefaceEnum typefaceEnum) {
        switch (typefaceEnum) {
            case BLACK:
                return getBlack();
            case BOLD:
                return getBold();
            case LIGHT:
                return getLight();
            case MEDIUM:
                return getMedium();
            case REGULAR:
                return getRegular();
            case THIN:
                return getThin();
            default:
                return getRegular();
        }
    }

    public Typeface getTypeface(int typefaceEnum) {
        switch (TypefaceEnum.values()[typefaceEnum]) {
            case BLACK:
                return getBlack();
            case BOLD:
                return getBold();
            case LIGHT:
                return getLight();
            case MEDIUM:
                return getMedium();
            case REGULAR:
                return getRegular();
            case THIN:
                return getThin();
            default:
                return getRegular();
        }
    }
}
