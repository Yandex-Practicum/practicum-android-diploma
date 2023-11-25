#!/bin/bash

# Initialize variables
repoOwner=""
repoName=""
prNumber=""
user_to_delete=""
token=""

# Parse named arguments
while [[ $# -gt 0 ]]; do
    key="$1"

    case $key in
        --repoOwner)
        repoOwner="$2"
        shift # past argument
        shift # past value
        ;;

        --repoName)
        repoName="$2"
        shift # past argument
        shift # past value
        ;;

        --prNumber)
        prNumber="$2"
        shift # past argument
        shift # past value
        ;;

        --username)
        user_to_delete="$2"
        shift # past argument
        shift # past value
        ;;

        --token)
        token="$2"
        shift # past argument
        shift # past value
        ;;

        *)    # unknown option
        shift # past argument
        ;;
    esac
done

# List all comments in the pull request
comments=$(curl -s -H "Authorization: token $access_token" \
                 -H "Accept: application/vnd.github.v3+json" \
                 "https://api.github.com/repos/$repo_owner/$repo_name/pulls/$pull_number/comments")

# Loop through comments and delete those made by the specified user
echo "$comments" | jq -c '.[] | select(.user.login == "'$user_to_delete'") | .url' | while read -r comment_url; do
    curl -s -X DELETE \
         -H "Authorization: token $access_token" \
         -H "Accept: application/vnd.github.v3+json" \
         "$comment_url"
    echo "Deleted comment: $comment_url"
done
