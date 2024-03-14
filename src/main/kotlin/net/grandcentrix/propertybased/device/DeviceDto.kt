package net.grandcentrix.propertybased.device

import java.util.UUID

data class DeviceDto(
    var id: UUID = UUID.randomUUID(),
    var name: String = "",
    var value: Long = 0
) {
    override fun toString(): String {
        return "DeviceDto(id=$id, name='$name', value=$value)"
    }
}

data class BrokenDeviceDto(
    var id: UUID = UUID.randomUUID(),
    var name: String = "",
    var value: Int = 0
) {
    override fun toString(): String {
        return "DeviceDto(id=$id, name='$name', value=$value)"
    }
}
