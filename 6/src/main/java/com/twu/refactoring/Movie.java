package com.twu.refactoring;

import java.util.HashMap;
import java.util.Map;

public class Movie {
    public static final int CHILDRENS = 2;
    public static final int REGULAR = 0;
    public static final int NEW_RELEASE = 1;
    private static final Map<Integer, PriceParameterTable> priceParameterMap;

    static class PriceParameterTable {
        private double fixCharge;
        private int basicDays;
        private double dailyAddedCharge;

        PriceParameterTable(double fixCharge, int basicDays, double dailyAddedCharge) {
            this.fixCharge = fixCharge;
            this.basicDays = basicDays;
            this.dailyAddedCharge = dailyAddedCharge;
        }

        double getFixCharge() {
            return fixCharge;
        }

        int getBasicDays() {
            return basicDays;
        }

        double getDailyAddedCharge() {
            return dailyAddedCharge;
        }
    }

    static {
        priceParameterMap = new HashMap<>();
        priceParameterMap.put(REGULAR, new PriceParameterTable(2, 2, 1.5));
        priceParameterMap.put(NEW_RELEASE, new PriceParameterTable(0, 0, 3));
        priceParameterMap.put(CHILDRENS, new PriceParameterTable(1.5, 3, 1.5));
    }

    private String title;
    private int priceCode;

    public Movie(String title, int priceCode) {
        this.title = title;
        this.priceCode = priceCode;
    }

    public String getTitle() {
        return title;
    }

    public int getPriceCode() {
        return priceCode;
    }

    public void setPriceCode(int priceCode) {
        this.priceCode = priceCode;
    }

    PriceParameterTable getPriceParameterTable() {
        return priceParameterMap.get(priceCode);
    }

}

