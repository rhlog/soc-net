(ns soc-net.server
  (:require
     [immutant.web :as web]
     [soc-net.routes :as routes]))



(defn -main [& args]
  (let [args-map (apply hash-map args)
        port-str (or (get args-map "-p")
                     (get args-map "--port")
                     "8080")]
    (web/run #'routes/all {:port (Integer/parseInt port-str)})))

(comment
  (def server (-main "--port" "3000"))
  (web/stop server))
