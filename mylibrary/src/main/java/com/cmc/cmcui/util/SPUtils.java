package com.cmc.cmcui.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
    /**
     * 存储数据
     *
     * @param context 上下文
     * @param key     key名
     * @param object  数据
     */
    public static void poutMessage(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (object instanceof String) {
            edit.putString(key, (String) object);
        } else if (object instanceof Integer) {
            edit.putInt(key, (Integer) object);
        } else if (object instanceof Float) {
            edit.putFloat(key, (Float) object);
        } else if (object instanceof Boolean) {
            edit.putBoolean(key, (Boolean) object);
        } else if (object instanceof Long) {
            edit.putLong(key, (Long) object);
        }
        edit.commit();
    }

    /**
     * 获取数据
     *
     * @param context 上下文
     * @param key     key名
     * @param object  数据
     */
    public static Object getMessage(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        if (object instanceof String) {
            return sp.getString(key, (String) object);
        } else if (object instanceof Integer) {
            return sp.getInt(key, (Integer) object);
        } else if (object instanceof Float) {
            return sp.getFloat(key, (Float) object);
        } else if (object instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) object);
        } else if (object instanceof Long) {
            return sp.getLong(key, (Long) object);
        }
        return null;
    }

    /**
     * 清除数据(退出登录时调用)
     *
     * @param context 上下文
     */
    public void eliminateAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
    }
}
