package com.briup.woss.main;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jTest {
	public static void main(String[] args) {
		//构建log4j对象
		//getRootLogger()
		//getLogger(Log4jTest.class)
		//声明
		Logger logger=Logger.getRootLogger();
		//初始化log4j配置信息
		//BasicConfigurator.configure();
		//初始化
		PropertyConfigurator.configure("src/com/briup/woss/File/log4j.properties");
		//debug info warn error
		//设置日志级别
		logger.setLevel(Level.INFO);
		logger.debug("log4j--debug");
		logger.info("log4j--info");
		logger.warn("log4j--warn");
		logger.error("log4j--error");
	}

}
