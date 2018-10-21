package pl.dragdrop.luxmedlogger.luxmed.search;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;


@AllArgsConstructor
public enum Doctor {

    Alergolog("4387"), Laryngolog("4522"), Chirurg_Onkolog("4418");
    private String id;

    public String getId() {
        return id;
    }

    public static String getDesc(String id) {
        for (Doctor doctor : Doctor.values()) {
            if (doctor.getId().equals(id)) {
                return doctor.name();
            }
        }
        return Strings.EMPTY;
    }
}
