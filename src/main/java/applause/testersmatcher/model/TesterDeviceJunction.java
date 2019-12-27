package applause.testersmatcher.model;

import java.util.Objects;

public final class TesterDeviceJunction {
    private final Long testerId;

    private final Long deviceId;

    public TesterDeviceJunction(Long testerId, Long deviceId) {
        this.testerId = testerId;
        this.deviceId = deviceId;
    }

    public Long getTesterId() {
        return testerId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TesterDeviceJunction that = (TesterDeviceJunction) o;
        return testerId.equals(that.testerId) &&
                deviceId.equals(that.deviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testerId, deviceId);
    }

    @Override
    public String toString() {
        return "TesterDeviceJunction{" +
                "testerId=" + testerId +
                ", deviceId=" + deviceId +
                '}';
    }
}
