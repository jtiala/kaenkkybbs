(ns forum.transit-util
  (:require [cognitect.transit :as t]))

(def write-options {:handlers
                    #?(:clj  {}
                       :cljs {})})

(def read-options {:handlers
                   #?(:clj {}
                      :cljs {})})

(defn clj->transit [data]
  #?(:clj
     (with-open [out (java.io.ByteArrayOutputStream.)]
       (t/write (t/writer out :json write-options) data)
       (str out))

     :cljs
     (t/write (t/writer :json write-options) data)))

(defn transit->clj [data]
  #?(:clj
     (with-open [in (java.io.ByteArrayInputStream. (.bytes data))]
       (t/read (t/reader in :json read-options)))

     :cljs
     (t/read (t/reader :json read-options) data)))
