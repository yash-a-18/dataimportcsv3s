package com.axiom.dataimport.csvcodecs



object admcodec:
  import com.axiom.dataimport.util.fieldutils.*
  import ru.johnspade.csv3s.printer.CsvPrinter
  import ru.johnspade.csv3s._, codecs._, parser._ , instances.given
  import better.files._

  import java.time.LocalDate

  import dataimportcodec.{*,given}


  case class ADM(id:Long,accountNumber:AccountNumber,unitNumber:UnitNumber,name:Name,sex:String,
    birthDate:BirthDate,healthCard:HealthCard,admitDate:LocalDate,floor:Floor,room:Room,bed:Bed,
    mrp:MRP, admittingPhysician:AdmittingPhysician,primaryCare:PrimaryCare ,familyPrivileges:FamilyPrivileges,
    hospitalistFlag:HospitalistFlag,flag:Flag ,service:Service ,f17:Field40,f18:Field30,f19:Field30,f20:Field20,
    f21:Field1,f22:Field10,f23:Field18,f24:Field18,f25:Field10,f26:Field8, auroraFile:AuroraFile)

  case class HOSPADM(id:Long,accountNumber:AccountNumber,unitNumber:UnitNumber,name:Name,sex:String,
    birthDate:BirthDate,healthCard:HealthCard,admitDate:LocalDate,floor:Floor,room:Room,bed:Bed,
    mrp:MRP, admittingPhysician:AdmittingPhysician,primaryCare:PrimaryCare ,familyPrivileges:FamilyPrivileges,
    hospitalistFlag:HospitalistFlag,flag:Flag ,service:Service, auroraFile:AuroraFile)


  
  given RowDecoder[HOSPADM] = RowDecoder.derived  
  given RowEncoder[HOSPADM] = RowEncoder.derived

  given decoder: RowDecoder[ADM] = RowDecoder.derived
  given encoder: RowEncoder[ADM] = RowEncoder.derived

  private def csvPrinter = CsvPrinter.withSeparator(';')
  private def csvParser = CsvParser(';')

  private def encodeToString[T](x: T)(using e:RowEncoder[T]) =  csvPrinter.print{
    e.encode(x)
  } + ';'

  private def decode[T](s:String)(using d:RowDecoder[T]) = 
    for {
      x <- parseRow(s,csvParser)
      r  <- d.decode(x)

    } yield (r)

  private def encodeADM(i:Iterator[ADM]):Iterator[String] = i.map{encodeToString[ADM]}
  private def encodeHOSPADM(i:Iterator[HOSPADM]):Iterator[String] = i.map{encodeToString[HOSPADM]}

  def decodeADM(f:File):Iterator[ADM] =  decodeADM(f.lineIterator.drop(1)) //drop header

  def decodeADMtoRow(i:Iterator[String])  = 
    i.map{decode[ADM]}
  def decodeADM(i:Iterator[String]):Iterator[ADM] = 
    i.map{decode[ADM]}
    .filter(_.isRight)
    .map{_.right.get}


  def decodeHOSPADM(f:File):Iterator[HOSPADM] = f.lineIterator
    .map{ decode[HOSPADM] }
    .filter(_.isRight).map{x => 
      x.right.get
    }

  def transferHOSPADMtoADM(hFile:File,admFile:File) = 
    decodeHOSPADM(hFile)
    .map(hospADMtoADM)
    .map(encodeToString[ADM])
    .foreach{admFile.appendLine}

  
  def encodeADM(f:File, i:Iterator[ADM]) = 
    f.overwrite("Account;Unit Number;Patient;Sex;DOB;HCN;Admit Date;Location;Room;Bed;Admitting;Attending;Family;Fam Priv?;Hosp?;Flag;Service;Aurora File?")
    f.appendLine()
    f.printLines(i.map(encodeToString[ADM]))
  def encodeHOSPADM(f:File, i:Iterator[HOSPADM]) = f.printLines(i.map(encodeToString[HOSPADM]))

  def hospADMtoADM(hadm : HOSPADM) = ADM(hadm.id,hadm.accountNumber,hadm.unitNumber,
      hadm.name,hadm.sex,hadm.birthDate,hadm.healthCard,hadm.admitDate,hadm.floor,hadm.room,
      hadm.bed,hadm.mrp,
      hadm.admittingPhysician,hadm.primaryCare,
      hadm.familyPrivileges,hadm.hospitalistFlag,
      hadm.flag,hadm.service,Field40(""),
      Field30(""),Field30(""),
      Field20(""),Field1(""),
      Field10(""),Field18(""),
      Field18(""),Field10(""),Field8(""),
      hadm.auroraFile
    )

end admcodec  

