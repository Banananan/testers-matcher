package applause.testersmatcher.model;

import java.util.Objects;

public final class Bug implements Identifiable<Long> {
    private final Long id;

    private final Long deviceId;

    private final Long testerId;

    public Bug(Long id, Long deviceId, Long testerId) {
        this.id = id;
        this.deviceId = deviceId;
        this.testerId = testerId;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public Long getTesterId() {
        return testerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bug bug = (Bug) o;
        return id.equals(bug.id) &&
                deviceId.equals(bug.deviceId) &&
                testerId.equals(bug.testerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deviceId, testerId);
    }

    @Override
    public String toString() {
        return "Bug{" +
                "id=" + id +
                ", deviceId=" + deviceId +
                ", testerId=" + testerId +
                '}';
    }
}
