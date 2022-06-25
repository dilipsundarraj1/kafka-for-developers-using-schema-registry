/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.learnavro;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class GreetingOld extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 6962157253369978678L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"GreetingOld\",\"namespace\":\"com.learnavro\",\"fields\":[{\"name\":\"greeting\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<GreetingOld> ENCODER =
      new BinaryMessageEncoder<GreetingOld>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<GreetingOld> DECODER =
      new BinaryMessageDecoder<GreetingOld>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<GreetingOld> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<GreetingOld> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<GreetingOld> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<GreetingOld>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this GreetingOld to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a GreetingOld from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a GreetingOld instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static GreetingOld fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.String greeting;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public GreetingOld() {}

  /**
   * All-args constructor.
   * @param greeting The new value for greeting
   */
  public GreetingOld(java.lang.String greeting) {
    this.greeting = greeting;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return greeting;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: greeting = value$ != null ? value$.toString() : null; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'greeting' field.
   * @return The value of the 'greeting' field.
   */
  public java.lang.String getGreeting() {
    return greeting;
  }


  /**
   * Sets the value of the 'greeting' field.
   * @param value the value to set.
   */
  public void setGreeting(java.lang.String value) {
    this.greeting = value;
  }

  /**
   * Creates a new GreetingOld RecordBuilder.
   * @return A new GreetingOld RecordBuilder
   */
  public static com.learnavro.GreetingOld.Builder newBuilder() {
    return new com.learnavro.GreetingOld.Builder();
  }

  /**
   * Creates a new GreetingOld RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new GreetingOld RecordBuilder
   */
  public static com.learnavro.GreetingOld.Builder newBuilder(com.learnavro.GreetingOld.Builder other) {
    if (other == null) {
      return new com.learnavro.GreetingOld.Builder();
    } else {
      return new com.learnavro.GreetingOld.Builder(other);
    }
  }

  /**
   * Creates a new GreetingOld RecordBuilder by copying an existing GreetingOld instance.
   * @param other The existing instance to copy.
   * @return A new GreetingOld RecordBuilder
   */
  public static com.learnavro.GreetingOld.Builder newBuilder(com.learnavro.GreetingOld other) {
    if (other == null) {
      return new com.learnavro.GreetingOld.Builder();
    } else {
      return new com.learnavro.GreetingOld.Builder(other);
    }
  }

  /**
   * RecordBuilder for GreetingOld instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<GreetingOld>
    implements org.apache.avro.data.RecordBuilder<GreetingOld> {

    private java.lang.String greeting;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.learnavro.GreetingOld.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.greeting)) {
        this.greeting = data().deepCopy(fields()[0].schema(), other.greeting);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
    }

    /**
     * Creates a Builder by copying an existing GreetingOld instance
     * @param other The existing instance to copy.
     */
    private Builder(com.learnavro.GreetingOld other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.greeting)) {
        this.greeting = data().deepCopy(fields()[0].schema(), other.greeting);
        fieldSetFlags()[0] = true;
      }
    }

    /**
      * Gets the value of the 'greeting' field.
      * @return The value.
      */
    public java.lang.String getGreeting() {
      return greeting;
    }


    /**
      * Sets the value of the 'greeting' field.
      * @param value The value of 'greeting'.
      * @return This builder.
      */
    public com.learnavro.GreetingOld.Builder setGreeting(java.lang.String value) {
      validate(fields()[0], value);
      this.greeting = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'greeting' field has been set.
      * @return True if the 'greeting' field has been set, false otherwise.
      */
    public boolean hasGreeting() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'greeting' field.
      * @return This builder.
      */
    public com.learnavro.GreetingOld.Builder clearGreeting() {
      greeting = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public GreetingOld build() {
      try {
        GreetingOld record = new GreetingOld();
        record.greeting = fieldSetFlags()[0] ? this.greeting : (java.lang.String) defaultValue(fields()[0]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<GreetingOld>
    WRITER$ = (org.apache.avro.io.DatumWriter<GreetingOld>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<GreetingOld>
    READER$ = (org.apache.avro.io.DatumReader<GreetingOld>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.greeting);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.greeting = in.readString();

    } else {
      for (int i = 0; i < 1; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.greeting = in.readString();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










