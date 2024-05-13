package com.github.elgleidson.jackson.problem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyLombokClass {

  private String id;
  private String xTransactionId;
  private String xxTransactionId;

}
