package com.axiom.configuration

import org.scalatest._,  wordspec._, matchers._
import better.files._, Dsl._

import com.axiom.dataimport.api.*

class ConfigTest extends AnyWordSpec with should.Matchers{
  import com.typesafe.config._
  val config: Config = ConfigFactory.load()
  
  "config adm path"    should {
    "be able to open file " in {
      val admpath = config.getString("app.adm.path")
      val file = `~` / "OneDrive/Desktop/Hospitalist/adm.txt"
      val file2 = File(admpath)

      file.pathAsString should be (file2.pathAsString)
      file.exists should be (true)
    }
  }

  "admfile" should {
    "exist" in {
      import com.axiom.dataimport.apiutils.*
      admFile.exists should be(true)
    }
  }

  "hospadmFile" should {
    "exist" in {
      import com.axiom.dataimport.apiutils.*
      hospadmFile.exists should be(true)
    }
  }
}  




