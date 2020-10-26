package gui.shape;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;

public class ShapeScene {

    private int numberOfFields;
    private HashMap<String, TextField> fields;
    private boolean actionButton;


    public HashMap<String, Double> newShape(String shapeName,  HashMap<String, String> fields){
        HashMap data = new HashMap();
        HashMap<String, TextField> textFields = new HashMap<>();
        Stage window = new Stage();
        GridPane gridpane = new GridPane();
        gridpane.setVgap(10);

        VBox layout = new VBox(10);

        // Reset number of fields and make the grid empty
        numberOfFields = 0;
        layout.getChildren().removeAll(gridpane);

        for(HashMap.Entry<String, String> entry : fields.entrySet()){
            Label fieldLabel = new Label(entry.getValue());
            gridpane.add(fieldLabel, 0, numberOfFields); // column= 0 row= numberOfFields
            numberOfFields++;

            TextField inputField = new TextField();
            inputField.setPrefWidth(180);
            inputField.setText(String.format("%.2f", data.getOrDefault(entry.getKey(),0.0)).replaceAll(",","."));
            gridpane.add(inputField,0 ,numberOfFields);
            numberOfFields++;

            inputField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
                    inputField.setText(newValue.substring(0,newValue.length()-1));
                }
            });

            textFields.put(entry.getKey(), inputField);
        }

        window.setMinWidth(350);
        window.setMinHeight(250);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("VAT - Vorm aanmaken " + shapeName);
        Button closeButton = new Button("Annuleren");
        closeButton.setOnAction((event) ->{
            this.actionButton = false;
            window.close();
        });


        Button createButton = new Button("Aanmaken");

        createButton.setOnAction(e -> {
            HashMap<String, Double> returnData = this.getData(textFields);
            if (returnData.containsValue(0.0) || returnData.containsValue("0.0")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "De waarde van de velden kan geen 0 zijn.", ButtonType.OK);
                alert.showAndWait();
            } else {
                this.actionButton = true;
                window.close();
            }
        });

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.getChildren().addAll(closeButton, createButton);
        layout.setPadding(new Insets(25));
        layout.getChildren().addAll(gridpane, buttons);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        if (this.actionButton) {
            return this.getData(textFields);
        }

        return null;

    }

    public HashMap<String, Double> editShape(HashMap<String, String> fields,HashMap<String, Double> data){
        HashMap<String, TextField> textFields = new HashMap<>();
        Stage window = new Stage();
        GridPane gridpane = new GridPane();
        gridpane.setVgap(10);

        VBox layout = new VBox(10);

        // Reset number of fields and make the grid empty
        numberOfFields = 0;
        layout.getChildren().removeAll(gridpane);

        for(HashMap.Entry<String, String> entry : fields.entrySet()){
            Label fieldLabel = new Label(entry.getValue());

            gridpane.add(fieldLabel, 0, numberOfFields); // column= 0 row= numberOfFields
            numberOfFields++;

            TextField inputField = new TextField();
            inputField.setPrefWidth(180);
            inputField.setText(String.format("%.2f", data.getOrDefault(entry.getKey(),0.0)).replaceAll(",","."));
            gridpane.add(inputField,0 ,numberOfFields);
            numberOfFields++;

            inputField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
                    inputField.setText(newValue.substring(0,newValue.length()-1));
                }
            });

            textFields.put(entry.getKey(), inputField);
        }

        window.setTitle("VAT - Vorm aanpassen");
        Button closeButton = new Button("Annuleren");
        closeButton.setOnAction((event)->{
            this.actionButton = false;
            window.close();
        });


        window.setMinWidth(350);
        window.setMinHeight(250);

        Button  editButton = new Button("Aanpassen");;

        editButton.setOnAction(e -> {
            HashMap<String, Double> returnData = this.getData(textFields);
            if (returnData.containsValue(0.0) || returnData.containsValue("0.0")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "De waarde van de velden kan geen 0 zijn.", ButtonType.OK);
                alert.showAndWait();
            } else {
                this.actionButton = true;
                window.close();
            }
        });

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.getChildren().addAll(closeButton, editButton);

        layout.getChildren().addAll(gridpane, buttons);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();


        if (this.actionButton) {
            return this.getData(textFields);
        }
        return null;
    }

    private HashMap<String, Double> getData(HashMap<String, TextField> textFields) {
        HashMap<String, Double> returnData = new HashMap<>();

        for (HashMap.Entry<String, TextField> entry : textFields.entrySet()) {
            TextField textField = entry.getValue();
            returnData.put(entry.getKey(), Double.parseDouble(textField.getText()));
        }

        return returnData;
    }
}
