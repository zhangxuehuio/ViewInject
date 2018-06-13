package com.viewinject;

/**
 * Created by zhangxuehui on 2018/6/13.
 */

class ViewInfo {
    private int resId;//
    private Object target;//目标对象
    private Class<?> type;//返回的事件的类型
    private String setMethod;//view中设置的方法名
    private String callbackName;//回调方法名
}
