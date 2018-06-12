package catalog.users;

import java.util.Objects;

public class User {

    private int id;
    private String name;
    private int password;

    public User() {
    }

    public User(String name, int password) {
        this.name = name;
        this.password = password;
    }

    public User(int id, String name, int password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "catalog.users.User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password=" + password +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name)+Objects.hashCode(password);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return user.getName().equals(this.getName())
                && this.getPassword() == user.getPassword();
    }


}
