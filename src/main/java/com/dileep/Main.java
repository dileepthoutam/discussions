package com.dileep;

class Repository {
  void save() {

  }
}

class Service {
  public Service() {
  }

  public void saveSomething() {
    Repository repository = new Repository();
    repository.save();
  }
}

public class Main {
  public static void main(String[] args) {

    Service service = new Service();
    service.saveSomething();

  }
}
