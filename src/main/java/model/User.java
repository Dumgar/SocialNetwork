package model;

import java.time.LocalDate;
import java.util.Date;

/**
 * User entity
 *
 * @author Roman Dmitriev
 */
public class User {
    /**
     * User's first name
     */
    private String firstName;
    /**
     * User's last name
     */
    private String lastName;
    /**
     * User's email, it must be unique
     */
    private String email;
    /**
     * User's password
     */
    private String password;
    /**
     * User's country
     */
    private String country;
    /**
     * User's city
     */
    private String city;
    /**
     * User's date of birth
     */
    private LocalDate birthDate;
    /**
     * user's unique id
     */
    private int id;
    /**
     * User's profile photo id
     */
    private int idPhoto;

    /**
     * Creates new User object
     * @param id
     * @param email
     * @param firstName
     * @param lastName
     * @param birthDate
     * @param country
     * @param city
     * @param idPhoto
     */
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

    /**
     * @return user's id
     */
    public int getId() {
        return id;
    }

    /**
     * @return profile photo's id
     */
    public int getIdPhoto() {
        return idPhoto;
    }

    /**
     * @return User's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return User's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return User's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return User's country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @return User's city
     */
    public String getCity() {
        return city;
    }

    /**
     * @return User's date of birth in LocalDate format
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        return email.equals(user.email);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + email.hashCode();
        return result;
    }

}
