package com.daijie.ksyllabusapp.utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatDrawableManager;

import com.daijie.ksyllabusapp.App;


/**
 * Created by daidaijie on 2017/3/8.
 * 主要用于给drawable着色
 * 针对Vector的兼容性，对api19以上的版本和以下的版本分别做了不同的处理以保证其兼容性
 */

public class DrawableTintUtils {

    public static Drawable getTintDrawableByColorRes(@DrawableRes int drawableId,
                                                     @ColorRes int colorRes) {
        return getTintDrawableByColorInt(drawableId,
                ContextCompat.getColor(App.getContext(), colorRes));
    }

    public static Drawable getTintDrawableByColorInt(@DrawableRes int drawableId,
                                                     @ColorInt int colorInt) {
        Drawable drawable = AppCompatDrawableManager
                .get().getDrawable(App.getContext(), drawableId);
        Drawable resultDrawable = drawable.mutate();
        DrawableCompat.setTint(resultDrawable, colorInt);
        return resultDrawable;
    }
}
