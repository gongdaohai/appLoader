package com.gdh.loader.application;

import com.gdh.loader.classloader.ClassLoaderManager;
import com.gdh.loader.config.GlobalSetting;
import com.gdh.loader.filelistener.JarFileChangeListener;
import org.apache.commons.vfs.*;
import org.apache.commons.vfs.impl.DefaultFileMonitor;

import java.io.File;
import java.net.URL;

public class ApplicationManager {
    private static ApplicationManager applicationManager;

    private static boolean reloadable;

    private ClassLoaderManager classLoaderManager;
    private FileSystemManager fileManager;
    private DefaultFileMonitor fileMonitor;

    private ApplicationManager() {
    }

    /**
     * 懒汉式（双重检查加锁）
     *
     * @return
     */
    public static ApplicationManager getInstance() {
        if (applicationManager == null) {
            synchronized (ApplicationManager.class) {
                if (applicationManager == null) {
                    applicationManager = new ApplicationManager();
                }
            }

        }
        return applicationManager;
    }

    public void start(boolean reloadable) {
        ApplicationManager.reloadable = reloadable;
        init();
    }

    private void init() {
        classLoaderManager=new ClassLoaderManager();
        classLoaderManager.init();
        if (reloadable) {
            URL basePath = this.getClass().getClassLoader().getResource(GlobalSetting.JAR_FOLDER);
            initMonitorForChange(basePath.getPath());
        }
    }

    public void initMonitorForChange(String basePath) {
        try {
            this.fileManager = VFS.getManager();
            File file = new File(basePath);
            FileObject monitoredDir = this.fileManager.resolveFile(file.getAbsolutePath());
            FileListener fileMonitorListener = new JarFileChangeListener();
            this.fileMonitor = new DefaultFileMonitor(fileMonitorListener);
            this.fileMonitor.setRecursive(true);
            this.fileMonitor.addFile(monitoredDir);
            this.fileMonitor.start();
            System.out.println("Now to listen " + monitoredDir.getName().getPath());
        } catch (FileSystemException e) {
            e.printStackTrace();
        }
    }

    public void reloadApplication(String name) {
        this.classLoaderManager.reloadApplication(name);
    }
}
