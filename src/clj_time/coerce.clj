(ns clj-time.coerce
  "Utilites to coerce Joda DateTime instances to and from various other types.
   For example, to convert a Joda DateTime to and from a Java long:
   
     => (to-long (date-time 1998 4 25))
     893462400000
     
     => (from-long 893462400000)
     #<DateTime 1998-04-25T00:00:00.000Z>"
  (:use clj-time.core)
  (:import (org.joda.time DateTime DateTimeZone)))

(defn to-long
  "Returns the number of milliseconds that the given DateTime is after Unix
   epoch."
  [#^DateTime dt]
  (.getMillis dt))

(defn from-long
  "Returns a DateTime instance in the UTC time zone corresponding to the given
   number of milliseconds after the Unix epoch."
   [#^Long millis]
   (DateTime. millis #^DateTimeZone utc))
