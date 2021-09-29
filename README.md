# Demo web shop server

## start
- Create new Spring Boot project
- Create a webshop database from given dump file: in the folder sql
- connect it to the project – user environment variable
- create a hello world controller and run the app, it should say “hello world”

## deploy to Heroku
- Create github repo and push the code there
- create new app in Heroku and connect to the github repo, enable auto deploys
- Provision JawsDB database
- Export your local database into a dump file (without CREATE SCHEMA statement). Or just use the same dump file.
- In MySQL Workbench connect to Heroku database server – use the connection strings found on Heroku in settings: config vars.
- Import the dump file into the empty database on Heroku.
- Now your app should run on Heroku as well
- test the hello world controller on Heroku

