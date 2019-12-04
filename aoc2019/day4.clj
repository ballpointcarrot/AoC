(ns aoc2019.day4
  (:require [clojure.string :as st]))

(defn satisfies-criteria?
  "checks for increasing values, with a flag for
  at least one double."
  [num]
  (let [digits (st/split (str num) #"")
        pairs (->> digits
                   (map #(Integer/parseInt %))
                   (partition 2 1))]
    (and (some (fn [[a b]] (= a b)) pairs)
         (every? (fn [[a b]] (>= b a)) pairs))))


(defn p2019-04-part1
  [input]
  (let [[low-bound high-bound] (map #(Integer/parseInt %) (st/split input #"-"))
        all-values (range low-bound high-bound)]
    (->> all-values
         (filter satisfies-criteria?)
         (count))))

(defn explicit-double?
  [num]
  (let [digits (map #(Integer/parseInt %) (st/split (str num) #""))]
    (some (fn [[k v]] (= v 2)) (frequencies digits))))

(defn p2019-04-part2
  [input]
  (let [[low-bound high-bound] (map #(Integer/parseInt %) (st/split input #"-"))
        all-values (range low-bound high-bound)]
    (->> all-values
         (filter satisfies-criteria?)
         (filter explicit-double?)
         (count))))

(defn run
  "Runs the Day 4 solutions."
  []
  (let [input "108457-562041"]
    (println (str "Part 1: " (p2019-04-part1 input)))
    (println (str "Part 2: " (p2019-04-part2 input)))))