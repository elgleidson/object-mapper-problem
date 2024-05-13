package com.github.elgleidson.jackson.problem;

public class MyClass {

  private String id;
  private String xTransactionId;
  private String xxTransactionId;

  public MyClass() {

  }

  public MyClass(String id, String xTransactionId, String xxTransactionId) {
    this.id = id;
    this.xTransactionId = xTransactionId;
    this.xxTransactionId = xxTransactionId;
  }

  public String getId() {
    return id;
  }

  public String getxTransactionId() {
    return xTransactionId;
  }

  public String getXxTransactionId() {
    return xxTransactionId;
  }
}
