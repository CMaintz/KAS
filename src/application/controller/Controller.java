package application.controller;

import application.model.*;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    public static Conference createConference(String name, String location, int pricePerDay, LocalDate startDate, LocalDate endDate) {
        Conference conference = new Conference(name, location, pricePerDay, startDate, endDate);
        Storage.addConference(conference);
        return conference;
    }

    public static void updateConference(Conference conference, String name, String location, int pricePerDay, LocalDate startDate, LocalDate endDate) {
        conference.setName(name);
        conference.setAddress(location);
        conference.setDayPrice(pricePerDay);
        conference.setStartDate(startDate);
        conference.setEndDate(endDate);
    }

    public static Companion createCompanion(String navn, String tlf, String adresse, Participant participant) {
        Companion companion = participant.createCompanion(navn, tlf, adresse);
        return companion;
    }

    public static ArrayList<Participant> getParticipants() {
        return Storage.getParticipants();
    }

    public static ArrayList<Conference> getConferences() {
        return Storage.getConferences();
    }

    public static Participant createParticipant(String name, String phoneNumber, String address) {
        Participant participant = new Participant(name, phoneNumber, address);
        Storage.addParticipant(participant);
        return participant;
    }

    public static void updateParticipant(Participant participant, String name, String address, String phoneNumber) {
        participant.setName(name);
        participant.setAddress(address);
        participant.setPhoneNr(phoneNumber);
    }

    public static void setExcursionsOrdered(ConferenceBooking booking, ArrayList<Excursion> excursions) {
        booking.setExcursionsOrdered(excursions);
    }

    //TODO foreach som hiver excursions fra companion og sammenligner med ConfExcursions; hvis de er en del af ConfExc, pris+=excursion.getprice()

//    public static void addExcursionBooking(Companion companion, Excursion excursion) {
//        companion.addExcursion(excursion);
//    }

    public static Hotel createHotel(Conference conference, String name, String location, String phoneNumber, int roomTypeSinglePrice, int roomTypeDoublePrice) {
        Hotel hotel = new Hotel(name, location, phoneNumber, roomTypeSinglePrice, roomTypeDoublePrice);
        hotel.setConference(conference);
        conference.addHotelToConference(hotel);
        Storage.addHotel(hotel);
        return hotel;
    }

    public static Excursion createExcursion(String name, String location, Conference conference, LocalDate date, int price) {
        Excursion excursion = conference.createExcursion(name, location, date, price);
        return excursion;
    }


    public static ConferenceBooking createBooking(Conference conference, Participant participant, Boolean isLecturer, Companion companion, Hotel hotel, RoomType roomType, LocalDate arrivalDate, LocalDate departureDate) {
        ConferenceBooking booking = participant.createConfBook(conference, isLecturer, companion, hotel, roomType, arrivalDate, departureDate);
        return booking;
    }

    public static void addHotelToBooking(ConferenceBooking booking, Hotel hotel, RoomType roomType) {
        if (booking.getConference().getHotels().contains(hotel)) {
            booking.setHotel(hotel, roomType);
        }
    }

    public static AddonPurchase createHotelAddon(Hotel hotel, String name, int price) {
        AddonPurchase addon = hotel.createAddonPurchase(name, price);
        return addon;
    }

    public static void addAddonToBooking(ConferenceBooking booking, AddonPurchase addon) {
        booking.addAddon(addon);
    }

    public static void deleteAddon(AddonPurchase addon) {
        addon.getHotel().removeAddon(addon);
    }

    public static void deleteConference(Conference conference) {
        Storage.deleteConference(conference);
    }

    public static void deleteCompanion(Participant participant, Companion companion) {
        participant.removeCompanion(companion);
    }


    public static void deleteParticipant(Participant participant) {
        participant.removeAllBookings();
        Storage.deleteParticipant(participant);
    }

    public static void deleteExcursion(Excursion excursion) {
        excursion.getConference().removeExcursion(excursion);
    }

    public static void deleteHotel(Hotel hotel) {
        Storage.deleteHotel(hotel);
        hotel.getConference().removeHotel(hotel);
    }

    public static void updateCompanion(Companion companion, String name, String address, String phone) {
        companion.setName(name);
        companion.setAddress(address);
        companion.setPhoneNumber(phone);
    }

    public static void selectionSortBooking(ArrayList<ConferenceBooking> bookings) {
        for (int i = 0; i < bookings.size(); i++) {
            int minPos = i;
            for (int j = i + 1; j < bookings.size(); j++) {
                if (bookings.get(j).getParticipant().getName().compareToIgnoreCase(bookings.get(minPos).getParticipant().getName()) < 0) {
                    minPos = j;
                }
            }
            swapBooking(bookings, i, minPos);
        }
    }

    private static void swapBooking(ArrayList<ConferenceBooking> bookings, int i, int j) {
        ConferenceBooking temp = bookings.get(i);
        bookings.set(i, bookings.get(j));
        bookings.set(j, temp);
    }


    public static void updateHotel(Conference conference, Hotel hotel, String name, String address, String phone, int singleRoomPrice, int doubleRoomPrice) {
        hotel.setName(name);
        hotel.setAddress(address);
        hotel.setPhoneNumber(phone);
        hotel.setSingleRoomPrice(singleRoomPrice);
        hotel.setDoubleRoomPrice(doubleRoomPrice);
    }

    public static void updateExcursion(Excursion excursion, String name, String address, LocalDate date, int price) {
        excursion.setName(name);
        excursion.setAddress(address);
        excursion.setDate(date);
        excursion.setPrice(price);
    }
}




