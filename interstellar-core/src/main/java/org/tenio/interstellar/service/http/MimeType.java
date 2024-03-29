package org.tenio.interstellar.service.http;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.lang.Assert;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.*;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: MimeType
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 14:04
 * &#064;version: 1.0
 */
public class MimeType implements Comparable<MimeType>, Serializable {

    private static final long serialVersionUID = 4085923477777865903L;
    /**
     * TODO
     */
    protected static final String WILDCARD_TYPE = "*";

    private static final String PARAM_CHARSET = "charset";

    private static final BitSet TOKEN;

    static {
        // variable names refer to RFC 2616, section 2.2
        BitSet ctl = new BitSet(128);
        for (int i = 0; i <= 31; i++) {
            ctl.set(i);
        }
        ctl.set(127);

        BitSet separators = new BitSet(128);
        separators.set('(');
        separators.set(')');
        separators.set('<');
        separators.set('>');
        separators.set('@');
        separators.set(',');
        separators.set(';');
        separators.set(':');
        separators.set('\\');
        separators.set('\"');
        separators.set('/');
        separators.set('[');
        separators.set(']');
        separators.set('?');
        separators.set('=');
        separators.set('{');
        separators.set('}');
        separators.set(' ');
        separators.set('\t');

        TOKEN = new BitSet(128);
        TOKEN.set(0, 128);
        TOKEN.andNot(ctl);
        TOKEN.andNot(separators);
    }

    /**
     * TODO
     */
    private final String type;
    /**
     * TODO
     */
    private final String subtype;
    /**
     * TODO
     */
    @SuppressWarnings("serial")
    private final Map<String, String> parameters;

    private transient Charset resolvedCharset;
    /**
     * TODO
     */
    private volatile String toStringValue;


    /**
     * Create a new {@code MimeType} for the given primary type.
     * <p>The {@linkplain #getSubtype() subtype} is set to <code>"&#42;"</code>,
     * and the parameters are empty.
     *
     * @param type the primary type
     * @throws IllegalArgumentException if any of the parameters contains illegal characters
     */
    public MimeType(String type) {
        this(type, WILDCARD_TYPE);
    }

    /**
     * Create a new {@code MimeType} for the given primary type and subtype.
     * <p>The parameters are empty.
     *
     * @param type    the primary type
     * @param subtype the subtype
     * @throws IllegalArgumentException if any of the parameters contains illegal characters
     */
    public MimeType(String type, String subtype) {
        this(type, subtype, Collections.emptyMap());
    }

    /**
     * Create a new {@code MimeType} for the given type, subtype, and character set.
     *
     * @param type    the primary type
     * @param subtype the subtype
     * @param charset the character set
     * @throws IllegalArgumentException if any of the parameters contains illegal characters
     */
    public MimeType(String type, String subtype, Charset charset) {
        this(type, subtype, Collections.singletonMap(PARAM_CHARSET, charset.name()));
        this.resolvedCharset = charset;
    }

    /**
     * Copy-constructor that copies the type, subtype, parameters of the given {@code MimeType},
     * and allows to set the specified character set.
     *
     * @param other   the other MimeType
     * @param charset the character set
     * @throws IllegalArgumentException if any of the parameters contains illegal characters
     * @since 4.3
     */
    public MimeType(MimeType other, Charset charset) {
        this(other.getType(), other.getSubtype(), addCharsetParameter(charset, other.getParameters()));
        this.resolvedCharset = charset;
    }

    /**
     * Copy-constructor that copies the type and subtype of the given {@code MimeType},
     * and allows for different parameter.
     *
     * @param other      the other MimeType
     * @param parameters the parameters (may be {@code null})
     * @throws IllegalArgumentException if any of the parameters contains illegal characters
     */
    public MimeType(MimeType other, Map<String, String> parameters) {
        this(other.getType(), other.getSubtype(), parameters);
    }

    /**
     * Create a new {@code MimeType} for the given type, subtype, and parameters.
     *
     * @param type       the primary type
     * @param subtype    the subtype
     * @param parameters the parameters (may be {@code null})
     * @throws IllegalArgumentException if any of the parameters contains illegal characters
     */
    public MimeType(String type, String subtype, Map<String, String> parameters) {
        Assert.notEmpty(type, "'type' must not be empty");
        Assert.notEmpty(subtype, "'subtype' must not be empty");
        checkToken(type);
        checkToken(subtype);
        this.type = type.toLowerCase(Locale.ENGLISH);
        this.subtype = subtype.toLowerCase(Locale.ENGLISH);
        if (CollectionUtil.isNotEmpty(parameters)) {
            Map<String, String> map = new LinkedHashMap<>(parameters.size());
            parameters.forEach((parameter, value) -> {
                checkParameters(parameter, value);
                map.put(parameter, value);
            });
            this.parameters = Collections.unmodifiableMap(map);
        } else {
            this.parameters = Collections.emptyMap();
        }
    }

    /**
     * Copy-constructor that copies the type, subtype and parameters of the given {@code MimeType},
     * skipping checks performed in other constructors.
     *
     * @param other the other MimeType
     * @since 5.3
     */
    protected MimeType(MimeType other) {
        this.type = other.type;
        this.subtype = other.subtype;
        this.parameters = other.parameters;
        this.resolvedCharset = other.resolvedCharset;
        this.toStringValue = other.toStringValue;
    }

    /**
     * Checks the given token string for illegal characters, as defined in RFC 2616,
     * section 2.2.
     *
     * @throws IllegalArgumentException in case of illegal characters
     * @see <a href="https://tools.ietf.org/html/rfc2616#section-2.2">HTTP 1.1, section 2.2</a>
     */
    private void checkToken(String token) {
        for (int i = 0; i < token.length(); i++) {
            char ch = token.charAt(i);
            if (!TOKEN.get(ch)) {
                throw new IllegalArgumentException("Invalid token character '" + ch + "' in token \"" + token + "\"");
            }
        }
    }

    /**
     * TODO
     *
     * @param parameter TODO
     * @param value     TODO
     */
    protected void checkParameters(String parameter, String value) {
        Assert.notEmpty(parameter, "'parameter' must not be empty");
        Assert.notEmpty(value, "'value' must not be empty");
        checkToken(parameter);
        if (PARAM_CHARSET.equals(parameter)) {
            if (this.resolvedCharset == null) {
                this.resolvedCharset = Charset.forName(unquote(value));
            }
        } else if (!isQuotedString(value)) {
            checkToken(value);
        }
    }

    private boolean isQuotedString(String s) {
        if (s.length() < 2) {
            return false;
        } else {
            return ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'")));
        }
    }

    /**
     * TODO
     *
     * @param s TODO
     * @return TODO
     */
    protected String unquote(String s) {
        return (isQuotedString(s) ? s.substring(1, s.length() - 1) : s);
    }

    /**
     * Indicates whether the {@linkplain #getType() type} is the wildcard character
     * <code>&#42;</code> or not.
     *
     * @return TODO
     */
    public boolean isWildcardType() {
        return WILDCARD_TYPE.equals(getType());
    }

    /**
     * Indicates whether the {@linkplain #getSubtype() subtype} is the wildcard
     * character <code>&#42;</code> or the wildcard character followed by a suffix
     * (e.g. <code>&#42;+xml</code>).
     *
     * @return whether the subtype is a wildcard
     */
    public boolean isWildcardSubtype() {
        return WILDCARD_TYPE.equals(getSubtype()) || getSubtype().startsWith("*+");
    }

    /**
     * Indicates whether this MIME Type is concrete, i.e. whether neither the type
     * nor the subtype is a wildcard character <code>&#42;</code>.
     *
     * @return whether this MIME Type is concrete
     */
    public boolean isConcrete() {
        return !isWildcardType() && !isWildcardSubtype();
    }

    /**
     * Return the primary type.
     *
     * @return TODO
     */
    public String getType() {
        return this.type;
    }

    /**
     * Return the subtype.
     *
     * @return TODO
     */
    public String getSubtype() {
        return this.subtype;
    }

    /**
     * Return the subtype suffix as defined in RFC 6839.
     *
     * @return TODO
     * @since 5.3
     */
    public String getSubtypeSuffix() {
        int suffixIndex = this.subtype.lastIndexOf('+');
        if (suffixIndex != -1 && this.subtype.length() > suffixIndex) {
            return this.subtype.substring(suffixIndex + 1);
        }
        return null;
    }

    /**
     * Return the character set, as indicated by a {@code charset} parameter, if any.
     *
     * @return the character set, or {@code null} if not available
     * @since 4.3
     */
    public Charset getCharset() {
        return this.resolvedCharset;
    }

    /**
     * Return a generic parameter value, given a parameter name.
     *
     * @param name the parameter name
     * @return the parameter value, or {@code null} if not present
     */
    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    /**
     * Return all generic parameter values.
     *
     * @return a read-only map (possibly empty, never {@code null})
     */
    public Map<String, String> getParameters() {
        return this.parameters;
    }

    /**
     * Indicate whether this MIME Type includes the given MIME Type.
     * <p>For instance, {@code text/*} includes {@code text/plain} and {@code text/html},
     * and {@code application/*+xml} includes {@code application/soap+xml}, etc.
     * This method is <b>not</b> symmetric.
     *
     * @param other the reference MIME Type with which to compare
     * @return {@code true} if this MIME Type includes the given MIME Type;
     * {@code false} otherwise
     */
    public boolean includes(MimeType other) {
        if (other == null) {
            return false;
        }
        if (isWildcardType()) {
            // */* includes anything
            return true;
        } else if (getType().equals(other.getType())) {
            if (getSubtype().equals(other.getSubtype())) {
                return true;
            }
            if (isWildcardSubtype()) {
                // Wildcard with suffix, e.g. application/*+xml
                int thisPlusIdx = getSubtype().lastIndexOf('+');
                if (thisPlusIdx == -1) {
                    return true;
                } else {
                    // application/*+xml includes application/soap+xml
                    int otherPlusIdx = other.getSubtype().lastIndexOf('+');
                    if (otherPlusIdx != -1) {
                        String thisSubtypeNoSuffix = getSubtype().substring(0, thisPlusIdx);
                        String thisSubtypeSuffix = getSubtype().substring(thisPlusIdx + 1);
                        String otherSubtypeSuffix = other.getSubtype().substring(otherPlusIdx + 1);
                        if (thisSubtypeSuffix.equals(otherSubtypeSuffix) && WILDCARD_TYPE.equals(thisSubtypeNoSuffix)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Indicate whether this MIME Type is compatible with the given MIME Type.
     * <p>For instance, {@code text/*} is compatible with {@code text/plain},
     * {@code text/html}, and vice versa. In effect, this method is similar to
     * {@link #includes}, except that it <b>is</b> symmetric.
     *
     * @param other the reference MIME Type with which to compare
     * @return {@code true} if this MIME Type is compatible with the given MIME Type;
     * {@code false} otherwise
     */
    public boolean isCompatibleWith(MimeType other) {
        if (other == null) {
            return false;
        }
        if (isWildcardType() || other.isWildcardType()) {
            return true;
        } else if (getType().equals(other.getType())) {
            if (getSubtype().equals(other.getSubtype())) {
                return true;
            }
            if (isWildcardSubtype() || other.isWildcardSubtype()) {
                String thisSuffix = getSubtypeSuffix();
                String otherSuffix = other.getSubtypeSuffix();
                if (getSubtype().equals(WILDCARD_TYPE) || other.getSubtype().equals(WILDCARD_TYPE)) {
                    return true;
                } else if (isWildcardSubtype() && thisSuffix != null) {
                    return (thisSuffix.equals(other.getSubtype()) || thisSuffix.equals(otherSuffix));
                } else if (other.isWildcardSubtype() && otherSuffix != null) {
                    return (this.getSubtype().equals(otherSuffix) || otherSuffix.equals(thisSuffix));
                }
            }
        }
        return false;
    }

    /**
     * Similar to {@link #equals(Object)} but based on the type and subtype
     * only, i.e. ignoring parameters.
     *
     * @param other the other mime type to compare to
     * @return whether the two mime types have the same type and subtype
     * @since 5.1.4
     */
    public boolean equalsTypeAndSubtype(MimeType other) {
        if (other == null) {
            return false;
        }
        return this.type.equalsIgnoreCase(other.type) && this.subtype.equalsIgnoreCase(other.subtype);
    }

    /**
     * Unlike {@link Collection#contains(Object)} which relies on
     * {@link MimeType#equals(Object)}, this method only checks the type and the
     * subtype, but otherwise ignores parameters.
     *
     * @param mimeTypes the list of mime types to perform the check against
     * @return whether the list contains the given mime type
     * @since 5.1.4
     */
    public boolean isPresentIn(Collection<? extends MimeType> mimeTypes) {
        for (MimeType mimeType : mimeTypes) {
            if (mimeType.equalsTypeAndSubtype(this)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MimeType)) {
            return false;
        }
        MimeType otherType = (MimeType) other;
        return (this.type.equalsIgnoreCase(otherType.type) &&
                this.subtype.equalsIgnoreCase(otherType.subtype) &&
                parametersAreEqual(otherType));
    }

    /**
     * Determine if the parameters in this {@code MimeType} and the supplied
     * {@code MimeType} are equal, performing case-insensitive comparisons
     * for {@link Charset Charsets}.
     *
     * @since 4.2
     */
    private boolean parametersAreEqual(MimeType other) {
        if (this.parameters.size() != other.parameters.size()) {
            return false;
        }

        for (Map.Entry<String, String> entry : this.parameters.entrySet()) {
            String key = entry.getKey();
            if (!other.parameters.containsKey(key)) {
                return false;
            }
            if (PARAM_CHARSET.equals(key)) {
                if (CompareUtil.compare(getCharset(), other.getCharset(), false) != 0) {
                    return false;
                }
            } else if (CompareUtil.compare(entry.getValue(), other.parameters.get(key), false) != 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = this.type.hashCode();
        result = 31 * result + this.subtype.hashCode();
        result = 31 * result + this.parameters.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String value = this.toStringValue;
        if (value == null) {
            StringBuilder builder = new StringBuilder();
            appendTo(builder);
            value = builder.toString();
            this.toStringValue = value;
        }
        return value;
    }

    /**
     * TODO
     *
     * @param builder TODO
     */
    protected void appendTo(StringBuilder builder) {
        builder.append(this.type);
        builder.append('/');
        builder.append(this.subtype);
        appendTo(this.parameters, builder);
    }

    private void appendTo(Map<String, String> map, StringBuilder builder) {
        map.forEach((key, val) -> {
            builder.append(';');
            builder.append(key);
            builder.append('=');
            builder.append(val);
        });
    }

    /**
     * Compares this MIME Type to another alphabetically.
     *
     * @param other the MIME Type to compare to
     */
    @Override
    public int compareTo(MimeType other) {
        int comp = getType().compareToIgnoreCase(other.getType());
        if (comp != 0) {
            return comp;
        }
        comp = getSubtype().compareToIgnoreCase(other.getSubtype());
        if (comp != 0) {
            return comp;
        }
        comp = getParameters().size() - other.getParameters().size();
        if (comp != 0) {
            return comp;
        }

        TreeSet<String> thisAttributes = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        thisAttributes.addAll(getParameters().keySet());
        TreeSet<String> otherAttributes = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        otherAttributes.addAll(other.getParameters().keySet());
        Iterator<String> thisAttributesIterator = thisAttributes.iterator();
        Iterator<String> otherAttributesIterator = otherAttributes.iterator();

        while (thisAttributesIterator.hasNext()) {
            String thisAttribute = thisAttributesIterator.next();
            String otherAttribute = otherAttributesIterator.next();
            comp = thisAttribute.compareToIgnoreCase(otherAttribute);
            if (comp != 0) {
                return comp;
            }
            if (PARAM_CHARSET.equals(thisAttribute)) {
                Charset thisCharset = getCharset();
                Charset otherCharset = other.getCharset();
                if (thisCharset != otherCharset) {
                    if (thisCharset == null) {
                        return -1;
                    }
                    if (otherCharset == null) {
                        return 1;
                    }
                    comp = thisCharset.compareTo(otherCharset);
                    if (comp != 0) {
                        return comp;
                    }
                }
            } else {
                String thisValue = getParameters().get(thisAttribute);
                String otherValue = other.getParameters().get(otherAttribute);
                if (otherValue == null) {
                    otherValue = "";
                }
                comp = thisValue.compareTo(otherValue);
                if (comp != 0) {
                    return comp;
                }
            }
        }

        return 0;
    }

    /**
     * Indicates whether this {@code MimeType} is more specific than the given
     * type.
     * <ol>
     * <li>if this mime type has a {@linkplain #isWildcardType() wildcard type},
     * and the other does not, then this method returns {@code false}.</li>
     * <li>if this mime type does not have a {@linkplain #isWildcardType() wildcard type},
     * and the other does, then this method returns {@code true}.</li>
     * <li>if this mime type has a {@linkplain #isWildcardType() wildcard type},
     * and the other does not, then this method returns {@code false}.</li>
     * <li>if this mime type does not have a {@linkplain #isWildcardType() wildcard type},
     * and the other does, then this method returns {@code true}.</li>
     * <li>if the two mime types have identical {@linkplain #getType() type} and
     * {@linkplain #getSubtype() subtype}, then the mime type with the most
     * parameters is more specific than the other.</li>
     * <li>Otherwise, this method returns {@code false}.</li>
     * </ol>
     *
     * @param other the {@code MimeType} to be compared
     * @return the result of the comparison
     * @see #isLessSpecific(MimeType)
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.3.2">HTTP 1.1: Semantics
     * and Content, section 5.3.2</a>
     * @since 6.0
     */
    public boolean isMoreSpecific(MimeType other) {
        Assert.notNull(other, "Other must not be null");
        boolean thisWildcard = isWildcardType();
        boolean otherWildcard = other.isWildcardType();
        if (thisWildcard && !otherWildcard) {  // */* > audio/*
            return false;
        } else if (!thisWildcard && otherWildcard) {  // audio/* < */*
            return true;
        } else {
            boolean thisWildcardSubtype = isWildcardSubtype();
            boolean otherWildcardSubtype = other.isWildcardSubtype();
            if (thisWildcardSubtype && !otherWildcardSubtype) {  // audio/* > audio/basic
                return false;
            } else if (!thisWildcardSubtype && otherWildcardSubtype) {  // audio/basic < audio/*
                return true;
            } else if (getType().equals(other.getType()) && getSubtype().equals(other.getSubtype())) {
                int paramsSize1 = getParameters().size();
                int paramsSize2 = other.getParameters().size();
                return paramsSize1 > paramsSize2;
            } else {
                return false;
            }
        }
    }

    /**
     * Indicates whether this {@code MimeType} is more less than the given type.
     * <ol>
     * <li>if this mime type has a {@linkplain #isWildcardType() wildcard type},
     * and the other does not, then this method returns {@code true}.</li>
     * <li>if this mime type does not have a {@linkplain #isWildcardType() wildcard type},
     * and the other does, then this method returns {@code false}.</li>
     * <li>if this mime type has a {@linkplain #isWildcardType() wildcard type},
     * and the other does not, then this method returns {@code true}.</li>
     * <li>if this mime type does not have a {@linkplain #isWildcardType() wildcard type},
     * and the other does, then this method returns {@code false}.</li>
     * <li>if the two mime types have identical {@linkplain #getType() type} and
     * {@linkplain #getSubtype() subtype}, then the mime type with the least
     * parameters is less specific than the other.</li>
     * <li>Otherwise, this method returns {@code false}.</li>
     * </ol>
     *
     * @param other the {@code MimeType} to be compared
     * @return the result of the comparison
     * @see #isMoreSpecific(MimeType)
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.3.2">HTTP 1.1: Semantics
     * and Content, section 5.3.2</a>
     * @since 6.0
     */
    public boolean isLessSpecific(MimeType other) {
        Assert.notNull(other, "Other must not be null");
        return other.isMoreSpecific(this);
    }

    /**
     * TODO
     *
     * @param ois TODO
     * @throws IOException            TODO
     * @throws ClassNotFoundException TODO
     */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        // Rely on default serialization, just initialize state after deserialization.
        ois.defaultReadObject();

        // Initialize transient fields.
        String charsetName = getParameter(PARAM_CHARSET);
        if (charsetName != null) {
            this.resolvedCharset = Charset.forName(unquote(charsetName));
        }
    }


    /**
     * Parse the given String value into a {@code MimeType} object,
     * with this method name following the 'valueOf' naming convention
     *
     * @param value TODO
     * @return TODO
     * @see MimeTypeUtils#parseMimeType(String)
     */
    public static MimeType valueOf(String value) {
        return MimeTypeUtils.parseMimeType(value);
    }

    private static Map<String, String> addCharsetParameter(Charset charset, Map<String, String> parameters) {
        Map<String, String> map = new LinkedHashMap<>(parameters);
        map.put(PARAM_CHARSET, charset.name());
        return map;
    }

}
