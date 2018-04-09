package com.kmv.domain.coah;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author khosro.makari@gmail.com
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class VideoURL {

    @XmlAttribute
    private String type;
    @XmlValue
    private String value;

    public String getType() {
        return type;
    }

}
