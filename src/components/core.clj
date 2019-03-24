(ns components.core
  (:require
    [rum.core :as rum]
  ))

(def m-item {"type" "photo"
             "text" "fjdkfjdkjfdjfd dfkjddfkj dfkjdfkdj"
             "date" "2019-03-03"
             "sizes" [
                      {"width" 11
                       "height" 12
                       "url" "#"}
                      {"width" 11
                       "height" 12
                       "url" "#"}
                      {"width" 11
                       "height" 12
                       "url" "#"}
                      {"width" 11
                       "height" 12
                       "url" "#"}]})

(defmulti m-cont (fn [item] (get item "type")))


(defmethod m-cont "photo" [media-item]
  (let [{:strs [text date sizes]} media-item
        len (count sizes)
        {:strs [width height url]} (nth sizes
                                        (- (count sizes) 1)
                                        (last sizes))]
     [:img {:alt text
            :src url
            :width width
            :height height
            :sizes sizes}]))

(defmethod m-cont :default [item-media] nil)



(rum/defc post [post-item]
  (let [{:strs [text date medias]} post-item]
    [:.post
     [:.post-media 
       (for [i medias]
        (m-cont i))]
     [:.post-text text]
     [:.post-footer
       [:.post-created date]]]))


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
