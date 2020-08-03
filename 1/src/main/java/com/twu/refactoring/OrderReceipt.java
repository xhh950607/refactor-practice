package com.twu.refactoring;

import java.util.function.Function;

/**
 * OrderReceipt prints the details of order including customer name, address, description, quantity,
 * price and amount. It also calculates the sales tax @ 10% and prints as part
 * of order. It computes the total order amount (amount of individual lineItems +
 * total sales tax) and prints it.
 */
public class OrderReceipt {
    private Order order;
    final static double TAX_RATE=.10;
    final static String HEAD = "======Printing Orders======\n";

    public OrderReceipt(Order order) {
        this.order = order;
    }


    public String printReceipt() {
        StringBuilder output = new StringBuilder();

        output.append(HEAD);
        output.append(order.getCustomerName());
        output.append(order.getCustomerAddress());
        output.append(formatLineItems());
        output.append("Sales Tax").append('\t').append(calcTotalSalesTax());
        output.append("Total Amount").append('\t').append(calcTotalAmount());

        return output.toString();
    }

    private String formatLineItems(){
        StringBuilder sb=new StringBuilder();
        for(LineItem lineItem: order.getLineItems()){
            sb.append(lineItem.getDescription());
            sb.append('\t');
            sb.append(lineItem.getPrice());
            sb.append('\t');
            sb.append(lineItem.getQuantity());
            sb.append('\t');
            sb.append(lineItem.totalAmount());
            sb.append('\n');
        }
        return sb.toString();
    }

    private double calcTotalSalesTax(){
        return addUpLineItemsByAttr(OrderReceipt::calcSalesTax);
    }

    private double calcTotalAmount(){
        return addUpLineItemsByAttr(OrderReceipt::calcAmount);
    }

    private double addUpLineItemsByAttr(Function<LineItem, Double> getAttribute){
        return order.getLineItems().stream()
                .map(lineItem -> getAttribute.apply(lineItem))
                .reduce(0d, Double::sum);
    }

    private static double calcSalesTax(LineItem lineItem){
        return lineItem.totalAmount() * TAX_RATE;
    }

    private static double calcAmount(LineItem lineItem){
        return lineItem.totalAmount() + calcSalesTax(lineItem);
    }
}