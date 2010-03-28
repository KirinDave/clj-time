(ns clj-time.fuzzy-parser.dates
  (:use [clojure.contrib.def :only (defvar defvar-)]
	[clojure.contrib.seq-utils :only (flatten)])
  (:import (org.joda.time DateTime)
           (org.joda.time.format DateTimeFormat DateTimeFormatter
                                 ISODateTimeFormat)))


(defvar- date-format-strs '("MMM dd YYYY"
			    "MM dd YYYY"
			    "MM/dd/YYYY"
			    "MM/dd/YY"
			    "MM/dd"
			    "MM dd"
			    "MMddYY"
			    "YYYYddMM"
			    "MMM dd, YYYY"))

(defvar- time-format-strs '("h:m aa"
			    "aa hh:mm"
			    "h:m:s aa"
			    "k:m"
			    "k:m:s"))


; Feel free to tell me an easier way to do this. ;)
(defvar- date-format-strings 
  (flatten (list date-format-strs time-format-strs
		 (for [d date-format-strs
		       t time-format-strs]
		   (list (str d " " t) (str t " " d))))))

(defvar fuzzy-date-formats (map #(DateTimeFormat/forPattern %1) date-format-strings))

(defn- silent-date-for-format [str format]
  (try
   (.parseDateTime format str)
   (catch Exception e)))

(defn date-from-str 
  "Lazily checks all date parsers for a pattern that matches the string, returns 
   that."
  [str] (first (drop-while nil? (map (partial silent-date-for-format str) fuzzy-date-formats))))