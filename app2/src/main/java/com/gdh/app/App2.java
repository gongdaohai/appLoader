package com.gdh.app;

import com.gdh.loader.application.IApplication;
import com.gdh.loader.config.AppConfig;

public class App2 implements IApplication {
    @Override
    public void init(AppConfig config) {
        System.out.println("App2 init");
    }

    @Override
    public void execute() {
        System.out.println("App2 execute");
    }

    @Override
    public void destory() {
        System.out.println("App2 destory");
    }
}
