package com.axiom.dataimport
import api._
import scala.util.Random

object randomize :
  import util.fieldutils.*
  lazy val newadmoutput = 
    import com.typesafe.config._
    import better.files._
    val config: Config = ConfigFactory.load()
    File(config.getString("app.newadmoutput.path"))


  lazy val randomVersionCodeIterator =   
    val amount = 1000
    val randomLetterIterator = Random.alphanumeric.filter(_.isLetter).map(_.toString().toUpperCase()).iterator
    (0 to amount*2).map{c => randomLetterIterator.next()}
    (0 to amount).map(i => randomLetterIterator.next + randomLetterIterator.next).toIterator

  lazy val  randomAccountNumberIterator =  {
    (0 to 2000).map(x =>  Random.nextInt(1000000)).toSet
      .map {n =>
        AccountNumber(f"TB${n}%06d/23")
      }.toIterator    
  }

  lazy val randomUnitNumberIterator = 
    (0 to 1000).map(x => Random.nextInt(1000000)).toSet
      .map {n =>
        UnitNumber(f"TB00${n}%06d")
      }.toIterator



  lazy val randomHealthcardIterator = 
    val amount = 1000
    val spaces = f"${""}%13s"
    (0 to amount)
      .map (c => Random.nextInt(1000000000))
      .map (n => n%115 match {
        case 1 => spaces//"             "
        case _ => f"$n%010d-${randomVersionCodeIterator.next()}"
      })
      .map(HealthCard(_))
      .toIterator
  
 
  import better.files._
  import scala.io.Source
  import util.*

  lazy val  randomNames = 
    val namelist = Source.fromResource("randomnames.txt").getLines().map(n => Name(n.toUpperCase())).toList
    Random.shuffle(namelist).toIterator


  lazy val  admlines   = Source.fromResource("adm.txt").getLines().toList.drop(1) //drop header
  

  lazy val decodedRows = csvcodecs.admcodec.decodeADMtoRow(admlines.iterator).toList
  
  lazy val parsedResult = csvcodecs.admcodec.decodeADM(admlines.iterator).toList


  def randomizeAdm() = 
    val newAdmData = parsedResult
      .map{adm => adm.copy( name = randomNames.next(), 
                            healthCard = randomHealthcardIterator.next(),
                            accountNumber = randomAccountNumberIterator.next(),
                            unitNumber = randomUnitNumberIterator.next())
      }   
    csvcodecs.admcodec.encodeADM(newadmoutput,newAdmData.iterator)   







