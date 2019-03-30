(ns soc-net.routes
  (:require
            [compojure.route :as route]
            [compojure.core :refer :all]
            [compojure.handler :as cjhandler]
            [soc-net.func.core :as fun]
   ))

(defroutes routes-handler
  (GET "/" req (fun/home))
  
  (route/resources "/" )
  (route/not-found "<h1>Page not found</h1>"))

(def all (cjhandler/site routes-handler))
