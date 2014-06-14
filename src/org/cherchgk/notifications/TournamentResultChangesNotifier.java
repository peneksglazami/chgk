package org.cherchgk.notifications;

import org.atmosphere.config.service.MeteorService;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.Meteor;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.atmosphere.cpr.AtmosphereResource.TRANSPORT.LONG_POLLING;

/**
 * Сервлет, использующийся для организации online-оповещения открытых страниц
 * редактирования результатов турнира о том, что результаты турнира были
 * изменены. Данный механизм необходим для того, чтобы открытые в браузерах
 * страницы редактирования результатов турнира всегда отображали актуальные
 * результаты.
 * Для каждого отдельного турнира создаётся отдельный broadcaster.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
@MeteorService(path = "/*", interceptors = {AtmosphereResourceLifecycleInterceptor.class})
public class TournamentResultChangesNotifier extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tournamentId = req.getParameter("chgk-tournament-id");
        Meteor meteor = Meteor.build(req);
        Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(tournamentId, true);
        meteor.setBroadcaster(broadcaster);
        meteor.resumeOnBroadcast(meteor.transport() == LONG_POLLING).suspend(-1);
    }
}
