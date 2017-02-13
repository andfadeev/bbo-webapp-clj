(ns bbo-webapp-clj.views.index
  (:require [hiccup.page :refer [html5 include-css]]
            [bbo-webapp-clj.views.layout :as layout]))

(defn index-page []
  (layout/base-layout
   [:div.layout__content-wrapper
    [:div.paddings]
    [:div.bloko-columns-wrapper
     [:div.bloko-column.bloko-column_xs-4.bloko-column_s-8.bloko-column_m-12.bloko-column_l-16.bloko-column_container
      [:h1 "Главная страница"]]]]))
