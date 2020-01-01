package applause.testersmatcher.model;

import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "bug_device",
            joinColumns = @JoinColumn(name = "bug_id"),
            inverseJoinColumns = @JoinColumn(name = "device_id")
    )
    private Set<Device> devices;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tester_id", referencedColumnName = "id", nullable = false)
    private Tester tester;

    public Bug(Set<Device> devices, Tester tester) {
        this.devices = devices;
        this.tester = tester;
    }

    public Long getId() {
        return id;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public Tester getTester() {
        return tester;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bug bug = (Bug) o;
        return Objects.equals(id, bug.id) &&
                devices.equals(bug.devices) &&
                tester.equals(bug.tester);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, devices, tester);
    }

    @Override
    public String toString() {
        return "Bug{" +
                "id=" + id +
                ", devices=" + devices +
                ", tester=" + tester +
                '}';
    }
}
