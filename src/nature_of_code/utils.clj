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

(defn offset [off measure pos]
  (if (or (>= pos (- (measure) off)) (<= (- pos off) 0)) -1 1))

(defn sign [x]
  (if (>= x 0)
    1 
    -1))

(defn x [vec]
  (m/mget vec 0))
(defn y [vec]
  (m/mget vec 1))

(defn- relative-x []
  (- (q/mouse-x) (/ (q/width) 2)))

(defn- relative-y []
  (- (q/mouse-y) (/ (q/height) 2)))

(defn mouse-vec []
  (m/vec2 (relative-x) (relative-y)))
