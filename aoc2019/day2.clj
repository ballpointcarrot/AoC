(ns aoc2019.day2
  (:require [clojure.string :as st]))

(defn get-action
  "Selects an action from the available opcodes"
  [action]
  (condp = action
    1 +
    2 *
    99 (constantly "stop")))

(defn parse-input
  "Converts input into atom holding instruction integers."
  [input]
  (map #(Integer/parseInt %) (st/split (st/trim-newline input) #",")))

(defn initial-adjustments
  [instructions noun verb]
  (-> instructions
      (assoc 1 noun)
      (assoc 2 verb)))

(defn calculate
  "Read opcodes, insert required values, and read position 0 when complete."
  [input noun verb]
  (loop [instructions (initial-adjustments (vec (parse-input input)) noun verb)
         cursor 0]
    (let [[action pos1 pos2 result-pos] (take 4 (drop cursor instructions))
          result (apply (get-action action) [(get instructions pos1) (get instructions pos2)])]
      (if (= result "stop")
        (first instructions)
        (recur (assoc instructions result-pos result) (+ 4 cursor))))))

(defn p2019-02-part1
  [input]
  (calculate input 12 2))

(defn p2019-02-part2
  "Using the calculator from part 1, determine the proper inputs for our expected value."
  [input]
  (let [expected-result 19690720
        noun (atom 0)
        verb (atom 0)]
    (while (not= expected-result (calculate input @noun @verb))
      (swap! verb inc)
      (if (= @verb 100)
        (do
          (swap! noun inc)
          (reset! verb 0))))
    (+ @verb (* 100 @noun))))

(defn run
  "Runs the Day 2 solutions."
  []
  (let [input (slurp "inputs/2019-02-input")]
    (println (str "Part 1: " (p2019-02-part1 input)))
    (println (str "Part 2: " (p2019-02-part2 input)))))