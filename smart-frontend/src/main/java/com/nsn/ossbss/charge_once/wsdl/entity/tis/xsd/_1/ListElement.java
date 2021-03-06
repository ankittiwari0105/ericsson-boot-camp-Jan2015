
package com.nsn.ossbss.charge_once.wsdl.entity.tis.xsd._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *         Is used for list which is part of a list.
 *       
 * 
 * <p>Java class for ListElement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListElement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Element" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}Element"/>
 *         &lt;element name="BooleanElement" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}BooleanElement"/>
 *         &lt;element name="ByteElement" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}ByteElement"/>
 *         &lt;element name="ShortElement" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}ShortElement"/>
 *         &lt;element name="IntElement" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}IntElement"/>
 *         &lt;element name="LongElement" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}LongElement"/>
 *         &lt;element name="FloatElement" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}FloatElement"/>
 *         &lt;element name="DoubleElement" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}DoubleElement"/>
 *         &lt;element name="DateElement" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}DateElement"/>
 *         &lt;element name="DateTimeElement" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}DateTimeElement"/>
 *         &lt;element name="DurationElement" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}DurationElement"/>
 *         &lt;element name="StringElement" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}StringElement"/>
 *         &lt;element name="HexBinaryElement" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}HexBinaryElement"/>
 *         &lt;element name="Base64BinaryElement" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}Base64BinaryElement"/>
 *         &lt;element name="EnumerationValueElement" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}EnumerationValueElement"/>
 *         &lt;element name="SymbolicElement" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}SymbolicElement"/>
 *         &lt;element name="VariableElement" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}VariableElement"/>
 *         &lt;element name="StructElement" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}StructElement"/>
 *         &lt;element name="ListElement" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}ListElement"/>
 *       &lt;/choice>
 *       &lt;attribute name="index" type="{http://nsn.com/ossbss/charge.once/wsdl/entity/Tis/xsd/1}Index" />
 *       &lt;attribute name="modifier" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="notification" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListElement", propOrder = {
    "elementOrBooleanElementOrByteElement"
})
public class ListElement {

    @XmlElements({
        @XmlElement(name = "EnumerationValueElement", type = EnumerationValueElement.class),
        @XmlElement(name = "VariableElement", type = VariableElement.class),
        @XmlElement(name = "FloatElement", type = FloatElement.class),
        @XmlElement(name = "IntElement", type = IntElement.class),
        @XmlElement(name = "SymbolicElement", type = SymbolicElement.class),
        @XmlElement(name = "DateElement", type = DateElement.class),
        @XmlElement(name = "StructElement", type = StructElement.class),
        @XmlElement(name = "DurationElement", type = DurationElement.class),
        @XmlElement(name = "HexBinaryElement", type = HexBinaryElement.class),
        @XmlElement(name = "ListElement", type = ListElement.class),
        @XmlElement(name = "ByteElement", type = ByteElement.class),
        @XmlElement(name = "DoubleElement", type = DoubleElement.class),
        @XmlElement(name = "DateTimeElement", type = DateTimeElement.class),
        @XmlElement(name = "StringElement", type = StringElement.class),
        @XmlElement(name = "Element", type = Element.class),
        @XmlElement(name = "BooleanElement", type = BooleanElement.class),
        @XmlElement(name = "ShortElement", type = ShortElement.class),
        @XmlElement(name = "Base64BinaryElement", type = Base64BinaryElement.class),
        @XmlElement(name = "LongElement", type = LongElement.class)
    })
    protected List<Object> elementOrBooleanElementOrByteElement;
    @XmlAttribute(name = "index")
    protected Long index;
    @XmlAttribute(name = "modifier")
    protected String modifier;
    @XmlAttribute(name = "notification")
    protected String notification;

    /**
     * Gets the value of the elementOrBooleanElementOrByteElement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the elementOrBooleanElementOrByteElement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getElementOrBooleanElementOrByteElement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EnumerationValueElement }
     * {@link VariableElement }
     * {@link FloatElement }
     * {@link IntElement }
     * {@link SymbolicElement }
     * {@link DateElement }
     * {@link StructElement }
     * {@link DurationElement }
     * {@link HexBinaryElement }
     * {@link ListElement }
     * {@link ByteElement }
     * {@link DoubleElement }
     * {@link DateTimeElement }
     * {@link StringElement }
     * {@link Element }
     * {@link BooleanElement }
     * {@link ShortElement }
     * {@link Base64BinaryElement }
     * {@link LongElement }
     * 
     * 
     */
    public List<Object> getElementOrBooleanElementOrByteElement() {
        if (elementOrBooleanElementOrByteElement == null) {
            elementOrBooleanElementOrByteElement = new ArrayList<Object>();
        }
        return this.elementOrBooleanElementOrByteElement;
    }

    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIndex(Long value) {
        this.index = value;
    }

    /**
     * Gets the value of the modifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * Sets the value of the modifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifier(String value) {
        this.modifier = value;
    }

    /**
     * Gets the value of the notification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotification() {
        return notification;
    }

    /**
     * Sets the value of the notification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotification(String value) {
        this.notification = value;
    }

}
