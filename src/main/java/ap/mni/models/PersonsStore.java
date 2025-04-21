package ap.mni.models;

import ap.mni.controllers.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class PersonsStore {
    private final ObservableList<Person> persons = FXCollections.observableArrayList();

    public PersonsStore() {
        loadFromDatabase(); // 🔁 Pull from DB at construction
    }

    public ObservableList<Person> getPersonsList() {
        return persons;
    }

    public void addPerson(Person person) {
        if (person != null) {
            String sql = "INSERT INTO persons (name, age, address) VALUES (?, ?, ?)";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, person.getName());
                stmt.setInt(2, person.getAge());
                stmt.setString(3, person.getAddress());
                stmt.executeUpdate();

                this.persons.add(person); // ✅ Add to UI list after DB success

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deletePerson(Person person) {
        if (person != null) {
            String sql = "DELETE FROM persons WHERE name = ? AND age = ? AND address = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, person.getName());
                stmt.setInt(2, person.getAge());
                stmt.setString(3, person.getAddress());
                stmt.executeUpdate();

                this.persons.remove(person);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updatePerson(Person person, String name, int age, String address) {
        if (person != null) {
            String sql = "UPDATE persons SET name = ?, age = ?, address = ? WHERE name = ? AND age = ? AND address = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, name);
                stmt.setInt(2, age);
                stmt.setString(3, address);
                stmt.setString(4, person.getName());
                stmt.setInt(5, person.getAge());
                stmt.setString(6, person.getAddress());

                stmt.executeUpdate();

                // Update local copy (JavaFX observable list)
                person.setName(name);
                person.setAge(age);
                person.setAddress(address);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadFromDatabase() {
        persons.clear();
        String sql = "SELECT * FROM persons";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String address = rs.getString("address");

                persons.add(new Person(name, age, address));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Added method to fix getAll() error
    public ObservableList<Person> getAll() {
        loadFromDatabase(); // Always get fresh data
        return FXCollections.observableArrayList(persons);
    }
}
