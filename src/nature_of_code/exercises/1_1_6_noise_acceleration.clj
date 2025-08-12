(ns nature-of-code.exercises.1-1-6-noise-acceleration
  (:require [quil.core :as q]
            [quil.middleware :as mm])
  (:require [nature-of-code.utils :as u])
  (:require [mikera.vectorz.core :as m]))

(def OFFSET 9)
(defn setup []
  (q/frame-rate 60)
  (q/background 230)
  (q/color-mode :hsb)
  (q/fill 0)
  {:counter 0
   :tx 0
   :ty 100
   :position (m/vec2 100 100)
   :acc (m/vec2 -0.1 0.1)
   :velocity (m/vec2 (q/random -1 1) (q/random -1 1))})

(defn update-state [{:keys [acc position velocity counter tx ty] :as state}]
  (let [[vx vy] (m/to-list velocity)
        [px py] (m/to-list position)
        mouse-n (m/normalise (u/mouse-vec))
        x-off (u/offset OFFSET q/width px)
        y-off (u/offset OFFSET q/height py)
        new-acc (u/limit (m/vec2 (* (q/noise tx) (q/random -2 2)) (* (q/noise ty) (q/random -1 1))) 5)
        new-velocity (m/vec2 (* x-off vx) (* y-off vy))]
    (q/print-every-n-millisec 1000 (u/x new-velocity) "\n-----" (u/y new-velocity))
    {:counter (inc counter)
     :acc (m/add mouse-n new-acc)
     :velocity (u/limit (m/add new-velocity new-acc) 10)
     :position (m/add position new-velocity)
     :tx (+ 0.01 tx)
     :ty (+ 0.01 ty)}))

(defn draw [{:keys [velocity position] :as state}]
  ; (q/print-every-n-millisec 1000 position velocity)
  (q/background 230)
  (q/ellipse (u/x position) (u/y position) 20 20))

(defn key-pressed [state event]
  (case (:key event)
    :s (do (q/start-loop) (assoc state :running true))
    :p (do (q/no-loop) (assoc state :running false))
    :r (setup)
    state))

(q/defsketch noise-acceleration
  :title "1.1.6 Noise Acceleration"
  :setup setup
  :update update-state
  :draw draw
  :key-pressed key-pressed
  :size [500 500]
  :middleware [mm/fun-mode mm/pause-on-error])
