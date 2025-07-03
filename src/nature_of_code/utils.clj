(ns nature-of-code.utils
  (:require [quil.core :as q])
  (:require [mikera.vectorz.core :as m]))

(defn rand-gaus
  ([] (rand-gaus 0 1))
  ([mean] (rand-gaus mean 1))
  ([mean stdev] (+ mean (* stdev (q/random-gaussian)))))

(defn limit [v max]
  (if (> (m/magnitude v) max)
   (m/mul (m/normalise v) max)
   v))

(defn x [vec]
  (m/mget vec 0))
(defn y [vec]
  (m/mget vec 1))
