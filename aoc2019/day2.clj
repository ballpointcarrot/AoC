(ns aoc2019.day2
  (:require [clojure.string :as st]))

(defn fetch-input
  []
  (Integer/parseInt (read-line)))

(defn write-output
  [val]
  (println val)
  val)

(defn less-than
  [p1 p2]
  (if (< p1 p2)
    1
    0))

(defn eql
  [p1 p2]
  (if (= p1 p2)
    1
    0))

(defn jump-if-true
  [p1 p2]
  {:jump true
   :jumppos (if (not= p1 0)
              p2
              nil)})

(defn jump-if-false
  [p1 p2]
  {:jump true
   :jumppos (if (= p1 0)
              p2
              nil)})

(def opcodes
  {1 {:params 2 :result 1 :action +}
   2 {:params 2 :result 1 :action *}
   3 {:params 0 :result 1 :action fetch-input}
   4 {:params 1 :result 0 :action write-output}
   5 {:params 2 :result 0 :action jump-if-true}
   6 {:params 2 :result 0 :action jump-if-false}
   7 {:params 2 :result 1 :action less-than}
   8 {:params 2 :result 1 :action eql}
   99 {:params 0 :result 0 :action (constantly "stop")}})

(defn immediate?
  "Using a segment of the calculated opcode, determines if the current place is
  in Immediate Mode, and returns the rest of the values for other parameters."
  [opcode-segment divisor]
  (let [left (rem opcode-segment divisor)]
    [(if (= left opcode-segment)
       false
       true) left]))

(defn get-action
  "Selects an action from the available opcodes"
  [opcode]
  (let [operation (get opcodes (mod opcode 100))
        [p2 p2rem] (immediate? opcode 1000)
        [p1 _] (immediate? p2rem 100)]
    ;; (println opcode operation p2 p1)
    (condp = (:params operation)
      2 (assoc operation :params [{:immediate p1} {:immediate p2}] :result [(:result operation)])
      1 (assoc operation :params [{:immediate p1}] :result [])
      0 (if (= 1 (:result operation))
          (assoc operation :params [] :result [{:immediate p1}])
          (assoc operation :params [] :result [])))))

(defn parse-input
  "Converts input into atom holding instruction integers."
  [input]
  (map #(Integer/parseInt %) (st/split (st/trim-newline input) #",")))

(defn calculate
  "Read opcodes, insert required values, and read position 0 when complete."
  [input]
  (loop [instructions (vec (parse-input input))
         cursor 0]
    (let [action-opcode (first (drop cursor instructions))
          action (get-action action-opcode)
          params (map (fn [[k v]]
                        (if (:immediate v)
                          k
                          (get instructions k)))
                      (partition 2 (interleave
                                    (take (count (:params action)) (drop (inc cursor) instructions))
                                    (:params action))))
          result-pos (first (drop (+ cursor
                                     (count (:params action))
                                     (count (:result action)))
                                  instructions))
          result (apply (:action action) params)]
      ;; (println (str "Applied " (:action action) " to params " (vec params) " to be placed at " result-pos))
      (if (= result "stop")
        (first instructions)
        (if (:jump result)
          (recur instructions
                 (or (:jumppos result)
                     (inc (+ (count (:params action))
                             cursor))))
          (recur (assoc instructions result-pos result)
                 (inc (+ (count (:params action))
                         (count (:result action))
                         cursor))))))))

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
  (calculate (preprocess-input input)))

(defn p2019-02-part2
  "Using the calculator from part 1, determine the proper inputs for our expected value."
  [input]
  (let [expected-result 19690720
        noun (atom 0)
        verb (atom 0)]
    (while (not= expected-result (calculate (preprocess-input input @noun @verb)))
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