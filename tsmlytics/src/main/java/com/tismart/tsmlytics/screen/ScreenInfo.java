package com.tismart.tsmlytics.screen;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.tismart.tsmlytics.entities.Screen;

/**
 * Created by luis.rios on 29/04/2015.
 */
public class ScreenInfo {

    /**
     * Obtiene la información de la pantalla del dispositivo.
     * Si hay algún error el método devolverá null
     * Screen.Density = Densidad de la pantalla en int
     * Screen.Size = Tamaño de la pantalla en float
     * Screen.Orientation = Orientación de la pantalla en String
     * @param mContext contexto de la aplicación
     * @return
     */
    public static Screen getScreenInfo(Context mContext) {
        Screen screen = new Screen();
        try {
            Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);

            // since SDK_INT = 1;
            int mWidthPixels = displayMetrics.widthPixels;
            int mHeightPixels = displayMetrics.heightPixels;

            // includes window decorations (statusbar bar/menu bar)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                try {
                    mWidthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                    mHeightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
                } catch (Exception ignored) {
                }
            }

            // includes window decorations (statusbar bar/menu bar)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                try {
                    Point realSize = new Point();
                    Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
                    mWidthPixels = realSize.x;
                    mHeightPixels = realSize.y;
                } catch (Exception ignored) {
                }
            }

            screen.setDensity(mContext.getResources().getDisplayMetrics().densityDpi);
            screen.setSize(Math.sqrt(Math.pow(mWidthPixels / screen.getDensity(), 2) + Math.pow(mHeightPixels / screen.getDensity(), 2)));

            switch (mContext.getResources().getConfiguration().orientation) {
                case Configuration.ORIENTATION_LANDSCAPE:
                    screen.setOrientation("Landscape");
                    break;
                case Configuration.ORIENTATION_PORTRAIT:
                    screen.setOrientation("Portrait");
                    break;
                default:
                    screen.setOrientation("");
                    break;
            }
        } catch (Exception ex) {
            screen = null;
        }
        return screen;
    }

    @Deprecated
    public static String getScreenSize(Context mContext) {
        try {
            Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);

            // since SDK_INT = 1;
            int mWidthPixels = displayMetrics.widthPixels;
            int mHeightPixels = displayMetrics.heightPixels;

            // includes window decorations (statusbar bar/menu bar)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                try {
                    mWidthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                    mHeightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
                } catch (Exception ignored) {
                }
            }

            // includes window decorations (statusbar bar/menu bar)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                try {
                    Point realSize = new Point();
                    Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
                    mWidthPixels = realSize.x;
                    mHeightPixels = realSize.y;
                } catch (Exception ignored) {
                }
            }
            int density = Integer.parseInt(getScreenDensity(mContext));
            return String.valueOf(Math.sqrt(Math.pow(mWidthPixels / density, 2) + Math.pow(mHeightPixels / density, 2)));
        } catch (Exception ex) {
            return "";
        }
    }

    @Deprecated
    public static String getScreenDensity(Context mContext) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        return metrics.densityDpi + "";
    }

    @Deprecated
    public static String getScreenOrientation(Context mContext) {
        switch (mContext.getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                return "Landscape";
            case Configuration.ORIENTATION_PORTRAIT:
                return "Portrait";
            default:
                return "";
        }
    }
}