package com.axiom.dataimport.util

import scala.util.Try
import com.axiom.dataimport.javatime.JavaTimeUtils


object fieldutils extends JavaTimeUtils:
  trait PadSize:
    val s:String  
    val padSize:Int
    def padded = s.padTo(padSize,' ')
    lazy val trimmed = s.trim()



    
  case class AccountNumber(s:String) extends PadSize:
    override val padSize = 11 
    


  case class UnitNumber(s:String)   extends PadSize:
    override val padSize = 10 

  case class Name(s:String) extends PadSize:
    override val padSize = 30
    lazy val lastName = s.split(",")(0).trim()
    lazy val firstName = s.split(",")(1).trim()

  case class BirthDate(s:String) extends PadSize :
    override val padSize = 10
    def optLocalDate = Try( localDate(s) ).toOption

  case class HealthCard(s:String) extends PadSize:
    override val padSize = 13

  case class Floor(s:String)  extends PadSize:
    override val padSize = 10

  case class Room(s:String)  extends PadSize:
    override val padSize = 10

  case class Bed(s:String) extends PadSize:
    override val padSize = 3  
  case class MRP(s:String) extends PadSize:
    override val padSize = 10
  case class AdmittingPhysician(s:String)  extends PadSize:
    override val padSize = 10
  case class PrimaryCare(s:String)  extends PadSize:
    override val padSize = 10

  case class FamilyPrivileges(s:String)  extends PadSize:
    override val padSize = 1
  case class HospitalistFlag(s:String)  extends PadSize:
    override val padSize = 1
  case class Flag(s:String) extends PadSize:
    override val padSize = 1

  case class Service(s:String)  extends PadSize:
    override val padSize = 40  

  case class Field1(s:String)   extends PadSize:
    override val padSize = 1
  case class Field8(s:String)  extends PadSize:
    override val padSize = 8  
  
  case class Field10(s:String)  extends PadSize:
    override val padSize = 10  

  case class Field18(s:String)  extends PadSize:
    override val padSize = 18
  
  case class Field20(s:String)  extends PadSize:
    override val padSize = 20
  
  case class Field30(s:String)  extends PadSize:
    override val padSize = 30
  

  case class Field40(s:String)  extends PadSize:
    override val padSize = 40  

end fieldutils