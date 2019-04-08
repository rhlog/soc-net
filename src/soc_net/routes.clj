(ns soc-net.routes
  (:require
            [compojure.route :as route]
            [compojure.core :refer :all]
            [compojure.handler :as cjhandler]
            [soc-net.home :as home]
   ))


(defroutes routes-handler
  (GET "/:n{\\d*}" [n] (home/home (if (= n "") 1 (Integer/parseInt n)) ))
  
  (route/resources "/public" )
  (route/not-found "<h1>Page not found</h1>"))

(def all (cjhandler/site routes-handler))
