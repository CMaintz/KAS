package gui;

import application.controller.Controller;
import application.model.Participant;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ParticipantWindow extends Stage {
    private Participant participant;

    private boolean isNewObject;

    public ParticipantWindow(String title, Participant participant, boolean isNewObject) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);

        this.participant = participant;
        this.isNewObject = isNewObject;

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane);
        setScene(scene);
    }
    // -------------------------------------------------------------------------

    private TextField txfName, txfAddress, txfCompany, txfPhone;
    private CheckBox chbCompany;
    private Label lblError;
    private Button btnOk;


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

        chbCompany = new CheckBox("Company");
        pane.add(chbCompany, 0, 6);
        ChangeListener<Boolean> listener = (ov, oldValue, newValue) -> selectedCompanyChanged(newValue);
        chbCompany.selectedProperty().addListener(listener);

        txfCompany = new TextField();
        pane.add(txfCompany, 0, 7);
        txfCompany.setDisable(true);

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 9);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setStyle("-fx-background-color: lightcoral; -fx-font-weight: bold");
        btnCancel.setCancelButton(true);
        btnCancel.setOnAction(event -> cancelAction());

        btnOk = new Button("Create");
        pane.add(btnOk, 0, 9);
        GridPane.setHalignment(btnOk, HPos.RIGHT);
        btnOk.setStyle("-fx-background-color: lightgreen; -fx-font-weight: bold");
        btnOk.setOnAction(event -> okAction());

        if (!isNewObject) {
            btnOk.setText("Update");
        }

        lblError = new Label();
        pane.add(lblError, 0, 8);
        lblError.setStyle("-fx-text-fill: red");

        initControls();
    }

    private void initControls() {
        if (participant != null) {
            txfName.setText(participant.getName());
            txfPhone.setText(participant.getPhoneNumber());
            txfAddress.setText(participant.getAddress());
            if (participant.getCompany().length() > 0) {
                txfCompany.setDisable(false);
                chbCompany.setSelected(true);
                txfCompany.setText(participant.getCompany());
            }
        } else {
            txfName.clear();
            txfAddress.clear();
            txfCompany.clear();
            txfPhone.clear();
        }
    }

    // -------------------------------------------------------------------------

    private void cancelAction() {
        hide();
    }

    private void okAction() {
        String name = txfName.getText().trim();
        String address = txfAddress.getText().trim();
        String phone = txfPhone.getText().trim();
        String company = "";
        if (name.isEmpty()) {
            lblError.setText("Name is empty");
        } else if (address.isEmpty()) {
            lblError.setText("Address is empty");
        } else if (phone.isEmpty()) {
            lblError.setText("Phone is empty");
        } else {
            boolean companyIsSelected = chbCompany.isSelected();
            if (companyIsSelected && !txfCompany.getText().trim().isEmpty()) {
                company = txfCompany.getText().trim();
            }
            if (isNewObject) {
                Participant part = Controller.createParticipant(name, address, phone);
                if (companyIsSelected) {
                    part.setCompany(company);
                }
            } else if (participant != null && !isNewObject) {
               Controller.updateParticipant(participant, name, address, phone);
            }
            this.close();
        }
    }

    // -------------------------------------------------------------------------

    private void selectedCompanyChanged(boolean checked) {
        txfCompany.setDisable(!checked);
    }

}
