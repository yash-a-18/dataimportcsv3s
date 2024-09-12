package com.axiom.dataimport

import org.scalatest._
import wordspec._
import matchers._




import better.files._
import ru.johnspade.csv3s.parser.*


class BetterFilesTest extends AnyWordSpec with should.Matchers{
  val resourceDir = ""/"jvm"/"src"/"test"/"resources"
  val file = resourceDir/"adm.txt"
  val parser = CsvParser(';')

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
      i.next() should be ("TB091080/16;TB00208063;KEESICK,DOMINICK TROY GLEN    ;M;25/01/1992;8937387366-PG;23/06/2016;T 1 FORENS;F100 TB   ;1  ;SCHUP     ;SCHUP     ;MACRI     ;N;N; ;FORENSICS                               ;                                        ;                              ;                              ;                    ;  ;          ;                  ;                  ;          ;        ;")
    }
  }
}
