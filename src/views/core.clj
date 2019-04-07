(ns views.core
  (:require
    [rum.core :as rum]
    [soc-net.func.time :as t ]
    [markdown.core :as md]
  ))


(defmulti m-cont (fn [item] (get item "type")))

(defmethod m-cont "photo" [media-item]
  (let [{:strs [text date sizes]} (get media-item "photo")
        photo  (nth sizes 4 (last sizes))
        {:strs [width height url type]} photo]
     [:img
           {:alt text
            :src url
            :width width
            :height height
            }]))

(defmethod m-cont :default [item-media]
  :?)



(rum/defc post [post-item]
  (let [{:strs [text date attachments]} post-item]
    (if (not (= "" text))
     [:div
       [:div
         (for [i attachments]
          (m-cont i))]
      [:div
       [:div text]
       [:div
         [:div
          [:div "добавлено"]
          [:div (t/from-unix date "dd-MM-yyyy HH:mm")] ]
         [:div
          [:div "добавлено"]
          [:div "Rustam"]]
         ]]]
     nil
    )))

(rum/defc brand [& {:keys [description photo sitename]}]
  [:a {:title description}
    [:img {:src photo}]
    [:h1 sitename]
    [:h2 description]])

(rum/defc menu [ menu-items ]
  [:.menu
    (map #(let [{:keys [text url]} %]
            [:a {:href url} text])
          menu-items)])

(rum/defc pagination [& {:keys [number-page count-posts]}]
  [:.pagination number-page count-posts])


(rum/defc page [& {:keys [style
                          script
                          lang 
                          charset
                          sitename
                          content
                          bodyclass]
                   :or {lang "ru"
                        charset "utf-8"}}]
    [:html {:lang lang}
     [:head
      [:meta {:charset charset}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1, shrink-to-fit=no"}]
      [:title sitename]
      (for [i style]
        [:link {:rel "stylesheet" :href i}])
      ]
     [:body {:class bodyclass}
      content
      (for [i script]
        [:script {:src i}])
      ]])

(defn index [& {:keys [sitename brand menu posts pagination bodyclass]
                :or {bodyclass "page page__index"}}]
    (page :sitename sitename
          :bodyclass bodyclass
          :content [:.container
                    [:.panel
                     brand
                     menu] 
                    [:.list-posts
                       posts
                       pagination]]))


(defn static [hiccup]
  (rum/render-static-markup hiccup))
