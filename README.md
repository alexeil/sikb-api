# sikb-api [![Build Status](https://travis-ci.com/alexeil/sikb-api.svg?branch=master)](https://travis-ci.com/alexeil/sikb-api) [![codecov](https://codecov.io/gh/alexeil/sikb-api/branch/master/graph/badge.svg)](https://codecov.io/gh/alexeil/sikb-api) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/258ba8c0d5124f799c00290f5376f4eb)](https://www.codacy.com/app/alexeil/sikb-api?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=alexeil/sikb-api&amp;utm_campaign=Badge_Grade) [![CII Best Practices](https://bestpractices.coreinfrastructure.org/projects/2463/badge)](https://bestpractices.coreinfrastructure.org/projects/2463) 

## WARNING
These APIs are still in progress from a functional point of view and technical feasibility.

## Documentation

You can tryout APIs [here](http://ec2-35-180-42-251.eu-west-3.compute.amazonaws.com:8080/sikb/swagger-ui/).

You need to set authorization in order to able to use apis. Use : test/test

### Api Subscription
Anyone wanting to consume these APIs need to send an email to thierry.boschat@kin-ball.fr

### Workflow

![Worflow](docs/schema/workflow.png)

1. A club need to send an email to thierry.boschat@kin-ball.fr to get their account initialized
2. Then after confirming his identity and update password, the club can log into the platform
3. Then the club is free to update jis information and create an affiliation

## Tools

* You can modify sequence diagrams (docs/schema/*.plantuml) with [planttext](https://www.planttext.com/)
* You can view and modify Swagger (docs/swagger/) with [Swagger Editor](https://editor.swagger.io/)  