# Angular

---

- angular

[x] observable, http requests

[x] http interceptors

[x] data binding

forms, (later) (good to know)

pipes, dependency injection how in angular

@Input(), @Output() (later) (this is important)

child, parent component relationships

[x] ngInit -> angular hooks

angular directives,
Key features include:
Component-based architecture
Dependency injection (@Injectable())
RxJS for reactive programming (Observable)
Angular CLI [to scaffold and manage angular applications]
Angular.json (able to explain how angular.json works)
Two-way data binding [(ngModel)] [x]
Routing (must know) (register component to a specific route, then router.navigate[/route], component loads)

app.component.ts -> load the '/' home route
'/' -> loading.component.ts,
login check, sso enable undha ledha, if sso enable, re route to SSO url, or display standard login URL

Forms and validation (good to know)
Lazy loading (is with Observable, don't run until .subscribe() is called)
Testability (good to know)

nginx basics and how to deploy an angular app to this, how to deploy a front end app
ng build --production, what happens

---

- we must import HttpClientModule in @NgModule if we want to use it,

```typescript
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    HttpClientModule, // 👈 add this
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
```

HttpClientModule is the whole http module,
but the actual class is HttpClient

need to inject via constructor
it returns an Observable<T>

create a service class, define data fetching methods (getPosts, getUsers etc.)
using HttpClient and return an Observable<T>

```typescript
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Post } from '../post.model';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  private apiUrl = 'https://jsonplaceholder.typicode.com/posts';

  constructor(private http: HttpClient) {}

  getPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(this.apiUrl);
    // can also pass additional params as json object
  }

  getPostById(id: number): Observable<Post> {
    return this.http.get<Post>(`${this.apiUrl}/${id}`);
  }
}
```

in the app component, inject Service class via constructor
then, use those methods and subscribe them.

```typescript
import { Component, OnInit } from '@angular/core';
import { PostService } from './services/post.service';
import { Post } from './post.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
})
export class AppComponent implements OnInit {
  posts: Post[] = [];

  constructor(private postService: PostService) {}

  ngOnInit(): void {
    this.postService.getPosts().subscribe((data) => {
      this.posts = data.slice(0, 5); // show only 5 posts
    });
  }
}
```

An Observable is:

A stream of data that emits values over time.

Observables are lazy.

They do NOTHING until you subscribe.
"Observable execution starts only when subscribed."

```typescript
observable.subscribe({
  next: (value) => {}, // for http data comes once, for non http, it's incremental
  error: (err) => {},
  complete: () => {},
});
```

Promise | Observable
Single value | Multiple values
Eager | Lazy
Cannot cancel | Can unsubscribe
No operators | Has RxJS operators

http.get() gives you a stream container, not the data itself.

You must:

Subscribe

OR use async pipe

OR convert to Promise

this is eager

```js
async getUsers() {
const users = await firstValueFrom(
this.http.get('https://api.example.com/users')
);

console.log(users);
}
```

this is lazy

```js
observable.subscribe(
  nextValue => { ... },   // when data arrives
  error => { ... },       // if error happens
  () => { ... }           // when completed
);
```

promise example

```js
Promise;
const fetchData = new Promise((resolve, reject) => {
  setTimeout(() => {
    // actual api call here using fetch in js
    resolve('Data received');
  }, 2000);
});

// calling
myPromise
  .then((result) => {
    console.log(result);
  })
  .catch((error) => {
    console.log(error);
  });
```

**if asked on how to do a http request in angular**

- import HttpClientModule in @NgModule via imports
- inject HttpClient into a service class via constructor and define methods
  such as getPosts, getUsers, or as per the needed requirement
- use service class inside the component.ts file and subscribe to those methods
- can have (data) =>, or (error) =>, or (complete) => {} events inside the subscribe

- observable means stream of data over time
- multiple values can be streamed
- rxjs operators can be used to transform data
- cancellabe via unsubscribe

## Data binding in Angular

- interpolation (means ts variable in html like this {{value}})
  ts: name: string = "hello";
  html: `<h1>{{name}}<h1>`
  ts variable as display elements in html
- property binding (input form variable binded into ts value)
  ts: imageUrl = "https://example.com/photo.jpg";
  html: `<img [src]="imageUrl">`
  ts variable as html attribute values
- event binding
  `<button (click)="sayHello()">Click me</button>`
- two way binding
  combination of property binding + event binding
  ts: username = "";
  must import FormsModule inside your ts.
  html: `<input [(ngModel)]="username">`
  basically (change) event and property binding combined
  ngModel is designed to work with elements that accept user input and emit a value change (like `<input>, <select>, and <textarea>`). A standard button is for triggering actions, not for data input in the same way.

### http interceptors

Interceptors
Interceptors are generally functions which you can run for each request, and have broad capabilities to affect the contents and overall flow of requests and responses. You can install multiple interceptors, which form an interceptor chain where each interceptor processes the request or response before forwarding it to the next interceptor in the chain.

Interceptor is a way to programmatically add logic/behaviour to each incoming/outgoing
request

Component → HttpClient → Interceptor → Server
Server → Interceptor → Component

Component
↓
HttpClient
↓
Interceptor
↓
Server
↑
Interceptor
↑
Component

It can:

Modify request

Modify response

Handle errors

Log traffic

Add headers

Retry

Show loader

command: ng generate interceptor auth

steps to use an interceptor:

- create an interceptor class
- extend via HttpInterceptorFn
- signature is (req, next)
- do something and forward the request like this next(req)
- here next() means forward to server or accept from server via interceptor
- also can add variables to http context when sending requests
- interceptors can use those variables in logic via, req.context.get(CONTEXT_VARIABLE)
- to add context variable is below

```js
this.http.get(url, {
  context: new HttpContext().set(AUTH_TYPE, 'JWT'),
});
```

```ts
import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authType = req.context.get(AUTH_TYPE);

  let modifiedReq = req;

  if (authType === 'JWT') {
    modifiedReq = req.clone({
      setHeaders: { Authorization: 'Bearer jwt-token-123' },
    });
  }

  if (authType === 'API_KEY') {
    modifiedReq = req.clone({
      setHeaders: { 'x-api-key': 'my-api-key' },
    });
  }

  return next(modifiedReq);
};
```

```ts
this.http.get(url, {
  context: new HttpContext().set(AUTH_TYPE, 'JWT'),
});
this.http.get(url, {
  context: new HttpContext().set(AUTH_TYPE, 'API_KEY'),
});
this.http.get(url);
```

### Angular hooks

- Angular creates, updates, and destroys components.

Lifecycle hooks are:

Special methods that Angular automatically calls at specific stages of a component’s life.

Born → Initialized → Updated → Destroyed

- Full lifecycle order
  constructor (first thing happeneing, dependency injection happens here)
  ngOnChanges (do this after learning parent,child stuff)
  ngOnInit (immediate after component is created, api calls, initial setup, subscriptions, data loading)
  ngDoCheck
  ngAfterContentInit
  ngAfterContentChecked
  ngAfterViewInit
  ngAfterViewChecked
  ngOnDestroy (Runs when component is removed from DOM.)

- angular hooks are special methods that are executed
  at various stages of a components life cycle
- such as ngOnInit(), ngOnChanges(), ngOnDestroy()
- ngOnInit() to perform initial instructions, api calls
- ngOnDestroy() when component is removed from DOM, unsubscribe to open Observables
