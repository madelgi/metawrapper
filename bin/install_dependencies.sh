#!/bin/bash

# Install prologbeans
mvn install:install-file \
  -Dfile=lib/prologbeans.jar \
  -DgroupId=se.sics -DartifactId=prologbeans -Dversion=4.2.1 \
  -Dpackaging=jar

# Install metamap
mvn install:install-file \
    -Dfile=lib/MetaMapApi.jar \
    -DgroupId=gov.nih.nlm.nls -DartifactId=metamap-api -Dversion=2.0 \
    -Dpackaging=jar
