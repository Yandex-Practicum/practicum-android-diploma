#!/bin/bash

envs="
hhAccessToken=${GH_HH_ACCESS_TOKEN}
developerEmailForApi=${GH_DEVELOPER_EMAIL}
"

(echo "$envs" | grep -E '.+=.+') >> develop.properties
