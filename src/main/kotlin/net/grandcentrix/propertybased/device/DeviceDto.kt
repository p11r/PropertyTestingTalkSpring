package net.grandcentrix.propertybased.device

import java.util.UUID

data class DeviceDto(
    var id: UUID,
    var name: String
) {
    override fun toString(): String {
        return "DeviceDto(id=$id, name='$name')"
    }
}