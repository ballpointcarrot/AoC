(ns aoc2019.day3
  (:require [clojure.string :as st]
            [clojure.set :refer [intersection]]))

(defn next-step
  [last-pos direction]
  (let [[x y] last-pos]
    (condp = direction
      \U [x (inc y)]
      \D [x (dec y)]
      \R [(inc x) y]
      \L [(dec x) y])))

(defn walk
  "Provide the list of coords passed when moving a direction"
  [current-path step]
  (let [direction (first step)
        distance (inc (Integer/parseInt (st/join (rest step))))
        start-from (last current-path)]
    (apply conj current-path (rest (take distance (iterate #(next-step % direction) start-from))))))

(defn all-wire-coords
  [steps]
  (loop [path [[0 0]]
         steps steps]
    (if (empty? steps)
      (rest path) ; remove initial [0 0] coordinate
      (recur (walk path (first steps)) (rest steps)))))

(defn p2019-03-part1
  [wires crosses]
    (->> crosses
         (map (fn [[x y]] (+ (Math/abs x) (Math/abs y))))
         (apply min)))

(defn walk-distance
  [wires coord]
  (let [first-wire (first wires)
        second-wire (last wires)]
    (+ (inc (.indexOf first-wire coord))
       (inc (.indexOf second-wire coord)))))

(defn p2019-03-part2
  [wires crosses]
    (->> crosses
         (map (fn [coord] (walk-distance wires coord)))
         (apply min)))

(defn run
  "Runs the Day 3 solutions."
  []
  (let [input (slurp "inputs/2019-03-input")
        lines (st/split-lines input)
        wires (map #(all-wire-coords (st/split % #",")) lines)
        crosses (apply intersection (map #(into #{} %) wires))]
    (println (str "Part 1: " (p2019-03-part1 wires crosses)))
    (println (str "Part 2: " (p2019-03-part2 wires crosses)))))