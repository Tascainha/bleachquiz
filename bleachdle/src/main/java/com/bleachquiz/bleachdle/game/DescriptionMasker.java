package com.bleachquiz.bleachdle.game;

import java.util.regex.Pattern;

final class DescriptionMasker {

    private DescriptionMasker() {
    }

    static String mask(String description, String characterName) {
        String masked = description;
        for (String part : characterName.split("\\s+")) {
            if (part.isBlank()) {
                continue;
            }
            Pattern pattern = Pattern.compile("(?i)\\b" + Pattern.quote(part) + "\\b");
            masked = pattern.matcher(masked).replaceAll("█".repeat(part.length()));
        }
        return masked;
    }
}
