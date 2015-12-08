# Slack Snap Bot

a slack slash command for snap-ci

POC stage right now.

I'm looking to support commands like

    /snapci login heldeen:apiKey
    /snapci list
    /snapci trigger repoName
    /snapci trigger repoName 77 ProdDeploy 
    
Where these would setup your snap credentials in snap in a way that could be used for the bot, list any pipelines, 
or trigger a new pipeline or a manual step in an existing pipeline.     

Using AWS SimpleDB

## SimpleDB

Setup via AWS CLI  

    $ aws configure set preview.sdb true
    $ aws sdb create-domain --domain-name snap_credentials