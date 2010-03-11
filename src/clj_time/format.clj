(ns clj-time.format
  "Utilities for printing and parsing DateTimes as Strings.

   Printing and parsing are controlled by formatters. You can either use one
   of the built in ISO8601 formatters or define your own, e.g.:

     (def built-in-formatter (formatters :basic-date-time))
     (def custom-formatter (formatter \"yyyyMMdd\"))

   To see a list of available built-in formatters and an example of a date-time
   printed in their format:

     (show-printers)

    Once you have a formatter, printing and parsing are straitforward:

      => (parse custom-formatter \"20100311\")
      #<DateTime 2010-03-11T00:00:00.000Z>

      => (print custom-formatter (date-time 2010 10 3))
      \"20101003\"

    Note that print returns a String; it does not write to *out* or some other
    stream. Also, note that the parse function always returns a DateTime
    instance with a UTC time zone, and the print function always represents a
    given DateTime instance in UTC."
  (:refer-clojure :exclude (second contains? print))
  (:use [clojure.contrib.def :only (defvar defvar-)])
  (:require [clj-time.core :as core]
            [clojure.set :as set])
  (:import (org.joda.time DateTime)
           (org.joda.time.format DateTimeFormat DateTimeFormatter
                                 ISODateTimeFormat)))

; The formatters map and show-printers idea are strait from chrono.

(defvar formatters
  (into {} (map
    (fn [[k #^DateTimeFormatter f]] [k (.withZone f #^DateTimeZone core/utc)])
    {:basic-date (ISODateTimeFormat/basicDate)
     :basic-date-time (ISODateTimeFormat/basicDateTime)
     :basic-date-time-no-ms (ISODateTimeFormat/basicDateTimeNoMillis)
     :basic-ordinal-date (ISODateTimeFormat/basicOrdinalDate)
     :basic-ordinal-date-time (ISODateTimeFormat/basicOrdinalDateTime)
     :basic-ordinal-date-time-no-ms (ISODateTimeFormat/basicOrdinalDateTimeNoMillis)
     :basic-time (ISODateTimeFormat/basicTime)
     :basic-time-no-ms (ISODateTimeFormat/basicTimeNoMillis)
     :basic-t-time (ISODateTimeFormat/basicTTime)
     :basic-t-time-no-ms (ISODateTimeFormat/basicTTimeNoMillis)
     :basic-week-date (ISODateTimeFormat/basicWeekDate)
     :basic-week-date-time (ISODateTimeFormat/basicWeekDateTime)
     :basic-week-date-time-no-ms (ISODateTimeFormat/basicWeekDateTimeNoMillis)
     :date (ISODateTimeFormat/date)
     :date-element-parser (ISODateTimeFormat/dateElementParser)
     :date-hour (ISODateTimeFormat/dateHour)
     :date-hour-minute (ISODateTimeFormat/dateHourMinute)
     :date-hour-minute-second (ISODateTimeFormat/dateHourMinuteSecond)
     :date-hour-minute-second-fraction (ISODateTimeFormat/dateHourMinuteSecondFraction)
     :date-hour-minute-second-ms (ISODateTimeFormat/dateHourMinuteSecondMillis)
     :date-opt-time (ISODateTimeFormat/dateOptionalTimeParser)
     :date-parser (ISODateTimeFormat/dateParser)
     :date-time (ISODateTimeFormat/dateTime)
     :date-time-no-ms (ISODateTimeFormat/dateTimeNoMillis)
     :date-time-parser (ISODateTimeFormat/dateTimeParser)
     :hour (ISODateTimeFormat/hour)
     :hour-minute (ISODateTimeFormat/hourMinute)
     :hour-minute-second (ISODateTimeFormat/hourMinuteSecond)
     :hour-minute-second-fraction (ISODateTimeFormat/hourMinuteSecondFraction)
     :hour-minute-second-ms (ISODateTimeFormat/hourMinuteSecondMillis)
     :local-date-opt-time (ISODateTimeFormat/localDateOptionalTimeParser)
     :local-date (ISODateTimeFormat/localDateParser)
     :local-time (ISODateTimeFormat/localTimeParser)
     :ordinal-date (ISODateTimeFormat/ordinalDate)
     :ordinal-date-time (ISODateTimeFormat/ordinalDateTime)
     :ordinal-date-time-no-ms (ISODateTimeFormat/ordinalDateTimeNoMillis)
     :time (ISODateTimeFormat/time)
     :time-element-parser (ISODateTimeFormat/timeElementParser)
     :time-no-ms (ISODateTimeFormat/timeNoMillis)
     :time-parser (ISODateTimeFormat/timeParser)
     :t-time (ISODateTimeFormat/tTime)
     :t-time-no-ms (ISODateTimeFormat/tTimeNoMillis)
     :week-date (ISODateTimeFormat/weekDate)
     :week-date-time (ISODateTimeFormat/weekDateTime)
     :week-date-time-no-ms (ISODateTimeFormat/weekDateTimeNoMillis)
     :weekyear (ISODateTimeFormat/weekyear)
     :weekyear-week (ISODateTimeFormat/weekyearWeek)
     :weekyear-week-day (ISODateTimeFormat/weekyearWeekDay)
     :year (ISODateTimeFormat/year)
     :year-month (ISODateTimeFormat/yearMonth)
     :year-month-day (ISODateTimeFormat/yearMonthDay)}))
  "Map of ISO8601-standard formatters that can be used for parsing and, in most
  cases, printing.")

(defvar- parsers
  #{:date-element-parser :date-opt-time :date-parser :date-time-parser
    :local-date-opt-time :local-date :local-time :time-element-parser
    :time-parser})

(defvar- printers
  (set/difference (set (keys formatters)) parsers))

(defn formatter
  "Returns a custom formatter for the given date-time pattern."
  [#^String fmts]
  (.withZone (DateTimeFormat/forPattern fmts) #^DateTimeZone core/utc))

(defn parse
  "Returns a DateTime instance in the UTC time zone obtained by parsing the
   given string according to the given formatter."
  [#^DateTimeFormatter fmt #^String s]
  (.parseDateTime fmt s))

(defn print
  "Returns a string representing the given DateTime instance in UTC and in the
  form determined by the given formatter."
  [#^DateTimeFormatter fmt #^DateTime dt]
  (.print fmt dt))

(defn show-formatters
  "Shows how a given DateTime, or by default the current time, would be
  formatted with each of the available printing formatters."
  ([] (show-formatters (core/now)))
  ([#^DateTime dt]
    (doseq [p printers]
      (let [fmt (formatters p)]
        (printf "%-40s%s\n" p (print fmt dt))))))
