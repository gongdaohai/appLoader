package com.gdh.loader.classloader;


public interface ClassLoaderFactory {


    public ClassLoader createClassLoader(ClassLoader parentClassLoader, String... paths);

}
