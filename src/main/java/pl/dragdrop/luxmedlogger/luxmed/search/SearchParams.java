package pl.dragdrop.luxmedlogger.luxmed.search;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchParams {
    private String doctorId;
    private String timeOption;
    private String fromDate;
    private String toDate;
    private String login;
    private String password;
}
