(ns build
  (:require
   [babashka.fs :as fs]
   [clojure.java.io :as io]
   [clojure.tools.build.api :as b]))

(def class-dir "target/classes")
(def basis {:project "deps.edn"})
(def file "target/asm4clj-standalone.jar")

(defn clean [_]
  (b/delete {:path "target"}))

(defn jar [opts]
  (clean opts)
  (println "Building uberjar...")
  (let [basis (b/create-basis (update basis :aliases concat (:extra-aliases opts)))
        src-dirs (into ["src" "resources"] (:extra-dirs opts))]
    (b/copy-dir {:src-dirs src-dirs
                 :target-dir class-dir})
    (b/compile-clj {:basis basis
                    :src-dirs src-dirs
                    :class-dir class-dir})
    (b/uber {:class-dir class-dir
             :uber-file file
             :main 'asm4clj.main
             :basis basis})))

(defn native-cli [opts]
  (println "Building native image...")
  (if-let [graal-home (System/getenv "GRAALVM_HOME")]
    (let [jar (do (jar (merge opts {:extra-aliases [:native]}))
                  file)
          native-image (if (fs/windows?) "native-image.cmd" "native-image")
          command (->> [(str (io/file graal-home "bin" native-image))
                        "-jar" jar
                        "asm4clj"
                        "-H:+ReportExceptionStackTraces"
                        "--verbose"
                        "--no-fallback"
                        "--native-image-info"
                        "-J-Xmx8g"
                        (when (= "true" (System/getenv "ASM4CLJ_STATIC"))
                          ["--static"
                           (if (= "true" (System/getenv "ASM4CLJ_MUSL"))
                             ["--libc=musl" "-H:CCompilerOption=-Wl,-z,stack-size=2097152"]
                             ["-H:+StaticExecutableWithDynamicLibC"])])]
                       (flatten)
                       (remove nil?))
          {:keys [exit]} (b/process {:command-args command})]
      (when-not (= 0 exit)
        (System/exit exit)))
    (println "Set GRAALVM_HOME env")))
