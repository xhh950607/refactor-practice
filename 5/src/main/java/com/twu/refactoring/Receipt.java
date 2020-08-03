package com.twu.refactoring;

public class Receipt {
    private static final int FIXED_CHARGE = 50;
    private static final double PEAK_TIME_MULTIPLIER = 1.2;
    private static final double OFF_PEAK_MULTIPLIER = 1.0;
    private static final int RATE_CHANGE_DISTANCE = 10;
    private static final int PRE_RATE_CHANGE_NON_AC_RATE = 15;
    private static final int POST_RATE_CHANGE_NON_AC_RATE = 12;
    private static final int PRE_RATE_CHANGE_AC_RATE = 20;
    private static final int POST_RATE_CHANGE_AC_RATE = 17;
    private static final double SALES_TAX_RATE = 0.1;

    private final Taxi taxi;

    public Receipt(Taxi taxi) {
        this.taxi = taxi;
    }

    public double getTotalCost() {
        int totalKms = taxi.getTotalKms();
        double peakTimeMultiple = taxi.isPeakTime() ? PEAK_TIME_MULTIPLIER : OFF_PEAK_MULTIPLIER;
        int preRateChange = taxi.isAirConditioned() ? PRE_RATE_CHANGE_AC_RATE : PRE_RATE_CHANGE_NON_AC_RATE;
        int postRateChange = taxi.isAirConditioned() ? POST_RATE_CHANGE_AC_RATE : POST_RATE_CHANGE_NON_AC_RATE;

        double cost;
        if (totalKms <= RATE_CHANGE_DISTANCE) {
            cost = preRateChange * totalKms;
        } else {
            cost = preRateChange * RATE_CHANGE_DISTANCE + postRateChange * (totalKms - RATE_CHANGE_DISTANCE);
        }

        return (cost * peakTimeMultiple + FIXED_CHARGE) * (1 + SALES_TAX_RATE);
    }
}
