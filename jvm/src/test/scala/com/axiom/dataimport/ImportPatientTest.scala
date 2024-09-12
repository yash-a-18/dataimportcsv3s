package com.axiom.dataimport

import org.scalatest._, wordspec._, matchers._
import dataimportapi._
class ImportPatientTest extends AnyWordSpec with should.Matchers{
  "import Patient list " should {
    "contain more than 100 entities" in {
      importpatients.size should be > 100
    }

  }


}
