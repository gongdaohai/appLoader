package com.gdh.app;

import com.gdh.loader.application.IApplication;
import com.gdh.loader.config.AppConfig;

public class App1 implements IApplication {
    @Override
    public void init(AppConfig config) {
        System.out.println("App1 init...222222222");
    }

    @Override
    public void execute() {
        System.out.println("App1 execute");
    }

    @Override
    public void destory() {
        System.out.println("App1 destory");
    }
}