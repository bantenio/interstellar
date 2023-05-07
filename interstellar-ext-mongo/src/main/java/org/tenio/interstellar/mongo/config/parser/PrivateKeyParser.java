package org.tenio.interstellar.mongo.config.parser;

import org.tenio.interstellar.buffer.Buffer;
import org.tenio.interstellar.mongo.exception.MongoException;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.util.Arrays;

public class PrivateKeyParser {
    private static final byte[] OID_RSA_PUBLIC_KEY = { 0x2A, (byte) 0x86, 0x48, (byte) 0x86, (byte) 0xF7, 0x0D, 0x01,
            0x01, 0x01 };
    /**
     * ASN.1 OID for EC public key.
     */
    private static final byte[] OID_EC_PUBLIC_KEY = { 0x2A, (byte) 0x86, 0x48, (byte) 0xCE, 0x3D, 0x02, 0x01 };

    /**
     * Gets the algorithm used by a PKCS#8 encoded private key.
     *
     * @param encodedKey The encoded private key.
     * @return The algorithm name, either <em>RSA</em> or <em>EC</em>, depending on
     *         the algorithm identifier found in the encoded key.
     * @throws MongoException if the key is not PKCS#8 encoded or uses an unsupported
     *         algorithm.
     */
    public static String getPKCS8EncodedKeyAlgorithm(byte[] encodedKey) {

        DerParser parser = new DerParser(encodedKey);
        Asn1Object sequence = parser.read();
        if (sequence.getType() != DerParser.SEQUENCE) {
            throw new MongoException("Invalid PKCS8 encoding: not a sequence");
        }

        parser = sequence.getParser();
        BigInteger version = parser.read().getInteger();
        if (version.intValue() != 0) {
            throw new MongoException("Unsupported version, expected 0 but found " + version.intValue());
        }

        sequence = parser.read();
        if (sequence.getType() != DerParser.SEQUENCE) {
            throw new MongoException("Invalid PKCS8 encoding: could not read Algorithm Identifier");
        }

        parser = sequence.getParser();
        byte[] algorithmIdentifier = parser.read().getObjectIdentifier();
        if (Arrays.equals(OID_RSA_PUBLIC_KEY, algorithmIdentifier)) {
            return "RSA";
        } else if (Arrays.equals(OID_EC_PUBLIC_KEY, algorithmIdentifier)) {
            return "EC";
        } else {
            throw new MongoException("Unsupported algorithm identifier");
        }
    }

    /**
     * Convert PKCS#1 encoded private key into RSAPrivateCrtKeySpec.
     * <p/>
     * <p/>The ASN.1 syntax for the private key with CRT is
     * <p/>
     * <pre>
     * --
     * -- Representation of RSA private key with information for the CRT algorithm.
     * --
     * RSAPrivateKey ::= SEQUENCE {
     *   version           Version,
     *   modulus           INTEGER,  -- n
     *   publicExponent    INTEGER,  -- e
     *   privateExponent   INTEGER,  -- d
     *   prime1            INTEGER,  -- p
     *   prime2            INTEGER,  -- q
     *   exponent1         INTEGER,  -- d mod (p-1)
     *   exponent2         INTEGER,  -- d mod (q-1)
     *   coefficient       INTEGER,  -- (inverse of q) mod p
     *   otherPrimeInfos   OtherPrimeInfos OPTIONAL
     * }
     * </pre>
     *
     * @param keyBytes PKCS#1 encoded key
     * @return KeySpec
     * @throws MongoException
     */
    public static RSAPrivateCrtKeySpec getRSAKeySpec(byte[] keyBytes) throws MongoException {
        DerParser parser = new DerParser(keyBytes);

        Asn1Object sequence = parser.read();
        if (sequence.getType() != DerParser.SEQUENCE) {
            throw new MongoException("Invalid DER: not a sequence");
        }

        // Parse inside the sequence
        parser = sequence.getParser();

        parser.read(); // Skip version
        BigInteger modulus = parser.read().getInteger();
        BigInteger publicExp = parser.read().getInteger();
        BigInteger privateExp = parser.read().getInteger();
        BigInteger prime1 = parser.read().getInteger();
        BigInteger prime2 = parser.read().getInteger();
        BigInteger exp1 = parser.read().getInteger();
        BigInteger exp2 = parser.read().getInteger();
        BigInteger crtCoef = parser.read().getInteger();

        return new RSAPrivateCrtKeySpec(
                modulus, publicExp, privateExp, prime1, prime2,
                exp1, exp2, crtCoef);
    }

    /**
     * A bare-minimum ASN.1 DER decoder, just having enough functions to
     * decode PKCS#1 private keys. Especially, it doesn't handle explicitly
     * tagged types with an outer tag.
     * <p/>
     * <p/>This parser can only handle one layer. To parse nested constructs,
     * get a new parser for each layer using {@code Asn1Object.getParser()}.
     * <p/>
     * <p/>There are many DER decoders in JRE but using them will tie this
     * program to a specific JCE/JVM.
     *
     * &#064;author zhang
     */
    static class DerParser {

        // Classes
        private final static int UNIVERSAL = 0x00;
        private final static int APPLICATION = 0x40;
        private final static int CONTEXT = 0x80;
        private final static int PRIVATE = 0xC0;

        // Constructed Flag
        private final static int CONSTRUCTED = 0x20;

        // Tag and data types
        private final static int ANY = 0x00;
        private final static int BOOLEAN = 0x01;
        private final static int INTEGER = 0x02;
        private final static int BIT_STRING = 0x03;
        private final static int OCTET_STRING = 0x04;
        private final static int NULL = 0x05;
        private final static int OBJECT_IDENTIFIER = 0x06;
        private final static int REAL = 0x09;
        private final static int ENUMERATED = 0x0a;

        private final static int SEQUENCE = 0x10;
        private final static int SET = 0x11;

        private final static int NUMERIC_STRING = 0x12;
        private final static int PRINTABLE_STRING = 0x13;
        private final static int VIDEOTEX_STRING = 0x15;
        private final static int IA5_STRING = 0x16;
        private final static int GRAPHIC_STRING = 0x19;
        private final static int ISO646_STRING = 0x1A;
        private final static int GENERAL_STRING = 0x1B;

        private final static int UTF8_STRING = 0x0C;
        private final static int UNIVERSAL_STRING = 0x1C;
        private final static int BMP_STRING = 0x1E;

        private final static int UTC_TIME = 0x17;

        private Buffer in;
        private int pos;

        /**
         * Create a new DER decoder from an input stream.
         *
         * @param in The DER encoded stream
         */
        DerParser(Buffer in) throws MongoException {
            this.in = in;
        }

        /**
         * Create a new DER decoder from a byte array.
         *
         * @param bytes The encoded bytes
         * @throws MongoException
         */
        DerParser(byte[] bytes) throws MongoException {
            this(Buffer.buffer(bytes));
        }

        private int readByte() throws MongoException {
            if (pos + 1 >= in.length()) {
                throw new MongoException("Invalid DER: stream too short, missing tag");
            }
            return in.getUnsignedByte(pos++);
        }

        private byte[] readBytes(int len) throws MongoException {
            if (pos + len > in.length()) {
                throw new MongoException("Invalid DER: stream too short, missing tag");
            }
            Buffer s = in.slice(pos, pos + len);
            pos += len;
            return s.getBytes();
        }

        /**
         * Read next object. If it's constructed, the value holds
         * encoded content and it should be parsed by a new
         * parser from {@code Asn1Object.getParser}.
         *
         * @return A object
         * @throws MongoException
         */
        public Asn1Object read() throws MongoException {
            int tag = readByte();
            int length = getLength();
            byte[] value = readBytes(length);
            return new Asn1Object(tag, length, value);
        }

        /**
         * Decode the length of the field. Can only support length
         * encoding up to 4 octets.
         * <p/>
         * <p/>In BER/DER encoding, length can be encoded in 2 forms,
         * <ul>
         * <li>Short form. One octet. Bit 8 has value "0" and bits 7-1
         * give the length.
         * <li>Long form. Two to 127 octets (only 4 is supported here).
         * Bit 8 of first octet has value "1" and bits 7-1 give the
         * number of additional length octets. Second and following
         * octets give the length, base 256, most significant digit first.
         * </ul>
         *
         * @return The length as integer
         * @throws MongoException
         */
        private int getLength() throws MongoException {

            int i = readByte();

            // A single byte short length
            if ((i & ~0x7F) == 0) {
                return i;
            }

            int num = i & 0x7F;

            // We can't handle length longer than 4 bytes
            if (i >= 0xFF || num > 4) {
                throw new MongoException("Invalid DER: length field too big ("
                        + i + ")");
            }

            byte[] bytes = readBytes(num);
            return new BigInteger(1, bytes).intValue();
        }
    }


    /**
     * An ASN.1 TLV. The object is not parsed. It can
     * only handle integers and strings.
     * <p>
     * &#064;author zhang
     */
    static class Asn1Object {

        protected final int type;
        protected final int length;
        protected final byte[] value;
        protected final int tag;

        /**
         * Construct a ASN.1 TLV. The TLV could be either a
         * constructed or primitive entity.
         * <p/>
         * <p/>The first byte in DER encoding is made of following fields,
         * <pre>
         * -------------------------------------------------
         * |Bit 8|Bit 7|Bit 6|Bit 5|Bit 4|Bit 3|Bit 2|Bit 1|
         * -------------------------------------------------
         * |  Class    | CF  |     +      Type             |
         * -------------------------------------------------
         * </pre>
         * <ul>
         * <li>Class: Universal, Application, Context or Private
         * <li>CF: Constructed flag. If 1, the field is constructed.
         * <li>Type: This is actually called tag in ASN.1. It
         * indicates data type (Integer, String) or a construct
         * (sequence, choice, set).
         * </ul>
         *
         * @param tag    Tag or Identifier
         * @param length Length of the field
         * @param value  Encoded octet string for the field.
         */
        public Asn1Object(int tag, int length, byte[] value) {
            this.tag = tag;
            this.type = tag & 0x1F;
            this.length = length;
            this.value = value;
        }

        public int getType() {
            return type;
        }

        public int getLength() {
            return length;
        }

        public byte[] getValue() {
            return value;
        }

        public boolean isConstructed() {
            return (tag & DerParser.CONSTRUCTED) == DerParser.CONSTRUCTED;
        }

        /**
         * For constructed field, return a parser for its content.
         *
         * @return A parser for the construct.
         * @throws MongoException
         */
        public DerParser getParser() throws MongoException {
            if (!isConstructed()) {
                throw new MongoException("Invalid DER: can't parse primitive entity");
            }

            return new DerParser(value);
        }

        /**
         * Get the value as integer
         *
         * @return BigInteger
         * @throws MongoException
         */
        public BigInteger getInteger() throws MongoException {
            if (type != DerParser.INTEGER) {
                throw new MongoException("Invalid DER: object is not integer");
            }

            return new BigInteger(value);
        }

        public byte[] getObjectIdentifier() throws MongoException {

            switch(type) {
                case DerParser.OBJECT_IDENTIFIER:
                    return value;
                default:
                    throw new MongoException("Invalid DER: object is not an Object Identifier");
            }
        }

        /**
         * Get value as string. Most strings are treated
         * as Latin-1.
         *
         * @return Java string
         * @throws MongoException
         */
        public String getString() throws MongoException {

            String encoding;

            switch (type) {

                // Not all are Latin-1 but it's the closest thing
                case DerParser.NUMERIC_STRING:
                case DerParser.PRINTABLE_STRING:
                case DerParser.VIDEOTEX_STRING:
                case DerParser.IA5_STRING:
                case DerParser.GRAPHIC_STRING:
                case DerParser.ISO646_STRING:
                case DerParser.GENERAL_STRING:
                    encoding = "ISO-8859-1";
                    break;

                case DerParser.BMP_STRING:
                    encoding = "UTF-16BE";
                    break;

                case DerParser.UTF8_STRING:
                    encoding = "UTF-8";
                    break;

                case DerParser.UNIVERSAL_STRING:
                    throw new MongoException("Invalid DER: can't handle UCS-4 string");

                default:
                    throw new MongoException("Invalid DER: object is not a string");
            }

            try {
                return new String(value, encoding);
            } catch (UnsupportedEncodingException e) {
                throw new MongoException(e);
            }
        }
    }
}
