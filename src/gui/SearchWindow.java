package gui;

import application.model.Companion;
import application.model.ConferenceBooking;
import application.model.Participant;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SearchWindow extends Stage {
    private TextField nametxf,adresstxf,phonetxf;
    private ListView<ConferenceBooking> lvwBookings = new ListView<>();
    private ListView<Companion> ledsagerLsw = new ListView<>();
    private Participant participant;
    private TextArea txaParticipantInfo, txaBookingInfo;
    private static final int PREF_WIDTH = 200;
    private static final int PREF_HEIGHT = 260;
    public SearchWindow(Participant participant) {
        this.participant = participant;
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.setTitle("Search for participant");
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);    }
    public void initContent(GridPane pane){
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblPartInfo = new Label("Participant info:");
        pane.add(lblPartInfo,0,0);

        txaParticipantInfo = new TextArea();
        txaParticipantInfo.setEditable(false);
        txaParticipantInfo.setPrefWidth(PREF_WIDTH);
        txaParticipantInfo.setPrefHeight(PREF_HEIGHT);
        pane.add(txaParticipantInfo,0,1);

        Label lblBooking = new Label("Bookings:");
        pane.add(lblBooking,1,0);

        lvwBookings = new ListView<>();
        lvwBookings.setPrefHeight(PREF_HEIGHT);
        lvwBookings.setPrefWidth(PREF_WIDTH);
        pane.add(lvwBookings,1,1);


        ChangeListener<ConferenceBooking> listener = (observable, oldValue, newValue) -> updateBookingSelection();
        lvwBookings.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblBookingInfo = new Label("Booking info:");
        pane.add(lblBookingInfo,2,0);

        txaBookingInfo = new TextArea();
        txaBookingInfo.setEditable(false);
        txaBookingInfo.setPrefWidth(PREF_WIDTH);
        txaBookingInfo.setPrefHeight(PREF_HEIGHT);
        pane.add(txaBookingInfo,2,1);

        Button btnOk = new Button("Close");
        pane.add(btnOk,2,3);
        btnOk.setPrefWidth(80);
        GridPane.setHalignment(btnOk, HPos.RIGHT);
        btnOk.setOnAction(event -> btnOk());

        if (participant.getBookings().size() > 0) {
            lvwBookings.getItems().setAll(participant.getBookings());
            lvwBookings.getSelectionModel().select(0);
            txaBookingInfo.setText(participant.getBookings().get(0).getSearchDetails());
        }
        txaParticipantInfo.setText(participant.getDetails());
    }
    public void btnOk(){
        this.close();
    }

    public void updateBookingSelection() {
        if (lvwBookings.getSelectionModel().getSelectedItem() != null) {
            ConferenceBooking booking = lvwBookings.getSelectionModel().getSelectedItem();
            txaBookingInfo.setText(booking.getSearchDetails());
        }
    }
}