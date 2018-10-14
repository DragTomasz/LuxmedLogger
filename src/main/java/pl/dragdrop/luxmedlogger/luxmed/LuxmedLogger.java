package pl.dragdrop.luxmedlogger.luxmed;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class LuxmedLogger {

    private String user = "test";
    private String password = "test";

    private CookieHeaderWrapper wrapper = new CookieHeaderWrapper();

    private _1_LoginPage login = new _1_LoginPage();
    private _2_MainPage main = new _2_MainPage();
    private _3_CoordinationPage coordination = new _3_CoordinationPage();
    private _4_ActivityPage activity = new _4_ActivityPage();
    private _5_ReservationListPage reservationList = new _5_ReservationListPage();
    private _6_ReservationConfirmPage reservationConfirm = new _6_ReservationConfirmPage();
    private _7_FinalReservationPage finalReservation = new _7_FinalReservationPage();

    public void findDoctor() throws IOException {
        boolean search = true;
        login.getLoginPage(wrapper);
        main.getMainPage(wrapper, user, password);
        coordination.getCoordinationPage(wrapper);
        activity.getActivityPage(wrapper);
        while (search) {
            try {
                search = !reservationList.getReservationList(wrapper);
                Thread.sleep(1_000);
            } catch (IOException e) {
                log.error(e.getMessage());
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        reservationConfirm.getReservation(wrapper);
        reservationConfirm.getReservationConfirm(wrapper);
        finalReservation.getFinalReservation(wrapper);
    }
}