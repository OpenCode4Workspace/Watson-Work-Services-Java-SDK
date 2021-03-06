## Watson Work Services Java SDK

This project is intended to give a Java wrapper for Watson Work Services REST and GraphQL APIs. For more information on the endpoints available, see https://workspace.ibm.com/developer. For a GraphQL tester site, see https://workspace.ibm.com/graphql.

**OpenCode4Workspace**

    This project is an OpenNTF project, and is available under the Apache License V2.0.  
    All other aspects of the project, including contributions, defect reports, discussions, 
    feature requests and reviews are subject to the OpenNTF Terms of Use - available at 
    http://openntf.org/Internal/home.nsf/dx/Terms_of_Use.

## Issues Tracking ##
Report Issues here: [JIRA](https://jira.openntf.org/secure/RapidBoard.jspa?rapidView=22&projectKey=WWAPI&view=planning.nodetail "Report Issues") 

## Full Documentation ##
See [OpenNTF Wiki](https://wiki.openntf.org/display/WWSJava/)

## Release Notes ##
### 0.8.0 ###
WWAPI-9 Added Focus endpoint to pass text to Watson Work Services for analysis against focus lenses  
WWAPI-11 Re-authenticating requests  
WWAPI-14 Added updatedSince as filter for Spaces query  
WWAPI-15, WWAPI-16, WWAPI-17, WWAPI-18, WWAPI-19, WWAPI-23, WWAPI-24 Added "mentioned" to GraphQL queries
WWAPI-30, WWAPI-32, WWAPI-36 Added addSpaceMembers mutation  
WWAPI-31 Added ibmUniqueID property to Person  
WWAPI-34, WWAPI-36 Added removeSpaceMembers mutation  
WWAPI-37 **CHANGE** membersUpdated property in space should be a date not a string  
WWAPI-38 Changed TokenScope extraction to be an array  
WWAPI-39 **CHANGE** Added methods to be able to access PageInfo objects in queries

### 0.7.0 ###
WWAPI-12 Ability to pass multiple query objects in a single request, supporting aliases
WWAPI-10 Support for Photo REST endpoint
WWAPI-13 Support for Files endpoint

### 0.6.0 ###
Mutations have been added for the following GraphQL mutations:
- createSpace
- deleteSpace
- updateSpace