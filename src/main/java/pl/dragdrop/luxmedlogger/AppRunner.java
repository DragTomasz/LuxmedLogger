package pl.dragdrop.luxmedlogger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.dragdrop.luxmedlogger.luxmed.LuxmedLogger;
import pl.dragdrop.luxmedlogger.luxmed.search.Doctor;
import pl.dragdrop.luxmedlogger.luxmed.search.SearchParams;
import pl.dragdrop.luxmedlogger.luxmed.search.TimeOfDay;
import java.util.ArrayList;
import java.util.List;

@Component
public class AppRunner implements CommandLineRunner {

    private final LuxmedLogger luxmedLogger;

    public AppRunner(LuxmedLogger luxmedLogger) {
        this.luxmedLogger = luxmedLogger;
    }

    @Override
    public void run(String... args) {
        List<SearchParams> paramsList = createSearchParams();
        luxmedLogger.findDoctor(paramsList.get(0));
        luxmedLogger.findDoctor(paramsList.get(1));
    }

    private List<SearchParams> createSearchParams() {
        List<SearchParams> paramsList = new ArrayList<>();
        String password = "";
        String login = "";
        paramsList.add(new SearchParams(Doctor.Alergolog.getId(), TimeOfDay.any.getTimeOption(), "22-10-2018", "23-10-2018", login, password));
        paramsList.add(new SearchParams(Doctor.Chirurg_Onkolog.getId(), TimeOfDay.any.getTimeOption(), "22-10-2018", "23-10-2018", login, password));
        return paramsList;
    }
}