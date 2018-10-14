package pl.dragdrop.luxmedlogger.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class CookieHeaderWrapper {

    private Map<String, String> cookies = BaseCookies.getBaseCookies();
    private Map<String, String> headers = new HashMap<>();
    private String token;
    private String termId;
    private String key;
    private String variant;
    public static int counter;

    public void addCookies(Map<String, String> mapCookies) {
        for (Map.Entry<String, String> map : mapCookies.entrySet()) {
            if (this.cookies.containsKey(map.getKey())) {
                this.cookies.replace(map.getKey(), map.getValue());
            } else {
                this.cookies.put(map.getKey(), map.getValue());
            }
        }
    }
}
