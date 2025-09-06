package com.github.elgleidson.jackson.problem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

class ObjectMapperTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Nested
  class JavaClass {
    @Test
    void toJson() throws JsonProcessingException, JSONException {
      var obj = new MyClass("123", "456", "789");

      var expectedJson = """
      {
        "id": "123",
        "xTransactionId": "456",
        "xxTransactionId": "789"
      }
      """;

      var actualJson = objectMapper.writeValueAsString(obj);

      JSONAssert.assertEquals(expectedJson, actualJson, true);
    }

    @Test
    void fromJson() throws JsonProcessingException {
      String json = """
      {
        "id": "123",
        "xTransactionId": "456",
        "xxTransactionId": "789"
      }
      """;

      var actualObj = objectMapper.readValue(json, MyClass.class);

      assertEquals("123", actualObj.getId(), "id");
      assertEquals("456", actualObj.getxTransactionId(), "xTransactionId");
      assertEquals("789", actualObj.getXxTransactionId(), "xxTransactionId");
    }
  }

  @Nested
  class JavaRecord {
    @Test
    void toJson() throws JsonProcessingException, JSONException {
      var obj = new MyRecord("123", "456", "789");

      var expectedJson = """
      {
        "id": "123",
        "xTransactionId": "456",
        "xxTransactionId": "789"
      }
      """;

      var actualJson = objectMapper.writeValueAsString(obj);

      JSONAssert.assertEquals(expectedJson, actualJson, true);
    }

    @Test
    void fromJson() throws JsonProcessingException {
      String json = """
      {
        "id": "123",
        "xTransactionId": "456",
        "xxTransactionId": "789"
      }
      """;

      var actualObj = objectMapper.readValue(json, MyRecord.class);

      assertEquals("123", actualObj.id(), "id");
      assertEquals("456", actualObj.xTransactionId(), "xTransactionId");
      assertEquals("789", actualObj.xxTransactionId(), "xxTransactionId");
    }
  }

  @Nested
  class Lombok {
    @Test
    void toJson() throws JsonProcessingException, JSONException {
      var obj = new MyLombokClass("123", "456", "789");

      var expectedJson = """
      {
        "id": "123",
        "xTransactionId": "456",
        "xxTransactionId": "789"
      }
      """;

      var actualJson = objectMapper.writeValueAsString(obj);

      // Fails with:
      // Expected: xTransactionId
      //     but none found
      // ;
      //Unexpected: xtransactionId
      JSONAssert.assertEquals(expectedJson, actualJson, true);
    }

    @Test
    void fromJson() throws JsonProcessingException {
      String json = """
      {
        "id": "123",
        "xTransactionId": "456",
        "xxTransactionId": "789"
      }
      """;

      var actualObj = objectMapper.readValue(json, MyLombokClass.class);

      assertEquals("123", actualObj.getId(), "id");
      assertEquals("456", actualObj.getXTransactionId(), "xTransactionId");
      assertEquals("789", actualObj.getXxTransactionId(), "xxTransactionId");
    }
  }

  @Nested
  class Avro {

    abstract static class IgnoreAvroFields {
      @JsonIgnore
      abstract void getSchema();
      @JsonIgnore
      abstract void getSpecificData();
    }

    @Test
    void toJson() throws JsonProcessingException, JSONException {
      var obj = new MyAvro("123", "456", "789");

      var expectedJson = """
      {
        "id": "123",
        "xTransactionId": "456",
        "xxTransactionId": "789"
      }
      """;

      objectMapper.addMixIn(MyAvro.class, IgnoreAvroFields.class);
      var actualJson = objectMapper.writeValueAsString(obj);

      // Fails with:
      // Expected: xTransactionId
      //     but none found
      // ;
      //Unexpected: xtransactionId
      JSONAssert.assertEquals(expectedJson, actualJson, true);
    }

    @Test
    void fromJson() throws JsonProcessingException {
      String json = """
      {
        "id": "123",
        "xTransactionId": "456",
        "xxTransactionId": "789"
      }
      """;

      // Fails with
      // com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException:
      // Unrecognized field "xTransactionId" (class com.github.elgleidson.jackson.problem.MyAvro), not marked as ignorable
      // (3 known properties: "xxTransactionId", "id", "xtransactionId"])
      var actualObj = objectMapper.readValue(json, MyAvro.class);

      assertEquals("123", actualObj.getId(), "id");
      assertEquals("456", actualObj.getXTransactionId(), "xTransactionId");
      assertEquals("789", actualObj.getXxTransactionId(), "xxTransactionId");
    }
  }

}
