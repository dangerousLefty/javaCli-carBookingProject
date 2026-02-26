package com.hamza.util;

import java.time.LocalDate;
import java.util.Optional;

public class DateInput {
    public static Optional<LocalDate> parseDateFromInput(String input) {
        try {
            String[] parts = input.split("\\s+");

            if (parts.length != 3) {
                System.out.println("Invalid format. Use mm dd yyyy.");
                return Optional.empty();
            }

            for (String part : parts) {
                if (!part.matches("\\d+")) {
                    System.out.println("All inputs must be numeric.");
                    return Optional.empty();
                }
            }

            int month = Integer.parseInt(parts[0]);
            int day = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);

            if (month < 1 || month > 12) {
                System.out.println("Invalid month.");
                return Optional.empty();
            }

            if (day < 1 || day > 31) {
                System.out.println("Invalid day.");
                return Optional.empty();
            }

            if (year < 1900 || year > 2099) {
                System.out.println("Invalid year.");
                return Optional.empty();
            }

            return Optional.of(LocalDate.of(year, month, day));
        } catch (Exception e) {
            System.out.println("Invalid date.");
            return Optional.empty();
        }
    }

    public static boolean isStartDateBeforeEndDate(LocalDate start, LocalDate end) {
        return start.isBefore(end);
    }
}
