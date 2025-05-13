(ns nature-of-code.exercises.0-8-9-noise-clouds
  (:require [quil.core :as q])
  (:require [nature-of-code.utils :as u]))

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
  (let [v (m/vec3 10 10 10)]
    (q/text (str "Counter: " (:counter state)) (:x v) (:y v))))

