(ns clj-time.core
  (:refer-clojure :exclude (second contains?))
  (:import
    (org.joda.time DateTime DateTimeZone Period Interval)))

(def #^{:tag DateTimeZone} utc
  (DateTimeZone/UTC))

(defn now []
  (DateTime. utc))

(defn epoch []
  (DateTime. (long 0) utc))

(defn date-time
  ([year]
   (date-time year 1 1 0 0 0))
  ([year month]
   (date-time year month 1 0 0 0))
  ([year month day]
   (date-time year month day 0 0 0))
  ([year month day hour]
   (date-time year month day hour 0 0))
  ([year month day hour minute]
   (date-time year month day hour minute 0))
  ([#^Integer year #^Integer month #^Integer day #^Integer hour
    #^Integer minute #^Integer second]
   (DateTime. year month day hour minute second 0 utc)))

(defn year [#^DateTime dt]
  (.getYear dt))

(defn month [#^DateTime dt]
  (.getMonthOfYear dt))

(defn day [#^DateTime dt]
  (.getDayOfMonth dt))

(defn hour [#^DateTime dt]
  (.getHourOfDay dt))

(defn minute [#^DateTime dt]
  (.getMinuteOfHour dt))

(defn second [#^DateTime dt]
  (.getSecondOfMinute dt))

(defn time-zone-for-offset
  ([hours]
   (DateTimeZone/forOffsetHours hours))
  ([hours minutes]
   (DateTimeZone/forOffsetHoursMinutes hours minutes)))

(defn time-zone-for-id [#^String id]
  (DateTimeZone/forID id))

(defn to-time-zone
  "Returns a new DateTime corresponding to the same absolute instant in time as
   the given DateTime, but with calendar fields corresponding to the given
   TimeZone."
  [#^DateTime dt #^DateTimeZone tz]
  (.withZone dt tz))

(defn from-time-zone
  "Returns a new DateTime corresponding to the same point in calendar time as
  the given DateTime, but for a correspondingly different absolute instant in
  time."
  [#^DateTime dt #^DateTimeZone tz]
  (.withZoneRetainFields dt tz))

(defn after? [#^DateTime dt-a #^DateTime dt-b]
  (.isAfter dt-a dt-b))

(defn before? [#^DateTime dt-a #^DateTime dt-b]
  (.isBefore dt-a dt-b))

(defn years [#^Integer n]
  (Period/years n))

(defn months [#^Integer n]
  (Period/months n))

(defn weeks [#^Integer n]
  (Period/weeks n))

(defn days [#^Integer n]
  (Period/days n))

(defn hours [#^Integer n]
  (Period/hours n))

(defn minutes [#^Integer n]
  (Period/minutes n))

(defn seconds [#^Integer n]
  (Period/seconds n))

(defn plus
  ([#^DateTime dt #^Period p]
   (.plus dt p))
  ([dt p & ps]
   (reduce #(plus %1 %2) (plus dt p) ps)))

(defn minus
  ([#^DateTime dt #^Period p]
   (.minus dt p))
  ([dt p & ps]
   (reduce #(minus %1 %2) (minus dt p) ps)))

(defn period [#^DateTime dt-a #^DateTime dt-b]
  (Period. dt-a dt-b))

(defn in-weeks [#^Period p]
  (.. p toStandardWeeks getWeeks))

(defn in-days [#^Period p]
  (.. p toStandardDays getDays))

(defn in-hours [#^Period p]
  (.. p toStandardHours getHours))

(defn in-minutes [#^Period p]
  (.. p toStandardMinutes getMinutes))

(defn in-seconds [#^Period p]
  (.. p toStandardSeconds getSeconds))
(defn interval [#^DateTime dt-a #^DateTime dt-b]
  (Interval. dt-a dt-b))

(defn contains? [#^Interval i #^DateTime dt]
  (.contains i dt))

(defn overlaps? [#^Interval i-a #^Interval i-b]
  (.overlaps i-a i-b))

(defn abuts? [#^Interval i-a #^Interval i-b]
  (.abuts i-a i-b))
