(ns aoc2019.day10
  (:require [clojure.string :as st]))

(defn read-map
  [input]
  (let [points (map vec (st/split-lines input))
        asteroid-list (transient [])]
    (doseq
        [[y-pos row] (map-indexed vector points)]
      (doseq
          [[x-pos point] (map-indexed vector row)]
        (if (= point \#)
          (conj! asteroid-list [x-pos y-pos]))))
    (persistent! asteroid-list)))

(defn- slope
  [[x1 y1] [x2 y2]]
  (if (= x1 x2)
    (if (= y1 y2)
      :self
      :vertical)
    (/ (- y2 y1) (- x2 x1))))

(defn- direction
  [[x1 y1] [x2 y2]]
  (if (> x2 x1)
    (if (> y2 y1)
      :dr
      :ur)
    (if (> y2 y1)
      :dl
      :ul)))

(defn slopes-for-point
  [point point-vec]
  (map (fn [p2] {:slope (slope point p2)
                 :direction (direction point p2)}) point-vec))

(defn p2019-10-part1
  [input]
  (let [field (read-map input)
        sights (map #(into #{} (slopes-for-point % field)) field)]
    (apply max (map #(count (disj % {:slope :self :direction :ul})) sights))))
