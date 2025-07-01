(ns nature-of-code.exercises.1-1-4-vector-mult
  (:require [quil.core :as q])
  (:require [nature-of-code.utils :as u])
  (:require [mikera.vectorz.core :as v]))

(defn setup []
  (q/frame-rate 100)
  (q/background 230)
  (q/color-mode :hsb)
  (q/fill 0)
  {:counter 0
   :v1 (v/vec2 0 0)
   :v2 (v/vec2 0 0)})

(defn update-state [state]
  (q/with-translation [(/ (q/width) 2)
                       (/ (q/height) 2)]
    (let [mouse (v/vec2 (q/mouse-x) (q/mouse-y))
          center (v/vec2 (/ (q/width) 2) (/ (q/height) 2))
          v1n (v/clone (v/sub mouse center))
          v2n (v/div v1n 2)]
      {:counter (inc (:counter state))
       :v1 v1n
       :v2 v2n})))

(defn draw [{:keys [v1 v2] :as state}]
  (q/with-translation [(/ (q/width) 2)
                       (/ (q/height) 2)]
    ; (q/background 230)
    (q/text (str "Counter: " (:counter state)) 100 100)
    (q/stroke 0)
    (q/stroke-weight 2)
    (q/line 0 0 (u/x v2) (u/y v2))
    (q/stroke 200)
    (q/line 0 0 (u/x v1) (u/y v1))))

