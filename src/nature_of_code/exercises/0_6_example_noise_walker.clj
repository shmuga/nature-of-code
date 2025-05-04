(ns nature-of-code.exercises.0-6-example-noise-walker
  (:require [quil.core :as q])
  (:require [nature-of-code.utils :as u]))

(defn setup []
  (q/frame-rate 100)
  (q/background 230)
  (q/color-mode :hsb)
  {:counter 0
   :tx 0
   :ty 10000
   :x (/ 2 (q/width))
   :y (/ 2 (q/height))})

(defn pick-step []
  (let [r1 (q/random 1)
        r2 (q/random -1)
        p (* (* r1 r1))]
    (if (< r2 p) r2 (pick-step))))

(defn update-state [{:keys [x y tx ty] :as state}]
  (let [step (* 10 (pick-step))]
    {:x (q/map-range (q/noise tx) 0 1 0 (q/width))
     :y (q/map-range (q/noise ty) 0 1 0 (q/height))
     :tx (+ 0.01 tx)
     :ty (+ 0.01 ty)}))

(defn draw [state]
  (q/ellipse (:x state) (:y state) 5 5))
