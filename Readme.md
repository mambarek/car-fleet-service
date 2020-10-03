## Car Fleet Management

#### api-docs json format
http://localhost:8030/v3/api-docs

#### YAML format to download
http://localhost:8030/v3/api-docs.yaml

#### swagger ui
http://localhost:8030/swagger-ui.html

### Open API Defenition
under /resources/openapi/definitions we define the YAML-Files for the API
#### mustache templates
to adjust the Annotations in the generated classes you have to edit the corresponding mustache templates 
1) Download The Open API Generator from Github https://github.com/OpenAPITools/openapi-generator
2) The mustache templates are under  
>/openapi-generator/modules/openapi-generator/src/main/resources/JavaSpring

Copy the files needed and put them in project under /resources/openapi/definition/templates
In this project we need the following files
```
api.mustache
apiController.mustache
apiDelegate.mustache
``` 

### Generated API from API Definition
In this project we define an Open API Definition under /resources/openapi/defintions
In the pom.xml we use openapi-generator-maven-plugin to generate the service stubs with delegate pattern 
The generated classes are in folder target 
   
1) implement CarService to make CRUD calls to backend
2) Implement CarsApiDelegateImpl tha extends the generated Interface CarsApiDelegate and make calls 
to CarService to make CRUD Operations to backend.  
