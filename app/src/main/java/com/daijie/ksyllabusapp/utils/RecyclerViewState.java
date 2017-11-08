package com.daijie.ksyllabusapp.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by daidaijie on 17-10-21.
 */

public class RecyclerViewState {

    public static final int DATA_PRE = -1;
    public static final int DATA_EMPTY = 0;
    public static final int DATA_SUCCESS = 1;
    public static final int DATA_ERROR = 2;
    public static final int LOAD_MORE_END = 3;
    public static final int LOAD_MORE_ERROR = 4;


    @IntDef({DATA_PRE, DATA_EMPTY, DATA_SUCCESS, DATA_ERROR, LOAD_MORE_END, LOAD_MORE_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RecyclerViewStateDef {
    }
}
