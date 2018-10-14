package pl.dragdrop.luxmedlogger.luxmed.stages;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.dragdrop.luxmedlogger.utils.CookieHeaderWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class _7_FinalReservationPage extends Page {

    public void getFinalReservation(CookieHeaderWrapper wrapper) throws IOException {
        log.info("Udana próba rezerwacji: " + wrapper.getKey());
        Connection.Response response =
                Jsoup.connect("https://portalpacjenta.luxmed.pl/PatientPortal/Reservations/Reservation/ReserveTerm?key=" + wrapper.getKey() + "&variant=" + wrapper.getVariant())
                        .timeout(10 * 1000)
                        .method(Connection.Method.POST)
                        .cookies(wrapper.getCookies())
                        .headers(getFinalReservationConfirmHeaders())
                        .data("termId", wrapper.getTermId())
                        .followRedirects(true)
                        .execute();
        Document document = response.parse();
        log.info(document.toString());
    }

    private Map<String, String> getFinalReservationConfirmHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "*/*");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "pl-PL,pl;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Length", "0");
        headers.put("Host", "portalpacjenta.luxmed.pl");
        headers.put("Origin", "https://portalpacjenta.luxmed.pl");
        headers.put("Referer", "https://portalpacjenta.luxmed.pl/PatientPortal/Reservations/Reservation/Find");
        headers.put("User-Agent", userAgent);
        headers.put("X-Requested-With", "XMLHttpRequest");
        return headers;
    }
}
