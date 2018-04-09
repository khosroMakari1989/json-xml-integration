package com.kmv.domain.coah;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author khosro.makari@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Paragraph {

    @XmlAttribute
    private String headline;
    @XmlAttribute
    private String description;
    @XmlValue
    private String value;

}
