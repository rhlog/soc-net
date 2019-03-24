(ns soc-net.func.fmt-date
  (:require 
     [clj-time.core :as tcore]
     [clj-time.format :as tformat]
     [clj-time.local :as tlocal]
     [clj-time.coerce :as tcoerce]
   ))


(defn unixtime->date [t]
  (let [t (* t 1000)
        formatter (tformat/formatter "HH:mm dd-MM-yyyy")]
    (->> t 
         (tcoerce/from-long)
         (tformat/unparse formatter)
         )))
