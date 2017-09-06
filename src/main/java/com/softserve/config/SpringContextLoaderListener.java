package com.softserve.config;

import lombok.RequiredArgsConstructor;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@RequiredArgsConstructor
public class SpringContextLoaderListener implements ServletContextListener {

    private final AnnotationConfigWebApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);
        context.setServletContext(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //do nothing
    }
}