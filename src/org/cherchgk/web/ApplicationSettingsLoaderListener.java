package org.cherchgk.web;

import org.cherchgk.utils.ApplicationSettings;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.Properties;

/**
 * Класс, выполняющий начальную загрузку настроек приложения.
 * Осуществялется чтение конфигурационного файла WEB-INF/chgk.properties.
 *
 * @author Andrey Grigorov
 */
public class ApplicationSettingsLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Properties properties = new Properties();
        try {
            properties.load(servletContextEvent.getServletContext().getResourceAsStream("WEB-INF/chgk.properties"));
            ApplicationSettings.setProperties(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
