package pl.dragdrop.luxmedlogger.luxmed;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.dragdrop.luxmedlogger.luxmed.search.Doctor;
import pl.dragdrop.luxmedlogger.luxmed.search.SearchParams;
import pl.dragdrop.luxmedlogger.luxmed.stages._4_ActivityPage;
import pl.dragdrop.luxmedlogger.luxmed.stages._3_CoordinationPage;
import pl.dragdrop.luxmedlogger.luxmed.stages._5_ReservationListPage;
import pl.dragdrop.luxmedlogger.luxmed.stages._1_LoginPage;
import pl.dragdrop.luxmedlogger.luxmed.stages._2_MainPage;
import pl.dragdrop.luxmedlogger.luxmed.stages._6_ReservationConfirmPage;
import pl.dragdrop.luxmedlogger.luxmed.stages._7_FinalReservationPage;
import pl.dragdrop.luxmedlogger.utils.CookieHeaderWrapper;

import java.io.IOException;

@NoArgsConstructor
@Slf4j
@Service
public class LuxmedLogger {

    private _1_LoginPage login = new _1_LoginPage();
    private _2_MainPage main = new _2_MainPage();
    private _3_CoordinationPage coordination = new _3_CoordinationPage();
    private _4_ActivityPage activity = new _4_ActivityPage();
    private _5_ReservationListPage reservationList = new _5_ReservationListPage();
    private _6_ReservationConfirmPage reservationConfirm = new _6_ReservationConfirmPage();
    private _7_FinalReservationPage finalReservation = new _7_FinalReservationPage();

    @Async
    public void findDoctor(SearchParams params) {
        log.info("Starts thread looking for {}", Doctor.getDesc(params.getDoctorId()));
        CookieHeaderWrapper wrapper = new CookieHeaderWrapper();
        do {
            try {
                wrapper.setFounded(false);
                login.getLoginPage(wrapper);
                main.getMainPage(wrapper, params.getLogin(), params.getPassword());
                coordination.getCoordinationPage(wrapper);
                activity.getActivityPage(wrapper);
                while (!wrapper.getFounded() && wrapper.getLoggedIn()) {
                    wrapper.setFounded(reservationList.getReservationList(wrapper, params));
                    if (wrapper.getFounded()) {
                        reservationConfirm.getReservation(wrapper);
                        reservationConfirm.getReservationConfirm(wrapper);
                        finalReservation.getFinalReservation(wrapper);
                    }
                    Thread.sleep(1_000);
                }
            } catch (IOException e) {
                log.error(e.getMessage() + " : " + Doctor.getDesc(params.getDoctorId()));
            } catch (InterruptedException e) {
                log.error(e.getMessage() + " : " + Doctor.getDesc(params.getDoctorId()));
                Thread.currentThread().interrupt();
            }
        } while (wrapper.getKeepSearching());
    }
}