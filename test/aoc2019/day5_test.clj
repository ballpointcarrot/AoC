(ns aoc2019.day5-test
  (:require  [aoc2019.day2 :as sut]
             [clojure.test :refer [deftest testing is]]))

(deftest opcode3
  (testing "it processes input"
    (with-in-str "32"
      (is (= 32 (sut/new-calculate "3,0,99"))))))

(deftest opcode4
  (testing "it sends output with direct mode"
    (let [actual (with-out-str "98"
                   (sut/new-calculate "4,3,99,98"))]
      (is (= (clojure.string/trim-newline actual) "98"))))
  (testing "it sends output with immediate mode"
    (let [actual (with-out-str
                   (sut/new-calculate "104,0,99"))]
      (is (= (clojure.string/trim-newline actual) "0")))
    (let [actual (with-out-str
                   (sut/new-calculate "104,98,99"))]
      (is (= (clojure.string/trim-newline actual) "98")))))
