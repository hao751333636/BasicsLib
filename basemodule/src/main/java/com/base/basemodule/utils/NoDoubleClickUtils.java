package com.base.basemodule.utils;

public class NoDoubleClickUtils {
    private final static int SPACE_TIME = 700;//2次点击的间隔时间，单位ms
    private static long lastClickTime;

    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick;
        long l = currentTime - lastClickTime;
        if (0 < l && l < SPACE_TIME) {
            isClick = false;
        } else {
            isClick = true;
        }
        lastClickTime = currentTime;
        return isClick;
    }
}
