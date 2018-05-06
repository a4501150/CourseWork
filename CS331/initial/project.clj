(defproject initial "0.1.0-SNAPSHOT"
  :description "CS 331 Programming Assignment &mdash; Initial Lab"
  :url "http://mccarthy.cs.iit.edu/cs331/assignments/initial"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [io.aviso/pretty "0.1.8"] ]
  :injections  [(require 'io.aviso.repl 
                         'clojure.repl 
                         'clojure.main)
                (alter-var-root #'clojure.main/repl-caught
                                (constantly @#'io.aviso.repl/pretty-pst))
                (alter-var-root #'clojure.repl/pst
                                (constantly @#'io.aviso.repl/pretty-pst))]
  :profiles {:dev {:dependencies [[midje "1.6.3"]]}})
