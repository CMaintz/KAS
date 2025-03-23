package application.model;

import java.util.ArrayList;

public class Companion {
    private final Participant participant;
    private final ArrayList<Excursion> excursions = new ArrayList<>();
    private String name;
    private String phoneNumber;
    private String address;

    Companion(String name, String phoneNumber, String address, Participant participant) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.participant = participant;
    }

    public ArrayList<Excursion> getExcursions() {
        return new ArrayList<>(excursions);
    }

    public void addExcursion(Excursion excursion) {
        if (!excursions.contains(excursion)) {
            excursions.add(excursion);
            excursion.addCompanionToExcursion(this);
        }
    }

    public void removeExcursion(Excursion excursion) {
        excursions.remove(excursion);
    }

    public Participant getParticipant() {
        return participant;
    }

    @Override
    public String toString() {
        return name;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone) {
        this.phoneNumber = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
