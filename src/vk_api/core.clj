(ns vk-api.core
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [vk-api.config :as config]))

(defn- map2params [m]
  (str "?"
       (clojure.string/join "&"
                            (for [[k v] m]
                              (str (name k) "=" v)))))

(defn- give [method & {:as params}]
  (let [params (merge config/default-params params)]
    (as-> (str "https://api.vk.com/method/"
               method
               (map2params params)) $
      (client/get $)
      (json/read-str (:body $)))))
