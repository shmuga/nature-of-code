(ns nature-of-code.exercises.1-1-5-limited-vec
  (:require [quil.core :as q])
  (:require [nature-of-code.utils :as u])
  (:require [mikera.vectorz.core :as m]))

(def OFFSET 9)
(defn setup []
  (q/frame-rate 60) (q/background 230)
  (q/color-mode :hsb)
  (q/fill 0)
  {:counter 0
   :position (m/vec2 100 100)
   :acc (m/vec2 -0.1 0.1)
   :velocity (m/vec2 (q/random -5 5) (q/random -5 5))})

(defn update-state [{:keys [acc position velocity counter] :as state}]
  (let [[vx vy] (m/to-list (u/limit velocity 10))
        [px py] (m/to-list position)
        x-off (if (or (>= px (- (q/width) OFFSET)) (<= (- px OFFSET) 0)) -1 1)
        y-off (if (or (>= py (- (q/height) OFFSET)) (<= (- py OFFSET) 0)) -1 1)
        new-velocity (m/vec2 (* x-off vx) (* y-off vy))]
    (q/print-every-n-millisec 1000 x-off y-off new-velocity velocity)
    {:counter (inc counter)
     :acc (m/mul acc (m/vec2 x-off y-off))
     :velocity (m/add acc new-velocity)
     :position (m/add position new-velocity)}))

(defn draw [{:keys [velocity position] :as state}]
  ; (q/print-every-n-millisec 1000 position velocity)
  (q/background 230)
  (q/ellipse (u/x position) (u/y position) 20 20))
