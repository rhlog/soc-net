(ns views.core
  (:require
    [rum.core :as rum]
    [soc-net.func.time :as t ]
    [markdown.core :as md]
  ))


(defmulti m-cont (fn [item] (get item "type")))

(defmethod m-cont "photo" [media-item]
  (let [{:strs [text date sizes]} (get media-item "photo")
        {:strs [width height url type]} (nth sizes 4
                                          (last sizes))]
     [:img
           {:alt text
            :src url
            :width width
            :height height
            }]))

(defmethod m-cont :default [item-media]
  nil)



(rum/defc post [post-item]
  (let [{:strs [text date attachments]} post-item]
    (if (and text date attachments)
     [:.post.block-m__item
       [:.post__media
         (for [i attachments]
          (m-cont i))]
      [:.block-p
       [:.post__text.block-p__item text]
       [:.post__footer.list-p__item
         [:.mark.post__mark
          [:.mark__name "добавлено"]
          [:.mark__value (t/from-unix date "dd-MM-yyyy HH:mm")] ]
         [:.mark.post__mark
          [:.mark__name "автор"]
          [:.mark__value "Rustam"]]
         ]]]
     nil
    )))


(rum/defc list-posts [posts len]
    [:.block-m.container
      (for [item posts]
        (post item))
     ])

(rum/defc index [response group]
  (let [{:strs [items count]} response
        {:strs [description name photo_100]} group]
    [:html {:lang "ru"}
     [:head
      [:meta {:charset "utf-8"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1, shrink-to-fit=no"}]
      [:title name]
      [:link {:rel "stylesheet" :href "/css/style.css"}]
      ]
     [:body.page
      [:.page__panel
       [:.page__brand.i
          [:img.i__logo {:src photo_100}]
          [:h1.i__name name]
          [:h2.i__desc description]]
       [:.page__menu
        "menu" ]]
      [:.page__content
        (list-posts items count) ]
      [:.page__aside "aside"] 
      ]]))


(defn static [hiccup]
  (rum/render-static-markup hiccup))
