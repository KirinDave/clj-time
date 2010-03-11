(ns clj-time.core
  (:refer-clojure :exclude (second contains?))
  (:import
    (org.joda.time DateTime DateTimeZone Period Interval)))

(defn now
  "Returns a DateTime for the current time in UTC."
  []
  (DateTime. (DateTimeZone/UTC)))

(defn epoch
  "Returns a DateTime corresponding to the begining of the epoch in UTC."
  []
  (DateTime. (long 0) (DateTimeZone/UTC)))

(defn datetime
  ([year]
   (datetime year 1 1 0 0 0))
  ([year month]
   (datetime year month 1 0 0 0))
  ([year month day]
   (datetime year month day 0 0 0))
  ([year month day hour]
   (datetime year month day hour 0 0))
  ([year month day hour minute]
   (datetime year month day hour minute 0))
  ([year month day hour minute second]
   (DateTime. year month day hour minute second 0)))

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

(defn time-zone-for-offset-hours [#^Integer offset]
  (DateTimeZone/forOffsetHours offset))

(defn time-zone-for-id [#^String id]
  (DateTimeZone/forID id))

(defn period [#^DateTime dt-a #^DateTime dt-b]
  (Period. dt-a dt-b))

(defn after? [#^DateTime dt-a #^DateTime dt-b]
  (.isAfter dt-a dt-b))

(defn before? [#^DateTime dt-a #^DateTime dt-b]
  (.isBefore dt-a dt-b))

(defn plus
  ([#^DateTime dt #^Period p]
   (.plus dt p))
  ([dt p & [ps]]
   (reduce #(plus %1 %2) (plus dt p) ps)))

(defn minus
  ([#^DateTime dt #^Period p]
   (.minus dt p))
  ([dt p & [ps]]
   (reduce #(minus %1 %2) (minus dt p) ps)))

(defn years [#^Integer n]
  (Period/years n))

(defn months [#^Integer n]
  (Period/months n))

(defn days [#^Integer n]
  (Period/days n))

(defn hours [#^Integer n]
  (Period/hours n))

(defn minutes [#^Integer n]
  (Period/minutes n))

(defn seconds [#^Integer n]
  (Period/seconds n))

(defn interval [#^DateTime dt-a #^DateTime dt-b]
  (Interval. dt-a dt-b))

(defn contains? [#^Interval i #^DateTime dt]
  (.contains i dt))

(defn overlaps? [#^Interval i-a #^Interval i-b]
  (.overlaps i-a i-b))
