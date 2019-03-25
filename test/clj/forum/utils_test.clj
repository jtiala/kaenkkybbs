(ns forum.utils-test
  (:require [clojure.test :refer :all]
            [forum.utils :refer :all]))

(deftest format-key-test
  (testing "formats keys correctly"
    (is (= (format-key "correct") :correct))
    (is (= (format-key "correct-key") :correct-key))
    (is (= (format-key "correct_key") :correct-key))
    (is (= (format-key "correct_key_2") :correct-key-2))
    (is (= (format-key :correct_key) :correct-key))))

(deftest format-keys-test
  (testing "formats simple map"
    (is (=
         (format-keys {:a 1 :b-b 2 :c_c 3 :d_d 4})
         {:a 1 :b-b 2 :c-c 3 :d-d 4})))
  (testing "formats keys in nested map"
    (is (=
         (format-keys {:a 1 :b_b 2 :c_c {:d 3 :e_e 4}})
         {:a 1 :b-b 2 :c-c {:d 3 :e-e 4}})))
  (testing "formats keys in list of maps"
    (is (=
         (format-keys [{:a_a 1} {:b_b 2}])
         [{:a-a 1} {:b-b 2}])))
  (testing "formats keys complicated nested structure"
    (is (=
         (format-keys [{:a_a {:b_b [{:c_c 1} {:d_d 2}]}}])
         [{:a-a {:b-b [{:c-c 1} {:d-d 2}]}}]))))

(deftest format-response-test
  (testing "formats response correctly"
    (is (=
         (format-response "a")
         {:result "a"}))
    (is (=
         (format-response true)
         {:result true}))
    (is (=
         (format-response [{:a_a 1}])
         {:result [{:a-a 1}]}))))
