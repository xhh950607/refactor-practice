package com.twu.refactoring;


import java.util.ArrayList;
import java.util.Iterator;

public class Customer {

    private String name;
    private ArrayList<Rental> rentalList = new ArrayList<Rental>();

    public Customer(String name) {
        this.name = name;
    }

    public void addRental(Rental arg) {
        rentalList.add(arg);
    }

    public String getName() {
        return name;
    }

    private double calcTotalAmount() {
        return rentalList.stream()
                .map(Rental::getAmount)
                .reduce(0d, Double::sum);
    }

    private int calcFrequentRenterPoints() {
        return rentalList.stream()
                .map(Rental::getFrequentRenterPoints)
                .reduce(0, Integer::sum);
    }

    private String formatRentals() {
        StringBuilder sb = new StringBuilder();
        for (Rental rental : rentalList) {
            sb.append('\t')
                    .append(rental.getMovie().getTitle())
                    .append('\t')
                    .append(rental.getAmount())
                    .append('\n');
        }
        return sb.toString();
    }

    private String formatRentalsInHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<P>");
        for (Rental rental : rentalList) {
            sb.append(rental.getMovie().getTitle())
                    .append(": ")
                    .append(rental.getAmount())
                    .append("<BR>");
        }
        sb.append("<P>");
        return sb.toString();
    }

    public String statement() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rental Record for ").append(getName()).append('\n');
        sb.append(formatRentals());
        sb.append("Amount owed is ").append(calcTotalAmount()).append('\n');
        sb.append("You earned ").append(calcFrequentRenterPoints()).append(" frequent renter points");
        return sb.toString();
    }

    public String htmlStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append("<H1>Rentals for <EM>").append(getName()).append("</EM></H1>");
        sb.append(formatRentalsInHtml());
        sb.append("You owe <EM>").append(calcTotalAmount()).append("</EM><P>");
        sb.append("On this rental you earned <EM>").append(calcFrequentRenterPoints()).append("</EM> frequent renter points<P>");
        return sb.toString();
    }
}
