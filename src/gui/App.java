package gui;

import application.controller.Controller;
import application.model.*;
import javafx.application.Application;

import java.time.LocalDate;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        initStorage();
        Application.launch(StartWindow.class);
    }

    private static void initStorage() {
        Participant p1 = Controller.createParticipant("Finn Madsen", "1234", "Her");
        Participant p2 = Controller.createParticipant("Niels Petersen", "22", "Her");
        Participant participant3 = Controller.createParticipant("Peter Sommer", "bla", "meheh");
        participant3.setCompany("IBM");
        Participant p4 = Controller.createParticipant("Lone Jensen", "341", "k");
        Participant p5 = Controller.createParticipant("Chris Maintz", "1234", "Lige her");
        Participant p6 = Controller.createParticipant("Børge Madsen", "1634", "Her");
        Participant p7 = Controller.createParticipant("Anders Blabla", "14555", "Her");
        Participant p8 = Controller.createParticipant("Margrethe Dybdal", "14555", "Sønderhøj 30");

        Participant p9 = Controller.createParticipant("Anders Andersen", "14555", "Her");
        p9.setCompany("Dell");
        Participant p10 = Controller.createParticipant("Niels Nielsen", "22", "Her");
        Participant p11 = Controller.createParticipant("Finn Finnsen", "1234", "Her");
        Participant p12 = Controller.createParticipant("Lone Lonesen", "341", "k");
        Participant p13 = Controller.createParticipant("Margrethe Dybdals klon", "1634", "Her");
        Participant p14 = Controller.createParticipant("Peter Petersen", "bla", "meheh");
        Participant p15 = Controller.createParticipant("Chris Chrissen", "1234", "Lige her");

        Companion comp1 = Controller.createCompanion("Mie Sommer", "22", "NotHere", participant3);
        Companion comp2 = Controller.createCompanion("Jan Madsen", "k", "e", p4);
        Companion comp3 = Controller.createCompanion("Stinna Jakobsen", "9582", "Viby", p5);
        Companion comp4 = Controller.createCompanion("Stine Jensen", "9582", "Viby", p6);
        Companion comp5 = Controller.createCompanion("Karin Kiks", "9582", "Viby", p9);
        Companion comp6 = Controller.createCompanion("Benny", "9582", "Viby", p8);
        Companion comp7 = Controller.createCompanion("Karsten", "9582", "Salamanderparken, Stavtrup", p13);
        Companion comp8 = Controller.createCompanion("Nanna Nannasen", "233232", "Secret basement bunker", participant3);
        Companion comp9 = Controller.createCompanion("Dennis Duckhouse", "12345678", "Mums basement", participant3);

        Conference conference1 = Controller.createConference("Hav og himmel", "Somewhere", 1500,
                LocalDate.of(2023, 12, 18), LocalDate.of(2023, 12, 20));
        Conference c2 = Controller.createConference("Ting og sager", "Somewhere else", 2500,
                LocalDate.of(2023, 11, 14), LocalDate.of(2023, 11, 17));
        Conference c3 = Controller.createConference("Konferencehad", "SomewhereX", 1500,
                LocalDate.of(2023, 6, 15), LocalDate.of(2023, 6, 19));
        Conference c4 = Controller.createConference("Fight Club", "Don't Talk About It", 150,
                LocalDate.of(2024, 4, 24), LocalDate.of(2024, 4, 28));

        Excursion e1 = Controller.createExcursion("Odense Byrundtur", "Odense", conference1,
                LocalDate.of(2023, 12, 18), 125);
        Excursion e2 = Controller.createExcursion("Guidetur i Egeskov", "Egeskov", conference1,
                LocalDate.of(2023, 12, 19), 75);
        Excursion e3 = Controller.createExcursion("Trapholt Museum", "Kolding", conference1,
                LocalDate.of(2023, 12, 20), 200);
        Excursion e4 = Controller.createExcursion("Udflugt til EAA", "Sønderhøj 30, 8260 Viby", c2,
                LocalDate.of(2023, 11, 15), 50);
        Excursion e5 = Controller.createExcursion("Tur i et aktivt gaskammer", "Kælderen", c3,
                LocalDate.of(2023, 6, 16), 0);




        Hotel hotel1 = Controller.createHotel(conference1, "Den Hvide Svane", "Somewhere", "123456", 1050, 1250);
        AddonPurchase hotel1Addon1 = Controller.createHotelAddon(hotel1, "WiFi", 50);

        Hotel h2 = Controller.createHotel(conference1, "Hotel Phønix", "Idk", "69", 700, 800);
        AddonPurchase h2Add1 = Controller.createHotelAddon(h2, "Bad", 200);
        AddonPurchase h2Add2 = Controller.createHotelAddon(h2, "WiFi", 75);

        Hotel h3 = Controller.createHotel(conference1, "Pension Tusindfryd", "Meh", "420", 500, 600);
        AddonPurchase h3Add1 = Controller.createHotelAddon(h3, "Morgenmad", 100);
        Hotel h4 = Controller.createHotel(c2, "Pension Somewhere", "Meh", "420", 400, 650);
        Hotel h5 = Controller.createHotel(c2, "EAAA", "Sønderhøj", "420", 400, 300);
        Hotel h6 = Controller.createHotel(c3, "Uranus", "Up", "420", 400, 650);
        Hotel h7 = Controller.createHotel(c4, "The Club", "Shhh", "420", 350, 500);

        AddonPurchase h4Add1 = Controller.createHotelAddon(h4, "Special massage", 395);
        AddonPurchase h5Add1 = Controller.createHotelAddon(h6, "Up", 69);
        ConferenceBooking cb1 = Controller.createBooking(conference1, p1, false, null, null, null, LocalDate.of(2023, 12, 18), LocalDate.of(2023, 12, 20));
        ConferenceBooking cb2 = Controller.createBooking(conference1, p2, false, null, hotel1, RoomType.SINGLE, LocalDate.of(2023, 12, 18), LocalDate.of(2023, 12, 20));
        ConferenceBooking cb3 = Controller.createBooking(conference1, participant3, false, comp1, hotel1, RoomType.DOUBLE, LocalDate.of(2023, 12, 18), LocalDate.of(2023, 12, 20));
        Controller.addAddonToBooking(cb3, hotel1Addon1);
        ConferenceBooking cb4 = Controller.createBooking(conference1, p4, true, comp2, hotel1, RoomType.SINGLE,
                LocalDate.of(2023, 12, 18), LocalDate.of(2023, 12, 20));
        Controller.addAddonToBooking(cb4, hotel1Addon1);
        ConferenceBooking cb5 = Controller.createBooking(c2, p5, true, comp3, h2, RoomType.DOUBLE, LocalDate.of(2023, 11, 14), LocalDate.of(2023, 11, 15));
        ConferenceBooking cb6 = Controller.createBooking(conference1, p6, false, null, h2, RoomType.SINGLE, LocalDate.of(2023, 11, 18), LocalDate.of(2023, 11, 20));
        ConferenceBooking cb7 = Controller.createBooking(conference1, p7, false, null, h3, RoomType.DOUBLE, LocalDate.of(2023, 11, 19), LocalDate.of(2023, 11, 20));
        ConferenceBooking cb8 = Controller.createBooking(c2, p8, true, comp6, h3, RoomType.DOUBLE, LocalDate.of(2023, 11, 14), LocalDate.of(2023, 11, 15));
        ConferenceBooking cb9 = Controller.createBooking(c2, p9, true, null, null, null, LocalDate.of(2023, 11, 14), LocalDate.of(2023, 11, 15));
        ConferenceBooking cb10 = Controller.createBooking(c2, p10, false, null, hotel1, RoomType.SINGLE, LocalDate.of(2023, 11, 14), LocalDate.of(2023, 11, 15));
        ConferenceBooking cb11 = Controller.createBooking(c2, p11, true, comp4, h4, RoomType.DOUBLE, LocalDate.of(2023, 11, 14), LocalDate.of(2023, 11, 15));
        ConferenceBooking cb12 = Controller.createBooking(c2, p12, false, null, h3, RoomType.SINGLE, LocalDate.of(2023, 11, 14), LocalDate.of(2023, 11, 15));
        ConferenceBooking cb13 = Controller.createBooking(c2, p13, true, comp7, h4, RoomType.DOUBLE, LocalDate.of(2023, 11, 14), LocalDate.of(2023, 11, 15));
        ConferenceBooking cb14 = Controller.createBooking(c2, p14, true, null, h5, RoomType.SINGLE, LocalDate.of(2023, 11, 14), LocalDate.of(2023, 11, 15));
        ConferenceBooking cb15 = Controller.createBooking(c2, p15, false, null, h4, RoomType.DOUBLE, LocalDate.of(2023, 11, 14), LocalDate.of(2023, 11, 15));
        ConferenceBooking cb16 = Controller.createBooking(c2, participant3, false, null, h5, RoomType.SINGLE, LocalDate.of(2023, 11, 14), LocalDate.of(2023, 11, 15));
        ConferenceBooking cb17 = Controller.createBooking(c3, participant3, true, comp8, h6, RoomType.DOUBLE, LocalDate.of(2023, 11, 14), LocalDate.of(2023, 11, 15));
        ConferenceBooking cb18 = Controller.createBooking(conference1, p9, true, comp5, h2 ,RoomType.DOUBLE, LocalDate.of(2023, 6, 18), LocalDate.of(2023, 6, 19));
        ConferenceBooking cb19 = Controller.createBooking(c4, p9, false, null, h7, RoomType.SINGLE, LocalDate.of(2024, 4, 24), LocalDate.of(2024, 4, 26));

        ArrayList<Excursion> cbExcursions = new ArrayList<>();
        cbExcursions.add(e2);
        cbExcursions.add(e3);

        Controller.setExcursionsOrdered(cb3, cbExcursions);
        cbExcursions.clear();
        cbExcursions.add(e1);
        cbExcursions.add(e2);
        Controller.setExcursionsOrdered(cb4, cbExcursions);
        cbExcursions.clear();
        cbExcursions.add(e4);
        Controller.setExcursionsOrdered(cb5, cbExcursions);
//        Controller.addExcursionBooking(comp1, e2);
//        Controller.addExcursionBooking(comp1, e3);
//        Controller.addExcursionBooking(comp2, e1);
//        Controller.addExcursionBooking(comp2, e2);
//        Controller.addExcursionBooking(comp3, e4);

        System.out.println(cb1.getTotalPrice());
        System.out.println(cb2.getTotalPrice());
        System.out.println(cb3.getTotalPrice());
        System.out.println(cb4.getTotalPrice());



    }
}
