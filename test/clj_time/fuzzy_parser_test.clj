(ns clj-time.fuzzy-parser
  (:use clojure.test
        clj-time.core
	clj-time.fuzzy-parser
	clj-time.fuzzy-parser.dates
	clj-time.fuzzy-parser.intervals)) 

(deftest test-ifs 
  (is (= (integer-from-str "10m" (pat :minute)) 10))
  (is (= (integer-from-str "10s" (pat :minute)) 0))
  (is (= (integer-from-str "3 hours" (pat :hour)) 3))
  (is (= (integer-from-str "6 d" (pat :day)) 6))
  (is (= (integer-from-str "45 secs" (pat :second)) 45))
  (is (= (integer-from-str "1m 45s" (pat :second)) 45)))


(deftest test-seconds-from-str
  (let [tst seconds-from-str]
    (is (= (tst "1s") 1))
    (is (= (tst "dolly") 0))
    (is (= (tst "1m 1s") 61))
    (is (= (tst "1s 1m") 61))
    (is (= (tst "1d 1h 1m dolly") 90060))
    (is (= (tst "1000 years minutes") 31536000000))))


(deftest test-date-patterns-parse
  (is (nil? (date-from-str "this will never be a date, EVER")))
  (is (nil? (date-from-str "The clock will ring twice on Jan 1, 2001")))
  (is (nil? (date-from-str "2h 3m 30s")))
  (is (= (DateTime. 2001 1 1 0 0 0 0) (date-from-str "20010101")))
  (is (= (DateTime. 2001 1 1 0 0 0 0) 
	 (date-from-str "Jan 1 2001")
	 (date-from-str "Jan 01 2001")
	 (date-from-str "January 01 2001")
	 (date-from-str "1 01 2001")
	 (date-from-str "1/01/2001")))
  (is (= (DateTime. 2010 3 3 3 3 3 0)
	 (date-from-str "Mar 3 2010 3:03:03 AM")
	 (date-from-str "03:03:03 3/3/2010")
	 (date-from-str "03:03:03 AM 3/3/2010")))
  (is (= (DateTime. 2020 2 3 4 0 0 0)
	 (date-from-str "4:00 2/3/2020"))))
