# demo web shop server

## httpClient:

Info:
https://www.vojtechruzicka.com/intellij-idea-tips-tricks-testing-restful-web-services/


add a http file for requests for tutorials endpoint:

```http request
###
GET http://{{host}}/api/tutorials HTTP/1.1
Content-Type: application/json

> {%
client.test("Request executed successfully", function() {
  client.assert(response.status === 200, "Response status is not 200");
});
%}

###
GET http://{{host}}/api/tutorials/1 HTTP/1.1
Content-Type: application/json

###
GET http://{{host}}/api/tutorials/paginated?page=2&size=2 HTTP/1.1
Content-Type: application/json

### create new tutorial
POST http://{{host}}/api/tutorials HTTP/1.1
Content-Type: application/json

{
"title": "tut20",
"description": "tut description"
}

> {%
client.test("Request executed successfully", function() {
  client.assert(response.status === 201, "Response status is not 201");
});
%}
```

- Now you can run these from IntelliJ.
- You can also make tests.

To set up environment variables for httpClient create a file: http-client.private.env.json:
Example: host variable:
```
{
  "dev": {
    "host": "localhost:5552"
  }
}
```