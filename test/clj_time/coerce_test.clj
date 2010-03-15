(ns clj-time.coerce-test
  (:use clojure.test
        (clj-time core coerce))
  (:import java.util.Date))

(deftest test-to-long
  (is (= 893462400000 (to-long (date-time 1998 4 25)))))

(deftest test-from-long
  (is (= (date-time 1998 4 25) (from-long 893462400000))))

(deftest test-to-from-date
  (let [dt (from-long 893462400000)
        d  (to-date dt)]
    (is (instance? Date d))
    (is (= dt (from-date d)))))
