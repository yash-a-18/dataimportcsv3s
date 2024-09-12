package com.axiom.dataimport
import com.axiom.model.shared.dto.Patient
import hospadmcodec.HospADM
import hospadmcodec.given
import admcodec._
import com.axiom.model.shared.dto.Patient
import com.axiom.dataimport.utils

object dataimportapi:
  import com.typesafe.config._
  import better.files._, Dsl._
  import ru.johnspade.csv3s._, ru.johnspade.csv3s.parser._, codecs._

  import java.time._
  val dateFormat = format.DateTimeFormatter.ofPattern("dd/MM/yyyy")

  private val config: Config = ConfigFactory.load()
  lazy val admFile =  File(config.getString("app.adm.path"))
  lazy val hospadmFile = File(config.getString("app.hospadm.path"))
  private val csvParser = CsvParser(';')
  
  private def parseLine[T] (line:String)(using decoder:RowDecoder[T]) =
    val result = for (
      row <- parseRow(line,csvParser);
      adm  <- decoder.decode(row)
    ) yield adm
    result

  private def importAdm():List[ADM] = 
    import admcodec.given
    val lineIterator = admFile.lineIterator
    lineIterator.next() //skip header
    lineIterator.map( parseLine[ADM](_)).collect{ case Right(adm) => adm}.toList
  end importAdm

  private def importHospAdm():List[HospADM] = 
    import hospadmcodec.given
    val lineIterator = hospadmFile.lineIterator
    lineIterator.next() //skip header
    lineIterator.map( parseLine[HospADM](_)).collect{ case Right(adm) => adm}.toList
  end importHospAdm



  def adm(hospadm:HospADM): ADM = 
    import utils._
    ADM(
    hospadm.accountNumber,hospadm.unitNumber,hospadm.name,hospadm.sex,
    hospadm.birthDate,hospadm.healthCard,hospadm.admitDate,hospadm.floor,hospadm.room,hospadm.bed,
    hospadm.mrp,hospadm.admittingPhysician,hospadm.primaryCare,hospadm.familyPrivileges,
    hospadm.hospitalistFlag,hospadm.flag,hospadm.service,
    Field40(""),Field30(""),Field30(""),Field20(""),Field1(""),Field10(""),Field18(""),Field18(""),Field10(""),Field8("")
    )

  def adm(patient:Patient)  : ADM = 
    import utils._
    import java.time._
    ADM( 
      AccountNumber(patient.accountNumber),
      UnitNumber(patient.unitNumber),
      Name(patient.lastName + "," + patient.firstName),
      patient.sex,
      BirthDate(formattedString(patient.dob.get)),
      HealthCard(patient.OHIP.getOrElse("")),
      patient.admitDate.get.toLocalDate(),
      Floor(patient.floor.getOrElse("")),
      Room(patient.room.getOrElse("")),
      Bed(patient.bed.getOrElse("")),
      MRP(patient.mrp.getOrElse("")),
      AdmittingPhysician(patient.admittingPhys.getOrElse("")),
      PrimaryCare(patient.family.getOrElse("")),
      FamilyPrivileges(patient.famPriv.getOrElse("")),
      HospitalistFlag(patient.hosp.getOrElse("")),
      Flag(patient.flag.getOrElse("")),
      Service(patient.service.getOrElse("")),
      Field40(""),
      Field30(""),
      Field30(""),
      Field20(""),
      Field1(""),
      Field10(""),
      Field18(""),
      Field18(""),
      Field10(""),
      Field8("")
    )

  def patient(adm:ADM):Patient = 
    import java.time._
    def stringOrNone(opt:String) = opt match {
      case "" => None
      case _ =>  Some(opt)
    }

    Patient(
      accountNumber = adm.accountNumber.trimmed,
      unitNumber = adm.unitNumber.trimmed,
      lastName = adm.name.lastName,
      firstName = adm.name.firstName,
      sex = adm.sex,
      dob = adm.birthDate.optLocalDate,
      hcn = Some(adm.healthCard.trimmed),
      admitDate = Some(adm.admitDate.atStartOfDay),
      floor = Some(adm.floor.trimmed),
      room =  Some(adm.room.trimmed),
      bed = Some(adm.bed.trimmed),
      mrp = Some(adm.mrp.trimmed).flatMap(stringOrNone),
      admittingPhys = Some(adm.admittingPhysician.trimmed).flatMap(stringOrNone),
      family = Some(adm.primaryCare.trimmed),
      famPriv = Some(adm.familyPrivileges.trimmed).flatMap(stringOrNone),
      hosp = Some(adm.hospitalistFlag.trimmed).flatMap(stringOrNone),
      flag = Some(adm.flag.trimmed).flatMap(stringOrNone),
      service = Some(adm.service.trimmed).flatMap(stringOrNone),
      address1 = Some(adm.f17.trimmed).flatMap(stringOrNone),
      address2 = Some(adm.f18.trimmed).flatMap(stringOrNone),
      city = Some(adm.f19.trimmed).flatMap(stringOrNone),
      province = Some(adm.f20.trimmed).flatMap(stringOrNone),
      postalCode =  Some(adm.f21.trimmed).flatMap(stringOrNone),
      homePhoneNumber = Some(adm.f22.trimmed).flatMap(stringOrNone),
      workPhoneNumber = Some(adm.f23.trimmed).flatMap(stringOrNone),
      OHIP = Some(adm.f24.trimmed).flatMap(stringOrNone),
      attending = Some(adm.primaryCare.trimmed).flatMap(stringOrNone),
      collab1 = None,
      collab2 = None
      )
  end patient

  def importpatients:List[Patient] = importAdm().map(patient(_))
end dataimportapi
