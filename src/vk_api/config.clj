(ns vk-api.config)

(def app
  {:id          "6902171" ;; id приложения
   :service_key "81af66ff81af66ff81af66ffc381c63764881af81af66ffdd2274821894f4216d09c1b4" ;; сервисный ключ доступа
   :secure_key  "5XoAdtfVVZ5aPe6Gcnjv"})

(def default-params
  {:v "5.92" ;; версия api
   :access_token (:service_key app)}) ;; сервисный ключ доступа

(def group
  {:owner_id "179768713"}) ;; id группы
