package applause.testersmatcher.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Tester {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String country;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tester_device",
            joinColumns = @JoinColumn(name = "tester_id"),
            inverseJoinColumns = @JoinColumn(name = "device_id")
    )
    private Set<Device> devices;

    @OneToMany(mappedBy = "tester")
    private Set<Bug> bugs;

    public Tester() {
    }

    public Tester(
            String firstName,
            String lastName,
            String country,
            LocalDateTime lastLogin,
            Set<Device> devices
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.lastLogin = lastLogin;
        this.devices = devices;
    }

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

    public Set<Device> getDevices() {
        return devices;
    }

    public Set<Bug> getBugs() {
        return bugs;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tester tester = (Tester) o;
        return Objects.equals(id, tester.id) &&
                firstName.equals(tester.firstName) &&
                lastName.equals(tester.lastName) &&
                country.equals(tester.country) &&
                lastLogin.equals(tester.lastLogin) &&
                Objects.equals(devices, tester.devices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, country, lastLogin, devices);
    }

    @Override
    public String toString() {
        return "Tester{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", country='" + country + '\'' +
                ", lastLogin=" + lastLogin +
                ", devices=" + devices +
                ", bugs=" + bugs +
                '}';
    }
}
