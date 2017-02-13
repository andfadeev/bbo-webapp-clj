(ns bbo-webapp-clj.sql.util
  (:require [clojure.java.jdbc :as jdbc]
            [honeysql
             [format :as f]
             [helpers :as dsl]]
            [clojure.string :as str]
            [java-time.convert :refer [to-sql-timestamp to-sql-date]])
  (:import (java.sql Timestamp Date Time)
           (java.time LocalDateTime LocalDate)))

(defmethod f/format-clause :returning [[_ fields] _]
  (str "RETURNING " (f/comma-join (map f/to-sql fields))))

(f/register-clause! :returning 225)

(dsl/defhelper returning [m fields]
  (assoc m :returning (dsl/collify fields)))

(defn- format-column-name [col]
  (-> col str/lower-case (str/replace "_" "-")))

(defn fetch [db query]
  (jdbc/query db (f/format query) {:identifiers format-column-name}))

(defn fetch-one [db query]
  (first (fetch db query)))

;; sql date types to java 8 java.time.* converter
(extend-protocol jdbc/IResultSetReadColumn
  Timestamp
  (result-set-read-column [^Timestamp v _2 _3]
    (.toLocalDateTime v))
  Date
  (result-set-read-column [^Date v _2 _3]
    (.toLocalDate v))
  Time
  (result-set-read-column [^Time v _2 _3]
    (.toLocalTime v)))

(extend-protocol jdbc/ISQLValue
  LocalDateTime
  (sql-value [v]
    (to-sql-timestamp v))
  LocalDate
  (sql-value [v]
    (to-sql-date v)))

