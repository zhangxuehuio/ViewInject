package com.viewinjectdemo.proxyTest;

import com.viewinject.EventProxyHandler;

import java.lang.reflect.Proxy;

/**
 * Created by zhangxuehui on 2018/6/13.
 */

public class ProxyDemo {
    public static void main(String[] args) {
        User user = new User("aaaa");
        user.setName("张学辉");
        user.showName();
        EventProxyHandler handler = new EventProxyHandler(user);
        UserInterface obj = (UserInterface) Proxy.newProxyInstance(user.getClass().getClassLoader(),
                user.getClass().getInterfaces(),
                handler);
        obj.showName();
        System.out.println(obj.getClass().getSimpleName());
    }
}
