(ns nature-of-code.exercises.0-3-mouse-biased-walk
  (:require [quil.core :as q]
            [quil.middleware :as mm]))

(defn setup []
  (q/frame-rate 1000)
  (q/background 230)
  (q/color-mode :hsb)
  {:counter 0
   :x (/ 2 (q/width))
   :y (/ 2 (q/height))})

; It's kind of find moving cause accelareting when far and slowing down when close
; But I low key like it this way
(defn bias-norm [x curr-x]
  (+ (- 0.1) (/ (q/norm (- x curr-x) 0 500) 5)))

(defn update-positions [x y]
  (let [choice (q/floor (q/random 4))
        bias [(bias-norm (q/mouse-x) x) (bias-norm (q/mouse-y) y)]
        step (q/random -1 1.01)]
    [(+ step x (first bias)) (+ step y (second bias))]))

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

(q/defsketch mouse-biased-walk
  :title "0.3 Mouse Biased Walk"
  :setup setup
  :update update-state
  :draw draw
  :key-pressed key-pressed
  :size [500 500]
  :middleware [mm/fun-mode mm/pause-on-error])

