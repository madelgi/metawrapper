#!/bin/sh

# Launch necessary servers
/opt/public_mm/bin/skrmedpostctl start &
while ! nc -z localhost 1795; do sleep 1; done

/opt/public_mm/bin/wsdserverctl start &
while ! nc -z localhost 5554; do sleep 1; done

/opt/public_mm/bin/mmserver18 -V NLM -C -c -z -g
