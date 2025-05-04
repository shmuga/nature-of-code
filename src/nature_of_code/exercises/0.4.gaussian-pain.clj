(ns nature-of-code.sandbox
  (:require [quil.core :as q])
  (:require [nature-of-code.utils :as u]))

(def COLOR_BASE 128)
(def COLOR_SPREAD 128)

(def SIZE 10)
(def SIZE_SPREAD 20)

(def SPREAD 100)
(def ALPHA 1000)

(defn setup []
  (q/frame-rate 100)
  (q/background 230)
  (q/fill 0)
  {:counter 0})

(defn update-state [state]
  {:counter (inc (:counter state))})

(defn draw [state]
  (q/fill 0)
  (let [size (u/rand-gaus SIZE SIZE_SPREAD)
        color1 (u/rand-gaus COLOR_BASE COLOR_SPREAD)
        color2 (u/rand-gaus COLOR_BASE COLOR_SPREAD)
        color3 (u/rand-gaus COLOR_BASE COLOR_SPREAD)
        alpha (u/rand-gaus 0 ALPHA)
        x (u/rand-gaus (/ (q/width) 2) SPREAD)
        y (u/rand-gaus (/ (q/height) 2) SPREAD)]
    (q/fill color1 color2 color3 alpha)
    (q/ellipse x y size size)))

