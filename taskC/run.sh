#!/usr/bin/env bash

javac TaskC.java
jar cvfe task.jar TaskC *.class
#time java -Dfile.encoding=UTF8 -XX:+HeapDumpOnOutOfMemoryError -Xms1m -Xmx1m -d64 -jar task.jar
#time java -Dfile.encoding=UTF8 -XX:+HeapDumpOnOutOfMemoryError -Xms1m -Xmx1m -d64 -jar task.jar
#time java -Dfile.encoding=UTF8 -XX:+HeapDumpOnOutOfMemoryError -Xms1m -Xmx1m -d64 -jar task.jar
#time java -Dfile.encoding=UTF8 -XX:+HeapDumpOnOutOfMemoryError -Xms1m -Xmx1m -d64 -jar task.jar
/usr/bin/time -f "%Es / %MK" java -Dfile.encoding=UTF8 -d64 -Xmx1024M -Xss1024M -jar task.jar
/usr/bin/time -f "%Es / %MK" java -Dfile.encoding=UTF8 -d64 -Xmx1024M -Xss1024M -jar task.jar
/usr/bin/time -f "%Es / %MK" java -Dfile.encoding=UTF8 -d64 -Xmx1024M -Xss1024M -jar task.jar
/usr/bin/time -f "%Es / %MK" java -Dfile.encoding=UTF8 -d64 -Xmx1024M -Xss1024M -jar task.jar
rm task.jar *.class