package com.apolloits.viaplus.btvltoitagandiclptranslator.utility;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Component
public class DateTimeFormatter {

    public static String getFormattedNow() {
        ZonedDateTime now = ZonedDateTime.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
        String formattedNow = now.format(formatter);
        return formattedNow;
    }
}
