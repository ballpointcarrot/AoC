(ns aoc2019.day2-test
  (:require [aoc2019.day2 :as sut]
            [clojure.test :refer [deftest testing is]]))

(deftest get-action
  (testing "when using a known value"
    (is (= + (:action (sut/get-action 1))))
    (is (= * (:action (sut/get-action 2))))
    (is (= "stop" ((:action (sut/get-action 99)))))))

;; to make sure I don't break these as part of my updates for Day 5:
(deftest p2019-02-part1
  (testing "Provided answer still matches"
    (is (= 3850704 (sut/p2019-02-part1 (slurp "inputs/2019-02-input"))))))

(deftest p2019-02-part2
  (testing "Provided answer still matches"
    (is (= 6718 (sut/p2019-02-part2 (slurp "inputs/2019-02-input"))))))

(deftest preprocess-input
  (testing "Converts second and third positions"
    (is (= "0,12,2,3,4,5" (sut/preprocess-input "0,1,99,3,4,5")))))
