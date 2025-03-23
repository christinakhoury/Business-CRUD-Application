package ap.mni.models;

import javafx.beans.property.*;

public class Client {
    private final StringProperty id;
    private final StringProperty name;
    private final IntegerProperty age;
    private final StringProperty gender;

    public Client(String id, String name, int age, String gender) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
        this.gender = new SimpleStringProperty(gender);
    }


    public StringProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public String getId() {
        return id.get();
    }
    public String getName() {
        return name.get();
    }
    public int getAge() {
        return age.get();

    }
    public String getGender() {
        return gender.get();
    }


    public void setId(String id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }
}
