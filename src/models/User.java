package models;

import java.io.Serializable;
import java.util.Objects;

/**
 * Since Message class implements Serializable, User class also have to, because User is parameter in Message constructor
 */
public class User implements Serializable {
    private String username;
    private String password;
    private Status status;
    private String firstName;
    private String lastName;
    // TODO date of birth
    private String photo;


    public User(String username, String password, Status status) {
        this.username = username;
        this.password = password;
        this.status=status;
        this.photo="images/avatar.jpg";  //default photo assigned during registration
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
