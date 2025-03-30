package ap.mni.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClientsStore {
    private static final ObservableList<Client> clientsList = FXCollections.observableArrayList();

    public static void addClient(Client client) {
        clientsList.add(client);
    }

    public static void removeClient(String id) {
        clientsList.removeIf(client -> client.getId().equals(id));
    }

    public static ObservableList<Client> getClients() {
        return clientsList;
    }
}
