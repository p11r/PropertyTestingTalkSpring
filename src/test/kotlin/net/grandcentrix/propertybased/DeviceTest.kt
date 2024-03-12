package net.grandcentrix.propertybased

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.databind.ObjectMapper
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

    private fun getDeviceDtoJson(uuid: UUID, name: String, value: Long) = """
        {
            "id": "$uuid",
            "name": "$name",
            "value": $value
        }
    """

    @Test
    fun `test json deserialisation`() {
        val deviceDto = DeviceDto(
            id = UUID.randomUUID(),
            name = "Test Device",
            value = 9223372036854775807
        )
        val json = getDeviceDtoJson(deviceDto.id, deviceDto.name, deviceDto.value)
        val resultDeviceDto = ObjectMapper().readValue(json, DeviceDto::class.java)

        assertThat(resultDeviceDto).isEqualTo(deviceDto)
    }

    @Property(tries = 500000)
    fun `property test json deserialisation`(@ForAll("randomDeviceDto") deviceDto: DeviceDto) {
        val json = getDeviceDtoJson(deviceDto.id, deviceDto.name, deviceDto.value)
        val resultDeviceDto = ObjectMapper().readValue(json, DeviceDto::class.java)

        assertThat(resultDeviceDto).isEqualTo(deviceDto)
    }

    @Test
    fun `round trip for mapping dto down to entity and back`() {
        val deviceDto = DeviceDto(
            id = UUID.randomUUID(),
            name = "Test Device",
            value = 42
        )
        val resultDeviceDto = deviceDto
            .let { DeviceMapper().toModel(it) }
            .let { DeviceMapper().toDto(it) }

        assertThat(resultDeviceDto).isEqualTo(deviceDto)
    }

    @Provide
    @Suppress("NAME_SHADOWING")
    fun randomDeviceDto(): Arbitrary<DeviceDto>  {
        val uuid = Arbitraries.strings().map { UUID.randomUUID() }
        val name = Arbitraries.strings().alpha().ofMinLength(1).ofMaxLength(100)
        val value = Arbitraries.longs()

        return combine(uuid, name, value) { uuid, name, value ->
            DeviceDto(uuid, name, value)
        }
    }

    @Property(tries = 10000)
    fun `round trip for mapping dto down to entity and back as pbt`(@ForAll("randomDeviceDto") deviceDto: DeviceDto) {
        val resultDeviceDto = deviceDto
            .let { DeviceMapper().toModel(it) }
            .let { DeviceMapper().toDto(it) }

        assertThat(resultDeviceDto).isEqualTo(deviceDto)
    }

}