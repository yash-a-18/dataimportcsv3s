package com.axiom.dataimport

import org.scalatest._, wordspec._, matchers._
import api._
class ImportPatientTest extends AnyWordSpec with should.Matchers{
  "import Patient list " should {
    "contain more than 100 entities" in {
      importpatients.size should be > 100
    }
  }

  "convert patient list to json" should {
    "contain more than 100 entities" in {
      

      import zio.json._
      import java.time._ 

      importpatients.toJson.size should be > 100
      info(importpatients.toJson)
    }
  }
}
