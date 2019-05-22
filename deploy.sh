#!/bin/bash
mvn package -DskipTests
docker-compose up --force-recreate --build