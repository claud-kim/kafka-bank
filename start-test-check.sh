#!/bin/bash

java -jar target/check-jar-with-dependencies.jar

# or
diff in_gen.txt out_gen.txt 
