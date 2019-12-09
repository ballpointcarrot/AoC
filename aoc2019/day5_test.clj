(ns aoc2019.day5-test
  (:require  [aoc2019.day2 :as sut]
             [clojure.test :refer [deftest testing is]]))

(deftest opcode3
  (testing "it processes input"
    (with-in-str "32"
      (is (= 32 (sut/calculate "3,0,99"))))))
