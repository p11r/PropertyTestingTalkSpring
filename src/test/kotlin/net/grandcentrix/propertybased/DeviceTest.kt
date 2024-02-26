package net.grandcentrix.propertybased

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.grandcentrix.propertybased.device.DeviceDto
import net.grandcentrix.propertybased.device.DeviceMapper
import net.jqwik.api.Arbitraries
import net.jqwik.api.Arbitrary
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.Provide
import net.jqwik.api.Tuple.Tuple2
import net.jqwik.kotlin.api.any
import net.jqwik.kotlin.api.combine
import org.junit.jupiter.api.Test
import java.util.*


class DeviceTest {

    @Test
    fun `round trip for mapping dto down to entity and back`() {
        val deviceDto = DeviceDto(
            id = UUID.randomUUID(),
            name = "Test Device"

        )
        val resultDeviceDto = deviceDto
            .let { DeviceMapper().toModel(it) }
            .let { DeviceMapper().toDto(it) }

        assertThat(resultDeviceDto).isEqualTo(deviceDto)
    }

    @Provide
    fun randomDeviceDto(): Arbitrary<DeviceDto>  {
        val uuid = Arbitraries.strings().map { UUID.randomUUID() }
        val name = Arbitraries.strings().alpha().ofMinLength(1).ofMaxLength(100)

        return combine(uuid, name) { uuid, name ->
            DeviceDto(uuid, name)
        }
    }

    @Property(tries = 100)
    fun `round trip for mapping dto down to entity and back as pbt`(@ForAll("randomDeviceDto") deviceDto: DeviceDto) {
        val resultDeviceDto = deviceDto
            .let { DeviceMapper().toModel(it) }
            .let { DeviceMapper().toDto(it) }

        print(deviceDto)
        
        assertThat(resultDeviceDto).isEqualTo(deviceDto)
    }

}