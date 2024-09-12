package com.axiom.shared.ziojson

import org.scalatest._,  wordspec._, matchers._
/**
  * Note that shared code is automaticallyh tested for jvm and js when test is run
  */

class ZioJsonExampleTest extends AnyWordSpec with should.Matchers{
  "zio.json"   should {
    import zio.json._
    case class Banana(curvature: Double)
    val jsonBanana = """{"curvature":0.5}"""
    object Banana {
      given JsonDecoder[Banana] = DeriveJsonDecoder.gen[Banana]
      given JsonEncoder[Banana] = DeriveJsonEncoder.gen[Banana]
    }
    "decode" in {
      jsonBanana.fromJson[Banana] should be(Right(Banana(0.5)))
    }

    "encode" in {
      Banana(0.5).toJson should be ( jsonBanana )
    }
  }



}




