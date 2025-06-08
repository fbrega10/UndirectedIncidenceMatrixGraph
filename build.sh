#!/bin/bash

mvn install:install-file -Dfile=lib/Algoritmi2LabVC-1.0-SNAPSHOT.jar -DpomFile=lib/pom.xml
mvn clean install

