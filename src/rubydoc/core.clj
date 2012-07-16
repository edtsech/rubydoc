(ns rubydoc.core
  (:require [clj-yaml.core :as yaml]
            [clojure.java.io :as io]))

(declare rows)

(defn rubydoc [str-or-regex]
  (let [matches? (if (instance? java.util.regex.Pattern str-or-regex)
                   #(re-find str-or-regex (str %))
                   #(.contains (str %) (str str-or-regex)))]
    (filter #(matches? (:ruby %)) @rows)))

(def ^:private rows
  (let [dir (-> (ClassLoader/getSystemResource *file*) io/file .getParent)]
    (delay
      (->> (slurp (str dir "/db.yml")) yaml/parse-string))))
