(ns nature-of-code.exercises.0-8-9-noise-clouds
  (:require [quil.core :as q]
            [quil.middleware :as mm])
  (:require [nature-of-code.utils :as u])
  (:require [mikera.vectorz.core :as m]))

(def OCTAVES 7)
(def FALLOFF 0.5)

(defn setup []
  (q/frame-rate 60)
  (q/background 230)
  (q/color-mode :rgb 255)
  (q/fill 0)
  (q/update-pixels)
  (q/noise-seed (q/random 1000))
  (let [img (q/create-image (q/width) (q/height) :rgb)]
    {:counter 0
     :zoff 0
     :image img}))

(defn draw-clouds [zoff img]
  (q/background 255)
  (q/noise-detail OCTAVES FALLOFF)
  (let [width (q/width)
        height (q/height)
        xoffs (take width (iterate #(+ % 0.01) 0.0))]
    (doseq [[x xoff] (map-indexed vector xoffs)]
      (let [yoffs (take height (iterate #(+ % 0.01) 0.0))]
        (doseq [[y yoff] (map-indexed vector yoffs)]
          (let [bright (Math/floor (q/map-range (q/noise xoff yoff zoff) 0 1 0 255))]
            (q/set-pixel img x y (q/color bright))))))
    (q/update-pixels img)
    (q/image img 0 0)))

(defn update-state [state]
  (assoc state :counter (inc (:counter state)) :zoff (+ 0.1 (:zoff state))))

(defn draw [state]
  (draw-clouds (:zoff state) (:image state))
  (let [v (m/vec2 10 10)]
    (q/text (str "Counter: " (:counter state)) (u/x v) (u/y v))))

(defn key-pressed [state event]
  (case (:key event)
    :s (do (q/start-loop) (assoc state :running true))
    :p (do (q/no-loop) (assoc state :running false))
    :r (setup)
    state))

(q/defsketch noise-clouds
  :title "0.8.9 Noise Clouds"
  :setup setup
  :update update-state
  :draw draw
  :key-pressed key-pressed
  :size [500 500]
  :middleware [mm/fun-mode mm/pause-on-error])

