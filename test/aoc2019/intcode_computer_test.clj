(ns aoc2019.intcode-computer-test
  (:require [aoc2019.intcode-computer :as sut]
            [clojure.test :refer [deftest testing is]]))

(deftest fetch
  (testing "retrieve a regular opcode"
    (is (= {:opcode 2 :params 3 :action 2}
           (sut/fetch {:instructions [2 18 12 7]
                       :program-counter 0}))))
  (testing "retrieve an immediate opcode"
    (is (= {:opcode 104 :params 1 :action 4}
           (sut/fetch {:instructions [1 12 14 6 104 1]
                       :program-counter 4})))))

(deftest opcode-to-parameters
  (testing "finds immediate values"
    (is (= [{:mode :immediate} {:mode :immediate} {:mode :direct}]
           (sut/opcode-to-parameters 1101)))))

(deftest retrieve-args
  ;; should receive back:
  ;; {:opcode 1
  ;;  :params [{:mode :direct
  ;;            :param-pos 1
  ;;            :param-val 1}
  ;;           {:mode :direct
  ;;            :param-pos 8
  ;;            :param-val 9}
  ;;           {:mode :direct
  ;;            :param-pos 7
  ;;            :param-val 0}]}
  (testing "has the correct number of parameters"
    (is (= 3 (-> (sut/retrieve-args
                  {:instructions [1 1 8 7 4 7 99 0 9]
                   :program-counter 0} {:opcode 1 :params 3})
                 (:params)
                 (count))))
    (is (= 1 (-> (sut/retrieve-args
                  {:instructions [4 0 99]
                   :program-counter 0 } {:opcode 4 :params 1})
                 (:params)
                 (count))))
    (is (= 0 (-> (sut/retrieve-args
                  {:instructions [99]
                   :program-counter 0 } {:opcode 99 :params 0})
                 (:params)
                 (count)))))
  (testing "identifies the mode of the parameters"
    (is (= [:direct :direct :direct]
           (->> (sut/retrieve-args
                 {:instructions [1 1 8 7 4 7 99 0 9]
                  :program-counter 0 } {:opcode 1 :params 3})
                (:params)
                (map :mode))))
    (is (= [:immediate :immediate :direct]
           (->> (sut/retrieve-args
                 {:instructions [1101 1 8 7 4 7 99 0 9]
                  :program-counter 0 } {:opcode 1101 :params 3})
                (:params)
                (map :mode)))))
  (testing "doesn't throw an error in intermediate mode"
    (is (sut/retrieve-args {:instructions [1001 1 -3 8 7 4 7 99 0 9]
                            :program-counter 0 } {:opcode 1 :params 3}))))
