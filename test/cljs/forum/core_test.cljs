(ns forum.core-test
  (:require [cljs.test :refer-macros [deftest is testing]]))

(deftest equality-test
  (testing "If this fails, abandon ship"
    (is (= 1 1)
        "1 should be 1")))
