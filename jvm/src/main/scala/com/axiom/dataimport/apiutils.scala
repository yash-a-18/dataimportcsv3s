package com.axiom.dataimport

object apiutils :
  import better.files._, Dsl._
  import ru.johnspade.csv3s.printer.CsvPrinter
  import ru.johnspade.csv3s._, codecs._, parser._ , instances.given
  import csvcodecs.*, admcodec.{ADM}, hospadmcodec.{HospADM}
  
  
  import java.time._
  val dateFormat = format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
  
  import com.typesafe.config._
  private val config: Config = ConfigFactory.load()
  lazy val admFile =  File(config.getString("app.adm.path"))
  lazy val hospadmFile = File(config.getString("app.hospadm.path"))
  private val csvParser = CsvParser(';')


  def parseLine[T] (line:String)(using decoder:RowDecoder[T]) =
    val result = for (
      row <- parseRow(line,csvParser);
      adm  <- decoder.decode(row)
    ) yield adm
    result

  def importAdm():List[ADM] = 
    import admcodec.given
    val lineIterator = admFile.lineIterator
    lineIterator.next() //skip header
    lineIterator.map( parseLine[ADM](_)).collect{ case Right(adm) => adm}.toList
  end importAdm

  private def importHospAdm():List[HospADM] = 
    import csvcodecs.hospadmcodec.{*,given}
    val lineIterator = hospadmFile.lineIterator
    lineIterator.next() //skip header
    lineIterator.map( parseLine[HospADM](_)).collect{ case Right(adm) => adm}.toList
  end importHospAdm



  

