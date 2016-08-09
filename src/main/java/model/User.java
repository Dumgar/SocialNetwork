package model;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by romandmitriev on 05.08.16.
 */
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String country;
    private String city;
    private LocalDate birthDate;
    private int id;
    private int idPhoto;

    public User(int id, String email, String firstName, String lastName, LocalDate birthDate, String country, String city, int idPhoto) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.country = country;
        this.city = city;
        this.birthDate = birthDate;
        this.id = id;
        this.idPhoto = idPhoto;
    }

    public int getId() {
        return id;
    }

    public int getIdPhoto() {
        return idPhoto;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", idPhoto=" + idPhoto +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", birthDate='" + birthDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        return email.equals(user.email);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + email.hashCode();
        return result;
    }

}
