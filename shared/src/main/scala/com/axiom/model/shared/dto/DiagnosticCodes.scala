package com.axiom.model.shared.dto

import zio.json.JsonCodec
import zio.json.DeriveJsonCodec

final case class DiagnosticCodes(
    diagnosticCode: String,
    label: String,
    description: String
)

object DiagnosticCodes: 
    given codec: JsonCodec[DiagnosticCodes] = DeriveJsonCodec.gen[DiagnosticCodes]