package applause.testermatcher.repository

import applause.testersmatcher.model.Device
import applause.testersmatcher.repository.DeviceRepository
import applause.testersmatcher.util.DateTimeParser
import spock.lang.Shared
import spock.lang.Specification

class DeviceRepositoryTest extends Specification {
    def 'saves new device'() {
        given:
            def deviceRepository = new DeviceRepository("devices.csv", new DateTimeParser())
            def device = new Device(null, "Device")
        when:
            def newDevice = deviceRepository.save(device)
        then:
            newDevice.id != null
            def deviceFromDb = deviceRepository.getById(newDevice.id)
            deviceFromDb != null
            deviceFromDb == newDevice
    }

    def 'updates device'() {
        given:
            def deviceRepository = new DeviceRepository("devices.csv", new DateTimeParser())
            def device = new Device(null, "Device")
            def newDevice = deviceRepository.save(device)
        when:
            device = new Device(newDevice.id, "DeviceUpdated")
            def updatedDevice = deviceRepository.update(device)
        then:
            updatedDevice.id == newDevice.id
            def updatedDeviceFromDb = deviceRepository.getById(newDevice.id)
            updatedDeviceFromDb != null
            updatedDeviceFromDb == device
    }

    def 'deletes device'() {
        given:
            def deviceRepository = new DeviceRepository("devices.csv", new DateTimeParser())
            def device = new Device(null, "Device")
            def newDevice = deviceRepository.save(device)
        when:
            deviceRepository.delete(newDevice)
        then:
            def deviceFromDb = deviceRepository.getById(newDevice.id)
            deviceFromDb == null
    }
}
