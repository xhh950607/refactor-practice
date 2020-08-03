package com.twu.refactoring;

public class Rental {

    private Movie movie;

    private int daysRented;

    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public Movie getMovie() {
        return movie;
    }

    public double getAmount() {
        Movie.PriceParameterTable table = movie.getPriceParameterTable();
        double fixCharge = table.getFixCharge();
        int basicDays = table.getBasicDays();
        double dailyAddedCharge = table.getDailyAddedCharge();

        double result = fixCharge;
        if (daysRented > basicDays)
            result += (daysRented - basicDays) * dailyAddedCharge;
        return result;
    }

    public int getFrequentRenterPoints() {
        if (movie.getPriceCode() == Movie.NEW_RELEASE && daysRented > 1)
            return 2;
        else
            return 1;
    }
}