(ns nature-of-code.utils
  (:require [quil.core :as q]))

(defn rand-gaus
  ([] (rand-gaus 0 1))
  ([mean] (rand-gaus mean 1))
  ([mean stdev] (+ mean (* stdev (q/random-gaussian)))))
