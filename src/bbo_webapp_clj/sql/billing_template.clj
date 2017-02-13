(ns bbo-webapp-clj.sql.billing-template
  (:require [honeysql.helpers :as dsl]))

(defn find-active-templates-query [type site-id lang]
  (cond->
    (-> (dsl/select :bt.*, :s.code)
        (dsl/from [:billing-template :bt])
        (dsl/left-join [:site :s] [:= :s.site-id :bt.site-id])
        (dsl/order-by :bt.type :bt.lang :bt.site-id))
    type (dsl/merge-where [:= :bt.type type])
    site-id (dsl/merge-where [:= :bt.site-id site-id])
    lang (dsl/merge-where [:= :bt.lang lang])))

(defn find-distinct-sites-query []
  (-> (dsl/select :bt.site-id :s.code)
      (dsl/modifiers :distinct)
      (dsl/from [:billing-template :bt])
      (dsl/left-join [:site :s] [:= :s.site-id :bt.site-id])
      (dsl/order-by :bt.site-id)))