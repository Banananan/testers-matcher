package applause.testersmatcher.model;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Tester implements Identifiable<Long> {
    private final Long id;

    private final String firstName;

    private final String lastName;

    private final String country;

    private final LocalDateTime lastLogin;

    public Tester(Long id, String firstName, String lastName, String country, LocalDateTime lastLogin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.lastLogin = lastLogin;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountry() {
        return country;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tester tester = (Tester) o;
        return id.equals(tester.id) &&
                firstName.equals(tester.firstName) &&
                lastName.equals(tester.lastName) &&
                country.equals(tester.country) &&
                Objects.equals(lastLogin, tester.lastLogin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, country, lastLogin);
    }

    @Override
    public String toString() {
        return "Tester{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", country='" + country + '\'' +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
