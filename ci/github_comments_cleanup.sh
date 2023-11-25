#!/bin/bash

# Variables
repo_owner="$1"
repo_name="$2"
pull_number="$3"
username="$4"
github_token="$5"

# List all comments in the pull request
comments_url="https://api.github.com/repos/$repo_owner/$repo_name/pulls/$pull_number/comments"
echo "Comments URL: $comments_url"

comments=$(curl \
        -L \
        -H "Accept: application/vnd.github+json" \
        -H "Authorization: Bearer $github_token" \
        "$comments_url" | jq -c '.[] | select(.user.login == "'$username'") | .url')

echo "$comments" | while read -r comment_url; do
    # Remove quotes from comment_url
    fixed_comment_url="${comment_url%\"}"
    fixed_comment_url="${comment_url#\"}"

    curl -s -X DELETE \
         -H "Accept: application/vnd.github+json" \
         -H "Authorization: Bearer $github_token" \
         -H "X-GitHub-Api-Version: 2022-11-28" \
         $fixed_comment_url
    sleep 1

    echo "Deleted comment: $fixed_comment_url"
done
