# demo web shop server

## CI-CD with Heroku

We have 2 integration tests.

In github go to: "Actions" and add:

Java CI with Maven

This will generate maven.yml file. Now pull the changes to your local git.

You should have in your project: .github/workflows/maven.yml

Make some changes in yml file:
Add environment variable with the database url:

```yml
name: Java CI with Maven

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 17
      uses: actions/setup-java@v2
      with:
        java-version: '17'
        distribution: 'adopt'
        cache: maven
    - name: Build with Maven
      env:
        DATABASE_URL: ${{secrets.DATABASE_URL}}
      run: mvn -B package --file pom.xml
```

I am using SDK 17 and java 17, if you use different ones then fill it accordingly.

The DATABASE_URL is set up from github secrets so you need to create it there:
github / settings / secrets:
Take the connection string from your JawsDB database on heroku and add jdbc: in front of it

In Heroku check the option in "Deployment" called "Wait for CI to pass before deploy"

NOTE: Heroku might need an extra file where you specify java versioin: system.properties:

```java
java.runtime.version=17
```

Test if it works:
When you push to github, the CI action will run, if successful it will deploy to Heroku.