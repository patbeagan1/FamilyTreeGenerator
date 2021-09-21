#!/bin/bash

if (( $# != 1 )); then
    >&2 echo "Illegal number of parameters"
    exit 1
fi

git tag -a v"$1" -m "Release $1"
