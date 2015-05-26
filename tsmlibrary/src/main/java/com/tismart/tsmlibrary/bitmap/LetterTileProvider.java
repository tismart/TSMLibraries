package com.tismart.tsmlibrary.bitmap;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;

import com.tismart.tsmlibrary.R;

/**
 * Created by luis.burgos on 29/01/2015.
 *
 * Clase que genera una palabra con un fondo de un color.
 */
@SuppressWarnings("unused")
public class LetterTileProvider {

    private static LetterTileProvider provider;
    final char[] mFirstChar;
    private final TextPaint mPaint = new TextPaint();
    private final Rect mBounds = new Rect();
    private final Canvas mCanvas = new Canvas();
    private final int mTileLetterFontSize;
    private final Bitmap mDefaultBitmap;
    private final Context context;
    private final int letrasAMostrarse;
    private int num_tile_colors;
    private TypedArray mColors;

    /**
     * Constructor for <code>LetterTileProvider</code>
     *
     * @param context The {@link Context} to use
     * @param letrasAMostrarse cantidad de lestras a mostrarse
     */
    public LetterTileProvider(Context context, int letrasAMostrarse) {
        this.context = context;
        this.letrasAMostrarse = letrasAMostrarse;
        final Resources res = context.getResources();

        mPaint.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setAntiAlias(true);

        mColors = res.obtainTypedArray(R.array.letter_tile_colors);
        num_tile_colors = mColors.length();

        mFirstChar = new char[letrasAMostrarse];

        mTileLetterFontSize = res.getDimensionPixelSize(R.dimen.tile_letter_font_size);
        mDefaultBitmap = BitmapFactory.decodeResource(res, android.R.drawable.sym_def_app_icon);
    }

    public static LetterTileProvider newInstance(Context context, int letrasAMostrarse) {
        if (provider == null) {
            provider = new LetterTileProvider(context, letrasAMostrarse);
        }
        return provider;
    }

    /**
     * @param word letras a mostrarse en la imagen
     * @return Verdadero si <code>word</code> esta dentro del alfabeto ingles, falso caso contrario
     */
    private static boolean isEnglishLetterOrDigit(char[] word) {
        boolean noesletra = true;
        for (char c : word) {
            if (!('A' <= c && c <= 'Z' || 'a' <= c && c <= 'z' || '0' <= c && c <= '9')) {
                noesletra = false;
            }
        }
        return noesletra;
    }

    public void setCustomColors(int array_color_id) {
        try {
            mColors = context.getResources().obtainTypedArray(array_color_id);
            num_tile_colors = mColors.length();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * @param displayName The name used to create the letter for the tile
     * @param key         The key used to generate the background color for the tile
     * @param width       The desired width of the tile
     * @param height      The desired height of the tile
     * @return A {@link Bitmap} that contains a letter used in the English
     * alphabet or digit, if there is no letter or digit available, a
     * default image is shown instead
     */
    public Bitmap getLetterTile(String displayName, String key, int width, int height) {
        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        char[] chars = new char[letrasAMostrarse];
        for (int i = 0; i < letrasAMostrarse; i++) {
            chars[i] = displayName.charAt(i);
        }

        final Canvas c = mCanvas;
        c.setBitmap(bitmap);
        c.drawColor(pickColor(key));

        if (isEnglishLetterOrDigit(chars)) {
            for (int i = 0; i < letrasAMostrarse; i++) {
                mFirstChar[i] = i > 0 ? Character.toLowerCase(chars[i]) : Character.toUpperCase(chars[i]);
            }
            mPaint.setTextSize(mTileLetterFontSize);
            mPaint.getTextBounds(mFirstChar, 0, chars.length, mBounds);
            c.drawText(mFirstChar, 0, chars.length, width / 2, height / 2
                    + (mBounds.bottom - mBounds.top) / 2, mPaint);
        } else {
            c.drawBitmap(mDefaultBitmap, 0, 0, null);
        }
        return bitmap;
    }

    public Bitmap getLetterTile(String displayName, String key, int letterSize, int width, int height) {
        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        char[] chars = new char[letrasAMostrarse];
        for (int i = 0; i < letrasAMostrarse; i++) {
            chars[i] = displayName.charAt(i);
        }

        final Canvas c = mCanvas;
        c.setBitmap(bitmap);
        c.drawColor(pickColor(key));

        if (isEnglishLetterOrDigit(chars)) {
            for (int i = 0; i < letrasAMostrarse; i++) {
                mFirstChar[i] = i > 0 ? Character.toLowerCase(chars[i]) : Character.toUpperCase(chars[i]);
            }
            mPaint.setTextSize(letterSize);
            mPaint.getTextBounds(mFirstChar, 0, chars.length, mBounds);
            c.drawText(mFirstChar, 0, chars.length, width / 2, height / 2
                    + (mBounds.bottom - mBounds.top) / 2, mPaint);
        } else {
            c.drawBitmap(mDefaultBitmap, 0, 0, null);
        }
        return bitmap;
    }

    /**
     * @param key Llave a usarse para obtener el color.
     * @return Un nuevo o previo color elegido para <code>key</code> usado como fondo para la letra
     */
    private int pickColor(String key) {
        final int color = Math.abs(key.hashCode()) % num_tile_colors;
        return mColors.getColor(color, Color.BLACK);
    }

}
