FROM clojure:lein-alpine
RUN mkdir -p /app /app/resources
WORKDIR /app
COPY . .
RUN lein fig:min
RUN lein uberjar
CMD java -jar target/kaenkkybbs-0.1.0-SNAPSHOT-standalone.jar
EXPOSE 80
