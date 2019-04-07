(ns soc-net.core-test
  (:require [clojure.test :refer :all]
            [soc-net.core :refer :all]))


(defn index [ctx]
  (fn []
    [(:a ctx) (:b ctx)] ))

(def ^:dinamic x 1)
(def ^:dinamic y 1)

(binding [x 2 y 3]
  (+ x y))
