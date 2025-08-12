(ns nature-of-code.exercises.0-6-random-walker-accept-reject
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

(defn pick-step []
  (let [r1 (q/random 1)
        r2 (q/random -1)
        p (* (* r1 r1))]
    (if (< r2 p) r2 (pick-step))))

(defn update-state [{:keys [x y] :as state}]
  (let [step (* 10 (pick-step))
        choice (q/floor (q/random 4))]
    (condp = choice
      0 {:x (+ x step) :y y}
      1 {:x (- (:x state) step) :y y}
      2 {:x x :y (+ y step)}
      3 {:x x :y (- y step)})))

(defn draw [state]
  (q/with-translation [(/ (q/width) 2)
                       (/ (q/height) 2)]
    (q/ellipse (:x state) (:y state) 5 5)))

(defn key-pressed [state event]
  (case (:key event)
    :s (do (q/start-loop) (assoc state :running true))
    :p (do (q/no-loop) (assoc state :running false))
    :r (setup)
    state))

(q/defsketch random-walker-accept-reject
  :title "0.6 Random Walker Accept Reject"
  :setup setup
  :update update-state
  :draw draw
  :key-pressed key-pressed
  :size [500 500]
  :middleware [mm/fun-mode mm/pause-on-error])
