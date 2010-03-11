(ns clj-time.coerce-test
  (:refer-clojure :exclude (second contains?))
  (:use clojure.test
        (clj-time core coerce)))

(deftest test-to-long
  (is (= 893462400000 (to-long (date-time 1998 4 25)))))

(deftest test-from-long
  (is (= (date-time 1998 4 25) (from-long 893462400000))))
