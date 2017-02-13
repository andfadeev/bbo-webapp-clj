(ns bbo-webapp-clj.routes.billing-template-manager
  (:require [bbo-webapp-clj.db :refer [db]]
            [bbo-webapp-clj.sql.billing-template :as template-sql]
            [bbo-webapp-clj.sql.site :as site-sql]
            [bbo-webapp-clj.sql.util :as sql]
            [bbo-webapp-clj.views.template-manager :as template-manager-view]))

(defn find-enum-by-id [enums id]
  (some (fn [enum] (when (= (:id enum) id) enum)) enums))

(defstruct template-type :id :code :title)

(def template-types
  [(struct template-type 0 :bill "Шаблон счета")
   (struct template-type 1 :agreement "Шаблон договора")
   (struct template-type 2 :agreement-hrspace "Шаблон договора HRSpace")
   (struct template-type 3 :bill-ua-without-vat "Шаблон счета без НДС (Украина)")
   (struct template-type 4 :bill-by-individual "Шаблон счета ИП (Белоруссия)")
   (struct template-type 5 :bill-sberbank "Шаблон квитанции Сбербанка")])

(defstruct lang :id :code)

(def langs
  [(struct lang 1 :RU)
   (struct lang 2 :EN)
   (struct lang 3 :AZ)
   (struct lang 4 :KZ)
   (struct lang 5 :UA)])

(defn convert-template [template]
  (-> template
      (update :lang (fn [lang] (find-enum-by-id langs lang)))
      (update :type (fn [type] (find-enum-by-id template-types type)))))

;;todo rewrite to ->> (threading macro) and extract functions
(defn template-list-route [type site-id lang r]
  (let [templates (sql/fetch db (template-sql/find-active-templates-query type site-id lang))
        templates (group-by #(select-keys % [:type :site-id :lang]) templates)
        templates (map (fn [entry]
                         (let [versions (last entry)
                               last-version (last (sort-by :creation-time versions))
                               versions-count (count versions)]
                           (merge last-version {:version versions-count}))) templates)
        templates (map convert-template templates)
        templates (sort-by (fn [t] [(get-in t [:type :id])
                                    (:site-id t)
                                    (get-in t [:lang :id])]) templates)
        _ (clojure.pprint/pprint templates)
        sites (sql/fetch db (template-sql/find-distinct-sites-query))]
    (template-manager-view/templates-page
      templates
      {:template-types template-types
       :sites sites
       :langs langs}
      {:selected-template-type type
       :selected-site site-id
       :selected-lang lang})))

(defn billing-template-versions-route [billing-template-id r]

  )