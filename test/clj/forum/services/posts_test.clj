(ns forum.services.posts-test
  (:require [clojure.test :refer :all]
            [forum.services.posts :refer :all]))

(deftest get-post-test
  (testing "get-post returns correct set of posts"
    (let [db nil
          data '({:id 1, :title "Clojure thread"}
                 {:id 2, :title "ClojureScript thread"}
                 {:id 3, :title "JavaScript thread"})]
      (with-redefs [all-posts (fn [db] data)
                    post-by-id (fn [db params] (filter #(= (:id %) (:id params)) data))]
        (is (= (get-post db) {:result data})
            "Should return all posts when no id is given")
        (is (= (get-post db :all) {:result data})
            "Should return all post when :all is given as id")
        (is (= (get-post db 2) {:result '({:id 2, :title "ClojureScript thread"})})
            "Should return correct post when id is given and corresponding post exists")
        (is (= (get-post db 4) {:result '()})
            "Should return an empty list when id is given but no corresponding post exists")))))
