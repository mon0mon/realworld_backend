# run-api-tests.ps1
param (
    [string]$APIURL = "http://localhost:8080",
    [string]$USERNAME = "user2",
    [string]$EMAIL = "user2@example.com",
    [string]$PASSWORD = "123"
)

$SCRIPTDIR = Split-Path -Parent $MyInvocation.MyCommand.Path

npx newman run "$SCRIPTDIR\Conduit.postman_collection.json" `
  --delay-request 500 `
  --global-var "APIURL=$APIURL" `
  --global-var "USERNAME=$USERNAME" `
  --global-var "EMAIL=$EMAIL" `
  --global-var "PASSWORD=$PASSWORD" `
  $args