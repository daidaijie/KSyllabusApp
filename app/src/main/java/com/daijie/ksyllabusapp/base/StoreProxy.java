package com.daijie.ksyllabusapp.base;

import android.os.Bundle;
import android.os.Parcelable;

import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by liyujie on 2017/4/21.
 */

public class StoreProxy {

    public static void onSaveInstanceState(Bundle outState, IStorable storable) {
        Map<String, Object> storeMap = storable.getStoreData();
        Iterator<Map.Entry<String, Object>> iter = storeMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> data = iter.next();
            Bundle bundle = new Bundle();
            storeObject(bundle, data.getKey(), data.getValue());
            outState.putAll(bundle);
        }
    }

    public static void onRestoreInstanceState(Bundle savedInstanceState, IStorable storable) {
        if (savedInstanceState == null) {
            return;
        }
        Map<String, Object> storeMap = storable.getStoreData();
        Iterator<Map.Entry<String, Object>> iter = storeMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> data = iter.next();
            data.setValue(reStoreObject(savedInstanceState, data.getKey()));
        }

        storable.restoreData(storeMap);
    }

    private static void storeObject(Bundle bundle, String key, Object value) {
        if (value instanceof Parcelable) {
            bundle.putParcelable(key, (Parcelable) value);
        } else if (value instanceof Integer) {
            bundle.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            bundle.putBoolean(key, (Boolean) value);
        } else if (value instanceof String) {
            bundle.putString(key, (String) value);
        } else if (value instanceof Float) {
            bundle.putFloat(key, (Float) value);
        } else if (value instanceof Short) {
            bundle.putFloat(key, (Short) value);
        } else if (value instanceof Double) {
            bundle.putDouble(key, (Double) value);
        } else if (value instanceof ArrayList) {
            ArrayList array = (ArrayList) value;
            if (array != null && array.size() > 0) {
                Object o = array.get(0);
                if (o instanceof Parcelable) {
                    bundle.putParcelableArrayList(key, (ArrayList<? extends Parcelable>) value);
                }
            }
        } else if (value instanceof Serializable) {
            bundle.putSerializable(key, (Serializable) value);
        }
    }

    private static Object reStoreObject(Bundle bundle, String key) {
        return bundle.get(key);
    }

}
