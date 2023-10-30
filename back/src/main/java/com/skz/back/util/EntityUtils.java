package com.skz.back.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public final class EntityUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityUtils.class);

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);

    public static final Predicate<String> isStandardEmail = s ->
            s != null && VALID_EMAIL_ADDRESS_REGEX.matcher(s).find();

    public static final Predicate<String> isStrongPassword = password -> {
        if (password.length() < 8) {
            return false;
        }

        // Must have 1 Uppercase, 1 Lowercase, 1 number, 1 special char
        return Pattern.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()-_+=]).*$", password);
    };

    public static final Predicate<String> containsOnlyNonNumeric = str ->
         str.chars().allMatch(ch -> (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'));

    public static final Predicate<LocalDate> isMoreThan14YearsAgo = date ->
         LocalDate.now().minusYears(14).isAfter(date);

    private EntityUtils() {
    }

    public static <T> T copyNonNullAttributes(T source, T destination, Class<T> sourceClass) {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Source and destination objects must not be null.");
        }
        LOGGER.info(String.format("EntityUtils::copyNonNullAttributes(Class, T, T): %s\n%s\n%s", sourceClass, source, destination));

        Arrays.stream(sourceClass.getDeclaredFields())
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        Object sourceValue = field.get(source);
                        if (sourceValue != null) {
                            field.set(destination, sourceValue);
                        }
                    } catch (IllegalAccessException e) {
                        LOGGER.error("Error coping not null values ...");
                    }
                });

        return destination;
    }

}
