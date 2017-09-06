package com.softserve;

import com.codahale.metrics.health.HealthCheck;
import com.softserve.config.MainConfiguration;
import com.softserve.config.SpringContextLoaderListener;
import com.softserve.controller.ItemController;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.ws.rs.Path;
import java.util.Arrays;
import java.util.Map;

public class MainApplication extends Application<MainConfiguration>{


    public static void main(String[] args) throws Exception {
        new MainApplication().run(args);
    }

    public void run(MainConfiguration configuration, Environment environment) throws Exception {
        AnnotationConfigWebApplicationContext parent = new AnnotationConfigWebApplicationContext();
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        parent.refresh();
        parent.getBeanFactory().registerSingleton("configuration",configuration);
        parent.registerShutdownHook();
        parent.start();

        ctx.setParent(parent);
        ctx.register(MainSpringConfiguration.class);
        ctx.refresh();
        ctx.registerShutdownHook();
        ctx.start();

        Arrays.asList(ctx.getBeanDefinitionNames()).forEach(System.out::println);

        //health checks
        Map<String, HealthCheck> healthChecks = ctx.getBeansOfType(HealthCheck.class);
        for(Map.Entry<String,HealthCheck> entry : healthChecks.entrySet()) {
            environment.healthChecks().register("template", entry.getValue());
        }

        //resources
        Map<String, Object> resources = ctx.getBeansWithAnnotation(Path.class);
        for(Map.Entry<String, Object> entry : resources.entrySet()) {
            environment.jersey().register(entry.getValue());
        }

        //last, but not least,let's link Spring to the embedded Jetty in Dropwizard
        environment.servlets().addServletListeners(new SpringContextLoaderListener(ctx));
        final ItemController itemController = new ItemController();
        environment.jersey().register(itemController);
    }
}
