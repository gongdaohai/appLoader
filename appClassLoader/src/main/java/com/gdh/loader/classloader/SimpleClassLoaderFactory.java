package com.gdh.loader.classloader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;


public class SimpleClassLoaderFactory implements ClassLoaderFactory {

    @Override
    public ClassLoader createClassLoader(ClassLoader parentClassLoader, String... folders) {

        List<URL> jarsToLoad = new ArrayList<URL>();
        for (String folder : folders) {
            List<String> jarPaths = scanJarFiles(folder);
            for (String jar : jarPaths) {
                try {
                    File file = new File(jar);
                    jarsToLoad.add(file.toURI().toURL());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }

        URL[] urls = new URL[jarsToLoad.size()];
        jarsToLoad.toArray(urls);

        return new URLClassLoader(urls, parentClassLoader);
    }


    private List<String> scanJarFiles(String folderPath) {

        List<String> jars = new ArrayList<String>();
        File folder = new File(folderPath);
        if (!folder.isDirectory()) {
            throw new RuntimeException("The file path to scan for the jars is not a directory, path:" + folderPath);
        }
        for (File f : folder.listFiles()) {
            if (!f.isFile()) {
                continue;
            }
            String name = f.getName();

            // check the file is a .jar file
            if (name == null || name.length() == 0) {
                continue;
            }

            int extIndex = name.lastIndexOf(".");
            if (extIndex < 0) {
                continue;
            }

            String ext = name.substring(extIndex);
            if (!ext.equalsIgnoreCase(".jar")) {
                continue;
            }

            jars.add(folderPath + "/" + name);
        }
        return jars;
    }
}
