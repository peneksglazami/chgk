/*
 * Copyright 2012-2015 Andrey Grigorov, Anton Grigorov
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.cherchgk.test.ui;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.StdErrLog;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;

/**
 * Запускатель сервера Jetty 8 с приложением для UI-тестирования.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class TestWebAppLauncher {

    private final int port;
    private final Server server;

    public TestWebAppLauncher(int port) {
        Log.setLog(new StdErrLog());
        this.port = port;
        server = new Server();
    }

    public TestWebAppLauncher run() throws Exception {

        System.out.println("Start jetty launcher at " + port);
        System.out.println("Start chgk webapp at " + new File("src/main/webapp").getAbsolutePath());

        // тестовое приложение будет работать с БД, загружаемой в память
        System.setProperty("jdbc.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        Connector connector = new SelectChannelConnector();
        connector.setPort(port);
        connector.setMaxIdleTime(30000);
        server.addConnector(connector);

        WebAppContext context = new WebAppContext("src/main/webapp", "/");
        context.setResourceBase("src/main/webapp");
        context.setParentLoaderPriority(true);

        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        File scratchDir = new File(tempDir.toString(), "embedded-jetty-jsp");
        context.setAttribute("javax.servlet.context.tempdir", scratchDir);
        context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                ".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/.*taglibs.*\\.jar$");

        server.setHandler(context);

        addShutdownHook();
        server.start();
        return this;
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                TestWebAppLauncher.this.stop();
            }
        });
    }

    public final void stop() {
        try {
            System.out.println("Shutdown jetty launcher at " + port);
            server.stop();
        } catch (Exception ignore) {
        }
    }

    public static void main(String[] args) throws Exception {
        new TestWebAppLauncher(7777).run();
    }
}
