(ns asm4clj.main
  (:require
   [clojure.java.io :as io]
   [clojure.pprint :as pprint]
   [clojure.reflect :as reflect])
  (:import
   [clojure.reflect ClassResolver])
  (:gen-class))

(deftype URIClassResolver []
  ClassResolver
  (resolve-class [_ uri]
    (io/input-stream uri)))

(extend-protocol reflect/TypeReference
  java.lang.String
  (typename [o] (str o)))

(defn parse-class-name [^String class-name]
  (reflect/reflect (Class/forName class-name)))

(defn parse-class-uri [^String uri]
  (reflect/type-reflect uri :reflector (reflect/->AsmReflector (URIClassResolver.))))

(defn -main [& args]
  (pprint/pprint (parse-class-uri (first args))))
