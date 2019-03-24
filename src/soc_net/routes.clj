(ns soc-net.routes
  (:require
            [compojure.route :as route]
            [compojure.core :refer :all]
            [compojure.handler :as cjhandler]
            [soc-net.functions :as fun]
   ))

(defroutes routes-handler
  (GET "/" req (fns/home))
  
  (route/resources "/" )
  (route/not-found "<h1>Page not found</h1>"))

(def routes (cjhandler routes-handler))
