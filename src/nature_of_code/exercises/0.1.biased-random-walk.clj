(ns nature-of-code.sandbox
  (:require [quil.core :as q]))

(defn setup []
  (q/frame-rate 1000)
  (q/background 230)
  (q/color-mode :hsb)
  {:counter 0
    :x (/ 2 (q/width))
    :y (/ 2 (q/height))})

(defn update-positions [x y] 
  (let [choice (q/floor (q/random 4))
         step (q/random -1 1.01)]
    [(+ step x), (+ step y)]))

(defn update-state [state]
  {:counter (inc (:counter state))
   :x (first (update-positions (:x state) (:y state))) 
   :y (second (update-positions (:x state) (:y state)))})

(defn draw [state]
  (q/with-translation [(/ (q/width) 2)
                       (/ (q/height) 2)]
    (q/point (:x state) (:y state))))

