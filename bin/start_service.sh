#!/bin/sh

# Stop running service
/opt/public_mm/bin/skrmedpostctl stop
/opt/public_mm/bin/wsdserverctl stop

# Launch necessary servers
/opt/public_mm/bin/skrmedpostctl start &
while ! nc -z localhost 1795; do sleep 1; done

/opt/public_mm/bin/wsdserverctl start &
while ! nc -z localhost 5554; do sleep 1; done

/opt/public_mm/bin/mmserver18 -V NLM -C -c -z -g &
while ! nc -z localhost 8066; do sleep 1; done

./bin/start_metawrapper.sh
