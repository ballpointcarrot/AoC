(ns aoc2019.day2
  (:require [clojure.string :as st]
            [aoc2019.intcode-computer :as ic]))

(defn parse-input
  "Converts input into atom holding instruction integers."
  [input]
  (map #(Integer/parseInt %) (st/split (st/trim-newline input) #",")))

(defn new-calculate
  [input]
  (let [instructions (vec (parse-input input))]
    (loop [machine {:instructions instructions
                    :program-counter 0
                    :stop false}]
      (if (:stop machine)
        (first (:instructions machine))
        (recur (->> (ic/fetch machine)
                    (ic/retrieve-args machine)
                    (ic/execute)
                    (ic/apply-changes machine)))))))

(defn preprocess-input
  ([input]
   (preprocess-input input 12 2))
  ([input noun verb]
   (let [parsed-input (vec (parse-input input))]
     (st/join "," (-> parsed-input
                      (assoc 1 noun)
                      (assoc 2 verb))))))

(defn p2019-02-part1
  [input]
  (new-calculate (preprocess-input input)))

(defn p2019-02-part2
  "Using the calculator from part 1, determine the proper inputs for our expected value."
  [input]
  (let [expected-result 19690720
        noun (atom 0)
        verb (atom 0)]
    (while (not= expected-result (new-calculate (preprocess-input input @noun @verb)))
      (swap! verb inc)
      (if (= @verb 100)
        (do
          (swap! noun inc)
          (reset! verb 0))))
    (+ @verb (* 100 @noun))))
