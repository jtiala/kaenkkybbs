(ns forum.services.users-test
  (:require [clojure.test :refer :all]
            [forum.services.users :refer :all]))

(def users-mock '({:id 1 :email "user1@example.com" :username "User 1"}
                  {:id 2 :email "user2@example.com" :username "User 2"}
                  {:id 3 :email "user3@example.com" :username "User 3"}))

(deftest get-user-test
  (testing "get-user returns correct user"
    (let [db nil
          data users-mock]
      (with-redefs [get-users-query (fn [db] data)
                    get-user-query (fn [db params] (filter #(= (:id %) (:id params)) data))]
        (is (= (get-user db 2) {:result '({:id 2 :email "user2@example.com" :username "User 2"})})
            "Should return correct user when id is given and corresponding user exists")
        (is (= (get-user db 4) {:result '()})
            "Should return an empty list when id is given but no corresponding user exists")))))

(deftest get-users-test
  (testing "get-users returns correct set of users"
    (let [db nil
          data users-mock]
      (with-redefs [get-users-query (fn [db] data)
                    get-user-query (fn [db params] (filter #(= (:id %) (:id params)) data))]
        (is (= (get-users db) {:result data})
            "Should return all users")))))
