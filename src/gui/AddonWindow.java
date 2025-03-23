package gui;

import application.controller.Controller;
import application.model.AddonPurchase;
import application.model.Hotel;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddonWindow extends Stage {
    private Hotel hotel;

    public AddonWindow(String title, Hotel hotel) {

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.hotel = hotel;

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private TextField txfName, txfPrice;
    Label lblError;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblName = new Label("Name");
        pane.add(lblName, 0, 0);

        txfName = new TextField();
        pane.add(txfName, 0, 1);

        Label lblPrice = new Label("Price");
        pane.add(lblPrice, 0, 2);

        txfPrice = new TextField();
        pane.add(txfPrice, 0, 3);

        lblError = new Label();
        pane.add(lblError, 0, 4);
        lblError.setStyle("-fx-text-fill: red");

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 5);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setStyle("-fx-background-color: lightcoral; -fx-font-weight: bold");
        btnCancel.setCancelButton(true);
        btnCancel.setOnAction(event -> this.cancelAction());

        Button btnOk = new Button("Create");
        pane.add(btnOk, 0, 5);
        GridPane.setHalignment(btnOk, HPos.RIGHT);
        btnOk.setStyle("-fx-background-color: lightgreen; -fx-font-weight: bold");
        btnOk.setOnAction(event -> this.okAction());
    }

    private void cancelAction() {
        this.close();
    }

    private void okAction() {
        String name = txfName.getText().trim();
        int price;
        if (name.isEmpty()) {
            lblError.setText("Name is empty");
        } else {
            price = -1;
            try {
                price = Integer.parseInt(txfPrice.getText().trim());
            } catch (NumberFormatException ex) {
                lblError.setText("Price error");
            }
            if (price < 0) {
                lblError.setText("Invalid price");
            } else {
                AddonPurchase addon = Controller.createHotelAddon(hotel, name, price);
                this.close();
            }
        }
    }


}
