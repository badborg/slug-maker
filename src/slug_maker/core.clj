(ns slug-maker.core
  (:require [clojure.string :as s]
            [ring.util.codec :as codec]))

(def default-length 200)

(defn- strip-tags
  [string]
  (.text (org.jsoup.Jsoup/parse string)))

(defn- pre-encode
  [string]
  (-> string
      codec/url-decode
      s/trim
      strip-tags
      (s/replace #"[\p{Z}\p{P}]+" "-")
      (s/replace #"[^\p{L}\p{M}0-9\-]" "")
      (s/replace #"\-$" "")
      (s/replace #"^\-" "")))

(defn encode
  [string & [length]]
  (let [length (or length default-length)]
    (->> (s/split (pre-encode string) #"")
         (take length)
         (map (fn [c]
                (-> c
                    codec/url-encode
                    s/lower-case)))
         (reduce
           (fn [enc-str enc-chr]
             (let [apnd-enc-str (str enc-str enc-chr)]
               (if (-> (count apnd-enc-str)
                       (> length))
                 (reduced enc-str)
                 apnd-enc-str)))
           ""))))

(defn- num-suffixed
  [encoded-slug n length]
  (let [n-len (-> n str count)]
    (-> encoded-slug
        codec/url-decode
        (encode (- length n-len 1))
        (str "-" n))))

(defn- ensure-unique-slug
  [encoded-slug all-slugs length]
  (let [found? (fn [slug]
                 (some #(= slug %) all-slugs))]
    (loop [n 2
           enc-slg encoded-slug]
      (if (found? enc-slg)
        (recur (inc n)
               (num-suffixed enc-slg n length))
        enc-slg))))

(defn encode-unique
  [slug-string all-slugs & [length]]
  (let [length (or length default-length)]
    (-> (encode slug-string length)
        (ensure-unique-slug all-slugs length))))
