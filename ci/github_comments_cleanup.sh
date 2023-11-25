#!/bin/bash

# Initialize variables
repo_owner=""
repo_name=""
pull_number=""
username=""
github_token=""

# Parse named arguments
while [[ $# -gt 0 ]]; do
    key="$1"

    case $key in
        --repo_owner)
        repo_owner="$2"
        shift # past argument
        shift # past value
        ;;

        --repo_name)
        repo_name="$2"
        shift # past argument
        shift # past value
        ;;

        --pull_number)
        pull_number="$2"
        shift # past argument
        shift # past value
        ;;

        --username)
        username="$2"
        shift # past argument
        shift # past value
        ;;

        --github_token)
        github_token="$2"
        shift # past argument
        shift # past value
        ;;

        *)    # unknown option
        shift # past argument
        ;;
    esac
done

# List all comments in the pull request

com=$(curl \
        -L \
        -H "Accept: application/vnd.github+json" \
        -H "Authorization: Bearer $github_token" \
        https://api.github.com/repos/$repo_owner/$repo_name/pulls/$pull_number/comments)

echo "COMM"
echo $com

comments=$(curl \
              	-L \
              	-H "Accept: application/vnd.github+json" \
              	-H "Authorization: Bearer $github_token" \
              	https://api.github.com/repos/$repo_owner/$repo_name/pulls/$pull_number/comments \
              	| jq -c '.[] | select(.user.login == "'$username'") | .url' )
comments_arr=($comments)

for comment_url in "${comments_arr[@]}"
do
    curl -s -X DELETE \
        -H "Authorization: Bearer $github_token" \
        -H "Accept: application/vnd.github.v3+json" \
        $comment_url

    echo "Deleted comment: $comment_url "
done

