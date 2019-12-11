(ns aoc2019.day7
  (:require [aoc2019.day2 :as d2]
            [clojure.string :as st]))

(defn permutations [s]
  (lazy-seq
   (if (seq (rest s))
     (apply concat (for [x s]
                     (map #(cons x %) (permutations (remove #{x} s)))))
     [s])))

(defn calculate-thrust
  [signal input]
  (-> (reduce (fn [memo phase]
                (with-in-str (str phase "\n" memo)
                  (with-out-str (d2/new-calculate input)))) 0 signal)
      (st/trim-newline)
      (Integer/parseInt)))

(defn p2019-07-part1 [input]
  (let [signals (permutations (range 5))]
    (map #(calculate-thrust % input) signals)))

(defn p2019-07-part2
  "Handle feedback loop for the computers attached to
  the amplifiers."
  [input]
  ;; TODO: Set up the ability to handle I/O as clojure.async Channels.
  )
