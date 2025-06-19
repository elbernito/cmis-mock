package ch.elbernito.cmis.mock.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VersionUtil {

    private VersionUtil() {
        // Prevent instantiation
    }

    /**
     * Returns the next version number as a String.
     * If input is null or empty, returns "1".
     * 
     * @param version current version as String
     * @return incremented version as String
     */
    public static String getNextVersion(String version) {
        if (version == null || version.trim().isEmpty()) {
            log.info("No version provided. Starting with version '1'.");
            return "1";
        }
        try {
            int v = Integer.parseInt(version.trim());
            v++;
            log.info("Incrementing version from '{}' to '{}'.", version, v);
            return String.valueOf(v);
        } catch (NumberFormatException e) {
            log.warn("Invalid version '{}', returning '1' as fallback.", version);
            return "1";
        }
    }
}