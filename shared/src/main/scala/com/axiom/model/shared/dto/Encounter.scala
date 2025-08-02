package com.axiom.model.shared.dto

import zio.json._
import java.time.LocalDateTime
import zio.json.JsonEncoder.fromCodec

final case class Encounter(
    encounterId: Long,
    accountId: Long,
    doctorId: Long,
    startDate: LocalDateTime,
    endDate: Option[LocalDateTime] = None,
    auroraFileContent: Option[Array[Byte]] = None
)

object Encounter {
    given codec: JsonCodec[Encounter] = DeriveJsonCodec.gen[Encounter]
}
