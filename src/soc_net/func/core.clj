(ns soc-net.func.core
  (:require 
     [vk-api.core :as vk]
     [views.core :as rc]
   ))

(def g (nth (get (vk/group) "response") 0))

(defn home []
  (as-> (vk/wall-get :count 10) $
      (get $ "response")
      (rc/index $ g)
      (rc/static $)
    ))

(home)
