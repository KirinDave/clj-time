(ns clj-time.fuzzy-parser
  (:use clj-time.core
	[clj-time.fuzzy-parser (intervals :only [period-from-str])
	                       (dates     :only [date-from-str])])
  (:import [org.joda.time DateTime Interval]))

(defn- make-date-interval [str now]
  (when-let [date (date-from-str str)]
    (Interval. now date)))

(defn- make-time-interval [str now]
  (when-let [period (period-from-str str)]
    (Interval. now period)))

(defn fuzzy-parse-date [str]
  (if-let [date (date-from-str str)]
    date
    (if-let [period (period-from-str str)]
      (-> (DateTime.) (.plus period))
      nil)))

(defn fuzzy-parse-interval [str]
  (let [now (DateTime.)]
    (or (make-date-interval str now)
	(make-time-interval str now))))








