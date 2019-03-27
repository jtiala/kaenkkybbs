FROM clojure:lein-alpine
RUN mkdir -p /app /app/resources
WORKDIR /app
COPY . .
COPY config.edn.example config.edn
RUN lein fig:min
RUN lein uberjar
COPY config.edn.aws config.edn
CMD java -jar target/kaenkkybbs-0.1.0-SNAPSHOT-standalone.jar
EXPOSE 80
