# allegro-server
This is alternative allegro server that synchronize with the original one and share the data via the rests controllers.
Because it stores the data locally in the datebase it is fast and reliable.

You can access the data with any rest client or use our [allegro-client](https://github.com/awronski/allegro-client)
java library

# What's new?
- Version 3.0 support **Allegro Rest Api**
- Version 2.4 supports changing of price for the ongoing auctions even with offers
- Version 2.2 - 2.3 improvements and bug fixes
- Version 2.1 supports single payment for multiple sellers
- Version 2.0 support multi users.

# What does it do?
It synchronize data with allegro including:
- transaction journals:
 - new auction, change, end
 - buy, cancel
- deals journals:
 - clients orders
 - clients payments
 - clients data for shipping and invoices
- auctions statistics
- payments (post buy forms)

It give rest api for:
- searching and retriving:
 - journals
 - deals
 - auctions
 - payments
- creating, changing and closing auctions

## Requirements
- Java 8
- Postgres 9
- [allegro-nice-api](https://github.com/awronski/allegro-nice-api) library

## Installation

### Clone repo
```
git clone https://github.com/awronski/allegro-server.git
```

### Create database
```sql
CREATE USER alle;
ALTER ROLE alle PASSWORD 'password';
CREATE DATABASE alledb OWNER alle ENCODING = 'UTF-8';
```
Create schema from ```src/resources/db/db_postgres.sqldb```

### Create allegro sellers
```sql
INSERT INTO clients select clientId, 'username', 'passwords', 'webapi_key';
```

### Set configuration
Rename file ```application.template``` to ```application.properties``` and change settings.

### Start server
```
mvn package && java -jar target/allegro-server.jar
```

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
