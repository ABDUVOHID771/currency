#!/bin/bash

echo "Performing a clean Maven build"
./mvnw clean package -DskipTests=true

echo "Building Currency conversion"
docker build --tag currency-conversion .
