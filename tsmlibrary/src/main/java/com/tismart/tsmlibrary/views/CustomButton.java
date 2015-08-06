package com.tismart.tsmlibrary.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.tismart.tsmlibrary.R;
import com.tismart.tsmlibrary.typefaces.RobotoTypefaceUtils;
import com.tismart.tsmlibrary.typefaces.TypefaceUtils;

/**
 * Created by luis.burgos on 20/03/2015.
 * Vista customizada de un Button donde se puede agregar los typeface customizados.
 */

@SuppressWarnings("unused")
public class CustomButton extends AppCompatButton {

    private TypefaceUtils mTypefaceUtils = null;
    private int fontValue = 0;

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.custom_view);
            fontValue = a.getInt(R.styleable.custom_view_typeface, 0);
            a.recycle();
            if (mTypefaceUtils == null) {
                mTypefaceUtils = RobotoTypefaceUtils.newInstance(context);
            }
            setTypeface(mTypefaceUtils.getTypeface(fontValue));
        }
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.custom_view);
            fontValue = a.getInt(R.styleable.custom_view_typeface, 0);
            a.recycle();
            if (mTypefaceUtils == null) {
                mTypefaceUtils = RobotoTypefaceUtils.newInstance(context);
            }
            setTypeface(mTypefaceUtils.getTypeface(fontValue));
        }
    }

    public void setTypefaceUtils(TypefaceUtils typefaceUtils) {
        this.mTypefaceUtils = typefaceUtils;
        setTypeface(mTypefaceUtils.getTypeface(fontValue));
    }
}
