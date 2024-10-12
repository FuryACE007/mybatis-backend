package com.fidelity.business.entity;


import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.Objects;
import java.util.Set;


public class Client {
    private String name;
    private String country;
    private String email;
    private int id;
    private LocalDate dob;
    private String postalCode;
    private String password;
    private Set<ClientIdentification> identities;
    private BigDecimal cashValue;
    private InvestmentPreference preference;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<ClientIdentification> getIdentities() {
        return identities;
    }

    public void setIdentities(Set<ClientIdentification> identities) {
        this.identities = identities;
    }

    public BigDecimal getCashValue() {
        return cashValue;
    }

    public void setCashValue(BigDecimal cashValue) {
        this.cashValue = cashValue;
    }

    public InvestmentPreference getPreference() {
        return preference;
    }

    public void setPreference(InvestmentPreference preference) {
        this.preference = preference;
    }

    public Client(int id, String name, String country, String email, LocalDate dob, BigDecimal initialCashValue, String postalCode, String password, Set<ClientIdentification> identities) {
        this.id = id;
        if (Objects.equals(name, "") || Objects.equals(country, "") || Objects.equals(email, ""))
            throw new NullPointerException("Name/Country/Email cannot be null or empty");
        this.name = name;
        this.dob = dob;
        this.country = country;
        this.email = email;
        this.identities = identities;
        this.cashValue = initialCashValue;
        this.password = password;
        this.postalCode = postalCode;
    }

    public Client(int id, String name, String email, LocalDate dob, String country, String postalcode, String password, BigDecimal initialCashValue) {

        if (Objects.equals(name, "") || Objects.equals(country, "") || Objects.equals(email, ""))
            throw new NullPointerException("Name/Country/Email cannot be null or empty");
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.country = country;
        this.email = email;
        this.postalCode = postalCode;
        this.password = password;
        this.cashValue = initialCashValue;
    }

    public Client(int id, String name, String country, String email, LocalDate dob, String postalCode, String password, Set<ClientIdentification> identities, BigDecimal cashValue, InvestmentPreference preference) {
        this.name = name;
        this.country = country;
        this.email = email;
        this.id = id;
        this.dob = dob;
        this.postalCode = postalCode;
        this.password = password;
        this.identities = identities;
        this.cashValue = cashValue;
        this.preference = preference;
    }

    public Client() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && Objects.equals(name, client.name) && Objects.equals(country, client.country) && Objects.equals(email, client.email) && Objects.equals(dob, client.dob) && Objects.equals(postalCode, client.postalCode) && Objects.equals(password, client.password) && Objects.equals(identities, client.identities) && Objects.equals(cashValue, client.cashValue) && Objects.equals(preference, client.preference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country, email, id, dob, postalCode, password, identities, cashValue, preference);
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", dob=" + dob +
                ", postalCode='" + postalCode + '\'' +
                ", password='" + password + '\'' +
                ", identities=" + identities +
                ", cashValue=" + cashValue +
                ", preference=" + preference +
                '}';
    }
}