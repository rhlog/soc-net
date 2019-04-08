(ns views.core
  (:require
    [rum.core :as rum]
    [soc-net.time :as t ]
    [markdown.core :as md]
  ))


(defmulti m-cont (fn [item] (get item "type")))

(defmethod m-cont "photo" [media-item]
  (let [{:strs [text date sizes]} (get media-item "photo")
        max-photo (last sizes)
        photo  (nth sizes 4 max-photo)
        {:strs [width height url type]} photo]
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
    [:.post
     [:.post__media-content
      (for [i attachments]
        (m-cont i))]
     [:.post__text text]
     [:.post__footer
      [:span
        [:span "добавлено"]
        [:span (t/from-unix date "dd-MM-yyyy HH:mm")] ]
      [:span
        [:span "добавлено"]
        [:span "Rustam"]]]]
    ))

(rum/defc brand [& {:keys [description photo sitename]}]
  [:a.brand {:title description}
    [:img.brand__logo {:src photo}]
    [:.brand__sitename sitename]
    [:.brand__description [:.span description]]])


(rum/defc menu [ menu-items ]
  [:.menu
    (map #(let [{:keys [text url]} %]
            [:a {:href url} text])
          menu-items)])


(rum/defc pagination [& {:keys [start end hip]}]
  (fn [cur]
    [:.pagi
     (let [
           hip+start (+ hip start)
           cur+start (+ cur (dec start))
           point (cond
                  (<= cur+start hip+start) hip+start
                  (>= cur (- end hip))     (- end hip)
                  :else                    cur+start)
  
           pagi  (range (- point hip) (+ point (inc hip)))
        ]
       (as-> pagi $
         (if (> (first pagi) start)
           (conj $ "..")
           $)
         (if (< (last pagi)  end )
           (concat $ [".."])
           $)
         (concat [1] $ [(inc end)])
         (map #(if (not= % "..")
                 [:a.pagi__item {:href (str "/" %)} %]
                 [:span.pagi__ellipsis ".."]) $)))]))

(def pagi (pagination :start 2 :end 14 :hip 3))


(rum/defc page [& {:keys [lang 
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
      [:link {:rel "stylesheet"
              :href "/public/css/style.css" }]]
     [:body {:class bodyclass}
      content
      [:script {:src "/public/js/bundle.js"}]]])

(defn index [& {:keys [sitename brand menu posts pagination bodyclass]
                :or {bodyclass "page page__index"}}]
    (page :sitename sitename
          :bodyclass bodyclass
          :content [:.container
                     [:.sidebar
                      [:.fix-top
                        brand
                        menu]]
                     [:.content
                        posts
                        pagination]
                     [:.aside]]
))


(defn static [hiccup]
  (rum/render-static-markup hiccup))
