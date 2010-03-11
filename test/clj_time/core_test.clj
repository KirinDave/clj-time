(ns clj-time.core-test
  (:refer-clojure :exclude (second contains?))
  (:use clojure.test
        clj-time.core))

(deftest test-now
  (is (<= 2010 (year (now)))))

(deftest test-epoch
  (let [e (epoch)]
    (is (= 1970 (year e)))
    (is (= 0 (second e)))))

(deftest test-date-time-and-accessors
  (let [d (date-time 1986)]
    (is (= 1986 (year   d)))
    (is (= 1    (month  d)))
    (is (= 1    (day    d)))
    (is (= 0    (hour   d)))
    (is (= 0    (minute d)))
    (is (= 0    (second d))))
  (let [d (date-time 1986 10 14 4 3 2)]
    (is (= 1986 (year   d)))
    (is (= 10   (month  d)))
    (is (= 14   (day    d)))
    (is (= 4    (hour   d)))
    (is (= 3    (minute d)))
    (is (= 2    (second d)))))

(deftest test-time-zone-for-offset
  (is (= utc (time-zone-for-offset 0)))
  (is (= (time-zone-for-offset 10 0) (time-zone-for-offset 10))))

(deftest test-time-zone-for-id
  (is (= utc (time-zone-for-id "UTC"))))

(deftest test-after?
  (is (after? (date-time 1987) (date-time 1986))))

(deftest test-before?
  (is (before? (date-time 1986) (date-time 1987))))

(deftest test-periods
  (is (= (date-time 1986 10 14 4 3 2)
         (plus (date-time 1984)
           (years 2)
           (months 9)
           (days 13)
           (hours 4)
           (minutes 3)
           (seconds 2)))))

(deftest test-plus
  (is (= (date-time 1986 10 14 6)
         (plus (date-time 1986 10 14 4) (hours 2))))
  (is (= (date-time 1986 10 14 6 5)
         (plus (date-time 1986 10 14 4 2) (hours 2) (minutes 3)))))

(deftest test-minus
  (is (= (date-time 1986 10 14 4)
         (minus (date-time 1986 10 14 6) (hours 2))))
  (is (= (date-time 1986 10 14 4 2)
         (minus (date-time 1986 10 14 6 5) (hours 2) (minutes 3)))))

(deftest test-contains?
  (let [d1 (date-time 1985)
        d2 (date-time 1986)
        d3 (date-time 1987)]
    (is (contains? (interval d1 d3) d2))
    (is (not (contains? (interval d1 d2) d3)))
    (is (not (contains? (interval d2 d3) d1)))))

(deftest test-overlaps?
  (let [d1 (date-time 1985)
        d2 (date-time 1986)
        d3 (date-time 1987)
        d4 (date-time 1988)]
    (is (overlaps? (interval d1 d3) (interval d2 d4)))
    (is (overlaps? (interval d1 d3) (interval d2 d3)))
    (is (not (overlaps? (interval d1 d2) (interval d2 d3))))
    (is (not (overlaps? (interval d1 d2) (interval d3 d4))))))
