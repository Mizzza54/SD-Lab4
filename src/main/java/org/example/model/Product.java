package org.example.model;

import org.bson.Document;

import java.util.Map;

import static org.example.model.Currency.RUB;

/**
 * @author Michael Gerasimov
 */
public class Product {
    private final int id;
    private final String name;
    private final double price;

    public Product(Document doc) {
        this(doc.getInteger("id"), doc.getString("name"), doc.getDouble("price"));
    }

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Document toDocument() {
        return new Document(Map.of(
                "id", id,
                "name", name,
                "price", price
        ));
    }

    public String toString(Currency currency) {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + RUB.convert(currency, price) + ' ' + currency + '\'' +
                '}';
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}