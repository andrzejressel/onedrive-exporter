#!/bin/bash

# Start the first process
(sleep 5 && ./application -Dquarkus.http.host=0.0.0.0) &

# Start the second process
(java -cp /work/h2.jar org.h2.tools.Server -ifNotExists -baseDir /data -tcpPort 9123) &

# Wait for any process to exit
wait -n

# Exit with status of process that exited first
exit $?