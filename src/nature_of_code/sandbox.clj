(ns nature-of-code.sandbox
  (:require [quil.core :as q]))

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

