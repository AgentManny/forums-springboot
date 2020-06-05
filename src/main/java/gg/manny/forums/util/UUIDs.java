package gg.manny.forums.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UUIDs {

    private static final Pattern UUID_PATTERN = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}");
    private static final Pattern DASHLESS_PATTERN = Pattern.compile("^([A-Fa-f0-9]{8})([A-Fa-f0-9]{4})([A-Fa-f0-9]{4})([A-Fa-f0-9]{4})([A-Fa-f0-9]{12})$");

    /**
     * Add dashes to a UUID.
     *
     * <p>If dashes already exist, the same UUID will be returned.</p>
     *
     * @param uuid the UUID
     * @return a UUID with dashes
     * @throws IllegalArgumentException thrown if the given input is not actually an UUID
     */
    public static String addDashes(String uuid) {
        uuid = uuid.replace("-", ""); // Remove dashes
        Matcher matcher = DASHLESS_PATTERN.matcher(uuid);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid UUID format");
        }
        return matcher.replaceAll("$1-$2-$3-$4-$5");
    }

    public static UUID parse(String source) {
        if (source != null && !source.isEmpty()) {
            if (UUID_PATTERN.matcher(source).find()) {
                return UUID.fromString(source);
            } else if (DASHLESS_PATTERN.matcher(source).find()) {
                return UUID.fromString(addDashes(source));
            }
        }
        return null;
    }
}
