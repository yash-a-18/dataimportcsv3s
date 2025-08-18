package com.axiom.dataimport

// import ru.johnspade.csv3s.printer.CsvPrinter
// import ru.johnspade.csv3s._, codecs._, parser._ , instances.given

import com.axiom.model.shared.dto.Patient
import util.fieldutils._
import csvcodecs.admcodec.ADM
import csvcodecs.hospadmcodec.HospADM


object api:
  def adm(hospadm:HospADM): ADM = 
    import util._
    ADM(hospadm.id,
    hospadm.accountNumber,hospadm.unitNumber,hospadm.name,hospadm.sex,
    hospadm.birthDate,hospadm.healthCard,hospadm.admitDate,hospadm.floor,hospadm.room,hospadm.bed,
    hospadm.mrp,hospadm.admittingPhysician,hospadm.primaryCare,hospadm.familyPrivileges,
    hospadm.hospitalistFlag,hospadm.flag,hospadm.service,
    Field40(""),Field30(""),Field30(""),Field20(""),Field1(""),Field10(""),Field18(""),Field18(""),Field10(""),Field8(""),
    hospadm.auroraFile
    )

  def adm(patient:Patient)  : ADM = 
    import util._
    import java.time._
    ADM( 
      patient.id,
      AccountNumber(patient.accountNumber),
      UnitNumber(patient.unitNumber),
      Name(patient.lastName + "," + patient.firstName),
      patient.sex,
      BirthDate(formattedString(patient.dob.get)),
      HealthCard(patient.ohip.getOrElse("")),
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
      Field8(""),
      AuroraFile(patient.auroraFile.getOrElse(""))
    )

  def patient(adm:ADM):Patient = 
    import java.time._
    def stringOrNone(opt:String) = opt match {
      case "" => None
      case _ =>  Some(opt)
    }

    Patient(
      id = adm.id,
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
      ohip = Some(adm.f24.trimmed).flatMap(stringOrNone),
      attending = Some(adm.primaryCare.trimmed).flatMap(stringOrNone),
      collab1 = None,
      collab2 = None,
      auroraFile = None
      )
  end patient

  def importpatients:List[Patient] = apiutils.importAdm().map(patient(_))
end api
