(ns main
  (:require [aoc2019.day1 :as d1]
            [aoc2019.day2 :as d2]
            [aoc2019.day2-test]
            [aoc2019.day3 :as d3]
            [aoc2019.day4 :as d4]
            [aoc2019.day5-test]
            [aoc2019.intcode-computer-test]
            [clojure.test :refer [run-tests]]))

;; (d1/run)
;; (d2/run)
;; (d3/run)
;; (d4/run)
(run-tests 'aoc2019.day2-test 'aoc2019.day5-test 'aoc2019.intcode-computer-test)