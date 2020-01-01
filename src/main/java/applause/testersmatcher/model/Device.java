package applause.testersmatcher.model;

import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    @ManyToMany(mappedBy = "devices")
    private Set<Bug> bugs;

    @ManyToMany(mappedBy = "devices")
    private Set<Tester> testers;

    public Device(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Set<Bug> getBugs() {
        return bugs;
    }

    public Set<Tester> getTesters() {
        return testers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(id, device.id) &&
                description.equals(device.description) &&
                Objects.equals(bugs, device.bugs) &&
                Objects.equals(testers, device.testers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, bugs, testers);
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", bugs=" + bugs +
                ", testers=" + testers +
                '}';
    }
}
