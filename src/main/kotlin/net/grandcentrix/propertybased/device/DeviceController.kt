package net.grandcentrix.propertybased.device

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class DeviceController {

    @GetMapping("/devices")
    fun getDevices(): List<DeviceDto> {
        val devices = mutableListOf<DeviceDto>()

        for (i in 1..10) {
            devices.add(
                DeviceDto(
                    id = UUID.randomUUID(),
                    name = "Device $i"
                )
            )
        }

        return devices
    }
}