package pl.dragdrop.luxmedlogger.luxmed.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TimeOfDay {

    any("Any"), from7to9("Morning"), from9to17("Afternoon"), from17to21("Evening");

    @Getter
    String timeOption;
}
