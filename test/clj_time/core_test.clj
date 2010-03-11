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

(deftest test-datetime-and-accessors
  (let [d (datetime 1986)]
    (is (= 1986 (year   d)))
    (is (= 1    (month  d)))
    (is (= 1    (day    d)))
    (is (= 0    (hour   d)))
    (is (= 0    (minute d)))
    (is (= 0    (second d))))
  (let [d (datetime 1986 10 14 4 3 2)]
    (is (= 1986 (year   d)))
    (is (= 10   (month  d)))
    (is (= 14   (day    d)))
    (is (= 4    (hour   d)))
    (is (= 3    (minute d)))
    (is (= 2    (second d)))))

