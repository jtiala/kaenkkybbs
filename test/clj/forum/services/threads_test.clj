(ns forum.services.threads-test
  (:require [clojure.test :refer :all]
            [forum.services.threads :refer :all]))

(def mock '({:id 1 :title "Thread 1"}
            {:id 2 :title "Thread 2"}
            {:id 3 :title "Thread 3"}))

(deftest get-threads-test
  (testing "get-threads returns correct set of threads"
    (with-redefs [get-threads-query (fn [db] mock)]
      (is (= (get-threads nil) {:result mock})
          "Should return all threads"))))

(deftest get-thread-test
  (testing "get-thread returns correct thread"
    (with-redefs [get-thread-query (fn [db params] (filter #(= (:id %) (:id params)) mock))]
      (is (= (get-thread nil 2) {:result (list (nth mock 1))})
          "Should return correct thread when id is given and corresponding thread exists")
      (is (= (get-thread nil 99) {:result '()})
          "Should return an empty list when id is given but no corresponding thread exists"))))
