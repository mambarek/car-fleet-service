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


6. GitHub API

This is actually what DownGit is using under the hood. Using GitHub's REST API, make a GET request to 
the content endpoint. The endpoint can be constructed as follows:
 
https://api.github.com/repos/:owner/:repo/contents/:path 

After replacing the placeholders, an example endpoint is: 
https://api.github.com/repos/babel/babel-eslint/contents/lib/parse.js. This gives you JSON data for 
that file, including a download URL (the same download URL that we used in the cURL example above). 
This method isn't all that useful for a single file, though (you'd be more likely to use it for 
downloading a specific folder, as detailed in the answer that I linked to above).

--- example Ali Mbarek The Repository must be public, private Repo not working
https://api.github.com/repos/mambarek/apirepo/contents/masterdata.yaml
in the json file search "download_url"
"download_url": "https://raw.githubusercontent.com/mambarek/apis-definitions-repo/master/definitions/commons/v1/master-data-schema.yaml",

This the url you can use to download the file

https://raw.githubusercontent.com/mambarek/apirepo/master/masterdata.yaml


https://raw.githubusercontent.com/mambarek/apis-definitions-repo/master/definitions/commons/v1/master-data-schema.yaml

##Api Definition Server (Git Repo)
In the pom.xml we fetch API Definitions from github server (apis-definitions-repo)

<inputSpec>https://raw.githubusercontent.com/mambarek/apis-definitions-repo/master/definitions/carfleet/v1/car-fleet-api.yaml</inputSpec>

## Profiles
### default
### oauth
### cloud

## Docker build with fabric8
Docker image would be created and pushed by maven at install phase see pom.xml
> mvn install

or use docker:build, docker:push
> mvn docker:build docker:push

or use Jenkins. In jenkinsfile (Pipeline) we call the two goals
