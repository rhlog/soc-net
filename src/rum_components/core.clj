(ns rum-components.core
  (:require [rum.core :as rum]
            [vk-api.core :as vk]
            [clj-time.coerce :as t_c]
            [clj-time.core :as t]
            [clj-time.format :as t_f]
            [clj-time.local :as t_l]))

(defmulti media-fn
  (fn [item]
    (get item "type")))


(defmethod media-fn "photo" [ item ]
  (let [resource (get item "photo")
        {:strs [sizes text]} resource
        {:strs [width height url]} (nth sizes 4)]

    [:.media-photo
     {:item sizes}
     [:img
      {:alt    text 
       :width  width
       :height height
       :src url}]] ))


(defmethod media-fn :default [ item ]
  nil)


(rum/defc media [item]
  (media-fn item))

(defn give-time [t_]

  (let [t_ (* t_ 1000)
        custom-formatter (t_f/formatter "HH:mm dd-MM-yyyy")]

    (->> t_
        (t_c/from-long)
        (t_f/unparse custom-formatter )
        )))


(rum/defc post [item]
  (let [{:strs [date text attachments]} item]

    [:.post
     [:.post-header
      [:.post-info
     [:.post-media 
      (for [i attachments]
        (media i))]
       [:.post-text text]
       [:.post-footer
        [:.post-created (give-time date)]]]
        ]]))

(rum/defc index [response]

  (let [{:strs [items groups count]} response 
        {:strs [name photo_200]} (get groups 0)]
    [:html
     [:head
      [:meta {:charset "utf-8"}]
      [:title name]
     [:body.page
      [:.wrap
       [:.header 
        [:img {:src photo_200}]
        [:h1.name name]]
       [:.content
        [:.count count " записи"]
        (for [post-item items]
          (post post-item))]]]]]))

(defn static [component args]
  (rum/render-static-markup (component args))  )
