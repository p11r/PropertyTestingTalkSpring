package net.grandcentrix.propertybased.device

class DeviceMapper {

    fun toDto(device: Device): DeviceDto {
        return DeviceDto(
            id = device.id,
            name = device.name,
            value = device.value
        )
    }

    fun toModel(deviceDto: DeviceDto): Device {
        return Device(
            id = deviceDto.id,
            name = deviceDto.name,
            value = deviceDto.value
        )
    }

}
