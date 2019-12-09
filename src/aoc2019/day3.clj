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
        distance (Integer/parseInt (st/join (rest step)))
        start-from (last current-path)]
    (apply conj current-path (take distance (iterate #(next-step % direction) start-from)))))

(defn p2019-03-part1
  [input]
  (loop [path [[0 0]]
         steps (st/split input #",")]
    (if (empty? steps)
      path
      (recur (walk path (first steps)) (rest steps)))))
