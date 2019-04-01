(ns soc-net.routes
  (:require
            [compojure.route :as route]
            [compojure.core :refer :all]
            [compojure.handler :as cjhandler]
            [soc-net.func.core :as fun]
   ))


(defroutes routes-handler
  (GET "/:n{\\d*}" [n] (fun/home (if (= n "") 1 (Integer/parseInt n)) ))
  
  (route/resources "/public" )
  (route/not-found "<h1>Page not found</h1>"))

(def all (cjhandler/site routes-handler))
