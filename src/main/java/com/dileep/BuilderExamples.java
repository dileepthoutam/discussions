package com.dileep;

class Comp {
  private String cpu;
  private String ram;
  private String storage;

  public Comp() {
  }

  public Comp cpu(String cpu) {
    this.cpu = cpu;
    return this;
  }

  public Comp ram(String ram) {
    this.ram = ram;
    return this;
  }

  public Comp build() {
    return this;
  }

  @Override
  public String toString() {
    return this.cpu + ", " + this.ram;
  }
}

class Computer {
  private String cpu;
  private String ram;
  private String storage;

  private Computer(Builder builder) {
    this.cpu = builder.cpu;
    this.ram = builder.ram;
    this.storage = builder.storage;
  }

  static class Builder {
    private String cpu;
    private String ram;
    private String storage;

    public Builder cpu(String cpu) {
      this.cpu = cpu;
      return this;
    }

    public Builder ram(String ram) {
      this.ram = ram;
      return this;
    }

    public Builder storage(String storage) {
      this.storage = storage;
      return this;
    }

    public Computer build() {
      return new Computer(this);
    }
  }
}


public class BuilderExamples {
}
