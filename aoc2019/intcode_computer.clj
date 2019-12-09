(ns aoc2019.intcode-computer)

(def operations
  {1 {:params 3, :action (fn [p1 p2 _] {:output (+ p1 p2)})}
   2 {:params 3, :action (fn [p1 p2 _] {:output (* p1 p2)})}
   3 {:params 1, :action (fn [_]
                           {:output (Integer/parseInt (read-line))})}
   4 {:params 1, :action (fn [p1] (println p1) {})}
   5 {:params 2, :action (fn [p1 p2] (if (not= 0 p1) {:jump p2} {}))}
   6 {:params 2, :action (fn [p1 p2] (if (= 0 p1) {:jump p2} {}))}
   7 {:params 3, :action (fn [p1 p2 _] (if (< p1 p2) {:output 1} {:output 0}))}
   8 {:params 3, :action (fn [p1 p2 _] (if (= p1 p2) {:output 1} {:output 0}))}
   99 {:params 0}})

(defn fetch
  "Retrieves the opcode, and the number of arguments for that opcode"
  [machine]
  (let [opcode (get (:instructions machine) (:program-counter machine))
        action (mod opcode 100)]
    {:opcode opcode
     :action action
     :params (:params (get operations action))}))

(defn opcode-to-parameters
  [opcode]
  (let [modes (->> opcode
                   (format "%05d")
                   (into [])
                   (take 3)
                   (reverse))]
    (map #(condp = %
            \0 {:mode :direct}
            \1 {:mode :immediate}) modes)))

(defn retrieve-args
  "Retrieves the parameters of the instruction from the code list, and
  returns a map of their positions, values, and mode"
  [machine fetched-instruction]
  (let [in-params (take (:params fetched-instruction)
                        (drop (inc (:program-counter machine)) (:instructions machine)))
        op-params (opcode-to-parameters (:opcode fetched-instruction))
        joiner (fn [in-param op-param]
                 {:mode (:mode op-param)
                  :param-pos in-param
                  :param-val (get (:instructions machine) in-param)})]
    (assoc fetched-instruction :params
           (map joiner in-params op-params))))

(defn execute
  "Executes the instruction provided, and returns any results of that
  execution."
  [instruction]
  (let [params (map #(condp = (:mode %)
                       :direct (:param-val %)
                       :immediate (:param-pos %)) (:params instruction))
        action (:action (get operations (:action instruction)))]
    (if action
      (assoc instruction :result (apply action params))
      (assoc instruction :result :stop))))

(defn apply-changes
  "Applies any changes based on the result of the instruction. Returns a
  potentially modified instruction list."
  [machine instruction-with-results]
  (let [result (:result instruction-with-results)
        inc-program-counter (fn []
                              (+ 1 (:program-counter machine)
                                 (count (:params instruction-with-results))))]
    (if (:jump result)
      (assoc machine :program-counter (:jump result))
      (if (= result :stop)
        (assoc machine :stop true)
        (if (:output result)
          (let [adjusted-instructions (assoc (:instructions machine)
                                             (:param-pos (last (:params instruction-with-results)))
                                             (:output result))]
            (assoc machine :instructions adjusted-instructions :program-counter (inc-program-counter)))
          (assoc machine :program-counter (inc-program-counter)))))))
