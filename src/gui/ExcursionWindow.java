package gui;

import application.controller.Controller;
import application.model.Conference;
import application.model.Excursion;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class ExcursionWindow  extends Stage {
    private Conference conference;
    private Excursion excursion;
    private boolean isNewObject;

    public ExcursionWindow(String title, Conference conference, Excursion excursion, boolean isNewObject) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.conference = conference;
        this.isNewObject = isNewObject;
        this.excursion = excursion;

        this.setTitle(title);

        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private TextField txfName, txfAddress, txfPrice;
    private DatePicker dateExcursion;
    private Label lblError;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(5));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPrefWidth(240);
        pane.setAlignment(Pos.CENTER);

        Label lblName = new Label("Name");
        pane.add(lblName, 0, 0);

        txfName = new TextField();
        pane.add(txfName, 0, 1);
        txfName.setPrefWidth(200);

        Label lblAddress = new Label("Address");
        pane.add(lblAddress, 0, 2);

        txfAddress = new TextField();
        pane.add(txfAddress, 0, 3);

        Label lblPrice = new Label("Excursion price");
        pane.add(lblPrice, 0, 4);

        txfPrice = new TextField();
        pane.add(txfPrice, 0, 5);

        Label date = new Label("Pick a date for the excursion");
        pane.add(date, 0, 6);

        dateExcursion = new DatePicker();
        pane.add(dateExcursion, 0, 7);
        dateExcursion.setPrefWidth(200);
        dateExcursion.setDayCellFactory(d -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(conference.getStartDate()) || item.isAfter(conference.getEndDate()));
            }
        });


        Button btnOk = new Button("Create");
        pane.add(btnOk, 0, 9);
        GridPane.setHalignment(btnOk, HPos.RIGHT);
        btnOk.setStyle("-fx-background-color: lightgreen; -fx-font-weight: bold");
        btnOk.setOnAction(event -> okAction());

        if (!isNewObject) {
            btnOk.setText("Update");
        }

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 9);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setStyle("-fx-background-color: lightcoral; -fx-font-weight: bold");
        btnCancel.setCancelButton(true);
        btnCancel.setOnAction(event -> cancelAction());

        lblError = new Label();
        pane.add(lblError, 0, 8);
        GridPane.setHalignment(lblError, HPos.CENTER);
        lblError.setStyle("-fx-text-fill: red");

        this.initControls();
    }

    private void initControls() {
        if (excursion != null && !isNewObject) {
            txfName.setText(excursion.getName());
            txfAddress.setText(excursion.getAddress());
            dateExcursion.setValue(excursion.getDate());
            txfPrice.setText(""+ excursion.getPrice());
        }
        else {
            txfName.clear();
            txfAddress.clear();
            txfPrice.clear();
        }
    }

    private void okAction() {
        String name = txfName.getText();
        String address = txfAddress.getText();
        LocalDate date = dateExcursion.getValue();
        int price;
        if (name.isEmpty()) {
            lblError.setText("Name is empty");
        } else if (address.isEmpty()) {
            lblError.setText("Address is empty");
        } else if (date == null) {
            lblError.setText("Pick a date");
        } else {
            price = -1;
            try {
                price = Integer.parseInt(txfPrice.getText());
            } catch (NumberFormatException e) {
                lblError.setText("Price error");
            }
            if (price < 0) {
                lblError.setText("Invalid price");
            } else {
                if (excursion != null && !isNewObject) {
                    Controller.updateExcursion(excursion, name, address, date, price);
                } else if (isNewObject) {
                    Controller.createExcursion(name, address, conference, date, price);
                }
                this.close();
            }
        }
    }

    private void cancelAction(){
        this.close();
    }
}

