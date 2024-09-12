package com.axiom.dataimport

import ru.johnspade.csv3s._, codecs._, parser._ , instances.given
import utils.*
import scala.util.Try
import com.axiom.dataimport.utils
object dataimportcodec :
  
  given StringEncoder[Field1] = _.padded
  given StringDecoder[Field1] =  s => Try(Field1(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Field8] = _.padded
  given StringDecoder[Field8] =  s => Try(Field8(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Field10] = _.padded
  given StringDecoder[Field10] =  s => Try(Field10(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Field18] = _.padded
  given StringDecoder[Field18] =  s => Try(Field18(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Field20] = _.padded
  given StringDecoder[Field20] =  s => Try(Field20(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Field30] = _.padded
  given StringDecoder[Field30] =  s => Try(Field30(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Field40] = _.padded
  given StringDecoder[Field40] =  s => Try(Field40(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))


  given StringEncoder[HospitalistFlag] = _.padded
  given StringDecoder[HospitalistFlag] =  s => Try(HospitalistFlag(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Flag] = _.padded
  given StringDecoder[Flag] =  s => Try(Flag(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Service] = _.padded
  given StringDecoder[Service] =  s => Try(Service(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  

  given StringEncoder[AccountNumber] = _.padded
  given StringDecoder[AccountNumber] =  s => Try(AccountNumber(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[UnitNumber] = _.padded
  given StringDecoder[UnitNumber] =  s => Try(UnitNumber(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[BirthDate] = _.padded
  given StringDecoder[BirthDate] =  s => Try(BirthDate(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Name] = _.padded
  given StringDecoder[Name] =  s => Try(Name(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[HealthCard] = _.padded
  given StringDecoder[HealthCard] =  s => Try(HealthCard(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Floor] = _.padded
  given StringDecoder[Floor] =  s => Try(Floor(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Room] = _.padded
  given StringDecoder[Room] =  s => Try(Room(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Bed] = _.padded
  given StringDecoder[Bed] =  s => Try(Bed(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[MRP] = _.padded
  given StringDecoder[MRP] =  s => Try(MRP(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[AdmittingPhysician] = _.padded
  given StringDecoder[AdmittingPhysician] =  s => Try(AdmittingPhysician(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[PrimaryCare] = _.padded
  given StringDecoder[PrimaryCare] =  s => Try(PrimaryCare(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))
  
  given StringEncoder[FamilyPrivileges] = _.padded
  given StringDecoder[FamilyPrivileges] =  s => Try(FamilyPrivileges(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))
  



  import java.time.LocalDate

  given StringEncoder[LocalDate] = d => formattedString(d)
  given StringDecoder[LocalDate] =  s => Try(localDate(s))
      .toEither
      .left
      .map(e => DecodeError.TypeError(e.getMessage))    

