package org.example.model;

import org.bson.Document;

import java.util.Map;

/**
 * @author Michael Gerasimov
 */
public class User {
    private final int id;
    private final String login;
    private final Currency currency;

    public User(Document doc) {
        this(doc.getInteger("id"), doc.getString("login"), doc.getString("currency"));
    }

    public User(int id, String login, String currency) {
        this(id, login, Currency.valueOf(currency));
    }

    public User(int id, String login, Currency currency) {
        this.id = id;
        this.login = login;
        this.currency = currency;
    }

    public Document toDocument() {
        return new Document(Map.of(
                "id", id,
                "login", login,
                "currency", currency.toString()
        ));
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", currency='" + currency +
                '}';
    }
}