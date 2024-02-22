package net.grandcentrix.propertybased.device

class DeviceMapper {

    fun toEntity(deviceModel: Device): DeviceEntity {
        return DeviceEntity(
            id = deviceModel.id,
            name = deviceModel.name
        )
    }

    fun toDto(device: Device): DeviceDto {
        return DeviceDto(
            id = device.id,
            name = device.name
        )
    }

    fun toModel(deviceDto: DeviceDto): Device {
        return Device(
            id = deviceDto.id,
            name = deviceDto.name
        )
    }

    fun toModel(deviceEntity: DeviceEntity): Device {
        return Device(
            id = deviceEntity.id!!,
            name = deviceEntity.name!!
        )
    }

}
