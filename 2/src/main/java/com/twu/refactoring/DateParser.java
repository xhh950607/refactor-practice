package com.twu.refactoring;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class DateParser {
    private final String dateAndTimeString;
    private static final HashMap<String, TimeZone> KNOWN_TIME_ZONES = new HashMap<String, TimeZone>();

    static {
        KNOWN_TIME_ZONES.put("UTC", TimeZone.getTimeZone("UTC"));
    }

    /**
     * Takes a date in ISO 8601 format and returns a date
     *
     * @param dateAndTimeString - should be in format ISO 8601 format
     *                          examples -
     *                          2012-06-17 is 17th June 2012 - 00:00 in UTC TimeZone
     *                          2012-06-17TZ is 17th June 2012 - 00:00 in UTC TimeZone
     *                          2012-06-17T15:00Z is 17th June 2012 - 15:00 in UTC TimeZone
     */
    public DateParser(String dateAndTimeString) {
        this.dateAndTimeString = dateAndTimeString;
    }

    public Date parse() {
        int year = extract(YEAR);
        int month = extract(MONTH);
        int date = extract(DATE);
        int hour, minute;

        if (dateAndTimeString.substring(11, 12).equals("Z")) {
            hour = 0;
            minute = 0;
        } else {
            hour = extract(HOUR);
            minute = extract(MINUTE);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(year, month - 1, date, hour, minute, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private static class Component {
        String name;
        int startPosition;
        int endPosition;
        int lowerBound;
        int upperBound;
        int numberOfChars;

        public Component(String name, int startPosition, int endPosition, int lowerBound, int upperBound) {
            this.name = name;
            this.startPosition = startPosition;
            this.endPosition = endPosition;
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            this.numberOfChars = endPosition - startPosition;
        }
    }

    private final static Component YEAR = new Component("Year", 0, 4, 2000, 2012);
    private final static Component MONTH = new Component("Month", 5, 7, 1, 12);
    private final static Component DATE = new Component("Date", 8, 10, 1, 31);
    private final static Component HOUR = new Component("Hour", 11, 13, 0, 23);
    private final static Component MINUTE = new Component("Minute", 14, 16, 0, 59);

    private int extract(Component component) {
        int res;
        try {
            String str = dateAndTimeString.substring(component.startPosition, component.endPosition);
            res = Integer.parseInt(str);
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(component.name + " string is less than "
                    + component.numberOfChars + " characters");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(component.name + " is not an integer");
        }
        if (res < component.lowerBound || res > component.upperBound)
            throw new IllegalArgumentException(component.name + " cannot be less than "
                    + component.lowerBound + " or more than " + component.upperBound);
        return res;
    }
}
