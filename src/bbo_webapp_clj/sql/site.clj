(ns bbo-webapp-clj.sql.site
  (:require [honeysql.helpers :as dsl]))

(defn find-distinct-sites-query []
  (-> (dsl/select :*)
      (dsl/from :site)))