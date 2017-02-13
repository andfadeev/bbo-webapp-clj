(ns bbo-webapp-clj.views.template-manager
  (:require [bbo-webapp-clj.views.layout :as l]))

(defn select-option-any []
  [:option {:value ""} "Любой"])

;;todo extract selectboxes to separate functions
;;todo extract table raw to separate function
(defn templates-page [templates
                      {:keys [sites langs template-types]}
                      {:keys [selected-template-type selected-lang selected-site]}]
  (l/base-layout
    [:div.layout__content-wrapper
     [:div.paddings]
     [:div.bloko-columns-wrapper
      [:div.bloko-column.bloko-column_xs-4.bloko-column_s-8.bloko-column_m-12.bloko-column_l-16.bloko-column_container
       [:h1 "Шаблоны"]
       [:div {:style "margin-bottom: 10px;"}
        [:a.bloko-button.bloko-button_primary "Загрузить шаблон"]]
       [:div.bloko-columns-wrapper {:style "background-color: lightblue; padding: 20px 20px 30px 20px;"}
        [:form {:method "get"}
         [:div
          [:div.bloko-column.bloko-column_s-2.bloko-column_m-3.bloko-column_l-4
           [:b "Тип шаблона"]]
          [:div.bloko-column.bloko-column_s-2.bloko-column_m-3.bloko-column_l-4
           [:b "Сайт"]]
          [:div.bloko-column.bloko-column_s-2.bloko-column_m-3.bloko-column_l-4
           [:b "Язык"]]]
         [:div.bloko-column.bloko-column_s-2.bloko-column_m-3.bloko-column_l-4
          [:select.bloko-select {:name "type"}
           (select-option-any)
           (for [{:keys [id title]} template-types]
             [:option {:value id
                       :selected (= id selected-template-type)} title])]]
         [:div.bloko-column.bloko-column_s-2.bloko-column_m-3.bloko-column_l-4
          [:select.bloko-select {:name "siteId"}
           (select-option-any)
           (for [{:keys [site-id code]} sites]
             [:option {:value site-id
                       :selected (= site-id selected-site)} code])]]
         [:div.bloko-column.bloko-column_s-2.bloko-column_m-3.bloko-column_l-4
          [:select.bloko-select {:name "lang"}
           (select-option-any)
           (for [{:keys [id code]} langs]
             [:option {:value id
                       :selected (= id selected-lang)} code])]]
         [:div.bloko-column.bloko-column_s-2.bloko-column_m-3.bloko-column_l-4
          [:button.bloko-button.bloko-button_primary {:type "submit"} "Найти"]
          [:a.bloko-button {:style "margin-left: 5px;" :href "/templates"} "Отмена"]]]]
       [:div.bloko-gap.bloko-gap_top]
       [:div.bloko-columns-wrapper
        [:div.bloko-column.bloko-column_s-8.bloko-column_m-12.bloko-column_l-16
         (if (seq templates)
           [:table.prosper-table
            [:tr
             [:th.prosper-table__head "Тип шаблона"]
             [:th.prosper-table__head "Сайт"]
             [:th.prosper-table__head "Язык"]
             [:th.prosper-table__head "Дата активации"]
             [:th.prosper-table__head "Кол-во версий"]
             [:th.prosper-table__head]]
            (for [{:keys [billing-template-id type code lang creation-time version]} templates]
              [:tr.prosper-table-row
               [:td.prosper-table__cell (:title type)]
               [:td.prosper-table__cell code]
               [:td.prosper-table__cell (:code lang)]
               [:td.prosper-table__cell (l/format-date creation-time)]
               [:td.prosper-table__cell version]
               [:td.prosper-table__cell
                [:ul
                 [:li [:a {:href (str "/template/download/" billing-template-id)} "Скачать"]]
                 [:li [:a {:href (str "/template/versions/" billing-template-id)} "История версий"]]
                 [:li
                  "Предпросмотр: "
                  [:a {:href (str "/template/preview/doc/" billing-template-id)} "DOC"]
                  ", "
                  [:a {:href (str "/template/preview/pdf/" billing-template-id)} "PDF"]]]]])]
           [:div "Нет шаблонов"])]]
       [:div.bloko-gap.bloko-gap_top]
       [:div.footer "© 2016 HeadHunter"]]]]))
