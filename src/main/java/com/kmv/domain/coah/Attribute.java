package com.kmv.domain.coah;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author khosro.makari@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Attribute {

    @XmlAttribute
    private String source;
    @XmlElement(name = "category")
    private List<HotelCategory> categories;

    public List<HotelCategory> getCategories() {
        return categories;
    }

}
