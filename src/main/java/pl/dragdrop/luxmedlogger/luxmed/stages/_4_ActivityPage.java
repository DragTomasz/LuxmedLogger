package pl.dragdrop.luxmedlogger.luxmed.stages;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.dragdrop.luxmedlogger.utils.CookieHeaderWrapper;

import java.io.IOException;

public class _4_ActivityPage extends Page {

    public CookieHeaderWrapper getActivityPage(CookieHeaderWrapper wrapper) throws IOException {
        Connection.Response response =
                Jsoup.connect("https://portalpacjenta.luxmed.pl/PatientPortal/Reservations/Coordination/Activity?actionId=90")
                        .userAgent(userAgent)
                        .cookies(wrapper.getCookies())
                        .headers(wrapper.getHeaders())
                        .timeout(10 * 1000)
                        .method(Connection.Method.GET)
                        .followRedirects(true)
                        .execute();

        Document document = response.parse();
        wrapper.setToken(
                document.getElementsByClass("advancedReservationBox")
                        .stream()
                        .map(i -> i.getElementsByAttributeValue("name", "__RequestVerificationToken"))
                        .findFirst()
                        .orElseThrow(RuntimeException::new)
                        .stream()
                        .flatMap(i -> i.attributes().asList().stream())
                        .filter(i -> i.getKey().equals("value")).findFirst().orElseThrow(RuntimeException::new).getValue()
        );

        wrapper.addCookies(response.cookies());
        wrapper.setHeaders(response.headers());
        return wrapper;
    }
}
