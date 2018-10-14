package pl.dragdrop.luxmedlogger.luxmed.stages;

import lombok.AllArgsConstructor;
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
public class _5_ReservationListPage extends Page {

    public boolean getReservationList(CookieHeaderWrapper wrapper) throws IOException {
        Connection.Response response =
                Jsoup.connect("https://portalpacjenta.luxmed.pl/PatientPortal/Reservations/Reservation/Find")
                        .timeout(10 * 1000)
                        .method(Connection.Method.POST)
                        .cookies(wrapper.getCookies())
                        .headers(getHeadersForReservationList())
                        .data("DateOption", "SelectedDate")
                        .data("PayersCount", "1")
                        .data("CityId", "8")
                        .data("ClinicId", "9")
                        .data("ServiceId", Doctor.Alergolog.id)
                        .data("LanguageId", "")
                        .data("SearchFirstFree", "false")
                        .data("FromDate", "15-10-2018")
                        .data("ToDate", "19-10-2018")
                        .data("TimeOption", "Morning")
                        .data("PayerId", "45800")
                        .data("__RequestVerificationToken", wrapper.getToken())
                        .data("IsDisabled", "")
                        .followRedirects(true)
                        .execute();

        Document document = response.parse();
        Elements attribute = document.getElementsByAttribute("term-id");
        if (attribute.isEmpty()) {
            log.info("Nie udało się: {}", ++CookieHeaderWrapper.counter);
            return false;
        } else {
            wrapper.setTermId(
                    attribute.stream()
                            .findFirst()
                            .orElseThrow(RuntimeException::new)
                            .attributes()
                            .asList()
                            .stream()
                            .filter(i -> i.getKey().equals("term-id"))
                            .findFirst()
                            .orElseThrow(RuntimeException::new)
                            .getValue()
            );
        }
        wrapper.addCookies(response.cookies());
        wrapper.setHeaders(response.headers());
        log.info("Udało się - liczba znalezionych terminów to: {}", attribute.size());
        return true;
    }

    private Map<String, String> getHeadersForReservationList() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "pl-PL,pl;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.put("Cache-Control", "max-age=0");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Length", "390");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Host", "portalpacjenta.luxmed.pl");
        headers.put("Origin", "https://portalpacjenta.luxmed.pl");
        headers.put("Referer", "https://portalpacjenta.luxmed.pl/PatientPortal/Reservations/Reservation/Find");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("User-Agent", userAgent);
        return headers;
    }

    @AllArgsConstructor
    private enum Doctor {
        Alergolog("4387"), Laryngolog("4522"), Chirurg_Onkolog("4418");
        private String id;
    }


}
