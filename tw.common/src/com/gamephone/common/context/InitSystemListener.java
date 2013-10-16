package com.gamephone.common.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class InitSystemListener implements ServletContextListener {

	final static Logger loger = Logger.getLogger(InitSystemListener.class);
	
	private static final String CONFIG_LOCATION_PARAM = "initConfigLocation";
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		loger.info("initialize system properties");
		String configFile = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);
		if (StringUtils.isNotBlank(configFile)){
			SystemProperties.loadPropertyFile(configFile);
		}
	}

}
