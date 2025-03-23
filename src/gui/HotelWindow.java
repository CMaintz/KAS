package gui;

import application.controller.Controller;
import application.model.Conference;
import application.model.Hotel;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HotelWindow extends Stage {
    private Hotel hotel;
    private Conference conference;
    private boolean isNewObject;

    public HotelWindow(String title, Hotel hotel, Conference conference, boolean isNewObject) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);

        this.conference = conference;
        this.hotel = hotel;
        this.isNewObject = isNewObject;

        this.setTitle(title);

        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private TextField txfName, txfAddress, txfPhone, txfSingleRoomPrice, txfDoubleRoomPrice;
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

        Label lblPhone = new Label("Phone");
        pane.add(lblPhone, 0, 4);

        txfPhone = new TextField();
        pane.add(txfPhone, 0, 5);

        Label lblSingleRoomPrice = new Label("Single room price");
        pane.add(lblSingleRoomPrice, 0, 6);

        txfSingleRoomPrice = new TextField();
        pane.add(txfSingleRoomPrice, 0, 7);

        Label lblDoubleRoomPrice = new Label("Double room price");
        pane.add(lblDoubleRoomPrice, 0, 8);

        txfDoubleRoomPrice = new TextField();
        pane.add(txfDoubleRoomPrice, 0, 9);

        Button btnOk = new Button("Create");
        pane.add(btnOk, 0, 11);
        btnOk.setStyle("-fx-background-color: lightgreen; -fx-font-weight: bold");
        GridPane.setHalignment(btnOk, HPos.RIGHT);
        btnOk.setOnAction(event -> okAction());

        if (!isNewObject) {
            btnOk.setText("Update");
        }

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 11);
        btnCancel.setStyle("-fx-background-color: lightcoral; -fx-font-weight: bold");
        btnCancel.setCancelButton(true);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> cancelAction());

        lblError = new Label();
        pane.add(lblError, 0, 10);
        lblError.setStyle("-fx-text-fill: red");

        this.initControls();
    }

    private void okAction() {
        String name = txfName.getText();
        String address = txfAddress.getText();
        String phone = txfPhone.getText();
        int singleRoomPrice;
        int doubleRoomPrice;
        if (name.isEmpty()) {
            lblError.setText("Name is empty");
        } else if (address.isEmpty()) {
            lblError.setText("Address is empty");
        } else if (phone.isEmpty()) {
            lblError.setText("Phone is empty");
        } else {
            singleRoomPrice = -1;
            doubleRoomPrice = -1;
            try {
                singleRoomPrice = Integer.parseInt(txfSingleRoomPrice.getText());
                doubleRoomPrice = Integer.parseInt(txfDoubleRoomPrice.getText());
            } catch (NumberFormatException e) {
                lblError.setText("Invalid price(s)");
            }
            if (singleRoomPrice < 0 || doubleRoomPrice < 0) {
                lblError.setText("Price(s) is not a positive number");
            } else {
                if (hotel != null && !isNewObject) {
                    Controller.updateHotel(conference, hotel, name, address, phone, singleRoomPrice, doubleRoomPrice);
                } else if (isNewObject)
                    Controller.createHotel(conference, name, address, phone, singleRoomPrice, doubleRoomPrice);
            }
            this.hide();
        }
    }

    private void initControls() {
        if (!isNewObject && hotel != null) {
            txfName.setText(hotel.getName());
            txfAddress.setText(hotel.getAddress());
            txfPhone.setText(hotel.getPhoneNumber());
            txfSingleRoomPrice.setText("" + hotel.getSingleRoomPrice());
            txfDoubleRoomPrice.setText("" + hotel.getDoubleRoomPrice());
        }
    }

    private void cancelAction() {
        this.hide();
    }


}
