(ns nature-of-code.exercises.0-7-random-walk-noise-step
  (:require [quil.core :as q])
  (:require [nature-of-code.utils :as u]))

(defn setup []
  (q/frame-rate 100)
  (q/background 230)
  (q/color-mode :hsb)
  {:counter 0
   :st 0
   :tx 0
   :ty 10000
   :prev-x (/ 2 (q/width))
   :prev-y (/ 2 (q/height))
   :x (/ 2 (q/width))
   :y (/ 2 (q/height))})

(defn pick-step []
  (let [r1 (q/random 1)
        r2 (q/random -1)
        p (* (* r1 r1))]
    (if (< r2 p) r2 (pick-step))))

(defn update-state [{:keys [x y st tx ty] :as state}]
  (let [step (* 10 (q/noise st))
        choice (q/floor (q/random 4))
        new-x (q/map-range (q/noise tx) 0 1 0 (q/width))
        new-y (q/map-range (q/noise ty) 0 1 0 (q/height))]
      {:x new-x 
       :y new-y 
       :prev-x x 
       :prev-y y 
       :st (+ 0.01 st)
       :tx (+ 0.01 tx) 
       :ty (+ 0.01 ty)}))

(defn draw [{:keys [x y prev-x prev-y] :as state}]
    (q/line prev-x prev-y x y))
