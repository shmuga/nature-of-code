(ns nature-of-code.exercises.0-5-random-gauss-walker
  (:require [quil.core :as q]
            [quil.middleware :as mm])
  (:require [nature-of-code.utils :as u]))

(defn setup []
  (q/frame-rate 100)
  (q/background 230)
  (q/color-mode :hsb)
  {:counter 0
    :x (/ 2 (q/width))
    :y (/ 2 (q/height))})

(defn update-positions [x y] 
  (let [choice (q/floor (q/random 4))
         step-sign-x (q/floor (q/random -1 1))
         step-sign-y (q/floor (q/random -1 1))
         step (u/rand-gaus 0 5)]
    [(+ (* step step-sign-x) x), (+ (* step step-sign-y) y)]))

(defn update-state [state]
  {:counter (inc (:counter state))
   :x (first (update-positions (:x state) (:y state))) 
   :y (second (update-positions (:x state) (:y state)))})

(defn draw [state]
  (q/with-translation [(/ (q/width) 2)
                       (/ (q/height) 2)]
    (q/point (:x state) (:y state))))

(defn key-pressed [state event]
  (case (:key event)
    :s (do (q/start-loop) (assoc state :running true))
    :p (do (q/no-loop) (assoc state :running false))
    :r (setup)
    state))

(q/defsketch random-gauss-walker
  :title "0.5 Random Gauss Walker"
  :setup setup
  :update update-state
  :draw draw
  :key-pressed key-pressed
  :size [500 500]
  :middleware [mm/fun-mode mm/pause-on-error])
