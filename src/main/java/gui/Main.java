package gui;

import gui.data.DataScene;
import gui.shape.ShapeScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import shape.MyShapeService;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import shape.Shape;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    public static MyShapeService myShapeService = new MyShapeService();

    private Stage window;
    private ListView<String> shapeList;

    ComboBox<String> shapeTypeComboBox;
    TextField volumeText;
    TextField totalVolumeText;



    ShapeScene shapeScene = new ShapeScene();
    DataScene dataScene = new DataScene();

    Button deleteButton;
    Shape previousSelectedShape = null;
    String previousSelectedItem = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("VAT Opdracht");

        BorderPane borderPane = new BorderPane();

        MenuBar menuBar = this.createMenu();
        GridPane windowGrid = this.createWindowGrid();

        borderPane.setTop(menuBar);
        borderPane.setCenter(windowGrid);

        Scene scene = new Scene(borderPane, 500, 250);
        scene.getStylesheets().add("style.css");

        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    private GridPane createWindowGrid(){
        GridPane windowGrid = new GridPane();
        ColumnConstraints leftColumn = new ColumnConstraints();
        ColumnConstraints rightColumn = new ColumnConstraints();
        leftColumn.setPercentWidth(50);
        rightColumn.setPercentWidth(50);
        windowGrid.getColumnConstraints().addAll(leftColumn,rightColumn);

        windowGrid.setHgap(20);
        windowGrid.setVgap(10);
        windowGrid.setPadding(new Insets(25));
        GridPane leftGridPane = new GridPane();
        GridPane rightGridPane = new GridPane();
        leftGridPane.setVgap(15);
        rightGridPane.setVgap(15);

        windowGrid.add(leftGridPane, 0, 0);
        windowGrid.add(rightGridPane, 1, 0);

        // Left side GridPane
        VBox shapeTypeBox = this.createShapeTypeBox();

        VBox volumeBox = new VBox();
        Label volumeLabel = new Label("Inhoud in m³");
        volumeText = new TextField();
        volumeText.setPrefWidth(150);
        volumeText.setText("0.0");
        volumeText.setDisable(true);
        volumeBox.getChildren().addAll(volumeLabel,volumeText);

        // Total volume
        VBox totalVolumeBox = new VBox();
        Label totalVolumeLabel = new Label("Totale inhoud in m³");
        totalVolumeText = new TextField();
        totalVolumeText.setPrefWidth(150);
        totalVolumeText.setText("0.0");
        totalVolumeText.setDisable(true);
        totalVolumeBox.getChildren().addAll(totalVolumeLabel,totalVolumeText);

        leftGridPane.add(shapeTypeBox, 0 ,0);
        leftGridPane.add(volumeBox, 0 ,1);
        leftGridPane.add(totalVolumeBox,0 ,2);

        // Shape list
        VBox shapeListBox = new VBox();
        Label shapeListTitle = new Label("Vormen");
        shapeList = new ListView<>();
        shapeList.setPrefWidth(250);
        shapeList.setPrefHeight(300);
        shapeList.setOnMouseClicked(click -> {
            String selectedItem = shapeList.getSelectionModel().getSelectedItem();
            int selectedIndex = shapeList.getSelectionModel().getSelectedIndex();

            if(selectedItem == null){
                return;
            }
            if(click.getClickCount() == 1){
                if (this.previousSelectedItem != null && this.previousSelectedItem.equals(selectedItem)) {
                    this.previousSelectedItem = null;
                    this.previousSelectedShape = null;
                    volumeText.setText("0.0");
                    shapeListTitle.setText("Vormen:");
                    deleteButton.setDisable(true);
                    Platform.runLater(() -> shapeList.getSelectionModel().select(null));
                } else {
                    Shape shape = myShapeService.getShapes().get(selectedIndex);
                    volumeText.setText(String.format("%1$,.2f", shape.calculateVolume()));
                    this.previousSelectedItem = selectedItem;
                    this.previousSelectedShape = shape;
                    deleteButton.setDisable(false);
                    shapeListTitle.setText("Hint: Dubbelklikken voor bewerken.");
                }
            } else if (click.getClickCount() == 2) {
                window.hide();

                HashMap<String,Double> data = shapeScene.editShape(myShapeService.getShapes().get(selectedIndex).getShapeFields(),myShapeService.getShapes().get(selectedIndex).getShapeData());
                this.previousSelectedItem = null;
                this.previousSelectedShape = null;
                if(data != null){
                    myShapeService.editShape(myShapeService.getShapes().get(selectedIndex),data);
                }
                deleteButton.setDisable(true);
                shapeListTitle.setText("Vormen:");
                window.show();
                updateView();
            }
        });

        shapeListBox.getChildren().addAll(shapeListTitle, shapeList);

        deleteButton = new Button("Verwijder de geselecteerde vorm");
        deleteButton.setDisable(true);
        deleteButton.setOnAction(e -> {
            if (previousSelectedItem != null) {
                myShapeService.getShapes().remove(previousSelectedShape);
                previousSelectedShape = null;
                previousSelectedItem = null;
                deleteButton.setDisable(false);
                shapeListTitle.setText("Vormen:");

                this.updateView();
            }
        });

        rightGridPane.add(shapeListBox, 0, 0);
        rightGridPane.add(deleteButton, 0, 1);

        this.updateView();
        return windowGrid;

    }

    private MenuBar createMenu(){
        Menu fileMenu = new Menu("");
        Label t = new Label("Bestand");
        t.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        fileMenu.setGraphic(t);
        MenuItem save = new MenuItem("Opslaan");
        save.setOnAction(e -> {
            dataScene.createDataWindow("Save");
            this.updateView();

        });
        MenuItem load = new MenuItem("Inladen");
        load.setOnAction(e -> {
            dataScene.createDataWindow("Load");
            this.updateView();

        });
        MenuItem close = new MenuItem("Afsluiten");
        close.setOnAction(e -> window.close());

        fileMenu.getItems().addAll(save,load,new SeparatorMenuItem(),close);

        MenuBar menuBar = new MenuBar();

        menuBar.setStyle("-fx-background-color: #07203c");
        menuBar.setPadding(new Insets(15, 15, 15,15));

        menuBar.getMenus().addAll(fileMenu);

        return menuBar;
    }

    private void updateView() {
        shapeList.getItems().clear();

        ArrayList<String> shapes = new ArrayList<>();

        for (Shape shape : myShapeService.getShapes()) {
            String values = "";
            for(HashMap.Entry<String, String> entry : shape.getShapeFields().entrySet()){
                values = values + " " + String.format("%.2f", shape.getShapeData().getOrDefault(entry.getKey(),0.0)).replaceAll(",",".");
            }
            shapes.add(shape.shapeType.toString() + " " + values);
        }

        volumeText.setText("0.0");
        totalVolumeText.setText(String.format("%1$,.2f", myShapeService.calculateTotalVolume()));

        shapeList.getItems().addAll(shapes);
    }

    private VBox createShapeTypeBox() {
        VBox shapeTypeBox = new VBox();
        Label shapeTypeLabel = new Label("Vorm aanmaken:");
        shapeTypeComboBox = new ComboBox<String>();
        shapeTypeComboBox.setPrefWidth(150.0);
        shapeTypeComboBox.setPromptText("Selecteer een vorm");
        shapeTypeComboBox.getItems().addAll("Kubus", "Kegel","Cilinder", "Piramide", "Bol");
        shapeTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        HashMap<String,Double> data = shapeScene.newShape(newValue, myShapeService.getFields(newValue));
                        if(data != null){
                            myShapeService.addShape(newValue, data);
                            this.updateView();
                        }
                        window.show();
                        Platform.runLater(() -> shapeTypeComboBox.setValue(null));
                    }
                });

        shapeTypeBox.getChildren().addAll(shapeTypeLabel, shapeTypeComboBox);
        return shapeTypeBox;
    }




    public static void main(String[] args) {
        launch(args);
    }
}
