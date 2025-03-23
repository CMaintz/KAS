package gui;

import application.controller.Controller;
import application.model.Conference;
import application.model.ConferenceBooking;
import application.model.Hotel;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HotelPane extends GridPane{
    private ListView<Conference> lvwConferences;
    private ListView<ConferenceBooking> lvwGuests;
    private ListView<Hotel> lvwHotels;
    private TextArea txaConfDeets, txaBookingDeets;
    private Label lbl1, lbl2, lbl3;
    private ComboBox<String> dropdown;
    private Conference conference;
    private static final int PREF_WIDTH = 200;
    private static final int PREF_HEIGHT = 240;

    private ComboBox<Conference> dropdownConferences;
    public HotelPane() {
        this.setPadding(new Insets(5));
        this.setHgap(10);
        this.setVgap(10);

        GridPane dropdownPane = new GridPane();
        this.add(dropdownPane, 0, 0);
        dropdownPane.setPadding(new Insets(5));
        dropdownPane.setHgap(0);
        dropdownPane.setVgap(10);

        GridPane pane = new GridPane();
        this.add(pane, 1, 0);
        pane.setPadding(new Insets(5));
        pane.setHgap(20);
        pane.setVgap(10);

        Label lblDropdown = new Label("Select conference");
        lblDropdown.setFont(Font.font("Comic sans MS", FontWeight.BOLD, 12));
        dropdownPane.add(lblDropdown, 0, 0);

        Label lblHotels = new Label("Hotels");
        lblHotels.setFont(Font.font("Comic sans MS", FontWeight.BOLD, 12));
        pane.add(lblHotels, 0, 0);

        Label lblGuests = new Label("Guests");
        lblGuests.setFont(Font.font("Comic sans MS", FontWeight.BOLD, 12));
        pane.add(lblGuests, 1, 0);

        lvwHotels = new ListView<>();
        pane.add(lvwHotels, 0, 1);
        lvwHotels.setPrefWidth(PREF_WIDTH);
        lvwHotels.setPrefHeight(PREF_HEIGHT);

        ChangeListener<Hotel> hotelListener = (ov, oldExcursion, newExcursion) -> this.updateGuests();
        lvwHotels.getSelectionModel().selectedItemProperty().addListener(hotelListener);

        lvwGuests = new ListView<>();
        pane.add(lvwGuests, 1, 1);
        lvwGuests.setPrefWidth(PREF_WIDTH);
        lvwGuests.setPrefHeight(PREF_HEIGHT);

        ChangeListener<ConferenceBooking> guestListener = (ov, oldParticipant, newParticipant) -> this.updateDetails();
        lvwGuests.getSelectionModel().selectedItemProperty().addListener(guestListener);

        Label lblBookingDeets = new Label("Booking Details");
        lblBookingDeets.setFont(Font.font("Comic sans MS", FontWeight.BOLD, 12));
        pane.add(lblBookingDeets, 2, 0);

        txaBookingDeets = new TextArea();
        txaBookingDeets.setEditable(false);
        pane.add(txaBookingDeets, 2, 1);
        txaBookingDeets.setPrefWidth(PREF_WIDTH);

        conference = Controller.getConferences().get(0);

        dropdownConferences = new ComboBox<>();
        dropdownPane.add(dropdownConferences, 0, 1);

        dropdownConferences.getItems().addAll(Controller.getConferences());
        dropdownConferences.setValue(Controller.getConferences().get(0));
        dropdownConferences.getSelectionModel().selectFirst();
        dropdownConferences.setOnAction(event -> dropDownUpdate());

        if (conference.getHotels().size() > 0) {
            lvwHotels.getItems().setAll(conference.getHotels());
            if (conference.getHotels().get(0).getGuests().size() > 0) {
                lvwGuests.getItems().setAll(conference.getHotels().get(0).getBookings());
                txaBookingDeets.setText(conference.getHotels().get(0).getBookings().get(0).getDetailsNoHotel());
            }
        }

    }

    private void dropDownAction() {

            if (conference.getHotels().size() > 0) {
                lvwHotels.getItems().setAll(conference.getHotels());
                if (conference.getHotels().get(0).getGuests().size() > 0) {
                    lvwGuests.getItems().setAll(conference.getHotels().get(0).getBookings());
                    txaBookingDeets.setText(conference.getHotels().get(0).getBookings().get(0).getDetailsNoHotel());
                } else {
                    lvwGuests.getItems().clear();
                    txaBookingDeets.clear();
                }

            } else {
                lvwHotels.getItems().clear();
                lvwGuests.getItems().clear();
                txaBookingDeets.clear();

            }
    }
    public void dropDownUpdate() {
        Conference conference = dropdownConferences.getSelectionModel().getSelectedItem();
        if (conference != null && conference.getHotels().size() > 0) {
            lvwHotels.getItems().setAll(conference.getHotels());
            if (conference.getHotels().get(0).getGuests().size() > 0) {
                lvwGuests.getItems().setAll(conference.getHotels().get(0).getBookings());
                txaBookingDeets.setText(conference.getHotels().get(0).getBookings().get(0).getDetailsNoHotel());
            } else {
                lvwGuests.getItems().clear();
                txaBookingDeets.clear();
            }
        } else {
            lvwHotels.getItems().clear();
            lvwGuests.getItems().clear();
            txaBookingDeets.clear();

        }
    }

    private void updateDetails() {
        if (lvwGuests.getSelectionModel().getSelectedItem() != null) {
            txaBookingDeets.setText(lvwGuests.getSelectionModel().getSelectedItem().getDetailsNoHotel());
        }
    }

    public void updateGuests() {
      if (lvwHotels.getSelectionModel().getSelectedItem() != null) {
          lvwGuests.getItems().setAll(lvwHotels.getSelectionModel().getSelectedItem().getBookings());
          txaBookingDeets.setText(lvwHotels.getSelectionModel().getSelectedItem().getBookings().get(0).getDetailsNoHotel());
      }
    }

    public void updateControls() {
        dropdownConferences.getItems().setAll(Controller.getConferences());
        dropdownConferences.getSelectionModel().selectFirst();
        Conference conference = dropdownConferences.getItems().get(0);
        if (conference.getHotels().size() > 0) {
            lvwHotels.getItems().setAll(conference.getHotels());
            if (conference.getHotels().get(0).getGuests().size() > 0) {
                lvwGuests.getItems().setAll(conference.getHotels().get(0).getBookings());
                txaBookingDeets.setText(conference.getHotels().get(0).getBookings().get(0).getDetailsNoHotel());
            } else {
                lvwGuests.getItems().clear();
                txaBookingDeets.clear();
            }
        } else {
            lvwHotels.getItems().clear();
            lvwGuests.getItems().clear();
            txaBookingDeets.clear();

        }
    }
}
