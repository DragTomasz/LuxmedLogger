package pl.dragdrop.luxmedlogger.luxmed.stages;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.dragdrop.luxmedlogger.luxmed.search.Doctor;
import pl.dragdrop.luxmedlogger.luxmed.search.SearchParams;
import pl.dragdrop.luxmedlogger.utils.CookieHeaderWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class _5_ReservationListPage extends Page {

    public Boolean getReservationList(CookieHeaderWrapper wrapper, SearchParams params) throws IOException {
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
                        .data("ServiceId", params.getDoctorId())
                        .data("LanguageId", "")
                        .data("SearchFirstFree", "false")
                        .data("FromDate", params.getFromDate())
                        .data("ToDate", params.getToDate())
                        .data("TimeOfDay", params.getTimeOption())
                        .data("PayerId", "45800")
                        .data("__RequestVerificationToken", wrapper.getToken())
                        .data("IsDisabled", "")
                        .followRedirects(true)
                        .execute();


        Document document = response.parse();
        wrapper.setLoggedIn(document.toString().contains(params.getLogin().toLowerCase()));
        Elements attribute = document.getElementsByAttribute("term-id");
        if (attribute.isEmpty()) {
            if ((wrapper.incrementCounter() % 10) == 0) log.info("Nie udało się: {}, {}", wrapper.getCounter(), Doctor.getDesc(params.getDoctorId()));
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
}
