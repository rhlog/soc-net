(ns soc-net.home
  (:require 
     [vk-api.core :as vk]
     [views.core :as rc]
   ))


(defn home
  ([]
   (home 1))

  ([n]
    (let [{count-posts "count" posts "items"} (vk/wall-get :count 10 :offset (* (- n 1) 10))
          {sitename "name" description "description" photo "photo_200"} vk/group
          menu-items [{:text "Главгая"
                       :url "/"}
                      {:text "O нас"
                       :url "#obout"}]
          number-page n]
     (rc/static 
       (rc/index :sitename   sitename
                 :brand      (rc/brand :description description
                                       :photo       photo
                                       :sitename    sitename)
                 :menu       (rc/menu menu-items)
                 :posts      (for [i posts] (rc/post i))
                 :pagination (rc/pagi 3))))))
