(ns soc-net.func.core
  (:require 
     [vk-api.core :as vk]
     [components.core :as cp]
     [soc-net.func.fmt-date :as fmt-date]
   ))

(defn home []
  (let [wall-response (vk/wall-get :count 3)]
    wall-response))

(home)
