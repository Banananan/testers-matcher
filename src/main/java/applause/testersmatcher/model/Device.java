package applause.testersmatcher.model;

import java.util.Objects;

public final class Device implements Identifiable<Long> {
    private final Long id;

    private final String description;

    public Device(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return id.equals(device.id) &&
                description.equals(device.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
