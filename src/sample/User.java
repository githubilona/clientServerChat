package sample;

import java.io.Serializable;
import java.util.Objects;

/**
 * Since Message class implements Serializable, User class also have to, because User is parameter in Message constructor
 */
public class User implements Serializable {
    private String username;
    private String password;
    private Status status;

    public User(String username, String password, Status status) {
        this.username = username;
        this.password = password;
        this.status=status;
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
