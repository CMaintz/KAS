package storage;

import application.model.Conference;
import application.model.Hotel;
import application.model.Participant;

import java.util.ArrayList;

public class Storage {

    public static ArrayList<Participant> participants = new ArrayList<>();
    public static ArrayList<Conference> conferences = new ArrayList<>();
    public static ArrayList<Hotel> hotels = new ArrayList<>();


    public static ArrayList<Participant> getParticipants() {
        return new ArrayList<Participant>(participants);
    }
    public static void addParticipant(Participant participant) {
        participants.add(participant);
    }
    public static void deleteParticipant(Participant participant) {
            participants.remove(participant);
    }


    public static ArrayList<Conference> getConferences() {
        return new ArrayList<>(conferences);
    }
    public static void addConference(Conference conference) {
        conferences.add(conference);
    }
    public static void deleteConference(Conference conference) {
        conferences.remove(conference);
    }


    public static ArrayList<Hotel> getHotels() {
        return new ArrayList<>(hotels);
    }
    public static void addHotel(Hotel hotel) {
        hotels.add(hotel);
    }
    public static void deleteHotel(Hotel hotel) {
        hotels.remove(hotel);
    }


}
