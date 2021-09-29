# demo web shop server

Start just with the tutorials table – generate the entity class.

Fix the @id in the entity class, att this:

@GeneratedValue(strategy = GenerationType.IDENTITY)

Create the whole layered architecture:

- entity
- DTO
- repository
- service: both interface and implementation class 
- controller: both interface and implementation class


- test the CRUD operations locally
- test on Heroku