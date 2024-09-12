package com.axiom.dataimport

import org.scalatest._
import wordspec._, matchers._
import better.files._


class BetterFilesTest extends AnyWordSpec with should.Matchers{
  val resourceDir = ""/"jvm"/"src"/"test"/"resources"
  val file = resourceDir/"adm.txt"

  "adm.txt file" should {
    "exist" in {

      file.exists shouldBe true
    }

    "contain more than 100 lines" in {

      val file = resourceDir/"adm.txt"
      file.lines.size should be > 100
    }
  }

  "line reader" should {
    "work" in {
      
      val i = file.lineIterator //.drop(1)
      i.next() should be ("Account;Unit Number;Patient;Sex;DOB;HCN;Admit Date;Location;Room;Bed;Admitting;Attending;Family;Fam Priv?;Hosp?;Flag;Service")
    }
  }
}
