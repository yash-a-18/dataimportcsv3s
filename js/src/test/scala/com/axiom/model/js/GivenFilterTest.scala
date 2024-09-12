package com.axiom.model.js

import org.scalatest._, wordspec._, matchers._
import com.axiom.model.shared.dto.Patient


class GivenFilterTest extends AnyWordSpec with should.Matchers{
  "this" should {
    "work" in {

      import com.axiom.model.js.filter.{*,given}
      import com.axiom.model.queryparser.*
      val searchterms =   parseFilterTerms("")  

      val filterF = filter.filterPredicate[Patient](searchterms)

      List[Patient]().filter(filterF) should be(List[Patient]())
    }
  }
}
