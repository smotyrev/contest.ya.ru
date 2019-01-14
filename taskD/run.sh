#!/usr/bin/env bash

javac TaskD.java
jar cvfe task.jar TaskD *.class
/usr/bin/time -f "%Es / %MK" java -Dfile.encoding=UTF8 -d64 -Xmx1024M -Xss1024M -jar task.jar
rm task.jar *.class