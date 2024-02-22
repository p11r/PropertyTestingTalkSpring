package net.grandcentrix.propertybased

import assertk.assertThat
import assertk.assertions.isEqualTo
import net.grandcentrix.propertybased.device.DeviceDto
import net.grandcentrix.propertybased.device.DeviceMapper
import net.jqwik.api.Arbitraries
import net.jqwik.api.Arbitrary
import net.jqwik.api.Combinators
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.Provide
import net.jqwik.api.Tuple.Tuple2
import net.jqwik.kotlin.api.anyForType
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
            .let { DeviceMapper().toEntity(it) }
            .let { DeviceMapper().toModel(it) }
            .let { DeviceMapper().toDto(it) }

        assertThat(resultDeviceDto).isEqualTo(deviceDto)
    }

    @Provide
    fun randomDeviceDto(): Arbitrary<DeviceDto> = anyForType()

    @Property(tries = 100000)
    fun `round trip for mapping dto down to entity and back as pbt`(@ForAll("randomDeviceDto") deviceDto: DeviceDto) {
        /*val deviceDto = DeviceDto(
            id = UUID.randomUUID(),
            name = "Test Device"
        )*/

        val resultDeviceDto = deviceDto
            .let { DeviceMapper().toModel(it) }
            .let { DeviceMapper().toEntity(it) }
            .let { DeviceMapper().toModel(it) }
            .let { DeviceMapper().toDto(it) }

        assertThat(resultDeviceDto).isEqualTo(deviceDto)
    }

}