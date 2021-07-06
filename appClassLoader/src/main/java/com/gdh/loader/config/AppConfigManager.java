package com.gdh.loader.config;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AppConfigManager {
    private List<AppConfig> configs = new ArrayList<>();

    public void initAppConfigs() {
        try {
            URL path = this.getClass().getClassLoader().getResource(GlobalSetting.APP_CONFIG_NAME);
            loadAllApplicationConfigs(path.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void loadAllApplicationConfigs(URI path) {
        File file = new File(path);
        XStream xstream = getAppConfigXStreamDefine();
        try {
            AppConfigList configList = (AppConfigList) xstream.fromXML(new FileInputStream(file));
            if (configList.getConfigs() != null) {
                this.configs.addAll(new ArrayList<AppConfig>(configList.getConfigs()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private XStream getAppConfigXStreamDefine() {
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("apps", AppConfigList.class);
        xstream.alias("app", AppConfig.class);
        xstream.aliasField("name", AppConfig.class, "name");
        xstream.aliasField("file", AppConfig.class, "file");
        xstream.addImplicitCollection(AppConfigList.class, "configs");
        return xstream;
    }

    public List<AppConfig> getConfigs() {
        return configs;
    }

    public AppConfig getConfig(String name) {
        for (AppConfig config : this.configs) {
            if (config.getName().equalsIgnoreCase(name)) {
                return config;
            }
        }
        return null;
    }
}
