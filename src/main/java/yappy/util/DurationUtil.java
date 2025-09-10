package yappy.util;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for parsing and formatting {@code Duration} objects.
 */
public final class DurationUtil {

    /**
     * Parse a string of the format XhYmZs (e.g. 2h10m3s) into a {@code Duration} object.
     *
     * The only units of time supported are hence only hours, mins and seconds.
     *
     * @param input The duration string to be parsed.
     * @return The {@code Duration} object.
     * @throws IllegalArgumentException If the input does not match the above specified format.
     */
    public static Duration parse(String input) throws IllegalArgumentException {
        input = input.trim().toLowerCase();

        Pattern pattern = Pattern.compile("(?:(\\d+)h)?(?:(\\d+)m)?(?:(\\d+)s)?");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid duration format: " + input);
        }

        long hours = matcher.group(1) != null ? Long.parseLong(matcher.group(1)) : 0;
        long minutes = matcher.group(2) != null ? Long.parseLong(matcher.group(2)) : 0;
        long seconds = matcher.group(3) != null ? Long.parseLong(matcher.group(3)) : 0;
        return Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    }

    /**
     * Formats a {@code Duration} object into the default string representation.
     *
     * @param duration The {@code Duration} object to format.
     * @return The default string representation.
     */
    public static String format(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        StringBuilder sb = new StringBuilder();
        if (hours > 0) {
            sb.append(hours).append("h ");
        }
        if (minutes > 0) {
            sb.append(minutes).append("m ");
        }
        if (seconds > 0 || sb.length() == 0) {
            sb.append(seconds).append("s");
        }

        return sb.toString().trim();
    }
}
