package org.springframework.samples.jpetstore;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.DispatcherServlet;

public final class TestApplication {

	public static final String CONTEXT_PATH = "/jpetstore";

	private static MockServletContext servletContext;

	private static GenericWebApplicationContext rootContext;

	private static DispatcherServlet dispatcherServlet;

	private TestApplication() {
	}

	public static synchronized void reset() throws Exception {
		if (dispatcherServlet == null) {
			servletContext = new MockServletContext("src/main/webapp", new FileSystemResourceLoader());
			rootContext = createRootContext(servletContext);
			servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, rootContext);
			DispatcherServlet servlet = new DispatcherServlet();
			servlet.init(new MockServletConfig(servletContext, "petstore"));
			dispatcherServlet = servlet;
		}
		TestDatabase.reset((DataSource) rootContext.getBean("dataSource"));
	}

	public static <T> T bean(Class<T> type) {
		return type.cast(rootContext.getBeansOfType(type).values().iterator().next());
	}

	public static ServletContext servletContext() {
		return servletContext;
	}

	public static Servlet dispatcherServlet() {
		return dispatcherServlet;
	}

	private static GenericWebApplicationContext createRootContext(ServletContext servletContext) {
		GenericWebApplicationContext context = new GenericWebApplicationContext();
		context.setServletContext(servletContext);
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(context);
		reader.loadBeanDefinitions(new ServletContextResource(servletContext, "/WEB-INF/dataAccessContext-local.xml"));
		reader.loadBeanDefinitions(new ServletContextResource(servletContext, "/WEB-INF/applicationContext.xml"));
		RootBeanDefinition dataSource = new RootBeanDefinition(DriverManagerDataSource.class);
		dataSource.getPropertyValues().addPropertyValue("driverClassName", TestDatabase.DRIVER);
		dataSource.getPropertyValues().addPropertyValue("url", TestDatabase.URL);
		dataSource.getPropertyValues().addPropertyValue("username", "sa");
		dataSource.getPropertyValues().addPropertyValue("password", "");
		context.registerBeanDefinition("dataSource", dataSource);
		context.refresh();
		return context;
	}
}
