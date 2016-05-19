package com.complaint.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.complaint.service.InitPartService;

public class CustomServletContextListener implements ServletContextListener {
	/**
	 * 用户监听servlet启动时，回去tomacat路径
	 */
	protected ServletContext servletContext;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
//		查出tomacat路径
		servletContext = arg0.getServletContext();	
//		获取tomocat路径拼接到images中
		String path = servletContext.getRealPath("");
//		为了调用初始化图片的service实例化此类
		ApplicationContext txt = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
//		调用初始化图片的service
		InitPartService initPartService = (InitPartService)txt.getBean("initPartService");
//		传入tomacat的路径初始化综合阈值图片
		initPartService.initIntegrationMap(path);
//		传入tomacat的路径初始化测试图例图片
		initPartService.initKpiMap(path);
		
	}
	
	protected ServletContext getServletContext(){
		return servletContext;
	}

}
