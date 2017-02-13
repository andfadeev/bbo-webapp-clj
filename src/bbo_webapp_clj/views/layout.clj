(ns bbo-webapp-clj.views.layout
  (:require [hiccup.page :refer [html5]]
            [java-time.format :as f]))

;;todo: show structural editing (lisp specific)

(defn nav []
  [:div.layout__panel.layout__panel_fixed
   [:div.bloko-columns-wrapper
    [:div.bloko-column.bloko-column_xs-4.bloko-column_s-8.bloko-column_m-12.bloko-column_l-16
     [:ul.top-menu
      [:li.top-menu_item [:a {:href "/"} "Главная"]]
      [:li.top-menu_item [:a {:href "/templates"} "Шаблоны"]]
      ;[:li.top-menu_item [:a {:href "/bills"} "Счета"]]
      ]
     [:ul.top-menu.top-menu__right
      [:li.top-menu_item
       [:form
        {:method "post", :action "/account/logout"}
        [:button.link-plain {:type "submit"} "Выйти"]
        [:input
         {:value "d38268c5116f63ad56c26392878487db"
          :name "_xsrf"
          :type "hidden"}]]]]]]])

(defn base-layout [body]
  (html5
   [:head
    [:meta {:content "text/html; charset=UTF-8" :http-equiv "Content-Type"}]
    [:meta {:content "IE=edge" :http-equiv "X-UA-Compatible"}]
    [:meta {:content "width=device-width, initial-scale=1.0" :name "viewport"}]
    [:link {:href "//i-backoffice.hh.ru/favicon/hh.ico?v=2015_09_25" :rel "icon"}]
    [:title "HeadHunter Backoffice"]
    [:link {:href "//i-backoffice.hh.ru/bloko/css/" :rel "stylesheet"}]
    [:link {:href "//i-backoffice.hh.ru/css/__all_7ea1956ac9d90b9ab6856a62ca1e1eea.css" :rel "stylesheet"}]
    [:script {:src "//i-backoffice.hh.ru/js/min/__42232514aceaf60392783dfb5c15a950.js"}]]
   [:body.backoffice
    [:div.layout
     [:div.layout__col (nav) body]]]))

(defn format-date [date]
  (f/format "dd-MM-yyyy HH:mm:ss" date))