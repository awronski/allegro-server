# allegro-server
This is alternative allegro server that synchronize with the original one and share the data via the rests controllers.
Because it stores the data locally it is fast and reliable.

# What does it do?
It synchronize data with allegro including:
- transaction journals:
 - new auction
 - change auction
 - end auction
 - buy
 - cancel
- deals journals:
 - clients orders
 - clients payments
 - clients data for shipping and invoices
- auctions statistics

It give rest api for:
- searching and retriving:
 - journals
 - deals
 - auctions
- createing new auctions
- canceling new auctions

## Installation

### Clone repo
```
git clone https://github.com/awronski/allegro-server.git
```

### Create user and database
```sql
CREATE USER alle;
ALTER ROLE alle PASSWORD 'password';
CREATE DATABASE alledb OWNER alle ENCODING = 'UTF-8';
```
Create schema from ```src/resources/db/db_postgres.sqldb```

### Set configuration
Rename file ```application.template``` to ```application.properties``` and change settings.

### Start server
```
mvn package && java -jar target/allegro-server-0.1.0.jar
```

### _... work in progress_

License
=======

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
