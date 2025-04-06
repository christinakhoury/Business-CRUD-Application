package ap.mni.controllers;

import ap.mni.models.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ClientsController {

    @FXML
    private TableView<Client> clientsTable;
    @FXML
    private TableColumn<Client, String> idColumn;
    @FXML
    private TableColumn<Client, String> nameColumn;
    @FXML
    private TableColumn<Client, Integer> ageColumn;
    @FXML
    private TableColumn<Client, String> genderColumn;

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private RadioButton maleRadio;
    @FXML
    private RadioButton femaleRadio;
    @FXML
    private ToggleGroup genderGroup;

    @FXML
    private Button addClientBtn;
    @FXML
    private Button removeClientBtn;
    @FXML
    private Button clearTableBtn;

    private ObservableList<Client> clientsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        genderGroup = new ToggleGroup();
        maleRadio.setToggleGroup(genderGroup);
        femaleRadio.setToggleGroup(genderGroup);


        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
        genderColumn.setCellValueFactory(cellData -> cellData.getValue().genderProperty());

        clientsTable.setItems(clientsList);

        addClientBtn.setOnAction(event -> addClient());
        removeClientBtn.setOnAction(event -> removeSelectedClient());
        clearTableBtn.setOnAction(event -> clearTable());
    }

    private void addClient() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        RadioButton selectedGender = (RadioButton) genderGroup.getSelectedToggle();

        if (id.isEmpty() || name.isEmpty() || ageText.isEmpty() || selectedGender == null) {
            showAlert("Error", "All fields must be filled!", Alert.AlertType.ERROR);
            return;
        }

        if (!id.matches("\\d+") || !ageText.matches("\\d+")) {
            showAlert("Error", "ID and Age must be numeric!", Alert.AlertType.ERROR);
            return;
        }

        // Check for duplicate ID (new validation)
        for (Client client : clientsList) {
            if (client.getId().equals(id)) {
                showAlert("Error", "ID must be unique! This ID already exists.", Alert.AlertType.ERROR);
                return;
            }
        }

        int age = Integer.parseInt(ageText);
        String gender = selectedGender.getText();

        Client newClient = new Client(id, name, age, gender);
        clientsList.add(newClient);

        idField.clear();
        nameField.clear();
        ageField.clear();
        genderGroup.selectToggle(null);
    }
    private void removeSelectedClient() {
        Client selectedClient = clientsTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            clientsList.remove(selectedClient);
        } else {
            showAlert("Warning", "No client was selected!", Alert.AlertType.WARNING);
        }
    }

    private void clearTable() {
        clientsList.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
