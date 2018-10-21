package pl.dragdrop.luxmedlogger.luxmed.stages;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.dragdrop.luxmedlogger.utils.CookieHeaderWrapper;

import java.io.IOException;

@Slf4j
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
        wrapper.setLoggedIn(document.toString().contains(login));
        log.info("Zalogowano: {}", login);
        wrapper.addCookies(response.cookies());
        wrapper.setHeaders(response.headers());
    }
}
