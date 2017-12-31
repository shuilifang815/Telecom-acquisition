package com.briup.util;

import java.util.Properties;



public class LoggerImpl implements Logger{
    //rg.apache.log4j.lf5.config
	  org.apache.log4j.Logger logger=org.apache.log4j.Logger.getRootLogger();
	 
	
	@Override
	public void init(Properties p) {
		// TODO Auto-generated method stub
		
	}
    //调试信息
	@Override
	public void debug(String s) {
		// TODO Auto-generated method stub
		logger.debug(s);
	}
    //系统错误信息
	@Override
	public void error(String s) {
		// TODO Auto-generated method stub
		logger.error(s);
	}

	@Override
	public void fatal(String s) {
		// TODO Auto-generated method stub
		logger.fatal(s);
	}

	@Override
	public void info(String s) {
		// TODO Auto-generated method stub
		logger.info(s);
	}

	@Override
	public void warn(String s) {
		// TODO Auto-generated method stub
		logger.warn(s);
	}
	
	
	

}
