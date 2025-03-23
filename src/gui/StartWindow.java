package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StartWindow extends Application {


    @Override
    public void start(Stage stage) {
        stage.setTitle("Conference demo");
        BorderPane pane = new BorderPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

//        -------------------------------------------------------------------------

    private void initContent(BorderPane pane) {
        TabPane tabPane = new TabPane();
        this.initTabPane(tabPane);
        pane.setCenter(tabPane);
    }

//        ------------------------------------------------------------------------

    private void initTabPane(TabPane tabPane) {
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        Tab tabConfs = new Tab("Conferences");
        tabPane.getTabs().add(tabConfs);

        ConferencePane conferencePane = new ConferencePane();
        tabConfs.setContent(conferencePane);
        tabConfs.setOnSelectionChanged(event -> conferencePane.updateControls());

//        ------------------------------------------------------------------------

        Tab tabExcursions = new Tab("Excursions");
        tabPane.getTabs().add(tabExcursions);

        ExcursionPane excursionPane = new ExcursionPane();
        tabExcursions.setContent(excursionPane);
        tabExcursions.setOnSelectionChanged(event -> excursionPane.updateControls());

//        ------------------------------------------------------------------------

        Tab tabHotels = new Tab("Hotels");
        tabPane.getTabs().add(tabHotels);

        HotelPane hotelPane = new HotelPane();
        tabHotels.setContent(hotelPane);
        tabHotels.setOnSelectionChanged(event -> hotelPane.updateControls());

//        ------------------------------------------------------------------------

        Tab tabAdmin = new Tab("Administration");
        tabPane.getTabs().add(tabAdmin);

        AdministrationPane administrationPane = new AdministrationPane();
        tabAdmin.setContent(administrationPane);
        tabAdmin.setOnSelectionChanged(event -> administrationPane.initControls());

//        ------------------------------------------------------------------------


    }
}
