package gui;

import application.controller.Controller;
import application.model.Companion;
import application.model.Conference;
import application.model.Excursion;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ExcursionPane extends GridPane {
    private ListView<Excursion> lvwExcursions;
    private ListView<Companion> lvwCompanions;
    private TextArea txaConfDeets, txaBookingDeets;
    private Label lbl1, lbl2, lbl3;
    private ComboBox<Conference> dropdownConferences;
    private Conference conference;

    private static final int PREF_WIDTH = 200;
    private static final int PREF_HEIGHT = 240;
    public ExcursionPane() {
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


        Label lblExcursion = new Label("Excursions");
        lblExcursion.setFont(Font.font("Comic sans MS", FontWeight.BOLD, 12));
        pane.add(lblExcursion, 0, 0);

        Label lblCompanions = new Label("Participating Companions");
        lblCompanions.setFont(Font.font("Comic sans MS", FontWeight.BOLD, 12));
        pane.add(lblCompanions, 1, 0);


        lvwExcursions = new ListView<>();
        pane.add(lvwExcursions, 0, 1);
        lvwExcursions.setPrefWidth(PREF_WIDTH);
        lvwExcursions.setPrefHeight(PREF_HEIGHT);
        lvwExcursions.setStyle("-fx-font-family: 'Comic Sans MS'");

        lvwCompanions = new ListView<>();
        pane.add(lvwCompanions, 1, 1);
        lvwCompanions.setPrefWidth(PREF_WIDTH);
        lvwCompanions.setPrefHeight(PREF_HEIGHT);
        lvwCompanions.setStyle("-fx-font-family: 'Comic Sans MS'");

        conference = Controller.getConferences().get(0);

        dropdownConferences = new ComboBox<>();
        dropdownPane.add(dropdownConferences, 0, 1);
        dropdownConferences.getItems().addAll(Controller.getConferences());
        dropdownConferences.setValue(Controller.getConferences().get(0));
        dropdownConferences.setOnAction(event -> updateConferenceSelection());
        dropdownConferences.setStyle("-fx-font-family: 'Comic Sans MS'");

        if (conference.getExcursions().get(0).getParticipatingCompanions().size() > 0) {
            lvwCompanions.getItems().setAll(conference.getExcursions().get(0).getParticipatingCompanions());
        }

        ChangeListener<Excursion> excursionListener = (ov, oldExcursion, newExcursion) -> this.updateCompanions();
        lvwExcursions.getSelectionModel().selectedItemProperty().addListener(excursionListener);

    }
    public void updateCompanions() {
        Excursion selectedExcursion = lvwExcursions.getSelectionModel().getSelectedItem();
        if (selectedExcursion != null && selectedExcursion.getParticipatingCompanions().size() > 0) {
            lvwCompanions.getItems().setAll(lvwExcursions.getSelectionModel().getSelectedItem().getParticipatingCompanions());
        } else {
        }
    }
    public void updateConferenceSelection() {
        conference = dropdownConferences.getValue();
        if (conference != null && conference.getExcursions().size() > 0) {
            lvwExcursions.getItems().setAll(conference.getExcursions());
            if (!conference.getExcursions().get(0).getParticipatingCompanions().isEmpty()) {
                lvwCompanions.getItems().setAll(dropdownConferences.getValue().getExcursions().get(0).getParticipatingCompanions());
            } else {
                lvwCompanions.getItems().clear();
            }
        } else {
            lvwExcursions.getItems().clear();
            lvwCompanions.getItems().clear();

        }
    }
    public void updateControls() {
        dropdownConferences.getItems().setAll(Controller.getConferences());
        dropdownConferences.setValue(dropdownConferences.getItems().get(0));
        Conference conference = Controller.getConferences().get(0);
        if (conference != null && conference.getExcursions().size() > 0) {
            lvwExcursions.getItems().setAll(conference.getExcursions());
            if (lvwExcursions.getSelectionModel().getSelectedItem() != null && lvwExcursions.getSelectionModel().getSelectedItem().getParticipatingCompanions().size() > 0) {
                lvwCompanions.getItems().setAll(lvwExcursions.getSelectionModel().getSelectedItem().getParticipatingCompanions());
            }

        }
    }

    }
//		---------------------------------------------------------------------------