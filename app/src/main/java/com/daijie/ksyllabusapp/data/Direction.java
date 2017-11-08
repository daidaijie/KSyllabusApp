package com.daijie.ksyllabusapp.data;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by daidaijie on 2017/3/13.
 * 方向类，定义上下左右方向
 */

public class Direction {

    public static final int LEFT = 1;
    public static final int TOP = 2;
    public static final int RIGHT = 3;
    public static final int BOTTOM = 4;

    @IntDef({LEFT, TOP, RIGHT, BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DirectionInf {

    }
}
