(defproject bbo-webapp-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha10"]
                 [org.postgresql/postgresql "9.2-1004-jdbc4"]
                 [hiccup "1.0.5"]
                 [ring/ring-anti-forgery "1.0.0"]
                 [ring "1.5.0"]
                 [ring/ring-defaults "0.2.1"]
                 [compojure "1.5.1"]
                 [mount "0.1.11"]
                 [honeysql "0.8.2"]
                 [http-kit "2.2.0"]
                 [clj-time "0.12.2"]
                 [clojure.java-time "0.2.2"]
                 [com.zaxxer/HikariCP "2.5.1"]
                 [org.clojure/java.jdbc "0.7.0-alpha1"]]
  :main bbo-webapp-clj.app
  :profiles {:dev {:source-paths ["dev"]}})
