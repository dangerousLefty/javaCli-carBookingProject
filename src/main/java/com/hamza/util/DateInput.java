package com.hamza.util;

import java.time.LocalDate;

public class DateInput {
    public LocalDate parseDateFromInput(String input) throws Exception {
        String[] parts = input.split("\\s+");

        if (parts.length != 3) {
            throw new Exception("Invalid format. Use mm dd yyyy.");
        }

        for (String part : parts) {
            if (!part.matches("\\d+")) {
                throw new Exception("All inputs must be numeric.");
            }
        }

        int month = Integer.parseInt(parts[0]);
        int day   = Integer.parseInt(parts[1]);
        int year  = Integer.parseInt(parts[2]);

        if (month < 1 || month > 12) {
            throw new Exception("Invalid month.");
        }

        if (day < 1 || day > 31) {
            throw new Exception("Invalid day.");
        }

        if (year < 1900 || year > 2099){
            throw new Exception("Invalid year.");
        }

        return LocalDate.of(year,month,day);
    }

    public boolean isStartDateBeforeEndDate(LocalDate start, LocalDate end){
        return start.isBefore(end);
    }
}
