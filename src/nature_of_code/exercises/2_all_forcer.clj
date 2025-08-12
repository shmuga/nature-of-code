(ns nature-of-code.exercises.2-all-forcer
  (:require [quil.core :as q]
            [quil.middleware :as mm]
            [mikera.vectorz.core :as m])
  (:require [nature-of-code.utils :as u])
  (:require [mikera.vectorz.core :as v]))

(def G (* 6.67428 (q/pow 10 -11)))

(defprotocol Renderable
  (update [this])
  (draw [this])
  (apply-constraints [this])
  (apply-force [this force])
  (attract [this body]))

(defrecord Mover [weight velocity acceleration position]
  Renderable
  (draw [this]
    (q/stroke 1)
    (q/fill 0)
    (q/ellipse (u/x position) (u/y position) (* 5 weight) (* 5 weight)))

  (attract [this mover]
    (let [force (m/sub (:position this) (:position mover))
          d (q/constrain (m/magnitude force) 10 25)
          msq (* (:weight this) (:weight mover))
          strength (/ (* 1 msq) (* d d))
          force' (m/mul (m/normalise force) strength)]
      force'))

  (update [this]
    (-> this
        (assoc :velocity (v/add velocity acceleration)
               :position (v/add position velocity)
               :acceleration (v/mul acceleration 0))
        (apply-constraints)))

  (apply-constraints [this]
    (let [[vx vy] (m/to-list velocity)
          [px py] (m/to-list position)
          OFFSET (* 2.5 weight)
          B 1
          B- (* -1 B)
          x-off (if (or (>= px (- (q/width) OFFSET)) (<= (- px OFFSET) 0)) B- B)
          y-off (if (or (>= py (- (q/height) OFFSET)) (<= (- py OFFSET) 0)) B- B)
          new-velocity (m/vec2 (* x-off vx) (* y-off vy))]
      (assoc this :velocity new-velocity)))

  (apply-force [this force]
    (let [force' (m/div force (:weight this))]
      (assoc this :acceleration (v/add acceleration force')))))

(defn make-mover
  ([velocity acceleration position]
   (->Mover 10 velocity acceleration position))
  ([weight velocity acceleration position]
   (->Mover weight velocity acceleration position)))

(defn check-x [liquid mover]
  (and 
    (> (u/x (:position mover)) (u/x (:position liquid)))
    (< (u/x (:position mover)) (+ (u/x (:dimensions liquid)) (u/x (:position liquid))))))

(defn check-y [liquid mover]
  (and 
    (> (u/y (:position mover)) (u/y (:position liquid)))
    (< (u/y (:position mover)) (+ (u/y (:dimensions liquid)) (u/y (:position liquid))))))

(defn calc-drag [mover]
  (let [c 0.1
        speed (m/magnitude (:velocity mover))]
   (m/scale (m/mul (:velocity mover) -1) (* c speed))))
  

(defprotocol Force
  (draw [this])
  (make-force [this r]))

(defrecord Liquid [position dimensions c]
  Force
  (draw [this]
    (q/no-stroke)
    (q/fill 175)
    (q/rect (u/x position) (u/y position) (u/x dimensions) (u/y dimensions)))

  (make-force [this mover]
    (let [is-x (check-x this mover)
          is-y (check-y this mover)]
      (if (and is-x is-y)
        (calc-drag mover)
        (m/vec2 0 0)))))

(defn make-liquid
  ([position dimensions]
   (->Liquid position dimensions 0.1))
  ([position dimensions c]
   (->Liquid position dimensions c)))

(defn setup []
  (q/frame-rate 120)
  (q/background 230)
  (q/color-mode :hsb)
  (q/fill 0)
  {:counter 0
   :l1 (make-liquid (m/vec2 100 300) (m/vec2 450 150))
   :mov2 (make-mover
          5
          (m/vec2 0.5 0.5)
          (m/vec2 0 0)
          (m/vec2 154 200))
   :mov1 (make-mover
          (m/vec2 0.5 0.5)
          (m/vec2 0 0)
          (m/vec2 354 100))})

(defn make-wind []
  (if (q/mouse-pressed?)
    (let [center (m/vec2 (/ (q/width) 2) (/ (q/height) 2))
          mouse-pos (m/vec2 (q/mouse-x) (q/mouse-y))
          relative-pos (m/sub mouse-pos center)]
      (m/div relative-pos center))
    (m/vec2 0 0)))

(defn make-gravity [mover]
  (m/mul (m/vec2 0 0.1) (:weight mover)))

(defn make-friction [mover]
  (let [c 0.2
        N 1
        R (* 5 (:weight mover))
        edge (- (q/height) R 1)
        is-touch-edge (>= (u/y (:position mover)) edge)
        mag (* c N)]
    (if is-touch-edge
      (-> (:velocity mover)
          (m/normalise)
          (m/mul -1)
          (m/mul mag))
      (m/vec2 0 0))))

(defn update-state [state]
  (let [l1 (:l1 state)
        wind (make-wind)]
    {:counter (inc (:counter state))
     :l1 l1
     :mov1 (as-> (:mov1 state) mover
             (apply-force mover (make-gravity mover))
             (apply-force mover (make-friction mover))
             (apply-force mover (make-force l1 mover))
             (apply-force mover wind)
             (apply-force mover (attract (:mov2 state) mover))
             (update mover))
     :mov2 (as-> (:mov2 state) mover
             (apply-force mover (make-gravity mover))
             (apply-force mover (make-friction mover))
             (apply-force mover (make-force l1 mover))
             (apply-force mover wind)
             (apply-force mover (attract (:mov1 state) mover))
             (update mover))}))

(defn draw [state]
  (q/background 230)
  (.draw (:l1 state))
  (.draw (:mov1 state))
  (.draw (:mov2 state)))


(defn key-pressed [state event]
  ; Start/stop the loop by pressing a key
  (case (:key event)
    :s (do (q/start-loop) (assoc state :running true))
    :p (do (q/no-loop) (assoc state :running false))
    :r (setup)
    state))

(q/defsketch whatever
  :title "Sandbox for playing with quil"
  :setup setup
  :update update-state
  :draw draw
  :key-pressed key-pressed
  :size [500 500]
  :middleware [mm/fun-mode mm/pause-on-error])
