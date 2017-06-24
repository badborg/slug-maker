(ns slug-maker.core-test
  (:require [clojure.test :refer :all]
            [slug-maker.core :refer :all]
            [ring.util.codec :as codec]))

(def short-string
  "आपके")

(def ascii
  "?Lorem ipsum 1% dolor  - sit_amet, everti <div>mentitum</div> .")

(def long-string
  "奈際同猪可食海婚通疲基右交高保。議力属角克善港禁射任者栖約作左民止者抜。特各載久六碁出情画賃題何政稿代。顕禁心長田代脳能料放上一験関止最甲制型亡。注点芸特問体更今麺考崎戻銀止関府本金速手。晴位対展的用居質願核指変者。休全各禁覆追充度星世退間達作費征銃本。世収暮毎変根予絵結町経応止通給。瞬雄文稿掲事添供成敗保器解長。")

(def all-slugs
  ["%e0%a4%86%e0%a4%aa%e0%a4%95%e0%a5%87"
   "%e0%a4%86%e0%a4%aa%e0%a4%95%e0%a5%87-2"
   "%e5%a5%88%e9%9a%9b%e5%90%8c%e7%8c%aa%e5%8f%af%e9%a3%9f%e6%b5%b7%e5%a9%9a%e9%80%9a%e7%96%b2%e5%9f%ba%e5%8f%b3%e4%ba%a4%e9%ab%98%e4%bf%9d%e3%80%82%e8%ad%b0%e5%8a%9b%e5%b1%9e%e8%a7%92%e5%85%8b%e5%96%84"])

(deftest round-trip
  (testing "Url encode into slug and decode"
    (is (= short-string
           (-> short-string
               encode
               codec/url-decode)))))

(deftest encode-long-string
  (testing "Url encode long string into 200 character length slug"
    (is (>= 200
           (-> long-string
               encode
               count)))))

(deftest encode-ascii
  (testing "Url encode ascii string"
    (is (= "lorem-ipsum-1-dolor-sit-amet-everti-mentitum"
           (-> ascii
               encode)))))

(deftest unique-slug
  (testing "If slug is not unique, add suffix"
    (let [slug (encode-unique short-string
                              all-slugs)]
      (is (= nil
             (some #(= % slug) all-slugs)))
      (is (= "%e0%a4%86%e0%a4%aa%e0%a4%95%e0%a5%87-3"
             slug)))))
