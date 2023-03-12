package org.example.model;

/**
 * @author Michael Gerasimov
 */
public enum Currency {
    RUB(1),
    DOLLAR(76),
    EURO(80);

    private final double value;

    Currency(double value) {
        this.value = value;
    }

    public double convert(Currency currency, double price) {
        return price * this.value / currency.value;
    }
}