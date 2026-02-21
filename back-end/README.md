# Bus Station manager - Back-end Java Spring Boot

## Instructions
1. Needs JRE to run 
2. Clone the repo
3. Navigate to the dir (busstation-back)
4. Run from IDE


Features
* Fully secured with Spring Security
* Supports CRUD and more
* DTO classes for file transfer
* High test coverage


The front-end can be found at
https://github.com/FTmiles/busstation-front


The main town bus station must be bus stop id 1L.
With the 1L bus stop input, you get access to "City buses" radio button. Which is 


#########
Manually insert user roles into the DB
INSERT INTO roles(NAME) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

########
# Register a new user
POST request to http://localhost:8080/api/auth/signup
JSON Body:
{
"username":"chad",
"email":"xxx@asd.com",
"password":"12345678",
"role": ["user", "mod", "admin"]
}

###
# Postman sign in to get JWT token
POST request to http://localhost:8080/api/auth/signin
JSON body:
{
    "username" : "chad",
    "password" : "12345678"
}

###
Close registration - disable the controller method @Controllers>AuthController
