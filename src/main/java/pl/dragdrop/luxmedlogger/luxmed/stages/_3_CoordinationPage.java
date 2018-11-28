package pl.dragdrop.luxmedlogger.luxmed.stages;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.dragdrop.luxmedlogger.utils.CookieHeaderWrapper;

import java.io.IOException;

@Slf4j
public class _3_CoordinationPage extends Page{

    public CookieHeaderWrapper getCoordinationPage(CookieHeaderWrapper wrapper) throws IOException {
        Connection.Response response =
                Jsoup.connect("https://portalpacjenta.luxmed.pl/PatientPortal/Reservations/Coordination")
                        .userAgent(userAgent)
                        .timeout(10 * 1000)
                        .method(Connection.Method.GET)
                        .cookies(wrapper.getCookies())
                        .headers(wrapper.getHeaders())
                        .followRedirects(true)
                        .execute();
        Document document = response.parse();
        wrapper.addCookies(response.cookies());
        wrapper.setHeaders(response.headers());
        return wrapper;
    }

}
