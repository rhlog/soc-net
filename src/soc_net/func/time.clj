(ns soc-net.func.time
  (:require 
     [clj-time.core :as tcore]
     [clj-time.format :as tformat]
     [clj-time.local :as tlocal]
     [clj-time.coerce :as tcoerce]
   ))


(defn from-unix [t format-str]
  (let [t         (* t 1000)
        formatter (tformat/formatter format-str)]
    (->> t 
         (tcoerce/from-long)
         (tformat/unparse formatter)
         )))
