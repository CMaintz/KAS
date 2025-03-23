package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Conference {
    private final ArrayList<Hotel> hotels = new ArrayList<>();
    private final ArrayList<Excursion> excursions = new ArrayList<>();
    private final ArrayList<ConferenceBooking> bookings = new ArrayList<>();
    private String name;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;
    private int confDayPrice;
    private int bookingNumber = 0;

    public Conference(String name, String address, int price, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.address = address;
        this.confDayPrice = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Excursion createExcursion(String name, String location, LocalDate date, int price) {
        Excursion excursion = new Excursion(name, location, this, date, price);
        excursions.add(excursion);
        return excursion;
    }

    public void removeExcursion(Excursion excursion) {
        excursions.remove(excursion);
    }

    public ArrayList<Hotel> getHotels() {
        return new ArrayList<>(hotels);
    }

    public void removeHotel(Hotel hotel) {
        hotels.remove(hotel);
    }

    public ArrayList<Excursion> getExcursions() {
        return new ArrayList<>(excursions);
    }

    public ArrayList<Participant> getParticipants() {
        ArrayList<Participant> participantList = new ArrayList<>();
        for (ConferenceBooking booking : bookings) {
            participantList.add(booking.getParticipant());
        }
        return participantList;
    }

    public void removeBooking(ConferenceBooking booking) {
        bookings.remove(booking);
    }

    public void addHotelToConference(Hotel hotel) {
        if (!hotels.contains(hotel)) {
            hotels.add(hotel);
        }
    }

    public void addBooking(ConferenceBooking booking) {
        if (!bookings.contains(booking)) {
            bookings.add(booking);
            bookingNumber++;
            booking.setBookingNumber(bookingNumber);
        }
    }

    public int getDayPrice() {
        return confDayPrice;
    }

    public void setDayPrice(int confDayPrice) {
        this.confDayPrice = confDayPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " (" + confDayPrice + ",- / day)";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ArrayList<ConferenceBooking> getBookings() {
        return new ArrayList<>(bookings);
    }

    public String getDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Name: " + name + "\n");
        details.append("Address: " + address + "\n");
        details.append("Start date: " + startDate + "\n");
        details.append("End date: " + endDate + "\n");
        details.append("Day price: " + confDayPrice + "\n");
        details.append("Bookings: " + bookings.size() + "\n");
        if (hotels.size() > 0) {
            details.append("Hotels available:\n");
            for (Hotel hotel : hotels) {
                details.append(hotel.getName() + "\n");
            }
        }

        if (excursions.size() > 0) {
            details.append("Excursions:\n");
            for (Excursion excursion : excursions) {
                details.append(excursion + "\n");
            }
        }
//        if (participants.size() > 0) {
//            details.append("Participants: " + participants.size() + "\n");
//            for (Participant participant : participants) {
//                details.append(participant.toString() + "\n");
//            }
//        }
        return details.toString();
    }

}

