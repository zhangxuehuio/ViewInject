package com.viewinject.proxyTest;

/**
 * Created by zhangxuehui on 2018/6/13.
 */

public class User implements UserInterface {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void showName() {
        System.out.println(this.getClass().getSimpleName() + " ====> " + name);
    }
}
