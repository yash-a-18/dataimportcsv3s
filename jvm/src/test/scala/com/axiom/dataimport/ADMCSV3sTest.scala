package com.axiom.dataimport

import org.scalatest._,  wordspec._, matchers._
import better.files._

import ru.johnspade.csv3s._, parser._, printer._
import admcodec.{ADM, given}

class ADMCSV3sTest extends FixtureAnyWordSpec with should.Matchers{
  case class FixtureParam(lineIterator: Iterator[String], parser: CsvParser)
  def withFixture(test: OneArgTest) = {
    // Shared setup (run at beginning of test)
    val lineIterator = this.getClass().getResource("/adm.txt").toFile.get.lineIterator
    val parser = CsvParser(';')
    

    val theFixture = FixtureParam(lineIterator,parser)
    try {
      withFixture(test.toNoArgTest(theFixture)) // "loan" the fixture to the test
    }
    finally {
      lineIterator.toList //forces the iterator to run to the end and then autocloses the file
    }
  }

  "The header"   should {
    "have 124 characters, otherwise standard length 394" in { fixture =>
      val header = fixture.lineIterator.next() 
      header.length should be (124)

      val standardLineLength = fixture.lineIterator.next().length()

      standardLineLength should be (394)
      fixture.lineIterator.foreach { line =>
        line.length should be (standardLineLength)
      }
    }
  }

  "csv3s parser" should {
    "reveal 28 fields to a row" in { fixture =>
      //skip header line
      fixture.lineIterator.next() //skip header
      val line = fixture.lineIterator.next() //first line

      val result = for {
        result <- parseRow(line,fixture.parser)
      }
      yield(result.l.size)
      result.isRight should be( true)
      result.getOrElse(0) should be (28)
    }
  }
    

  "decode all to ADM should work " in {fixture => 
    import admcodec.{*,given}
    fixture.lineIterator.next() //skip header
    fixture.lineIterator.foreach { line =>
      parseRow(line,fixture.parser).map { decoder.decode(_)  } match {
        case Right(admRow) => admRow.map { _ shouldBe a [ADM]}
        case Left(e) => fail()
      }
    }
  }

 

  "encode adm to csv" in {fixture =>
    import admcodec.{*,given}
    fixture.lineIterator.next() //skip header
    val line = fixture.lineIterator.next()
    for{
      row <- parseRow(line,fixture.parser)
      adm <- decoder.decode(row)
    } yield {
      import printer.CsvPrinter
      val encodedRow = admcodec.encoder.encode(adm)
      val encodedLine = CsvPrinter.withSeparator(';').print(encodedRow) + ';'

      line should be(encodedLine)
    }
    


  }
}
