package pl.dragdrop.luxmedlogger.luxmed.stages;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import pl.dragdrop.luxmedlogger.utils.CookieHeaderWrapper;

import java.io.IOException;

public class _1_LoginPage extends Page{

    public CookieHeaderWrapper getLoginPage(CookieHeaderWrapper wrapper) throws IOException {
        Connection.Response response =
                Jsoup.connect("https://portalpacjenta.luxmed.pl/PatientPortal/Account/LogOn")
                        .userAgent(userAgent)
                        .timeout(10 * 1000)
                        .method(Connection.Method.GET)
                        .followRedirects(true)
                        .execute();

        wrapper.addCookies(response.cookies());
        wrapper.setHeaders(response.headers());
        return  wrapper;
    }
}
