(ns clj-time.fuzzy-parser.intervals
  (:use [clojure.contrib.def :only (defvar defvar-)])
  (:import (org.joda.time DateTime Period)
           (org.joda.time.format DateTimeFormat DateTimeFormatter
                                 ISODateTimeFormat)))


(defn gen-intregexp [regexp-str]
  (re-pattern (str "([0-9]+) " regexp-str)))

(defvar interval-patterns {
   :second [(gen-intregexp "?(s|secs?|seconds?)") 1]			
   :minute [(gen-intregexp "?(m|mins?|minutes?)") 60]
   :hour   [(gen-intregexp "?(h|hours?|hr)")      3600]			
   :day    [(gen-intregexp "?(d|days?)")          86400]
   :year   [(gen-intregexp "?(y|years?)")         31536000]})


(defn- pat [k] ((k interval-patterns) 0))
(defn- sec [k] ((k interval-patterns) 1))


(defn integer-from-str [str regexp]
  (if-let [matches (re-find regexp str)]
    (Integer/parseInt (matches 1))
    0))

(defn seconds-from-str [str]
  (reduce + 0 (map #(* (or (integer-from-str str (pat %1)) 0) (sec %1)) 
		   (keys interval-patterns))))

(defn period-from-str [str]
  (let [secs (seconds-from-str str)]
    (if (> secs 0)
      (Period. (long (* (seconds-from-str str) 1000)))
      nil)))


