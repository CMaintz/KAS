package gui;

import application.controller.Controller;
import application.model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.util.ArrayList;

public class BookingWindow extends Stage {
    private Participant selectedParticipant;
    private Conference conference;
    private RoomType selectedRoomType;
    private Hotel selectedHotel;
    private ArrayList<AddonPurchase> selectedAddons;
    private ArrayList<Excursion> selectedExcursions;
    private Companion companion;

    public BookingWindow(String title, Conference conference) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);


        this.conference = conference;

        this.setTitle(title);
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(5));
        pane.setHgap(5);
        pane.setVgap(10);

        Scene scene = new Scene(pane);
        this.setScene(scene);


        this.initContent(pane);
        this.initControls();
    }

    // -------------------------------------------------------------------------

    private ListView<Participant> lvwParticipants;
    private DatePicker datePickStartDate, datePickEndDate;
    private TextField txfName, txfAddress, txfPrice, txfPrice2, txfPhone;
    private CheckBox chbLecturer, chbHotel, chbCompanion, chbAddons, chbExcursion;
    private ComboBox<Hotel> cbxHotels;
    private ComboBox<String> cbxRoomType;
    private ComboBox<Companion> cbxCompanions;
    private ListView<AddonPurchase> lvwAddons;
    private ListView<Excursion> lvwExcursions;
    private VBox vbxInclude;
    private Button btnCreateParticipant, btnAddAddons, btnAddExcursions, btnCreateCompanion;
    private Label lblExcursions, lblAddons, lblDayPrice, lblStartDate, lblEndDate;


    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);

        GridPane participantPane = new GridPane();
        pane.add(participantPane, 0, 0);
        participantPane.setPadding(new Insets(10));
        participantPane.setHgap(20);
        participantPane.setVgap(10);

        Label lblParticipants = new Label("Pick a participant");
        participantPane.add(lblParticipants, 0, 0);

        lvwParticipants = new ListView<>();
        lvwParticipants.setPrefWidth(200);
        lvwParticipants.setPrefHeight(200);
        participantPane.add(lvwParticipants, 0, 1);
        lvwParticipants.getItems().setAll(showUnbookedParticipants());

        ChangeListener<Participant> listener = (observable, oldValue, newValue) -> selectedParticipantChanged();
        lvwParticipants.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblNewParticipant = new Label("Or add a new participant");
        participantPane.add(lblNewParticipant, 0, 2);

        btnCreateParticipant = new Button("Create Participant");
        participantPane.add(btnCreateParticipant, 0, 3);
        btnCreateParticipant.setOnAction(event -> this.newParticipantAction());

        GridPane bookingPane = new GridPane();
        pane.add(bookingPane, 1, 0);
        bookingPane.setPadding(new Insets(10));
        bookingPane.setHgap(20);
        bookingPane.setVgap(10);

        LocalDate minDate = conference.getStartDate();
        LocalDate maxDate = conference.getEndDate();

        GridPane inclusionsPane = new GridPane();
        pane.add(inclusionsPane, 2, 0);
        inclusionsPane.setPadding(new Insets(10));
        inclusionsPane.setHgap(20);
        inclusionsPane.setVgap(10);
        inclusionsPane.setPrefWidth(200);

        GridPane companionPane = new GridPane();
        pane.add(companionPane, 3, 0);
        companionPane.setPadding(new Insets(10));
        companionPane.setHgap(10);
        companionPane.setVgap(10);

        GridPane buttonPane = new GridPane();
        pane.add(buttonPane, 0, 1, 4, 1);
        buttonPane.setPadding(new Insets(10));
        buttonPane.setHgap(10);
        buttonPane.setVgap(10);

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 3, 1);
        pane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setStyle("-fx-background-color: lightcoral; -fx-font-weight: bold");
        btnCancel.setCancelButton(true);
        btnCancel.setOnAction(event -> this.cancelAction());


        Button btnOk = new Button("Complete Booking");
        pane.add(btnOk, 3, 1);
        pane.setHalignment(btnOk, HPos.RIGHT);
        btnOk.setStyle("-fx-background-color: lightgreen; -fx-font-weight: bold");
        btnOk.setOnAction(event -> this.okAction());

        lblDayPrice = new Label("Price / day: " + conference.getDayPrice());
        bookingPane.add(lblDayPrice, 0, 0);

        lblStartDate = new Label("First Attendance Day");
        bookingPane.add(lblStartDate, 0, 1);

        datePickStartDate = new DatePicker();
        datePickStartDate.setPrefWidth(200);
        bookingPane.add(datePickStartDate, 0, 2);
        datePickStartDate.setDayCellFactory(d -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(minDate) || item.isAfter(maxDate));
            }
        });
        datePickStartDate.setEditable(false);
        datePickStartDate.setPrefWidth(120);
        datePickStartDate.setOnMouseClicked(event -> datePickStartDate.setValue(minDate));

        lblEndDate = new Label("Last Attendance Day");
        bookingPane.add(lblEndDate, 0, 3);

        datePickEndDate = new DatePicker();
        bookingPane.add(datePickEndDate, 0, 4);
        datePickEndDate.setDayCellFactory(d -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(minDate) || item.isAfter(maxDate));
            }
        });
        datePickEndDate.setEditable(false);
        datePickEndDate.setPrefWidth(120);
        datePickEndDate.setOnMouseClicked(event -> datePickEndDate.setValue(maxDate));

        chbLecturer = new CheckBox("Lecturer");
        bookingPane.add(chbLecturer, 0, 5);

        chbHotel = new CheckBox("Include accommodations");

        inclusionsPane.add(chbHotel, 0, 0);
        chbHotel.setOnAction(event -> this.showHotels());

        cbxHotels = new ComboBox<>();
        inclusionsPane.add(cbxHotels, 0, 1);
        cbxHotels.setVisible(false);
        cbxHotels.setOnAction(event -> hotelSelectionChanged());

        cbxRoomType = new ComboBox<>();
        inclusionsPane.add(cbxRoomType, 0, 2);
        cbxRoomType.setVisible(false);

        chbCompanion = new CheckBox("Companion");
        companionPane.add(chbCompanion, 0, 0);
        chbCompanion.setOnAction(event -> this.showCompanion());

        cbxCompanions = new ComboBox<>();
        companionPane.add(cbxCompanions, 0, 1);
        cbxCompanions.setPrefWidth(170);
        cbxCompanions.setVisible(false);

        btnCreateCompanion = new Button("New Companion");
        companionPane.add(btnCreateCompanion, 0, 2);
        btnCreateCompanion.setOnAction(event -> this.newCompanionAction());
        btnCreateCompanion.setVisible(false);


        lblAddons = new Label("Add-ons");
        inclusionsPane.add(lblAddons, 0, 3);
        lblAddons.setVisible(false);

        lvwAddons = new ListView<>();
        inclusionsPane.add(lvwAddons, 0, 4);
        lvwAddons.setPrefHeight(100);
        lvwAddons.setPrefWidth(170);
        lvwAddons.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvwAddons.setVisible(false);

        // TODO make lvwAddons & lvwExcursions into checkboxes, created with a for-loop?

        btnAddAddons = new Button("Add selected addon(s)");
        inclusionsPane.add(btnAddAddons, 0, 5);
        btnAddAddons.setOnAction(event -> this.addSelectedAddons());
        btnAddAddons.setVisible(false);

        lblExcursions = new Label("Excursions");
        companionPane.add(lblExcursions, 0, 3);
        lblExcursions.setVisible(false);

        lvwExcursions = new ListView<>();
        lvwExcursions.setPrefHeight(100);
        lvwExcursions.setPrefWidth(170);
        companionPane.add(lvwExcursions, 0, 4);
        lvwExcursions.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvwExcursions.setVisible(false);

        btnAddExcursions = new Button("Add selected excursion(s)");
        companionPane.add(btnAddExcursions, 0, 5);

        btnAddExcursions.setOnAction(event -> this.addSelectedExcursions());
        btnAddExcursions.setVisible(false);

        lblDayPrice.setDisable(true);
        lblStartDate.setDisable(true);
        lblEndDate.setDisable(true);
        datePickStartDate.setDisable(true);
        datePickEndDate.setDisable(true);
        chbLecturer.setDisable(true);
        chbCompanion.setDisable(true);
        chbHotel.setDisable(true);
    }

    private void selectedParticipantChanged() {
        if (lblDayPrice.isDisabled()) {
            enableStuff();
        }
        selectedParticipant = lvwParticipants.getSelectionModel().getSelectedItem();
        if (selectedParticipant.getCompanions().size() > 0) {
            cbxCompanions.setDisable(false);
            cbxCompanions.getItems().setAll(selectedParticipant.getCompanions());
            cbxCompanions.getSelectionModel().selectFirst();
            if (conference.getExcursions().size() > 0) {
                btnAddExcursions.setDisable(false);
                lvwExcursions.setDisable(false);
                btnAddExcursions.setDisable(false);
                lblExcursions.setDisable(false);
            } else {
                btnAddExcursions.setDisable(true);
                lvwExcursions.setDisable(true);
                btnAddExcursions.setDisable(true);
                lblExcursions.setDisable(true);
            }
        } else {
            cbxCompanions.getItems().clear();
            cbxCompanions.setDisable(true);
            btnAddExcursions.setDisable(true);
            lvwExcursions.setDisable(true);
            lblExcursions.setDisable(true);
        }
    }

    private void enableStuff() {
        lblDayPrice.setDisable(false);
        lblStartDate.setDisable(false);
        lblEndDate.setDisable(false);
        datePickStartDate.setDisable(false);
        datePickEndDate.setDisable(false);
        chbLecturer.setDisable(false);
        if (conference.getHotels().size() > 0) {
            chbHotel.setDisable(false);
        }
        chbCompanion.setDisable(false);
    }

    private void newCompanionAction() {
        CompanionWindow companionWindow = new CompanionWindow("Create Companion", selectedParticipant, true, null);
        companionWindow.showAndWait();

        if (!selectedParticipant.getCompanions().isEmpty()) {
            cbxCompanions.getItems().setAll(selectedParticipant.getCompanions());
            cbxCompanions.setDisable(false);
            cbxCompanions.getSelectionModel().selectLast();
            if (lvwExcursions.getItems().size() > 0) {
                btnAddExcursions.setDisable(false);
                lvwExcursions.setDisable(false);
                btnAddExcursions.setDisable(false);
            }
        }
    }

    private void addSelectedExcursions() {
        if (cbxCompanions.getValue() != null) {
            if (lvwExcursions.getSelectionModel().getSelectedItems().size() > 0) {
                selectedExcursions = new ArrayList<>(lvwExcursions.getSelectionModel().getSelectedItems());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Excursion");
            alert.setHeaderText("No companion selected");
            alert.show();
        }
    }

    private void addSelectedAddons() {
        if (lvwAddons.getSelectionModel().getSelectedItems().size() > 0) {
            selectedAddons = new ArrayList<>(lvwAddons.getSelectionModel().getSelectedItems());
        }
    }

    private void showCompanion() {
        if (chbCompanion.isSelected()) {
            cbxCompanions.setVisible(true);
            btnCreateCompanion.setVisible(true);
            lblExcursions.setVisible(true);
            lvwExcursions.setVisible(true);
            btnAddExcursions.setVisible(true);
            if (cbxCompanions.getItems().isEmpty()) {
                lblExcursions.setDisable(true);
                lvwExcursions.setDisable(true);
                btnAddExcursions.setDisable(true);
            } else {
                lblExcursions.setDisable(false);
                lvwExcursions.setDisable(false);
                btnAddExcursions.setDisable(false);
            }
            if (conference.getExcursions().size() > 0) {
                lvwExcursions.getItems().setAll(conference.getExcursions());
                if (selectedParticipant.getCompanions().size() > 0) {
                    cbxCompanions.getSelectionModel().selectFirst();
                } else {
                    cbxCompanions.setDisable(true);
                }
            }
        } else {
            cbxCompanions.setVisible(false);
            btnCreateCompanion.setVisible(false);
            lblExcursions.setVisible(false);
            lvwExcursions.setVisible(false);
            btnAddExcursions.setVisible(false);
        }
    }

    private void showHotels() {
        if (chbHotel.isSelected()) {
            cbxHotels.setVisible(true);
            if (cbxHotels.getItems().size() > 0) {
                cbxHotels.getSelectionModel().selectFirst();
                selectedHotel = cbxHotels.getSelectionModel().getSelectedItem();
                cbxRoomType.getItems().setAll("Roomtype Single (" + selectedHotel.getSingleRoomPrice() + ",-)", "Roomtype Double (" + selectedHotel.getDoubleRoomPrice() + ",-)");
                cbxRoomType.setValue(cbxRoomType.getItems().get(0));
                if (selectedHotel.getAddonsOnOffer().size() > 0) {
                    lblAddons.setDisable(false);
                    lvwAddons.setDisable(false);
                }
            }
            lvwAddons.setVisible(true);
            cbxRoomType.setVisible(true);
            lblAddons.setVisible(true);
            btnAddAddons.setVisible(true);

        } else {
            lblAddons.setVisible(false);
            cbxHotels.setVisible(false);
            cbxRoomType.setVisible(false);
            lvwAddons.setVisible(false);
            btnAddAddons.setVisible(false);
        }
    }

    private void hotelSelectionChanged() {
        selectedHotel = cbxHotels.getValue();
        cbxRoomType.getItems().setAll("Roomtype Single (" + selectedHotel.getSingleRoomPrice() + ",-)", "Roomtype Double (" + selectedHotel.getDoubleRoomPrice() + ",-)");
        cbxRoomType.setValue(cbxRoomType.getItems().get(0));
        if (selectedHotel.getAddonsOnOffer().size() > 0) {
            lvwAddons.getItems().setAll(selectedHotel.getAddonsOnOffer());
        } else {
            lvwAddons.getItems().clear();
            lblAddons.setDisable(true);
            lvwAddons.setDisable(true);
            selectedAddons = null;
        }
    }

    private void okAction() {
        checkboxChecker();
//        showErrorMsg();
        if (!showErrorMsg()) {
            if (selectedParticipant != null) {
                ConferenceBooking booking = null;
                try {
                    booking = Controller.createBooking(conference, selectedParticipant, chbLecturer.isSelected(), companion, selectedHotel, selectedRoomType, datePickStartDate.getValue(), datePickEndDate.getValue());
                } catch (NullPointerException e) {
                    showErrorMsg();
                }
                if (booking != null) {
                    if (companion != null && selectedExcursions != null) {
                        Controller.setExcursionsOrdered(booking, selectedExcursions);
//                    booking.setExcursionsOrdered(selectedExcursions);
                    }
                    if (selectedHotel != null && selectedAddons != null) {
                        booking.setAddonsOrdered(selectedAddons);
                        // TODO lav controller kald i stedet.
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Booking created");
                    alert.setContentText(booking.getDetails());
                    alert.showAndWait();
                    this.close();
                }
            }
        }

    }

    private void checkboxChecker() {
        selectedRoomType = null;

        if (!chbHotel.isSelected()) {
            selectedHotel = null;
            selectedRoomType = null;
            selectedAddons = null;
        } else if (chbHotel.isSelected() && selectedHotel != null) {
            if (cbxRoomType.getValue().compareToIgnoreCase("Roomtype Single (" + selectedHotel.getSingleRoomPrice() + ",-)") == 0) {
                selectedRoomType = RoomType.SINGLE;
            } else if (cbxRoomType.getValue().compareToIgnoreCase("Roomtype Double (" + selectedHotel.getDoubleRoomPrice() + ",-)") == 0) {
                selectedRoomType = RoomType.DOUBLE;
            }
        }
        if (!chbCompanion.isSelected()) {
            companion = null;
            selectedExcursions = null;
        } else if (chbCompanion.isSelected()) {
            if (cbxCompanions.getItems().isEmpty()) {
                companion = null;
                selectedExcursions = null;
            } else if (cbxCompanions.getValue() != null && selectedExcursions != null) {
                companion = cbxCompanions.getValue();
            } else if (cbxCompanions.getValue() == null) {
                companion = null;
                selectedExcursions = null;
            } else {
                companion = cbxCompanions.getValue();
            }
        }
    }

    private boolean showErrorMsg() {
        boolean hasError = false;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        if (selectedParticipant == null) {
            alert.setHeaderText("Please select a participant");
            hasError = true;
        } else if (datePickStartDate.getValue() == null) {
            alert.setHeaderText("Please select a start date");
            hasError = true;
        } else if (datePickEndDate.getValue() == null) {
            alert.setHeaderText("Please select an end date");
            hasError = true;
        } else if (datePickStartDate.getValue().isAfter(datePickEndDate.getValue())) {
            alert.setHeaderText("Start date must be before end date");
            hasError = true;
        } else if (datePickEndDate.getValue().isBefore(datePickStartDate.getValue())) {
            alert.setHeaderText("End date must be after start date");
            hasError = true;
        }
        if (hasError) {
            alert.show();
        }
        return hasError;
    }

    //TODO flyt unbooking til Controller el. lign.
    private ArrayList<Participant> showUnbookedParticipants() {
        ArrayList<Participant> allParticipants = Controller.getParticipants();
        ArrayList<Participant> bookedParticipants = new ArrayList<>(conference.getParticipants());
        ArrayList<Participant> unbookedParticipants = new ArrayList<>();
        for (Participant participant : allParticipants) {
            if (!bookedParticipants.contains(participant)) {
                unbookedParticipants.add(participant);
            }
        }
        return unbookedParticipants;
    }

    private void newParticipantAction() {
        ParticipantWindow participantWindow = new ParticipantWindow("Create Participant", null, true);
        participantWindow.showAndWait();
        lvwParticipants.getItems().setAll(showUnbookedParticipants());
        lvwParticipants.getSelectionModel().selectLast();
    }

    private void cancelAction() {
        this.hide();
    }

    private void initControls() {
        if (conference.getExcursions().size() > 0) {
            lvwExcursions.getItems().setAll(conference.getExcursions());
        } else {
            lblExcursions.setDisable(true);
            lvwExcursions.setDisable(true);
        }
        if (conference.getHotels().size() > 0) {
            selectedHotel = conference.getHotels().get(0);
            cbxHotels.getItems().setAll(conference.getHotels());
            cbxHotels.setValue(cbxHotels.getItems().get(0));
            if (selectedHotel.getAddonsOnOffer().size() > 0) {
                lvwAddons.getItems().setAll(conference.getHotels().get(0).getAddonsOnOffer());
            } else {
                lblAddons.setDisable(true);
                lvwAddons.setDisable(true);
            }
        } else {

        }
        btnCreateParticipant.requestFocus();
    }

}
