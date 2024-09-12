package com.axiom.shared.javatimeapi

import org.scalatest._,  wordspec._, matchers._
/**
  * Note that shared code is automaticallyh tested for jvm and js when test is run
  */

class JavaTimeTest extends AnyWordSpec with should.Matchers{
  info("Java.time api gives cross platform date time between scala js and jvm")
  import java.time._
  val fixedClock = Clock.fixed(Instant.ofEpochSecond(1234567890L), ZoneOffset.ofHours(0))
  val date = LocalDateTime.now(fixedClock)  //obtains a datetime from a clock
  
  "FixedClock and derivations"   should {
    "a clock that always returns the same instant" in {
      import java.time._
      info(s"$fixedClock")

      date.toString should be("2009-02-13T23:31:30")

      date.getMonth() should be(Month.FEBRUARY)
      date.getDayOfMonth() should be(13)
      date.getDayOfWeek() should be (DayOfWeek.FRIDAY)
      date.getHour() should be(23)

      val tomorrow = date.plusDays(1)
      tomorrow.toString() should be("2009-02-14T23:31:30")

      val duration = Duration.between(date,tomorrow) //duration handles second and nanosecond precision but treats days as equivalent to 24 hours = 86400 second 
      //use duration when you care about taking timezones of the datetimes into account
      s"$duration" should be("PT24H")

      duration.toMinutes() should be(24*60)

      //period is limited to calendar date precision
      val period = Period.between(date.toLocalDate(), tomorrow.toLocalDate())
      s"$period" should be("P1D")
      period.get(temporal.ChronoUnit.DAYS) should be(1)
    }
  }

  "Format" should {
    "work" in {

      val format1 = format.DateTimeFormatter.ofPattern("MMMM MM HH EEEE")
      info(s"${format1.format(date)}")
      s"${format1.format(date)}" should be("February 02 23 Friday") 

      //note the pattern EE is inconsistent between JVM and JS. EE gives 3 letter
      //abbreviation of day, but one of them puts a period and the other does not


    }
  }


}




