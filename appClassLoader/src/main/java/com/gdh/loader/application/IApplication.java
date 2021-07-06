package com.gdh.loader.application;

import com.gdh.loader.config.AppConfig;

/**
 * 自定义程序需实现的接口
 */
public interface IApplication {
    /**
     * 初始化
     */
    void init(AppConfig config);

    /**
     * 执行
     */
    void execute();

    /**
     * 销毁
     */
    void destory();

}