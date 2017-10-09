
package com.softserve;

import java.util.Arrays;
import java.util.Map;

import javax.sql.DataSource;
import javax.ws.rs.Path;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.softserve.config.MainConfiguration;
import com.softserve.config.SpringContextLoaderListener;
import com.softserve.healthCheck.DBHealthCheck;
import com.softserve.resource.ItemResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class MainApplication extends Application<MainConfiguration> {

    AnnotationConfigWebApplicationContext ctx;

    public static void main(String[] args) throws Exception {
        new MainApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<MainConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<MainConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(MainConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    public void run(MainConfiguration configuration, Environment environment) throws Exception {
        setUpSpringContext(configuration, environment);
        DataSource dataSource = (DataSource) ctx.getBean("dataSource");
        environment.healthChecks().register("DataBase", new DBHealthCheck(dataSource));
        environment.jersey().register(new ItemResource());
    }

    private void setUpSpringContext(MainConfiguration configuration, Environment environment) {
        AnnotationConfigWebApplicationContext parent = new AnnotationConfigWebApplicationContext();
        ctx = new AnnotationConfigWebApplicationContext();
        parent.refresh();
        parent.getBeanFactory().registerSingleton("configuration", configuration);
        parent.registerShutdownHook();
        parent.start();

        ctx.setParent(parent);
        ctx.register(MainSpringConfiguration.class);
        ctx.refresh();
        ctx.registerShutdownHook();
        ctx.start();

        Arrays.asList(ctx.getBeanDefinitionNames()).forEach(System.out::println);

        // resources
        Map<String, Object> resources = ctx.getBeansWithAnnotation(Path.class);
        for (Map.Entry<String, Object> entry : resources.entrySet()) {
            environment.jersey().register(entry.getValue());
        }

        // last, but not least,let's link Spring to the embedded Jetty in
        // Dropwizard
        environment.servlets().addServletListeners(new SpringContextLoaderListener(ctx));
    }
}
