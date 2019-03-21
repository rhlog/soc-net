(ns soc-net.core
  (:require [clj-http.client :as client]
            [compojure.route :as route]
            [compojure.core :refer :all]
            [compojure.handler :as cjh]
            [vk-api.core :as vk]
            [immutant.web :as web]
            [rum-components.core :as rumc]
            ))


(defroutes handler-routes
  (GET "/" req
       (rumc/static rumc/index
                    (-> (vk/give
                         "wall.get"
                         :owner_id "-179768713"
                         :filter "all"
                         :extended 1
                         :count 10)
                        (get "response"))))

  (route/resources "/" )
  (route/not-found "<h1>Page not found</h1>"))

(def app (cjh/site handler-routes))

(defn -main [& args]
  (let [args-map (apply hash-map args)
        port-str (or (get args-map "-p")
                     (get args-map "--port")
                     "8080")]
    (web/run #'app {:port (Integer/parseInt port-str)})))

(comment
  (def server (-main "--port" "8000"))
  (web/stop server)
  )
