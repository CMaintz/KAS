package gui;

import application.controller.Controller;
import application.model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;







public class ConferencePane extends GridPane {
    private ListView<Conference> lvwConferences;
    private ListView<ConferenceBooking> lvwBookings;
    private ListView<Excursion> lvwExcursions;
    private ListView<Hotel> lvwHotels;
    private TextArea txaConfDeets, txaBookingDeets;
    private Label lbl1, lbl2, lbl3, lblConfDetails;
    private Conference conference;
    private TextField txfSearch;
    private static final int PREF_WIDTH = 200;
    private static final int PREF_HEIGHT = 240;

    public ConferencePane() {
        this.setPadding(new Insets(5));
        this.setHgap(10);
        this.setVgap(10);

        GridPane pane = new GridPane();
        this.add(pane, 0, 0);
        pane.setPadding(new Insets(5));
        pane.setHgap(20);
        pane.setVgap(10);

        Label lblConferences = new Label("Conferences");
        lblConferences.setFont(Font.font("Comic sans MS", FontWeight.BOLD, 12));
        pane.add(lblConferences, 0, 0);

        lvwConferences = new ListView<>();
        pane.add(lvwConferences, 0, 1);
        lvwConferences.setPrefWidth(PREF_WIDTH);
        lvwConferences.setPrefHeight(PREF_HEIGHT);
        lvwConferences.getItems().setAll(Controller.getConferences());
        lvwConferences.setStyle("-fx-font-family: 'Comic Sans MS'");

        conference = lvwConferences.getItems().get(0);


        ChangeListener<Conference> listener = (ov, oldConference, newConference) -> updateConfControls();
        lvwConferences.getSelectionModel().selectedItemProperty().addListener(listener);

        lblConfDetails = new Label(conference.getName() + " details");
        lblConfDetails.setFont(Font.font("Comic sans MS", FontWeight.BOLD, 12));
        pane.add(lblConfDetails, 1, 0);

        txaConfDeets = new TextArea();
        txaConfDeets.setEditable(false);
        txaConfDeets.setPrefWidth(PREF_WIDTH);
        txaConfDeets.setPrefHeight(PREF_HEIGHT);
        pane.add(txaConfDeets, 1, 1);
        txaConfDeets.setText(lvwConferences.getItems().get(0).getDetails());
        txaConfDeets.setStyle("-fx-font-family: 'Comic Sans MS'");
        // TODO add Google Maps or OpenMaps

        Label lblBookings = new Label("Bookings");
        lblBookings.setFont(Font.font("Comic sans MS", FontWeight.BOLD, 12));
        pane.add(lblBookings, 2, 0);

        lvwBookings = new ListView<>();
        lvwBookings.setPrefWidth(PREF_WIDTH);
        lvwBookings.setPrefHeight(PREF_HEIGHT);
        pane.add(lvwBookings, 2, 1);
        lvwBookings.setStyle("-fx-font-family: 'Comic Sans MS'");

        ChangeListener<ConferenceBooking> bookingListener = (ov, oldBooking, newBooking) -> this.updateBookingControls();
        lvwBookings.getSelectionModel().selectedItemProperty().addListener(bookingListener);

        Label lblBookingDetails = new Label("Booking info");
        lblBookingDetails.setFont(Font.font("Comic sans MS", FontWeight.BOLD, 12));
        pane.add(lblBookingDetails, 3, 0);

        txaBookingDeets = new TextArea();
        txaBookingDeets.setEditable(false);
        txaBookingDeets.setPrefWidth(PREF_WIDTH - 40);
        txaBookingDeets.setPrefHeight(PREF_HEIGHT);
        pane.add(txaBookingDeets, 3, 1);
        txaBookingDeets.setStyle("-fx-font-family: 'Comic Sans MS'");


        Button btnNewBooking = new Button("New Conference Booking");
        pane.add(btnNewBooking, 0, 2);
        btnNewBooking.setStyle("-fx-font-family: 'Comic Sans MS'");
        btnNewBooking.setOnAction(event -> bookingAction());

        Label lblSearch = new Label("Search For Participant:");
        pane.add(lblSearch, 1, 2);
        GridPane.setHalignment(lblSearch, HPos.RIGHT);
        lblSearch.setFont(Font.font("Comic sans MS", FontWeight.BOLD, 12));

        txfSearch = new TextField();
        pane.add(txfSearch, 2, 2);
        txfSearch.setStyle("-fx-font-family: 'Comic Sans MS'");

        Button btnSearch = new Button("Search");
        pane.add(btnSearch, 3, 2);
        btnSearch.setStyle("-fx-font-family: 'Comic Sans MS'");
        btnSearch.setOnAction(event -> searchParticipent());

        updateControls();
    }

    private void bookingAction() {
        int index = lvwConferences.getSelectionModel().getSelectedIndex();
        Conference conference = lvwConferences.getSelectionModel().getSelectedItem();
        if (conference != null) {
            BookingWindow dia = new BookingWindow("Create Booking", conference);
            dia.showAndWait();
            lvwConferences.getItems().setAll(Controller.getConferences());
            lvwConferences.getSelectionModel().select(index);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Missing information");
            alert.setTitle("No conference selected");
            alert.setContentText("Please select a conference");
            alert.showAndWait();
        }
    }

    public void searchParticipent() {
        String deltagerNavn = txfSearch.getText();
        boolean found = false;
        for (int i = 0; i < Controller.getParticipants().size(); i++) {
            if (deltagerNavn.toUpperCase().equals(Controller.getParticipants().get(i).getName().toUpperCase())) {
                found = true;
                Participant deltager = Controller.getParticipants().get(i);
                SearchWindow searchWindow = new SearchWindow(deltager);
                searchWindow.showAndWait();
            }
        }
        if (!found) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No participant found");
            alert.setTitle("Not found");
            alert.setContentText("The specified participant wasn't found. \nPlease try again.");
            alert.showAndWait();
        }
    }

    private void updateBookingControls() {
        ConferenceBooking booking = lvwBookings.getSelectionModel().getSelectedItem();
        if (booking != null) {
            txaBookingDeets.setText(booking.getDetails());
        }
    }

    public void updateConfControls() {
        conference = lvwConferences.getSelectionModel().getSelectedItem();
        if (conference != null) {
            txaConfDeets.setText(conference.getDetails());
            lblConfDetails.setText(conference.getName() + " details");
            if (conference.getBookings().size() > 0) {
                showBookings();
            } else {
                txaConfDeets.clear();
                lvwBookings.getItems().clear();
                txaBookingDeets.clear();
            }
        }
    }

    private void showBookings() {
        if (conference.getBookings().size() > 1) {
            ArrayList<ConferenceBooking> sortedBookings = new ArrayList<>(conference.getBookings());
            Controller.selectionSortBooking(sortedBookings);
            lvwBookings.getItems().setAll(sortedBookings);
            lvwBookings.getSelectionModel().select(0);
            txaBookingDeets.setText(sortedBookings.get(0).getDetails());
        } else if (conference.getBookings().size() == 1) {
            lvwBookings.getItems().setAll(conference.getBookings());
            lvwBookings.getSelectionModel().select(0);
            txaBookingDeets.setText(lvwBookings.getSelectionModel().getSelectedItem().getDetails());
        } else {
            lvwBookings.getItems().clear();
            txaBookingDeets.clear();
        }
    }


    public void updateControls() {
        lvwConferences.getItems().setAll(Controller.getConferences());
        lvwConferences.getSelectionModel().select(0);
        conference = lvwConferences.getSelectionModel().getSelectedItem();
        if (conference != null) {
            txaConfDeets.setText(conference.getDetails());
            lblConfDetails.setText(conference.getName() + " details");
            if (conference.getBookings().size() > 0) {
                showBookings();
            } else {
                txaConfDeets.clear();
                lvwBookings.getItems().clear();
                txaBookingDeets.clear();
            }
        }
    }
}
