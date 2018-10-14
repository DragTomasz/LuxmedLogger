package pl.dragdrop.luxmedlogger.luxmed.stages;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.dragdrop.luxmedlogger.utils.CookieHeaderWrapper;

import java.io.IOException;
import java.util.Base64;

public class _2_MainPage extends Page{

    public void getMainPage(CookieHeaderWrapper wrapper, String login, String password) throws IOException {
        Connection.Response response =
                Jsoup.connect("https://portalpacjenta.luxmed.pl/PatientPortal/Account/LogIn")
                        .userAgent(userAgent)
                        .timeout(10 * 1000)
                        .method(Connection.Method.POST)
                        .data("Login", login)
                        .data("Password", password)
                        .followRedirects(true)
                        .execute();
        Document document = response.parse();
        wrapper.addCookies(response.cookies());
        wrapper.setHeaders(response.headers());
    }

    private String decode(String text) {
        return new String(Base64.getDecoder().decode(text));
    }
}
