package com.axiom.model.js

import com.axiom.model.shared.dto.Patient
import com.axiom.model.js.DataModel.patientTableData
import com.axiom.model.shared.dto.Patient
import com.axiom.model.queryparser.*, FilterTerm.FLOORWING
object filter :
  protected def floorWing(p:Patient):FLOORWING = 
    p.floor match {
      case Some(f) => 
        f match {
          case "T ER OVFLW" => FLOORWING("2",Some("E"))
          case "ED CONSULT" => FLOORWING("2",Some("E"))
          case "T HRM CED"  => FLOORWING("1",Some("H"))
          case "T 2 MH"     => FLOORWING("2",Some("M"))
          case "T 1 FORENS" => FLOORWING("1",Some("F"))
          case f            => FLOORWING(f.substring(2,3),Some(f.substring(3,4)))
        }
      case None => FLOORWING(" ",None)
    }



  given IncludeMethods[Patient](st=null) with
    import FilterTerm._
    def includeFullName(patientdata:Patient):Boolean = st.fn match {
      case None => true
      case Some(FULLNAME(last,first)) =>  
        val result = (last,first) match {
          case (None,None) => true
          case (Some(x),None) => patientdata.lastName.toUpperCase.startsWith(x.toUpperCase())
          case (None,Some(y)) => patientdata.firstName.toUpperCase.startsWith(y.toUpperCase())
          case (Some(x),Some(y)) => patientdata.lastName.toUpperCase.startsWith(x.toUpperCase()) && patientdata.firstName.toUpperCase.startsWith(y.toUpperCase())
        }
        result
    }
    def includeFloorWing(patientdata:Patient):Boolean = 
      st.fw match {
        case None => true
        case Some(FLOORWING(floor,None)) => 
          floor == floorWing(patientdata).floor

        case _ => st.fw.get == floorWing(patientdata)
      }

    def includeMrp(patientdata:Patient):Boolean = st.mrp match {
      case None => true
      case Some(MRP(text)) => {
        val result = text match {
          case None => true
          case Some(x) => patientdata.mrp.get.startsWith(x)
        }
        result
      }
    }

    def includeRoom(patientdata:Patient):Boolean = st.rm match {
      case None => true
      case Some(ROOMBED(text,None)) => patientdata.room.get.startsWith(text)
      case default => true
    }
      
  
  
  
  /**
    * higher order function that returns a filter predicate appropriate to T
    *
    * @param st
    * @param im
    * @return
    */
  def filterPredicate[T](st:FilterTerms)(using im: IncludeMethods[T]):(T)=>Boolean = 
    im.st = st
    (p:T) => im.include(p)

  
end filter
