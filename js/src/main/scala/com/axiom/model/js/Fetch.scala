package com.axiom.model.js

import zio.json._
import com.axiom.model.shared._, dto._
import com.raquo.laminar.api.L.{*, given}
import com.axiom.model.shared.dto.Patient

object Fetch :
  def patients = 
    FetchStream.get("http://localhost:8080/patientsjson")
    .map(s => s.fromJson[List[Patient]])
    .map(r => r.toOption)

end Fetch

  
