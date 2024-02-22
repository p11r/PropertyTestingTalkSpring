package net.grandcentrix.propertybased.device

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Entity
class DeviceEntity(

    @Id
    var id: UUID,

    var name: String
)
