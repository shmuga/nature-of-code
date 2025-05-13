(ns nature-of-code.exercises.1-1-2-example-bouncing-ball
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
   :velocity (m/vec2 (q/random -5 5) (q/random -5 5))})

(defn update-state [{:keys [position velocity counter] :as state}]
  (let [[vx vy] (m/to-list velocity)
        [px py] (m/to-list position)
        x-off (if (or (>= px (- (q/width) OFFSET)) (<= (- px OFFSET) 0)) -1 1)
        y-off (if (or (>= py (- (q/height) OFFSET)) (<= (- py OFFSET) 0)) -1 1)
        new-velocity (m/vec2 (* x-off vx) (* y-off vy))]
     {:counter (inc counter) 
      :velocity new-velocity
      :position (m/add position new-velocity)}))


(defn draw [{:keys [velocity position] :as state}]
  (q/print-every-n-millisec 1000 position velocity)
  (q/background 230)
  (q/ellipse (u/x position) (u/y position) 20 20))

