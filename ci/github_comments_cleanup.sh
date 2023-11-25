#!/bin/bash

# Variables
repo_owner="$1"
repo_name="$2"
pull_number="$3"
username="$4"
github_token="$5"

# List all comments in the pull request
comments_url="https://api.github.com/repos/$repo_owner/$repo_name/pulls/$pull_number/comments"
echo "COMMENT URL: $comments_url"

comments=$(curl \
              	-L \
              	-H "Accept: application/vnd.github+json" \
              	-H "Authorization: Bearer $github_token" \
              	"$comments_url" | jq -c '.[] | select(.user.login == "'$username'") | .url')
echo "COMMENTS URLS"
echo "$comments"
comments_arr=($comments)

for comment_url in "${comments_arr[@]}"
do
    curl -s -X DELETE \
        -H "Authorization: Bearer $github_token" \
        -H "Accept: application/vnd.github.v3+json" \
        $comment_url

    echo "Deleted comment: $comment_url "
done

