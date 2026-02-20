Core Concepts
---
1. What is an API
   ---
   Application Programming Interface.
   Set of rules, protocols, and definitions that enables different software applications to communicate and exchange data, acting as an intermediary layer between systems.
---  
2. REST vs GraphQL
---
3. HTTP methods (GET, POST, PUT etc)
   ---
   GET: Read
   ---
   i. Build
    ```java
        HttpRequest request = HttpRequest.newBuilder()
                                        .uri(URI.create("https://api.example.com/users"))
                                        .GET().build();
        HttpResponse<String> response =
        client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.body());
    ```
   ii. Controller
    ```java
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;
        import java.util.List;

        @RestController
        @RequestMapping("/api/users")
        public class UserController {
            @GetMapping
            public ResponseEntity<List<String>> getUsers() {
                return ResponseEntity.ok(List.of("Alice", "Bob"));
            }
            @GetMapping("/{id}")
            public ResponseEntity<String> getUserById(@PathVariable int id) {
                return ResponseEntity.ok("User " + id);
            }
        }
    ```
   iii. Invoke
   ```textmate
    @GetMapping
      GET http://localhost:8080/api/users
    
    @GetMapping("/{id}")
      GET http://localhost:8080/api/users/5
   ```
   POST: Create
   ---

   i. Build
    ```java
        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://api.example.com/users"))
                                .header("Content-Type", "application/json")
                                .POST(HttpRequest.BodyPublishers.ofString("{\"name\":\"John\",\"age\":30}")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    ```
   ii. Controller
    ```java
        @PostMapping
        public ResponseEntity<String> createUser(@RequestBody String user) {
            return ResponseEntity.ok("User created: " + user);
        }
    ```
   iii. Invoke
   ```textmate
    @PostMapping ->
    POST http://localhost:8080/api/users
    {
    "name": "Alice",
    "age": 25
    }
   ```
   PUT: Replace
   ---
   i. Build
    ```java
        HttpRequest request = HttpRequest.newBuilder()
                                        .uri(URI.create("https://api.example.com/users/1"))
                                        .header("Content-Type", "application/json")
                                        .PUT(HttpRequest.BodyPublishers.ofString("{\"name\":\"John Updated\"}"))
                                        .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    ```
   ii. Controller
    ```java
        @PutMapping("/{id}")
        public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody String user) {
            return ResponseEntity.ok("User updated " + id);
        }
    ```
   iii. Invoke
   ```textmate
   @PutMapping("/{id}")
   PUT http://localhost:8080/api/users/5
   {
   "name": "Alice Updated",
   "age": 26
   }
   ```
   PATCH: Partial update (Not sending whole request body but a feature of the object to be updated)
   ---  
    i. Build
    ```java
        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://api.example.com/users/1"))
                                        .header("Content-Type", "application/json")
                                        .method("PATCH", HttpRequest.BodyPublishers.ofString("{\"age\":31}")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    ```
   ii. Controller
    ```java
        @PatchMapping("/{id}")
        public ResponseEntity<String> patchUser(@PathVariable int id, @RequestBody String updates) {
        return ResponseEntity.ok("User patched " + id);
        }
    ```
   iii. Invoke
   ```textmate
   @PatchMapping("/{id}")
   PATCH http://localhost:8080/api/users/5
   {"age": 27 }
   ```
   DELETE: Delete records
   ---
   i. Build
    ```java
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.example.com/users/1"))
                                                        .DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    ```
   ii. Controller
    ```java
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable int id) {
            return ResponseEntity.noContent().build();
        }
    ```
   iii. Invoke
   ```textmate
   @DeleteMapping("/{id}")
   DELETE http://localhost:8080/api/users/5
   ```
   HEAD: Headers only
   ---  
   i. Build
    ```java
        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://api.example.com/users"))
                                .method("HEAD", HttpRequest.BodyPublishers.noBody()).build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        // System.out.println(response.headers().map());
    ```
   ii. Controller
    ```java
        @RequestMapping(method = RequestMethod.HEAD)
        public ResponseEntity<Void> headRequest() {
            return ResponseEntity.ok().build();
        }
    ```
   iii. Invoke
   ```textmate
   @RequestMapping(method = RequestMethod.HEAD)
   HEAD http://localhost:8080/api/users
   ```
   TRACE: Diagnostic echo (usually disabled)
   ---
   i. Build
    ```java
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.example.com"))
                                                        .method("TRACE", HttpRequest.BodyPublishers.noBody()).build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    ```
   ii. Controller
    ```java
        @RequestMapping(method = RequestMethod.TRACE)
        public ResponseEntity<String> trace() {
            return ResponseEntity.ok("TRACE response");
        }
    ```
   iii. Invoke
   ```textmate
   @RequestMapping(method = RequestMethod.TRACE)
   TRACE http://localhost:8080/api/users
   ```
4. HTTP status codes (2xx, 4xx, 5xx)
   ---
   ```textmate
   200 OK / 201 Created: Success.
   204 No content. Success.
   400 Bad Request: Client-side error (you sent bad data).
   401 Unauthorized: You aren't logged in.
   403 Forbidden: You are logged in, but don't have permission.
   404 Not Found: Resource doesn't exist.
   500 Internal Server Error: The server crashed.
   503 Service unavailable (dns mapping failed or url path doesn't exist)
   504 Gateway timeout
   ```
---
5. Stateless vs Stateful APIs
   ---
   Stateless:
   ---
   No Memory of information like (auth, tokens etc) between API requests.
   ```textmate
     What actually happens:
     ----------------------
     1. Client sends a request
     2. Request contains all required information
     3. Server processes it
     4. Server sends response
     5. Server forgets everything

     Next request repeats the same ritual.
   ```
   Example (REST, JWT-based)   
   ```textmate
   GET /api/orders
   Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   
   Server logic:
   -------------
   1. Reads JWT
   2. Identifies user
   3. Returns orders
   4. Forgets everything immediately
   ```
   ```textmate
   Typical uses
        REST APIs
        Microservices
        Cloud-native systems
   
   Pros
        Horizontally scalable
        Failures don’t break sessions
        Simple load balancing
   
   Cons
        Slightly more data per request
        Client handles state (tokens)
   ```

   Stateful: 
   --- 
   Back-to-Back Conversation with memory 🧠📒 until expiry.
   ```textmate
     What actually happens
     1. Client logs in
     2. Server creates session in memory or DB
     3. Server returns session ID
     4. Client sends only the session ID next time
     5. Server uses stored session data

     The server is remembering context.
   ```
   Example (Cookie, Session based)

   Login
   ```bash
    POST /login
   ```
   Server Stores
   ```yaml
   Session ID: abc123
   User: rosh
   Role: ADMIN
   Cart: [item1, item2]
   ```
   Response
   ```javascript
   Set-Cookie: JSESSIONID=abc123
   ```
   Next Request
   ```vbnet
   GET /api/orders
   Cookie: JSESSIONID=abc123
   ```
   ```textmate
   Typical uses
        Traditional web apps
        Banking portals
        Long workflows
   
   Pros
        Less data per request
        Easier server-side logic
   
   Cons
        Harder to scale
        Session replication needed
        Server crashes can lose sessions (incase of in-memory[RAM] saved sessions/cookies not db-saved)
      ```
---
6. Authentication vs Authorization
   ---
   Authentication: Who are you? [IDENTITY-CHECK]
   ```textmate
   Common authentication methods
    1. Username + password
    2. OTP
    3. Biometrics
    4. OAuth login (Google, GitHub)
    5. JWT verification
   ```

   Authorization: What are you allowed to do? [PERMISSIONS-CHECK]
   ```textmate
   Common authentication methods
    1. Role-based (ADMIN, USER)
    2. Permission-based (READ_USER, DELETE_USER)
    3. Attribute-based (time, location, ownership)
   ```
   | Aspect       | Authentication   | Authorization        |
   | ------------ | ---------------- | -------------------- |
   | Question     | Who are you?     | What can you do?     |
   | Happens when | First            | After authentication |
   | Depends on   | Credentials      | Roles / permissions  |
   | Failure code | 401 Unauthorized | 403 Forbidden        |
   | Example      | Login            | Access check         |


---
7. Session-based auth vs JWT
   ---
   Session-based auth → server remembers the user
   ```textmate
      1. Client logs in
      2. Server: Verifies creds, creates session in memory/DB, stores user info
      3. Sever responds Set-Cookie: JSESSIONID=abc123
      4. Client sends cookie on every request Cookie: JSESSIONID=abc123
      5. Server: looks up session, restores user context
   ```
   Example Request:
   ```bash
      GET /api/user
      Cookie: JSESSIONID=abc123
   ```

   JWT-based auth → client carries proof of identity
   ```textmate
      1. Client logs in
      2. Server: Verifies creds, creates JWT (signed token) (Here: UAA server OAuth2)
      3. Sever responds Authorization: Bearer <JWT>
      4. Client sends JWT on every HTTP request Authorization: Bearer <JWT
      5. Server: verifies signature, verifies expiry, extracts identity (No lookup, No memory)
   ```
   Example Request:
   ```bash
      GET /api/user
      Authorization: Bearer <JWT>
   ```
   | Aspect         | Session-based   | JWT-based               |
   | -------------- | --------------- | ----------------------- |
   | Server state   | Stores session  | Stateless               |
   | Login required | Yes             | Only to get token       |
   | Storage        | Server          | Client                  |
   | Scaling        | Hard            | Easy                    |
   | Load balancer  | Sticky sessions | Any node                |
   | Logout         | Easy            | Token invalidation hard |
   | Token size     | Small (ID)      | Larger (claims)         |

   ```textmate
      Sticky sessions (session affinity) are a load balancing technique that directs all requests from 
      a specific user client to the same backend server for the duration of a session. 
      This ensures session continuity and data integrity for stateful applications, such as maintaining shopping carts.
   ```
---
8. OAuth 2.0 (Login with Google, GitHub, etc.)
   ---
   OAuth 2.0 is an authorization framework, not an authentication protocol.
   Login with Google” uses OAuth 2.0, plus a small identity layer (OpenID Connect) on top.   
   
   ```textmate
      The core problem OAuth solves
      You want:
        * users to log in using Google/GitHub
        * without seeing or storing their passwords
   ```
   Roles in OAuth   

   | Role                 | Who it is (example)    |
   | -------------------- | ---------------------- |
   | Resource Owner       | User (you)             |
   | Client               | Your app               |
   | Authorization Server | Google / GitHub        |
   | Resource Server      | Google APIs            |
   | Redirect URI         | Your callback endpoint |

   Login With Google using OAuth
   -
   ```textmate
    Cast Of Characters: You(User), Your App (Website to login into), Google (Trusted identity provider)
    Your app wants to know: Is this really the Google user rosh@gmail.com? [your app must not ask/store Google password]
   
   Step1: Your App says -> Go ask Google to verify this user.
   Step1: 
   Step1: 
   Step1: 
   Step1: 
   Step1: 
   ```
   


---
9. Rate limiting vs Throttling
   ---
read: https://www.enjoyalgorithms.com/blog/throttling-and-rate-limiting/
- rate limit is a way to control the amount of requests served in a specific time period
- client sent a request
- server remebers ip/user id, api key etc.
- server will evaluate against the pre-defined rate limits
- based on evaluation, server decides to allow/throttle or reject
- if request falls within allowed limit then processed, if request exceed limit throttle or reject
- ![img.png](img.png)
- why? DDOS (Distrubted Denial of Service attack)

implementing
- request queues
- throttling
- fixed window technique
- leaky bucket
- sliding window

---
10. Idempotency (especially for payments)
    ---
- Performing the same operation multiple times produces the same result as performing it once.
- f(f(x)) = f(x)
  | Operation                     | Idempotent?  | Why                                    |
  | ----------------------------- | ------------ | -------------------------------------- |
  | `GET /users/1`                | ✅ Yes        | Reading doesn't change state           |
  | `PUT /users/1 {name: "John"}` | ✅ Yes        | Same update repeatedly → same result   |
  | `DELETE /users/1`             | ✅ Yes        | Deleting again doesn't change anything |
  | `POST /orders`                | ❌ Usually No | Creates a new order every time         |
- these are just sematics
- not mandatory but recommended to use rules
- so that everyone is on same page
- generally, POST means create resource, it's not naturally idempotent
- we should enforce idempotency in POST
 
server got the request
but due to network connection lost, server not able to respond
client thought request failed, network retry
but creation of resource already happened at server side
client is sending again

if network retry, you don't generate the msguid once again,
we prevented accidentally creating the same thing twice
always unique

how?
- implement a idempotency key check via a seperate table, or
- add an extram column in DB with external key and make it unique so that second insert to DB
will fail
- ALTER TABLE orders ADD CONSTRAINT unique_order UNIQUE(user_id, external_id);

edge case:

`SELECT FOR UPDATE`
remove idempotency keys regularly to housekeep DB


---

Databases & Data Handling
---
11. SQL vs NoSQL — when to use what
    ---

---
12. Indexes and how they speed up queries
    ---
What is Indexing in a Database?

Indexing in a database is a technique used to speed up data retrieval operations.

Think of it like the index of a book:

Without index → You scan every page.

With index → You directly jump to the page number.

In databases:

Without index → Full table scan (slow for large tables)

With index → Fast lookup using data structure (usually B-Tree)

indexing will introduce overhead in DB writes,
every time you create a row, you have to update the index as well

```sql
CREATE TABLE bugs (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     project_id INT,
                     status VARCHAR(20),
                     created_at DATETIME
);
CREATE INDEX idx_project_status_created
   ON bugs(project_id, status, created_at DESC);

ALTER TABLE users
   ADD INDEX IF NOT EXISTS idx_users_age (age);

```

why?
query performance
faster api response
scalable

drawback?
overhead to writes
don't use boolean columns as index
don't use columns that frequently change

---
13. ACID properties
    ---

atomicity -> either operation goes through or doesn't, no half completed task
if failed in middle, will rollback

```sqlSTART TRANSACTION;

UPDATE accounts SET balance = balance - 1000 WHERE id = 1;
UPDATE accounts SET balance = balance + 1000 WHERE id = 2;

COMMIT;
```

consistency -> database state should not inconsistent, 

db tables/relations constraints must be satisfied
not null, unique key, these kind of checks will be enforced by DB
cascade update/cascade delete  helps maintain consistency
internal -> it will keep log files, 

isolation -> two seperate txns do not share state, threads/txns execute independently
durability -> even if crashes or something like that happens, data should be persisted even in failures so that we
keep data consistent

---
14. Transactions & isolation levels
    ---

---
15. Database normalization vs denormalization
    ---

---
16. Pagination (offset vs cursor-based)
    ---
Pagination is the process of splitting large datasets into smaller chunks (pages) instead 
of returning everything at once.

to avoid fetching too much data, reduce DB work load, improve API response time
better to have multiple pages than infinite scroll

GET /users?page=2&size=10

what is offset based pagination?
means, offset number is the number of rows you skip and limit is the total no of result rows
SELECT * FROM users
ORDER BY id
LIMIT 10 OFFSET 20;

problems with offset pagination

OFFSET 1000000 LIMIT 10 (if you have query like this)
db must scan all those rows and discard them, and then return the next 10 rows

not good for large datasets


'cursor based pagination

instead of page numbers, use a cursor (last seen ID)

GET /users?after=105

```sql
SELECT * FROM users
WHERE id > 105
ORDER BY id
   LIMIT 10;
```

✅ Why It’s Better

No skipping large rows
Uses index efficiently
Fast even with millions of records
Stable results

in api response, along with data, send the last used id
in subsequent pages, use the id as cursor to the query



---
17. Sharding & partitioning
    ---

sharding is horizontal partitioning


scaling up vs scaling out
horizontal scaling vs vertical scaling

one db server -> single point of failure, if too much data perfromance is effected
add more replicas -> generally master & salve, CQRS read from one, write to another, sync them

here in multiple servers data is exactly the same
this is horizontal scaling, 


but sharding means -> data is split across servers

let's i have 1 billion records,
100M in each DB across 10 db servers
these are not replicas, dividing data into multiple servers

i have to fetch one user detail, shard key
data parallelism (sharding)

**partitioning**

splitting single table into smaller pieces inside same db server

one db -> one logical table -> many internal partitions

orders table (100 million rows)

orders_2025_jan
orders_2025_feb
orders_2025_mar

types partitioning

1. range partitioning
   CREATE TABLE orders (
   id BIGINT,
   created_at DATE
   ) PARTITION BY RANGE (created_at);

2. hash partitioning (hash(user_id) % 4)
3. List partitioning is region based partitioning if you deploy across continents

Horizontal: (subsets of table of rows partitioned by business logic)
Veritical: (subsets of table by columns)
Range: (divide data based on dates etcc)
List: (subset the data based on predefined list of values of a particular column)

CREATE TABLE orders (
id INT,
order_date DATE
)
PARTITION BY RANGE (YEAR(order_date)) (
PARTITION p2024 VALUES LESS THAN (2025),
PARTITION p2025 VALUES LESS THAN (2026)
);

range--

1M records
800K on one day
200K across 9 days

USERS
--------------------------------
id | name  | country
--------------------------------
1  | Asha  | IN
2  | John  | US
3  | Mei   | CN
4  | Ravi  | IN

based on a filter key, it will be split across

Horizontal partitioning → mainly for performance & scalability

Vertical partitioning → mainly for performance (I/O reduction)

Range partitioning → mainly for maintenance & lifecycle management, and also helps performance when queries align with the range

---
18. Read replicas & write scaling
    ---
A read replica is a copy of your main database used only for read queries (SELECT).
          ┌────────────┐
Writes →  │  Primary   │
          └─────┬──────┘
                │ (replication)
        ┌───────┴────────┐ 
        │       │        │
       Replica1  Replica2  Replica3
       (read)     (read)     (read)


🔹 Why Do We Need Them?

Because most systems are:

80–95% reads
5–20% writes

Instead of overloading the primary DB with reads, we:
Send writes → Primary
Send reads → Replicas

how replication works?
They use Write-Ahead Log (WAL) replication.

Flow:

Write happens on Primary
It writes to WAL
WAL is streamed to replicas
Replicas replay changes


synchronous -> write to primary and then write to all replica then respond
asynchronous -> write to primary, respond & sending sync request to replicas

how do you scale writes?
vertical (add more cpu, more memory, faster disk) but maxes out
horizontal (sharding)
multi primary(rare)

replication lag
failover (master goes down, slave comes up as master style)
read scaling
write scaling


---
19. Handling duplicate records
    ---
why duplicates happen

🔹 1. No Unique Constraints
🔹 2. Race Conditions (concurrent writes)
🔹 3. Retries in distributed systems
🔹 4. Event reprocessing
🔹 5. Idempotency not implemented

always prevent at DB/schema level, add unique key constraints


---
20. Optimistic vs pessimistic locking
    ---
pessimistic

User A → LOCK → update → unlock
User B → WAIT

optimistic

User A → update (version 1 → 2)
User B → update fails → retry

| Scenario                | Use         |
| ----------------------- | ----------- |
| High conflict rate      | Pessimistic |
| Rare conflicts          | Optimistic  |
| Read-heavy system       | Optimistic  |
| Financial transfers     | Pessimistic |
| High scalability needed | Optimistic  |

Thread A

BEGIN;

SELECT * FROM accounts
WHERE id = 1
FOR UPDATE;

-- row is now locked

UPDATE accounts
SET balance = balance - 100
WHERE id = 1;

COMMIT;


Thread B

BEGIN;

SELECT * FROM accounts
WHERE id = 1
FOR UPDATE;

this will wait

---


    Caching & Performance
21. What is caching and where to cache
temporary storage to improve performance
preferred for data that doesn't change and read heavy (static data)
where to?
in memory
but also something like redis (a cache server)
there are different caches like CDNs which are presented across globe to improve reading speed from DB

if we're fetching data from us server on AWS
to overcome latency, they have these edge locations where they have CDN servers
what CDN do? it caches the data in servers closer to the users
improves latency


22. Cache eviction strategies (TTL, LRU)
why do we cache eviction? can't keep cache growing forever
reset at somepoint to update with latest data
LRU (least recently used)
TTL (Time to live) (means after certain period of time, it's removed from cache)


23. Cache consistency & stale cache problems
24. CDN and edge caching
25. Why cache can make systems wrong
    Distributed Systems & Scaling
26. Load balancing (round-robin, least connections, hashing)
27. Horizontal vs vertical scaling 28. Microservices vs monoliths (trade-offs) 29. Service-to-service communication (sync vs
    async)
30. Message queues (Kafka, RabbitMQ, SQS)
    Reliability & Real-World Problems
31. Exactly-once vs at-least-once processing
32. Retries, timeouts, and circuit breakers
33. Handling race conditions
34. Distributed locking
35. Event-driven architecture
36. Saga pattern (distributed transactions)
37. 37.Graceful degradation
38. Observability (logs, metrics, tracing)
39. Deployments (blue-green, rolling)
40. Handling traffic spikes & viral load