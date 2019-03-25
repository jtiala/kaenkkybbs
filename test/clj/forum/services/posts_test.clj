(ns forum.services.posts-test
  (:require [clojure.test :refer :all]
            [forum.services.posts :refer :all]))

(def mock '({:id 1 :message "Post 1" :thread 1}
            {:id 2 :message "Post 2" :thread 2}
            {:id 3 :message "Post 3" :thread 1}))

(deftest get-posts-test
  (testing "get-posts returns correct set of posts"
    (with-redefs [get-posts-query (fn [db] mock)]
      (is (= (get-posts nil) {:result mock})
          "Should return all posts"))))

(deftest get-post-test
  (testing "get-post returns correct post"
    (with-redefs [get-post-query (fn [db params] (filter #(= (:id %) (:id params)) mock))]
      (is (= (get-post nil 2) {:result (nth mock 1)})
          "Should return correct post when id is given and corresponding post exists")
      (is (= (get-post nil 99) {:result nil})
          "Should return nil when id is given but no corresponding post exists"))))
