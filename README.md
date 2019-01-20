# sikb-api [![Build Status](https://travis-ci.com/alexeil/sikb-api.svg?branch=master)](https://travis-ci.com/alexeil/sikb-api) [![codecov](https://codecov.io/gh/alexeil/sikb-api/branch/master/graph/badge.svg)](https://codecov.io/gh/alexeil/sikb-api) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/258ba8c0d5124f799c00290f5376f4eb)](https://www.codacy.com/app/alexeil/sikb-api?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=alexeil/sikb-api&amp;utm_campaign=Badge_Grade) [![CII Best Practices](https://bestpractices.coreinfrastructure.org/projects/2463/badge)](https://bestpractices.coreinfrastructure.org/projects/2463) 

# WARNING
These APIs are still in progress from a functional point of view and technical feasibility.

# Documentation
1) First take contact with the administrator to provide to a user account and to initiate a club with a name.
2) Based on the swagger /docs/json/swagger.yml
* You will update your club information PUT /clubs/{clubId}
* Create your first affiliation for the current season (YYYYZZZZ) POST /clubs/{clubId}/{season}/affiliation
* You can still update your affiliation information with PUT /clubs/{clubId}/{season}/affiliation