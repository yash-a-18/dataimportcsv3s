package com.axiom.dataimport.javatime

trait JavaTimeUtils {
  import java.time._
  private val dateFormat = format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
  
  def localDate(s:String):LocalDate = LocalDate.parse(s,dateFormat )
  
  def formattedString(d:LocalDate):String =  dateFormat.format(d)

}
