(ns bbo-webapp-clj.routes.app_routes
  (:require [bbo-webapp-clj.routes.billing-template-manager :as template-manager-route]
            [bbo-webapp-clj.views.index :as index-view]
            [compojure
             [core :refer :all]
             [coercions :refer [as-int]]
             [route :refer [resources]]]
            [ring.middleware.defaults :as defaults]))

;;todo: add more pages
(defroutes
  app-routes
  (GET "/" [] (index-view/index-page))
  (GET "/templates" [type siteId lang :as r]
    (template-manager-route/template-list-route (as-int type) (as-int siteId) (as-int lang) r))
  (resources "/"))

(defn app-middleware [handler]
  (-> handler
      (defaults/wrap-defaults defaults/site-defaults)))
