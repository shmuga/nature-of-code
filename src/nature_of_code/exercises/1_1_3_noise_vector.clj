(ns nature-of-code.exercises.1-1-3-noise-vector
  (:require [quil.core :as q]
            [quil.middleware :as mm])
  (:require [nature-of-code.utils :as u])
  (:require [mikera.vectorz.core :as m]))

(defn setup []
  (q/frame-rate 100)
  (q/background 230)
  (q/color-mode :hsb)
  {:counter 0
   :noise (m/vec2 0 (q/random 9800 10000))
   :prev (m/vec2 100 100)
   :position (m/vec2 (/ 2 (q/width)) (/ 2 (q/height)))})

(defn update-state [{:keys [position noise] :as state}]
  (let [[tx ty] (m/to-list noise)
        [x y] (m/to-list position)
        new-x (q/map-range (q/noise tx) 0 1 0 (q/width))
        new-y (q/map-range (q/noise ty) 0 1 0 (q/height))]
    {:position (m/vec2 new-x new-y)
     :prev (m/vec2 x y)
     :noise (m/add noise (m/vec2 0.01 0.01))}))

(defn draw [{:keys [position prev] :as state}]
  (q/line (u/x prev) (u/y prev) (u/x position) (u/y position)))

(defn key-pressed [state event]
  (case (:key event)
    :s (do (q/start-loop) (assoc state :running true))
    :p (do (q/no-loop) (assoc state :running false))
    :r (setup)
    state))

(q/defsketch noise-vector
  :title "1.1.3 Noise Vector"
  :setup setup
  :update update-state
  :draw draw
  :key-pressed key-pressed
  :size [500 500]
  :middleware [mm/fun-mode mm/pause-on-error])
