package com.evnto.indotech.evnto.RegistrTickets;

/**
 * Created by imakash on 9/8/2017.
 */

public class RgstrList {
    String name;
    String number;
    String evntName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEvntName() {
        return evntName;
    }

    public void setEvntName(String evntName) {
        this.evntName = evntName;
    }

    public String getTickets() {
        return Tickets;
    }

    public void setTickets(String tickets) {
        Tickets = tickets;
    }

    String Tickets;
}
