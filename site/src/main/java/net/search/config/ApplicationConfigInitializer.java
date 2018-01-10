package net.search.config;

import net.search.servlet.HelloWorldServlet;
import net.search.servlet.JavaConfigServlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Set;

public class ApplicationConfigInitializer implements ServletContainerInitializer {
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        HelloWorldServlet servlet = new HelloWorldServlet();
        ServletRegistration.Dynamic servletConfig = servletContext.addServlet("HelloWorldServlet", servlet);
        servletConfig.addMapping("/hello-world");
        System.out.println("ApplicationConfigInitializer: hello-world");

        JavaConfigServlet javaConfigServlet = new JavaConfigServlet();
        ServletRegistration.Dynamic javaServletConfig = servletContext.addServlet("javaConfigServlet", javaConfigServlet);
        javaServletConfig.addMapping("/java");
        System.out.println("ApplicationConfigInitializer: java");
    }
}
