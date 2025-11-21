package ru.defix.tests;

import org.assertj.core.util.Lists;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {
    public static String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.toString().replaceAll("0+$", "")
                .replaceAll("\\.$", "");
    }

    public static String localTimeToString(LocalTime localTime) {
        return localTime.toString().replaceAll("0+$", "")
                .replaceAll("\\.$", "");
    }
}
