(ns bbo-webapp-clj.db
  (:require [mount.core :as mount]
            [bbo-webapp-clj.config :as config])
  (:import [com.zaxxer.hikari HikariConfig HikariDataSource]))

(defn- make-config [{{:keys [host port name user password]} :db}]
  (let [cfg (HikariConfig.)
        uri (str "jdbc:postgresql://" host ":" port "/" name)]
    (when uri (.setJdbcUrl cfg uri))
    (when user (.setUsername cfg user))
    (when password (.setPassword cfg password))
    cfg))

(defn start-pool [config]
  {:datasource (HikariDataSource. (make-config config))})

(defn stop-pool [pool]
  (.close (:datasource pool)))

(mount/defstate db
  :start (start-pool config/config)
  :stop (stop-pool db))
