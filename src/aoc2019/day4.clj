(ns aoc2019.day4
  (:require [clojure.string :as st]))

(def test-input "108457-562041")

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
