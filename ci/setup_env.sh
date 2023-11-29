#!/bin/bash

envs="
hhAccessToken=${GH_HH_ACCESS_TOKEN}
"

(echo "$envs" | grep -E '.+=.+') >> develop.properties
