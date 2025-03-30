package ap.mni.models;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class PersonsStore {
    private final ObservableList<Person> persons = FXCollections.observableArrayList();
    public PersonsStore() {
        this.persons.addAll(
                new Person("John", 23, "NY"),
                new Person("Sara", 32, "LA"),
                new Person("Tom", 22, "CA")
        );
    }

    public ObservableList<Person> getPersonsList() {
        return persons;
    }

    public void addPerson(Person person) {
        if (person != null) {
            this.persons.add(person);
        }
    }

    public void deletePerson(Person person) {
        if (person != null) {
            this.persons.remove(person);
        }
    }

    public void updatePerson(Person person, String name, int age, String address) {
        if (person != null) {
            person.setName(name);
            person.setAge(age);
            person.setAddress(address);
        }
    }
}
