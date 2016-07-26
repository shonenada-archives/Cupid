(defproject cupid "0.1.0-SNAPSHOT"
  :description "Cupid"
  :url "https://github.com/shonenada/Cupid"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.5"]
                 [org.clojure/tools.logging "0.3.0"]
                 [environ "0.5.0"]
                 [ring/ring-core "1.2.2"]
                 [ring/ring-json "0.3.1"]
                 [ring/ring-jetty-adapter "1.2.2"]
                 [compojure "1.0.1"]]
  :main ^:skip-aot cupid.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
