package gui;

import application.controller.Controller;
import application.model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Optional;

public class AdministrationPane extends GridPane {
    private ListView<Conference> lvwConferences;
    private ListView<Participant> lvwParticipants;
    private ListView<Excursion> lvwExcursions;
    private ListView<Hotel> lvwHotels;
    private ListView<AddonPurchase> lvwAddons;
    private ComboBox<String> cbxParticipants;
    private static final int PADDING = 5;
    private static final int HGAP = 5;
    private static final int VGAP = 10;
    private static final int PREF_WIDTH = 190;
    private static final int PREF_HEIGHT = 240;
    private static final Pos BUTTON_POS = Pos.CENTER;
    private String allParticipants = "All Participants";
    private String conferenceParticipants = "";

    public AdministrationPane() {
        this.setPadding(new Insets(PADDING));
        this.setHgap(HGAP * 2);
        this.setVgap(VGAP);

        GridPane conferencePane = new GridPane();
        this.add(conferencePane, 0, 0);
        conferencePane.setPadding(new Insets(PADDING));
        conferencePane.setHgap((HGAP * 4));
        conferencePane.setVgap(VGAP);

        Label lblConferences = new Label("Conferences");
        lblConferences.setFont(Font.font("Comic sans MS", FontWeight.BOLD, 12));
        conferencePane.add(lblConferences, 0, 0);

        lvwConferences = new ListView<>();
        conferencePane.add(lvwConferences, 0, 1);
        lvwConferences.setPrefWidth(PREF_WIDTH);
        lvwConferences.setPrefHeight(PREF_HEIGHT);
        lvwConferences.getItems().setAll(Controller.getConferences());

        ChangeListener<Conference> listener = (ov, oldConference, newConference) -> this.updateConferenceControls();
        lvwConferences.getSelectionModel().selectedItemProperty().addListener(listener);

        HBox hbxConfButtons = new HBox(HGAP);
        conferencePane.add(hbxConfButtons, 0, 2);
        hbxConfButtons.setAlignment(BUTTON_POS);

        Button btnCreateConf = new Button("Create");
        hbxConfButtons.getChildren().add(btnCreateConf);
        btnCreateConf.setOnAction(event -> this.createConferenceAction());

        Button btnUpdateConf = new Button("Update");
        hbxConfButtons.getChildren().add(btnUpdateConf);
        btnUpdateConf.setOnAction(event -> this.updateConferenceAction());

        Button btnDeleteConf = new Button("Delete");
        hbxConfButtons.getChildren().add(btnDeleteConf);
        btnDeleteConf.setOnAction(event -> this.deleteConferenceAction());

        cbxParticipants = new ComboBox<>();
        conferencePane.add(cbxParticipants, 1, 0);
        cbxParticipants.setPrefWidth(PREF_WIDTH);
        cbxParticipants.setOnAction(e -> updateParticipantList());

        lvwParticipants = new ListView<>();
        lvwParticipants.setPrefWidth(PREF_WIDTH);
        lvwParticipants.setPrefHeight(PREF_HEIGHT);
        conferencePane.add(lvwParticipants, 1, 1);

        HBox hbxPartButtons = new HBox(HGAP);
        conferencePane.add(hbxPartButtons, 1, 2);
        hbxPartButtons.setAlignment(BUTTON_POS);

        Button btnCreatePart = new Button("Create");
        hbxPartButtons.getChildren().add(btnCreatePart);
        btnCreatePart.setOnAction(event -> this.createParticipantAction());

        Button btnUpdatePart = new Button("Update");
        hbxPartButtons.getChildren().add(btnUpdatePart);
        btnUpdatePart.setOnAction(event -> this.updateParticipantAction());

        Button btnDeletePart = new Button("Delete");
        hbxPartButtons.getChildren().add(btnDeletePart);
        btnDeletePart.setOnAction(event -> this.deleteParticipantAction());

        Label lblExcursion = new Label("Excursions");
        lblExcursion.setFont(Font.font("Comic sans MS", FontWeight.BOLD, 12));
        conferencePane.add(lblExcursion, 2, 0);

        lvwExcursions = new ListView<>();
        conferencePane.add(lvwExcursions, 2, 1);
        lvwExcursions.setPrefWidth(PREF_WIDTH);
        lvwExcursions.setPrefHeight(PREF_HEIGHT);

        HBox hbxExcurButtons = new HBox(HGAP);
        conferencePane.add(hbxExcurButtons, 2, 2);
        hbxExcurButtons.setAlignment(BUTTON_POS);

        Button btnCreateExcursion = new Button("Create");
        hbxExcurButtons.getChildren().add(btnCreateExcursion);
        btnCreateExcursion.setOnAction(event -> this.createExcursionAction());

        Button btnUpdateExcursion = new Button("Update");
        hbxExcurButtons.getChildren().add(btnUpdateExcursion);
        btnUpdateExcursion.setOnAction(event -> this.updateExcursionAction());

        Button btnDeleteExcursion = new Button("Delete");
        hbxExcurButtons.getChildren().add(btnDeleteExcursion);
        btnDeleteExcursion.setOnAction(event -> this.deleteExcursionAction());

        Label lblHotels = new Label("Hotels");
        lblHotels.setFont(Font.font("Comic sans MS", FontWeight.BOLD, 12));
        conferencePane.add(lblHotels, 3, 0);

        lvwHotels = new ListView<>();
        conferencePane.add(lvwHotels, 3, 1);
        lvwHotels.setPrefWidth(PREF_WIDTH);
        lvwHotels.setPrefHeight(PREF_HEIGHT);

        lvwHotels.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lvwAddons.getItems().setAll(newValue.getAddonsOnOffer());
            }
        });

        HBox hbxHotelButtons = new HBox(HGAP);
        conferencePane.add(hbxHotelButtons, 3, 2);
        hbxHotelButtons.setAlignment(BUTTON_POS);

        Button btnCreateHotel = new Button("Create");
        hbxHotelButtons.getChildren().add(btnCreateHotel);
        btnCreateHotel.setOnAction(event -> this.createHotelAction());

        Button btnUpdateHotel = new Button("Update");
        hbxHotelButtons.getChildren().add(btnUpdateHotel);
        btnUpdateHotel.setOnAction(event -> this.updateHotelAction());

        Button btnDeleteHotel = new Button("Delete");
        hbxHotelButtons.getChildren().add(btnDeleteHotel);
        btnDeleteHotel.setOnAction(event -> this.deleteHotelAction());

        Label lblAddons = new Label("Additional Services");
        lblAddons.setFont(Font.font("Comic sans MS", FontWeight.BOLD, 12));
        conferencePane.add(lblAddons, 4, 0);

        lvwAddons = new ListView<>();
        conferencePane.add(lvwAddons, 4, 1);
        lvwAddons.setPrefWidth(140);
        lvwAddons.setPrefHeight(PREF_HEIGHT);

        HBox hbxAddonButtons = new HBox(HGAP);
        conferencePane.add(hbxAddonButtons, 4, 2);
        hbxAddonButtons.setAlignment(BUTTON_POS);

        Button btnCreateAddon = new Button("Create");
        hbxAddonButtons.getChildren().add(btnCreateAddon);
        btnCreateAddon.setOnAction(event -> this.createAddonAction());

        Button btnDeleteAddon = new Button("Delete");
        hbxAddonButtons.getChildren().add(btnDeleteAddon);
        btnDeleteAddon.setOnAction(event -> this.deleteAddonAction());

    }

    private void deleteExcursionAction() {
        Excursion exc = lvwExcursions.getSelectionModel().getSelectedItem();
        if (exc == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Excursion");
            alert.setHeaderText("No excursion selected");
            alert.setContentText("Please select an excursion to delete");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Excursion");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Are you sure you want to delete this excursion?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Controller.deleteExcursion(exc);
                lvwExcursions.getItems().setAll(lvwConferences.getSelectionModel().getSelectedItem().getExcursions());
                lvwExcursions.getSelectionModel().select(0);
            }
        }
    }

    private void updateExcursionAction() {
        Excursion exc = lvwExcursions.getSelectionModel().getSelectedItem();
        if (exc != null) {
            int index = lvwExcursions.getSelectionModel().getSelectedIndex();
            ExcursionWindow dia = new ExcursionWindow("Update Excursion", null, exc, false);
            dia.showAndWait();
            lvwExcursions.getItems().setAll(lvwConferences.getSelectionModel().getSelectedItem().getExcursions());
            lvwExcursions.getSelectionModel().select(index);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Excursion");
            alert.setHeaderText("No excursion selected");
            alert.setContentText("Please select an excursion to update");
            alert.showAndWait();
        }
    }

    private void createExcursionAction() {
        Conference conference = lvwConferences.getSelectionModel().getSelectedItem();
        if (conference != null) {
            ExcursionWindow dia = new ExcursionWindow("Create Excursion", conference, null, true);
            dia.showAndWait();
            lvwExcursions.getItems().setAll(conference.getExcursions());
            lvwExcursions.getSelectionModel().selectLast();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Create Excursion");
            alert.setHeaderText("No conference selected");
            alert.setContentText("Please select a conference to create an excursion for");
            alert.showAndWait();
        }
    }

    private void deleteParticipantAction() {

        Participant participant = lvwParticipants.getSelectionModel().getSelectedItem();
        if (participant != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Participant");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Are you sure you want to delete this participant?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Controller.deleteParticipant(participant);
                updateParticipantList();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Participant");
            alert.setHeaderText("No participant selected");
            alert.setContentText("Please select a participant to delete");
            alert.showAndWait();
        }
    }

    private void updateParticipantAction() {

        Participant participant = lvwParticipants.getSelectionModel().getSelectedItem();
        if (participant != null) {
            int index = lvwParticipants.getSelectionModel().getSelectedIndex();
            ParticipantWindow dia = new ParticipantWindow("Update Participant", participant, false);
            dia.showAndWait();
            updateParticipantList();
            lvwParticipants.getSelectionModel().select(index);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Participant");
            alert.setHeaderText("No participant selected");
            alert.setContentText("Please select a participant to update");
            alert.showAndWait();
        }
    }

    private void createParticipantAction() {
        ParticipantWindow dia = new ParticipantWindow("Create Participant", null, true);
        dia.showAndWait();
        updateParticipantList();
        lvwParticipants.getSelectionModel().selectLast();
    }

    private void deleteConferenceAction() {

        Conference conference = lvwConferences.getSelectionModel().getSelectedItem();
        if (conference != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Conference");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Are you sure you want to delete this conference?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Controller.deleteConference(conference);
                lvwConferences.getItems().setAll(Controller.getConferences());
                lvwConferences.getSelectionModel().select(0);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Conference");
            alert.setHeaderText("No conference selected");
            alert.setContentText("Please select a conference to delete");
            alert.showAndWait();
        }
    }

    private void updateConferenceAction() {
        Conference conference = lvwConferences.getSelectionModel().getSelectedItem();
        if (conference != null) {
            int index = lvwConferences.getSelectionModel().getSelectedIndex();
            ConferenceWindow dia = new ConferenceWindow("Update Conference", conference, false);
            dia.showAndWait();
            lvwConferences.getItems().setAll(Controller.getConferences());
            lvwConferences.getSelectionModel().select(index);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Conference");
            alert.setHeaderText("No conference selected");
            alert.show();
        }
    }

    private void createConferenceAction() {
        ConferenceWindow dia = new ConferenceWindow("Create Conference", null, true);
        dia.showAndWait();
        lvwConferences.getItems().setAll(Controller.getConferences());
        lvwConferences.getSelectionModel().selectLast();
    }

    private void updateHotelAction() {
        Hotel hotel = lvwHotels.getSelectionModel().getSelectedItem();
        if (hotel != null) {
            int index = lvwHotels.getSelectionModel().getSelectedIndex();
            HotelWindow dia = new HotelWindow("Update Hotel", hotel, null, false);
            dia.showAndWait();
            lvwHotels.getItems().setAll(lvwConferences.getSelectionModel().getSelectedItem().getHotels());
            lvwHotels.getSelectionModel().select(index);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Hotel");
            alert.setHeaderText("No hotel selected");
            alert.show();
        }
    }

    private void createHotelAction() {
        Conference conference = lvwConferences.getSelectionModel().getSelectedItem();
        if (conference != null) {
            HotelWindow dia = new HotelWindow("Create Hotel", null, conference, true);
            dia.showAndWait();
            updateHotels(conference);
            lvwHotels.getSelectionModel().selectLast();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Create Hotel");
            alert.setHeaderText("No conference selected");
            alert.show();
        }
    }

    private void deleteAddonAction() {
        AddonPurchase addon = lvwAddons.getSelectionModel().getSelectedItem();
        if (addon != null) {
            Hotel hotel = addon.getHotel();
            if (!hotel.addonBooked(addon)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Addon");
                alert.setHeaderText("Are you sure?");
                Optional<ButtonType> result = alert.showAndWait();
                if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                    Controller.deleteAddon(addon);
                    updateControls();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Addon");
                alert.setHeaderText("Can't delete an addon that's been booked");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Addon");
            alert.setHeaderText("No addon selected");
            alert.show();
        }
    }

    private void deleteHotelAction() {
        Hotel hotel = lvwHotels.getSelectionModel().getSelectedItem();
        if (hotel != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Hotel");
            alert.setHeaderText("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                Controller.deleteHotel(hotel);
                updateControls();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Hotel");
            alert.setHeaderText("No hotel selected");
            alert.show();
        }
    }


    private void createAddonAction() {
        Hotel selectedHotel = lvwHotels.getSelectionModel().getSelectedItem();
        if (selectedHotel != null) {
            AddonWindow addonWindow = new AddonWindow("Create Addon", selectedHotel);
            addonWindow.showAndWait();
            lvwAddons.getItems().setAll(selectedHotel.getAddonsOnOffer());
            lvwAddons.getSelectionModel().selectLast();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Create Addon");
            alert.setHeaderText("No hotel selected");
            alert.show();
        }
    }

    private void updateConferenceControls() {
        Conference conference = lvwConferences.getSelectionModel().getSelectedItem();
        if (conference != null) {
            updateParticipantList();
            comboboxUpdate();
            updateExcursions(conference);
            updateHotels(conference);
        }
    }


    public void updateControls() {
        int index = lvwConferences.getSelectionModel().getSelectedIndex();
        lvwConferences.getItems().setAll(Controller.getConferences());
        lvwConferences.getSelectionModel().select(index);
        Conference conference = lvwConferences.getSelectionModel().getSelectedItem();
        if (conference != null) {
            updateParticipantList();
            comboboxUpdate();
            updateExcursions(conference);
            updateHotels(conference);
        }
    }

    private void updateExcursions(Conference conference) {
        if (conference.getExcursions().size() > 0) {
            lvwExcursions.getItems().setAll(conference.getExcursions());
        } else {
            lvwExcursions.getItems().clear();
        }
    }

    private void updateHotels(Conference conference) {
        if (conference.getHotels().size() > 0) {
            lvwHotels.getItems().setAll(conference.getHotels());
            if (conference.getHotels().get(0).getAddonsOnOffer().size() > 0) {
                lvwAddons.getItems().setAll(conference.getHotels().get(0).getAddonsOnOffer());
            }
        } else {
            lvwHotels.getItems().clear();
            lvwAddons.getItems().clear();
        }
    }

    private void comboboxUpdate() {
        int index = cbxParticipants.getSelectionModel().getSelectedIndex();
        conferenceParticipants = lvwConferences.getSelectionModel().getSelectedItem().getName() + " Participants";
        cbxParticipants.getItems().setAll(conferenceParticipants, allParticipants);
        cbxParticipants.getSelectionModel().select(index);
    }

    public void initControls() {
        lvwConferences.getSelectionModel().select(0);
        Conference conference = lvwConferences.getSelectionModel().getSelectedItem();
        if (conference != null) {
            comboboxUpdate();
            cbxParticipants.getSelectionModel().select(0);
            lvwParticipants.getItems().setAll(conference.getParticipants());
            updateExcursions(conference);
            updateHotels(conference);
        } else {
            lvwParticipants.getItems().clear();
            lvwExcursions.getItems().clear();
        }
    }

    public void updateParticipantList() {
            if (cbxParticipants.getSelectionModel().getSelectedIndex() == 0) {
                lvwParticipants.getItems().setAll(lvwConferences.getSelectionModel().getSelectedItem().getParticipants());
            } else {
                lvwParticipants.getItems().setAll(Controller.getParticipants());
            }
    }



}
