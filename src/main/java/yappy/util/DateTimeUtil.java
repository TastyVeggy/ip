package yappy.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Utility class for parsing and formatting {@code LocalDateTime} objects.
 *
 * Supported input formats:
 * <ul>
 * <li>dd-MM-yyyy HH:mm</li>
 * <li>MMM d yyyy, h:mm a</li>
 * </ul>
 */
public final class DateTimeUtil {

    private record Format(DateTimeFormatter formatter, String format, String example) {
    }

    private static final List<Format> SUPPORTED_FORMATS = List.of(
            new Format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"), "dd-MM-yyyy HH:mm",
                    "07-08-2025 07:00"),
            new Format(DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm"), "d MMM yyyy, HH:mm",
                    "7 Aug 2025, 07:00"));

    private static final DateTimeFormatter DEFAULT_OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");

    private DateTimeUtil() {
        throw new UnsupportedOperationException("Utility class");
    }


    /**
     * Formats a {@code LocalDateTime} object into the default string representation.
     *
     * @param dateTime The {@code LocalDateTime} object to format.
     * @return The default string representation.
     */
    public static String format(LocalDateTime dateTime) {
        return dateTime.format(DEFAULT_OUTPUT_FORMAT);
    }

    /**
     * Parses a string into a {@code LocalDateTime} object if the format is supported.
     *
     * @param input The date-time string to be parsed.
     * @return The parsed {@code LocalDateTime} object.
     * @throws DateTimeParseException If the input does not match any supported format.
     */
    public static LocalDateTime parse(String input) throws DateTimeParseException {
        for (Format format : SUPPORTED_FORMATS) {
            try {
                return LocalDateTime.parse(input, format.formatter());
            } catch (DateTimeParseException ignored) {
                // Try next format
            }
        }
        throw new DateTimeParseException("Unsupported date format: " + input, input, 0);
    }

    /**
     * Returns a list of supported date-time formats with usage examples.
     *
     * @return List of supported date-time formats with usage examples.
     */
    public static List<String> getUsageForSupportedFormats() {
        return SUPPORTED_FORMATS.stream()
                .map(f -> String.format(f.format + " (E.g.: " + f.example + ")")).toList();
    }
}

