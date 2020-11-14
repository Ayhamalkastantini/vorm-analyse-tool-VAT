package gui.data;

import data.SQLStorage;
import data.ShapeList;
import data.StorageService;
import gui.Main;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class DataScene {
    private static StorageService storageService = new StorageService();

    private static Stage window;
    private static FileChooser fileChooser;
    private static ToggleGroup toggleGroup;

    /**
     *
     * @param type
     */
    public void createDataWindow(String type) {
        window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(350);
        window.setMinHeight(250);

        Label label = new Label();

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(15);
        layout.setPadding(new Insets(25));
        layout.getChildren().add(label);

        HBox toggle = new HBox();
        toggle.setSpacing(15);
        toggle.setAlignment(Pos.CENTER);

        toggleGroup = createToggleGroup(toggle);

        Button closeButton = new Button("Annuleren");
        closeButton.setOnAction(e -> window.close());

        Button rightButton = new Button();

        switch (type) {
            case "Load":
                window.setTitle("VAT - Inladen");
                label.setText("Selecteer de inladen wijze:");
                rightButton.setText("Inladen");
                rightButton.setOnAction(e -> load());
                break;
            case "Save":
                window.setTitle("VAT - Opslaan");
                label.setText("Selecteer de opslaan wijze:");
                rightButton.setText("Opslaan");
                rightButton.setOnAction(e -> save());
                break;
        }

        HBox buttons = new HBox(15);
        buttons.getChildren().addAll(closeButton, rightButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(25,0,0,0));

        layout.getChildren().addAll(toggle, buttons);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }


    private static void save() {
        fileChooser = new FileChooser();

        switch ((String) toggleGroup.getSelectedToggle().getUserData()) {
            case StorageService.STORAGE_TYPE_TEXT:
                fileChooser.setTitle("Open Text File");
                fileChooser.getExtensionFilters().clear();
                fileChooser.setInitialFileName(String.format("VAT-Text-%d.json", new Date().getTime()));
                File textFile = fileChooser.showSaveDialog(window);
                if (textFile != null) {
                    try {
                        storageService.getStorage(StorageService.STORAGE_TYPE_TEXT).saveData(textFile.toString(), Main.myShapeService.getShapes());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bestand is opgeslagen", ButtonType.OK);
                        alert.showAndWait();
                        window.close();
                    } catch (Exception err) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, err.getLocalizedMessage(), ButtonType.OK);
                        alert.showAndWait();
                        err.printStackTrace();
                    }
                }

                break;
            case StorageService.STORAGE_TYPE_OBJECT:
                fileChooser.setTitle("Open Object File");
                fileChooser.getExtensionFilters().clear();
                fileChooser.setInitialFileName(String.format("VAT-Object-%d.vat", new Date().getTime()));
                File objectFile = fileChooser.showSaveDialog(window);

                if (objectFile != null) {
                    try {
                        storageService.getStorage(StorageService.STORAGE_TYPE_OBJECT).saveData(objectFile.toString(), Main.myShapeService.getShapes());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Object is opgeslagen", ButtonType.OK);
                        alert.showAndWait();
                        window.close();
                    } catch (Exception err) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, err.getLocalizedMessage(), ButtonType.OK);
                        alert.showAndWait();
                        err.printStackTrace();
                    }
                }
                break;
            case StorageService.STORAGE_TYPE_SQL:
                SQLStorage sqlStorage = new SQLStorage();
                try {
                    Stage windowSQL = new Stage();
                    windowSQL.setTitle("VAT - Lijstnaam");
                    windowSQL.initModality(Modality.APPLICATION_MODAL);
                    windowSQL.setMinWidth(200);
                    windowSQL.setMinHeight(150);
                    TextField listNameField = new TextField();
                    Label label = new Label("Voer de lijstnaam in:");
                    Button saveButton = new Button("Opslaan");
                    Button cancelButton = new Button("Annuleren");
                    GridPane gridPane = new GridPane();
                    gridPane.setHgap(20);
                    gridPane.setVgap(10);
                    gridPane.setPadding(new Insets(25));
                    gridPane.add(label, 0, 0);
                    gridPane.add(listNameField, 0, 1);
                    gridPane.add(saveButton, 2, 2);
                    gridPane.add(cancelButton, 1, 2);

                    Scene scene = new Scene(gridPane);
                    windowSQL.setScene(scene);
                    windowSQL.show();
                    saveButton.setOnAction((event -> {
                        if(!listNameField.getText().isEmpty()){
                            try {
                                if(sqlStorage.saveList(Main.myShapeService.getShapes(),listNameField.getText())){
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Lijst is opgeslagen in de database", ButtonType.OK);
                                    alert.showAndWait();
                                    windowSQL.hide();
                                    window.hide();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                            Alert alert = new Alert(Alert.AlertType.WARNING,"Lijstnaam moet ingevuld worden!" , ButtonType.OK);
                            alert.showAndWait();
                        }
                    }));
                    cancelButton.setOnAction((event -> {
                        windowSQL.hide();
                    }));

                } catch (Exception err) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, err.getLocalizedMessage(), ButtonType.OK);
                    alert.showAndWait();
                    err.printStackTrace();
                }
                break;
        }
    }

    private static void load() {
        fileChooser = new FileChooser();
        switch ((String) toggleGroup.getSelectedToggle().getUserData()) {
            case StorageService.STORAGE_TYPE_TEXT:
                fileChooser.setTitle("Open Text File");
                fileChooser.getExtensionFilters().clear();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.json bestand", "*.json"));
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")));
                File textFile = fileChooser.showOpenDialog(window);

                if (textFile != null) {
                    try {
                        Main.myShapeService.setShapes(storageService.getStorage(StorageService.STORAGE_TYPE_TEXT).loadData(textFile.toString()));
                        window.close();
                    } catch (Exception err) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, err.getLocalizedMessage(), ButtonType.OK);
                        alert.showAndWait();
                        err.printStackTrace();
                    }

                }

                break;
            case StorageService.STORAGE_TYPE_OBJECT:
                fileChooser.setTitle("Open Object File");
                fileChooser.getExtensionFilters().clear();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.vat bestand", "*.vat"));
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")));
                File objectFile = fileChooser.showOpenDialog(window);

                if (objectFile != null) {
                    try {
                        Main.myShapeService.setShapes(storageService.getStorage(StorageService.STORAGE_TYPE_OBJECT).loadData(objectFile.toString()));
                        window.close();
                    } catch (Exception err) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, err.getLocalizedMessage(), ButtonType.OK);
                        alert.showAndWait();
                        err.printStackTrace();
                    }
                }

                break;
            case StorageService.STORAGE_TYPE_SQL:
                try {
                    Stage windowSQL = new Stage();
                    windowSQL.setTitle("VAT - Selecteer een lijstnaam");
                    windowSQL.initModality(Modality.APPLICATION_MODAL);
                    windowSQL.setMinWidth(200);
                    windowSQL.setMinHeight(150);
                    Label label = new Label("Selecteer een lijst");
                    Button loadButton = new Button("Inladen");
                    Button cancelButton = new Button("Annuleren");
                    GridPane gridPane = new GridPane();
                    gridPane.setHgap(20);
                    gridPane.setVgap(10);
                    gridPane.setPadding(new Insets(25));

                    // Fill the Tableview
                    SQLStorage sqlStorage = new SQLStorage();
                    ObservableList<ShapeList> names = sqlStorage.loadLists();
                    TableView tableView = new TableView();
                    TableColumn listId = new TableColumn("ID");
                    listId.setCellValueFactory(new PropertyValueFactory<>("id"));
                    TableColumn listName = new TableColumn("Naam");
                    listName.setCellValueFactory(new PropertyValueFactory<>("name"));
                    tableView.setItems(names);
                    tableView.getColumns().addAll(listId,listName);

                    Label selectedListLabel = new Label("Geselecteerde lijst:");
                    Label selectedLabel = new Label();
                    selectedLabel.setStyle("-fx-font-weight: bold");

                    gridPane.add(label, 0, 0);
                    gridPane.add(tableView, 0, 1);
                    gridPane.add(selectedListLabel, 0, 2);
                    gridPane.add(selectedLabel, 0, 3);
                    gridPane.add(cancelButton, 1, 3);
                    gridPane.add(loadButton, 2, 3);


                    Scene scene = new Scene(gridPane);
                    windowSQL.setScene(scene);
                    windowSQL.show();

                    AtomicInteger selectedRowId = new AtomicInteger();
                    // Getting Shapelist id
                    tableView.getSelectionModel().selectedIndexProperty().addListener(e->{
                        ShapeList selectedRow = (ShapeList) tableView.getSelectionModel().getSelectedItem();
                        selectedRowId.set(selectedRow.getId());
                        selectedLabel.setText(selectedRow.getName());
                    });

                    loadButton.setOnAction((event -> {
                        if(selectedRowId.get() != 0){
                            try {
                                Main.myShapeService.setShapes(sqlStorage.loadShapesList(selectedRowId.get()));
                                windowSQL.close();
                                window.close();
                            } catch (Exception e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                                alert.showAndWait();
                                e.printStackTrace();
                            }
                        }else{
                            Alert alert = new Alert(Alert.AlertType.WARNING, "U hebt nog geen lijst selecteren!", ButtonType.OK);
                            alert.showAndWait();
                        }
                    }));
                } catch (Exception err) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, err.getLocalizedMessage(), ButtonType.OK);
                    alert.showAndWait();
                    err.printStackTrace();
                }
                break;
        }
    }

    /**
     *
     * @param toggle
     * @return
     */
    private static ToggleGroup createToggleGroup(HBox toggle) {
        ToggleGroup toggleGroup = new ToggleGroup();

        toggleGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (toggleGroup.getSelectedToggle() == null) {
                toggleGroup.getToggles().get(0).setSelected(true);
            }
        });

        ToggleButton tb1 = new ToggleButton("Tekst");
        tb1.setUserData(StorageService.STORAGE_TYPE_TEXT);
        tb1.setToggleGroup(toggleGroup);
        tb1.setSelected(true);

        ToggleButton tb2 = new ToggleButton("Objecten");
        tb2.setUserData(StorageService.STORAGE_TYPE_OBJECT);
        tb2.setToggleGroup(toggleGroup);

        ToggleButton tb3 = new ToggleButton("SQL");
        tb3.setUserData(StorageService.STORAGE_TYPE_SQL);
        tb3.setToggleGroup(toggleGroup);
        toggle.getChildren().addAll(tb1, tb2, tb3);

        return toggleGroup;
    }

}
