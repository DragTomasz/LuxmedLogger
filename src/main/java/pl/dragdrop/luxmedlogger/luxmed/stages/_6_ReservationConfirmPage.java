package pl.dragdrop.luxmedlogger.luxmed.stages;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.dragdrop.luxmedlogger.utils.CookieHeaderWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class _6_ReservationConfirmPage extends Page {

    private static final String finalRequest = "'/PatientPortal/Reservations/Reservation/ReserveTerm?key=";

    public void getReservation(CookieHeaderWrapper wrapper) throws IOException {
        Connection.Response response =
                Jsoup.connect("https://portalpacjenta.luxmed.pl/PatientPortal/Reservations/ChangeTerm/GetUpcomingReservationIdsAvailableForChangeTermFor?termId=" + wrapper.getTermId())
                        .userAgent(userAgent)
                        .ignoreContentType(true)
                        .timeout(10 * 1000)
                        .method(Connection.Method.GET)
                        .cookies(wrapper.getCookies())
                        .headers(getReservationHeaders())
                        .followRedirects(true)
                        .execute();
        wrapper.addCookies(response.cookies());
        wrapper.setHeaders(response.headers());
    }

    public void getReservationConfirm(CookieHeaderWrapper wrapper) throws IOException {
        Connection.Response response =
                Jsoup.connect("https://portalpacjenta.luxmed.pl/PatientPortal/Reservations/Reservation/ReservationConfirmation")
                        .timeout(10 * 1000)
                        .method(Connection.Method.POST)
                        .cookies(wrapper.getCookies())
                        .headers(getReservationConfirmHeaders())
                        .data("termId", wrapper.getTermId())
                        .followRedirects(true)
                        .execute();
        Document document = response.parse();
        Elements elements = document.getElementsByAttributeValue("type", "text/javascript");
        String dataNode = elements.stream().findFirst().orElseThrow(RuntimeException::new).data();
        int variantIndexOf = dataNode.indexOf("var variant = ");
        wrapper.setVariant(String.valueOf(dataNode.charAt("var variant = ".length() + variantIndexOf)));
        wrapper.setKey(getKeyFromScrip(dataNode));
        log.info(document.toString());
    }

    private Map<String, String> getReservationHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "*/*");
        headers.put("Content-Type", "application/xml");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "pl-PL,pl;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.put("Connection", "keep-alive");
        headers.put("Host", "portalpacjenta.luxmed.pl");
        headers.put("Referer", "https://portalpacjenta.luxmed.pl/PatientPortal/Reservations/Reservation/Find");
        headers.put("User-Agent", userAgent);
        headers.put("X-Requested-With", "XMLHttpRequest");
        return headers;
    }

    private Map<String, String> getReservationConfirmHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "*/*");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "pl-PL,pl;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Length", "43");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("Host", "portalpacjenta.luxmed.pl");
        headers.put("Origin", "https://portalpacjenta.luxmed.pl");
        headers.put("Referer", "https://portalpacjenta.luxmed.pl/PatientPortal/Reservations/Reservation/Find");
        headers.put("User-Agent", userAgent);
        headers.put("X-Requested-With", "XMLHttpRequest");
        return headers;
    }

    private String getKeyFromScrip(String dataNode) {
        StringBuilder sb = new StringBuilder();
        int index = dataNode.indexOf(finalRequest) + finalRequest.length();
        for (; index < dataNode.length(); index++) {
            if (dataNode.charAt(index) == '\'') {
                return sb.toString();
            }
            sb.append(dataNode.charAt(index));
        }
        return null;
    }
}
