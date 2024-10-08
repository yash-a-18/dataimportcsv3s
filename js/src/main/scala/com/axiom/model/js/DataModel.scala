package com.axiom.model.js
// import com.axiom.model.js.filter.{*,given}
import com.axiom.model.shared.dto.*
// import com.axiom.model.queryparser.*


import com.raquo.airstream.ownership.OneTimeOwner
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

object DataModel :

  private given Owner = new OneTimeOwner(()=>())


  /* 
  periodic fetching of elements  every 30 seconds
  "So, in short, whenever parentStream emits a new ev event, flatStream switches from mirroring the previous 
    innerStream to mirroring the next innerStream. It forgets about the previous innerStream from then on."
    (laminar documentation)
  */
  
  val periodicFetch = EventStream.periodic(30000)
    .flatMapSwitch { e => 
      //this is an inner event stream that gets flattened and  the previous one that
      //was created gets forgotten and the new one that gets created
      //becomes active.  Effectively the inner event stream is "switched"
      Fetch.patients.map{_.getOrElse(List())}
    }
  

  /**
    * holds all patients that are fetched intermittenly to refresh this total list
    */  
  val patientTableData = Var[List[Patient]](List[Patient]())

  periodicFetch.foreach{ l =>
      patientTableData.set(l)
    }

  /**
    * The UI will "show" this filted version of patientTableData
    */
  val filteredPatientTableData = Var[List[Patient]](List[Patient]())
  

  /**
    * the current filter query string from UI
    */
  val filterQuery = Var[String]("")

  /* transforms the query string to a predicate function used for filtering */
  // private val fPredicate = filterQuery.signal.map{ q =>
  //   val criteria = parseFilterTerms(q)
  //   // dom.console.log(s"filter criteria $criteria")
  //   filterPredicate(criteria)
  // }

  // /*  whenever the filter query changes, the filteredPatientTableData is updated */
  // fPredicate.signal.foreach{ p => 
  //   val filteredList = patientTableData.now().filter{ p   } 
  //   filteredPatientTableData.set(filteredList)
  // }


  
  




  




  
  
  



end DataModel
  
