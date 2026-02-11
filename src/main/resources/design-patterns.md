

1. Singleton
2. Factory
3. Abstract Factory
4. Builder pattern
5. Strategy pattern

## Singleton 

intent: to create a single object to be shared across the application
eager initialisation
lazy initialisation

directly initialise when you declare the instance variable itself
```java
private static final EagerSingleton instance = new EagerSingleton();
```

lazy means initialise when it's first called
```java
public static Singleton getInstance() {
  if (instance == null) {
    return new Singleton();
  }
  return instance;
}
```

applications of singleton: where do we use?
DataSource, Instance managers (like MQ), logging, in memory cache,

@Cacheable -> only if idempotent, same response for same request always
can't use in dynamic data fetching from DB - caching method calls
can use on data queries like user details etc.

# Factory Pattern

factory method is a creational design pattern that provides an interface
for creating objects in a superclass, but allows subclasses to alter the
type of objects that will be created.

intent: to delete the object creation logic
single intent -> multiple implementations for different types

forex factory -> intent is to calculate rate,
but we have different ways, based on user input

we have to give type to get the object, (we don't create the object ourselves)


```java
enum NotificationType {
  EMAIL,
  SMS,
}

interface Notification {
  void sendMessage(String message);
}

class EmailNotification implements Notification {
  public void sendMessage(String message) {
    System.out.println("Send email.");
  }
}

class SmsNotification implements Notification {
  public void sendMessage(String message) {
    System.out.println("Send sms.");
  }
}

class NotificationFactory {
  public static Notification getNotification(NotificationType notificationType) {
    return switch (notificationType) {
      case EMAIL -> new EmailNotification();
      case SMS -> new SmsNotification();
    };
  }
}
```

## Abstract Factory

intent: Create families of related objects without specifying their concrete classes
factory for concrete classes
and we have factory for which factory to decide

GUIFactory.class (factory to decide which factory to get)
Button.class
CheckBox.class

WindowsFactory -> WindowsButton, WindowsCheckBox
MacFactory -> MacButton, MacCheckBox


## Builder Pattern

intent: to create objects with lots of configurable options
DataSource creation, MessageQueue creation, HttpRequest,
Query builders, batch jobs etc.

```java
Computer pc = new Computer.Builder()
    .cpu("i7")
    .ram("16GB")
    .storage("1TB")
    .build();
```
you have lot of fields with different possible configurations

you create a static Builder class with fields inside, with method names as possible 
configurable field parameters, and it returns this(Builder object)

and finally .build() will return the main class
in the Main constructor class, (Builder class is received)
constructor is private

basically mapping Builder class fields to main class fields,
private constructor
.build() will return like new Computer(this);


### Strategy pattern

instead of creating object, you pass an implementation object

if you have different implementations
, use a common interface
using .setStrategy() pass the implementation (or the object)







to discuss spring singleton pattern
Singleton but based on bean-id stored in ApplicationContext
