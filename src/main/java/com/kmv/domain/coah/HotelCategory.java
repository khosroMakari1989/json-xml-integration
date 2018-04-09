package com.kmv.domain.coah;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author khosro.makari@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class HotelCategory {

    @XmlAttribute
    private String name;

    public String getName() {
        return name;
    }

}
