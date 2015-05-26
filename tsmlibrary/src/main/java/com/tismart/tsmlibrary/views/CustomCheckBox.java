package com.tismart.tsmlibrary.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.tismart.tsmlibrary.R;
import com.tismart.tsmlibrary.typefaces.RobotoTypefaceUtils;
import com.tismart.tsmlibrary.typefaces.TypefaceUtils;

/**
 * Created by luis.burgos on 22/04/2015.
 *
 * Vista customizada de un CheckBox donde se puede agregar los typeface customizados.
 */
@SuppressWarnings("unused")
public class CustomCheckBox extends CheckBox {

    private TypefaceUtils mTypefaceUtils = null;
    private int fontValue = 0;

    public CustomCheckBox(Context context) {
        super(context);
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
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

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
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
