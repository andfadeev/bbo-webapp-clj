(ns bbo-webapp-clj.app
  (:require [compojure.core :refer [wrap-routes]]
            [clojure.pprint]
            [mount.core :as mount]
            [org.httpkit.server :refer [run-server]]
            [bbo-webapp-clj.config :as config]
            [bbo-webapp-clj.routes.app_routes :refer [app-middleware app-routes]]
            [bbo-webapp-clj.utils.runtime :as runtime]))

(defn start-app [{{:keys [host port]} :server}]
  (println "Starting bbo-webapp-clj")
  (-> app-routes
      (wrap-routes app-middleware)
      (run-server {:ip host
                   :port port})))

(mount/defstate app
  :start (start-app config/config)
  :stop (app :timeout 100))

(defn -main [& args]
  (println "Starting system with config:")
  (clojure.pprint/pprint config/config)
  (runtime/add-shutdown-hook ::stop-system #(mount/stop))
  (mount/start))
