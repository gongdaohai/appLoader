package com.gdh.loader;

import com.gdh.loader.application.ApplicationManager;

public class Main {
    public static void main(String[] args) {
        ApplicationManager applicationManager = ApplicationManager.getInstance();
        applicationManager.start(true);
        while (true) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
