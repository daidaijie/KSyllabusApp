package com.daijie.ksyllabusapp.base;

import java.util.Map;

/**
 * Created by liyujie on 2017/4/21.
 */

public interface IStorable {

    Map<String, Object> getStoreData();

    void restoreData(Map<String, Object> storeData);
}
