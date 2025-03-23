package gui;

import application.controller.Controller;
import application.model.Companion;
import application.model.Participant;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CompanionWindow extends Stage {
    private Participant participant;
    private Companion companion;
    private boolean isNewObject;

    public CompanionWindow(String title, Participant participant, boolean isNewObject, Companion companion) {

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.isNewObject = isNewObject;
        this.participant = participant;
        this.companion = companion;

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        pane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(pane);
        this.setScene(scene);
    }
    private TextField txfName, txfAddress, txfPhone;
    Label lblError;
    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblName = new Label("Name");
        pane.add(lblName, 0, 0);

        txfName = new TextField();
        pane.add(txfName, 0, 1);

        Label lblAddress = new Label("Address");
        pane.add(lblAddress, 0, 2);

        txfAddress = new TextField();
        pane.add(txfAddress, 0, 3);

        Label lblPhone = new Label("Phone");
        pane.add(lblPhone, 0, 4);

        txfPhone = new TextField();
        pane.add(txfPhone, 0, 5);

        lblError = new Label();
        pane.add(lblError, 0, 6);
        lblError.setStyle("-fx-text-fill: red");

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0,  7);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setStyle("-fx-background-color: lightcoral; -fx-font-weight: bold");
        btnCancel.setCancelButton(true);
        btnCancel.setOnAction(event -> this.cancelAction());

        Button btnOk = new Button("Create");
        pane.add(btnOk, 0, 7);
        btnOk.setStyle("-fx-background-color: lightgreen; -fx-font-weight: bold");
        GridPane.setHalignment(btnOk, HPos.RIGHT);
        btnOk.setOnAction(event -> this.okAction());
    }

    private void cancelAction() {
        this.close();
    }

    private void okAction() {
        String name = txfName.getText().trim();
        String address = txfAddress.getText().trim();
        String phone = txfPhone.getText().trim();



        if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            if (name.isEmpty()) {
                alert.setHeaderText("Please enter a name");
            } else if (address.isEmpty()) {
                alert.setHeaderText("Please enter an address");
            } else {
                alert.setHeaderText("Please enter a phone number");
            }
            alert.show();
            }
        else if (isNewObject) {
            companion = Controller.createCompanion(name, address, phone, participant);
        } else {
            Controller.updateCompanion(companion, name, address, phone);
        }
        this.close();
    }

    private void initControls() {
        if (!isNewObject) {
            txfName.setText(companion.getName());
            txfPhone.setText(companion.getPhoneNumber());
            txfAddress.setText(companion.getAddress());
        }
    }
}
