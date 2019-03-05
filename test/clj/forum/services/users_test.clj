(ns forum.services.users-test
  (:require [clojure.test :refer :all]
            [forum.services.users :refer :all]))

(def mock '({:id 1 :email "user1@example.com" :username "User 1"}
            {:id 2 :email "user2@example.com" :username "User 2"}
            {:id 3 :email "user3@example.com" :username "User 3"}))

(deftest get-users-test
  (testing "get-users returns correct set of users"
    (with-redefs [get-users-query (fn [db] mock)]
      (is (= (get-users nil) {:result mock})
          "Should return all users"))))

(deftest get-user-test
  (testing "get-user returns correct user"
    (with-redefs [get-user-query (fn [db params] (filter #(= (:id %) (:id params)) mock))]
      (is (= (get-user nil 2) {:result (list (nth mock 1))})
          "Should return correct user when id is given and corresponding user exists")
      (is (= (get-user nil 99) {:result '()})
          "Should return an empty list when id is given but no corresponding user exists"))))
