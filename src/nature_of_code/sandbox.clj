(ns nature-of-code.sandbox
  (:require [quil.core :as q]
            [quil.middleware :as mm])
  (:require [nature-of-code.utils :as u])
  (:require [mikera.vectorz.core :as v]))

(defn setup []
  (q/frame-rate 1000)
  (q/background 230)
  (q/color-mode :hsb)
  (q/fill 0)
  {:counter 0})


(defn update-state [state]
  {:counter (inc (:counter state))})

(defn draw [state]
 (q/text (str "Counter: " (:counter state)) 100 100))

(defn key-pressed [state event]
  (case (:key event)
    :s (do (q/start-loop) (assoc state :running true))
    :p (do (q/no-loop) (assoc state :running false))
    :r (setup)
    state))

(q/defsketch sandbox
  :title "Sandbox for playing with quil"
  :setup setup
  :update update-state
  :draw draw
  :key-pressed key-pressed
  :size [500 500]
  :middleware [mm/fun-mode mm/pause-on-error])
