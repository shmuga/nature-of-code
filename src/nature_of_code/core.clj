(ns nature-of-code.core
  (:require [quil.core :as q]
            [quil.middleware :as m])
  (:require [nature-of-code.sandbox :as sandbox]))

(defn key-pressed [state event]
  ; Start/stop the loop by pressing a key
  (case (:key event)
    :s (do (q/start-loop) (assoc state :running true))
    :p (do (q/no-loop) (assoc state :running false))
    :r (sandbox/setup)
    state))

(q/defsketch sandbox
  :title "Sandbox for playing with quil"
  :setup sandbox/setup
  :update sandbox/update-state
  :draw sandbox/draw
  :key-pressed key-pressed
  :size [500 500]
  :middleware [m/fun-mode])

