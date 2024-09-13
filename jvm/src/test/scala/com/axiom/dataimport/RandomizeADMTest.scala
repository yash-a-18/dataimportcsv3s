package com.axiom.dataimport

import org.scalatest._, wordspec._, matchers._
import dataimportapi._
import scala.util.Random


class RandomizeADMTest extends AnyWordSpec with should.Matchers{
   
  "random healthcard" should {
    "be valid" in {
      (1 to 100).foreach{i =>
        randomize.randomHealthcardIterator.next.s match {
          case "             " => assert(true)
          case hc => hc should fullyMatch regex """\d{10}-[A-Z]{2}"""
        }
      }    
    }
  }



  "randomizeAdm " should {
    "randomize names, unitnumber and account number in output file" in {
      import utils.*

      randomize.admlines.size should be > 200
      randomize.decodedRows.filter{_.isRight}.size should be > 200
      
      randomize.parsedResult.size should be(randomize.admlines.size)
      randomize.randomizeAdm()

      randomize.newadmoutput.lineCount should be (randomize.admlines.size + 1) //because admlines dropped the header

    }

  }


}

