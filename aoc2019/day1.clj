(ns aoc2019.day1
  (:require [clojure.string :as st]))

(defn fuel-cost
  [value]
  (- (Math/floor (/ value 3)) 2))

(defn adj-fuel-cost
  [value]
  (let [fuel (fuel-cost value)]
    (if (< fuel 0)
      0
      fuel)))

(defn p2019-01
  "Calculate Fuel requirements"
  [input]
  (apply +
         (map #(fuel-cost (Integer/parseInt %)) (st/split-lines input))))

(defn recursive-fuel-cost
  [value]
  (loop [remains value
         total 0]
    (if (= remains 0)
      total
      (let [fuel (adj-fuel-cost remains)]
        (recur fuel (+ total fuel))))))

(defn p2019-01-part2
  "Calculate fuel requirements, including weight of fuel"
  [input]
  (apply +
         (map #(recursive-fuel-cost (Integer/parseInt %)) (st/split-lines input))))

(defn run
  "Runs the Day 1 solutions."
  []
  (let [input (slurp "inputs/2019-01-input")]
    (println (str "Part 1: " (p2019-01 input)))
    (println (str "Part 2: " (p2019-01-part2 input)))))