#!/bin/bash

if [ $# == 1 ]
then
    (cd sampler; python3 sampler.py) &
    SAMPLER=$2
    (cd webapp; gunicorn -b $1:80 webapp:app) &
    WEBAPP=$2

    wait $SAMPLER $WEBAPP
else
    echo "Usage: ./run.sh ip"
fi
