# 🍕 KaenkkyBBS

Bulletin board for all kaenkky lovers.

## Overview

Does pineapple belong in pizza? Which kaenkkylae has the best majokaenkkys? Are you willing to pay EUR 5.90 for slizing of the pizza? Discussing these matters and many more have never been as easy as it is now, with KaenkkyBBS - the future of all pizza related discussion.

## Prerequisites

- [Clojure](https://clojure.org/)
- [Leiningen](https://leiningen.org/)
- [Docker](https://www.docker.com/)

## Development

- Copy `config.edn.example` as `config.edn` and edit in your details

      cp config.edn.example config.edn

- Build and start database container

      docker-compose up

- Start backend repl

      lein repl

- Start figwheel

      lein fig:build

- Load all source code and start the component system. In backend repl, run

      (go)

Your app should now be running at [http://localhost:8080](http://localhost:8080). When you modify the source code, just run `(reset)` in the backend repl to reload all source code.

## Building for production

To create a production build run:

    lein clean
    lein fig:min
    lein uberjar

## Running the migrations

Migrations are located in `resources/migrations`. Each migration has two files. The naming convention is `XYZ-name.[up|down].sql`.

Migrations can be ran in the backend repl with

    (migrate)

Migrations can be rolled back with

    (rollback)

## Running the tests

To run backend tests, run

    lein test

Frontend tests are running automatically, you can find a report page at [http://localhost:9500/figwheel-extra-main/auto-testing](http://localhost:9500/figwheel-extra-main/auto-testing). Tests can also be ran in the cli with

    lein fig:test
