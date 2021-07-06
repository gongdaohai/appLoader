package com.gdh.loader.classloader;

import com.gdh.loader.application.IApplication;
import com.gdh.loader.config.AppConfig;
import com.gdh.loader.config.AppConfigManager;
import com.gdh.loader.config.GlobalSetting;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ClassLoaderManager {
    private AppConfigManager appConfigManager;
    private ClassLoaderFactory classLoaderFactory;
    private Map<String, IApplication> apps = new HashMap();

    public void init() {
        appConfigManager = new AppConfigManager();
        classLoaderFactory = new SimpleClassLoaderFactory();
        appConfigManager.initAppConfigs();
        URL basePath = this.getClass().getClassLoader().getResource(GlobalSetting.JAR_FOLDER);
        loadAllApplications(basePath.getPath());

    }

    private void loadAllApplications(String basePath) {
        for (AppConfig config : this.appConfigManager.getConfigs()) {
            this.createApplication(basePath, config);
        }
    }

    private void createApplication(String basePath, AppConfig config) {
        String folderName = basePath + config.getName();
        ClassLoader loader = this.classLoaderFactory.createClassLoader(ClassLoaderManager.class.getClassLoader(), folderName);
        try {
            Class<?> appClass = loader.loadClass(config.getFile());
            IApplication app = (IApplication) appClass.newInstance();
            app.init(config);
            this.apps.put(config.getName(), app);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public void reloadApplication(String name) {
        IApplication oldApp = this.apps.remove(name);
        if (oldApp == null) {
            return;
        }
        oldApp.destory();
        AppConfig config = this.appConfigManager.getConfig(name);
        if (config == null) {
            return;
        }
        URL basePath = this.getClass().getClassLoader().getResource(GlobalSetting.JAR_FOLDER);
        createApplication(basePath.getPath(), config);
    }

    public Map<String, IApplication> getApps() {
        return apps;
    }
}
