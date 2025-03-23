package gui;

import application.controller.Controller;
import application.model.Conference;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class ConferenceWindow extends Stage {
    private Conference conference;
    private boolean isNewObject;
    private boolean newObjectCreated;

    public ConferenceWindow(String title, Conference conference, boolean isNewObject) {

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);

        this.isNewObject = isNewObject;
        this.conference = conference;

        this.setTitle(title);
        GridPane pane = new GridPane();

        Scene scene = new Scene(pane);
        this.setScene(scene);

        this.initContent(pane);

    }

    private TextField txfName, txfAddress, txfPrice;
    private DatePicker dateStartDate, dateEndDate;
    private Label lblError;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(5));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPrefWidth(240);
        pane.setAlignment(Pos.CENTER);

        Label lblName = new Label("Conference name");
        pane.add(lblName, 0, 0);

        txfName = new TextField();
        pane.add(txfName, 0, 1);
        txfName.setPrefWidth(200);

        Label lblAddress = new Label("Address");
        pane.add(lblAddress, 0, 2);

        txfAddress = new TextField();
        pane.add(txfAddress, 0, 3);

        Label lblPrice = new Label("Price per day");
        pane.add(lblPrice, 0, 4);

        txfPrice = new TextField();
        pane.add(txfPrice, 0, 5);

        Label lblStartDate = new Label("Conference start date");
        pane.add(lblStartDate, 0, 6);

        dateStartDate = new DatePicker();
        pane.add(dateStartDate, 0, 7);
        dateStartDate.setPrefWidth(200);

        Label lblEndDate = new Label("Conference end date");
        pane.add(lblEndDate, 0, 8);

        dateEndDate = new DatePicker();
        pane.add(dateEndDate, 0, 9);
        dateEndDate.setPrefWidth(200);

        lblError = new Label();
        pane.add(lblError, 0, 10);
        lblError.setStyle("-fx-text-fill: red");

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 11);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setStyle("-fx-background-color: lightcoral; -fx-font-weight: bold");
        btnCancel.setCancelButton(true);
        btnCancel.setOnAction(event -> this.cancelAction());

        Button btnOk = new Button("Create");
        pane.add(btnOk, 0, 11);
        GridPane.setHalignment(btnOk, HPos.RIGHT);
        btnOk.setStyle("-fx-background-color: lightgreen; -fx-font-weight: bold");
        btnOk.setOnAction(event -> this.okAction());

        if (!isNewObject) {
            btnOk.setText("Update");
        }

        this.initControls();
    }

    private void initControls() {
        if (conference != null) {
            txfName.setText(conference.getName());
            txfAddress.setText(conference.getAddress());
            txfPrice.setText("" + conference.getDayPrice());
            dateStartDate.setValue(conference.getStartDate());
            dateEndDate.setValue(conference.getEndDate());
        } else {
            txfName.clear();
            txfAddress.clear();
            txfPrice.clear();
        }
    }

    private void cancelAction() { this.close(); }

    private void okAction(){
        String name = txfName.getText().trim();
        String address = txfAddress.getText().trim();
        int pricePerDay;

        LocalDate startDate = dateStartDate.getValue();
        LocalDate endDate = dateEndDate.getValue();
        if (name.isEmpty()) {
            lblError.setText("Name is empty");
        } else if (address.isEmpty()) {
            lblError.setText("Address is empty");
        } else if (dateStartDate.getValue() == null) {
            lblError.setText("Please select a start date");
        } else if (dateEndDate.getValue() == null) {
            lblError.setText("Please select an end date");
        } else if (dateStartDate.getValue().isAfter(dateEndDate.getValue())) {
            lblError.setText("Start date must be before end date");
        } else if (dateEndDate.getValue().isBefore(dateStartDate.getValue())) {
            lblError.setText("End date must be after start date");
        } else {
            pricePerDay = -1;
            try {
                pricePerDay = Integer.parseInt(txfPrice.getText().trim());
            } catch (NumberFormatException ex) {
                lblError.setText("Price error");
            }
            if (pricePerDay < 0) {
                lblError.setText("Invalid price");
            } else {
                if (conference != null && !isNewObject) {
                    Controller.updateConference(conference, name, address, pricePerDay, startDate, endDate);
                } else if (isNewObject) {
                    Controller.createConference(name, address, pricePerDay, startDate, endDate);
                }
                this.close();
            }
        }
    }

}


