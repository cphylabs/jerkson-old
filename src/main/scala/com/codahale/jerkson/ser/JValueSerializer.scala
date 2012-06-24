package com.codahale.jerkson.ser

import org.codehaus.jackson.JsonGenerator
import org.codehaus.jackson.map.{SerializerProvider, JsonSerializer}
import com.codahale.jerkson.AST._
import java.math.BigInteger
import org.codehaus.jackson.map.annotate.JsonCachable

@JsonCachable
class JValueSerializer extends JsonSerializer[JValue] {
  def serialize(value: JValue, json: JsonGenerator, provider: SerializerProvider) {
    value match {
      case JInt(v) => json.writeNumber(new BigInteger(v.toString()))
      case JFloat(v) => json.writeNumber(v)
      case JString(v) => json.writeString(v)
      case JBoolean(v) => json.writeBoolean(v)
      case JArray(elements) => json.writeObject(elements)
      case JField(name, value) => {
        json.writeFieldName(name)
        json.writeObject(value)
      }
      case JObject(fields) => {
        json.writeStartObject()
        fields.foreach(json.writeObject)
        json.writeEndObject()
      }
      case JNull => json.writeNull()
    }
  }
}
