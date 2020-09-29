#!/bin/bash

echo "Performing a clean Maven build for currency-conversion"
# shellcheck disable=SC2164
cd ./currency-conversion
./mvnw clean package -DskipTests=true

echo "Building Currency conversion"
docker build --tag currency-conversion .

echo "Performing a clean Maven build for currency-exchange-service"
#shellcheck disable=SC2164
cd ../currency-exchange-service
./mvnw clean package -DskipTests=true
#
echo "Building Currency exchange service"
docker build --tag currency-exchange-service .

echo "Performing a clean Maven build for currency-eureka"
# shellcheck disable=SC2164
cd ../currency-eureka
./mvnw clean package -DskipTests=true

echo "Building Currency eureka"
docker build --tag currency-eureka .

echo "Performing a clean Maven build for currency-zuul"
# shellcheck disable=SC2164
cd ../currency-zuul
./mvnw clean package -DskipTests=true

echo "Building Currency zuul"
docker build --tag currency-zuul .
