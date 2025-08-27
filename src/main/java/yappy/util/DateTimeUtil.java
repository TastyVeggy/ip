package yappy.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public final class DateTimeUtil {

    private record Format(DateTimeFormatter formatter, String format, String example) {
    }

    private static final List<Format> SUPPORTED_FORMATS = List.of(
            new Format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"), "dd-MM-yyyy HH:mm",
                    "26-08-2025 15:00"),
            new Format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a"), "MMM d yyyy, h:mm a",
                    "Aug 26 2025, 3:00 PM"));

    private DateTimeUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    private static final DateTimeFormatter DEFAULT_OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");

    public static String format(LocalDateTime dateTime) {
        return dateTime.format(DEFAULT_OUTPUT_FORMAT);
    }

    public static LocalDateTime parse(String input) throws DateTimeParseException {
        for (Format format : SUPPORTED_FORMATS) {
            try {
                return LocalDateTime.parse(input, format.formatter());
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new DateTimeParseException("Unsupported date format: " + input, input, 0);
    }

    public static List<String> getUsageForSupportedFormats() {
        return SUPPORTED_FORMATS.stream()
                .map(f -> String.format(f.format + " (E.g.: " + f.example + ")")).toList();
    }
}

