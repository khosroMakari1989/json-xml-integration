package com.kmv.domain.coah;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author khosro.makari@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Usps {

    @XmlElement(name = "hotelAttributes")
    private HotelAttributeList hotelAttributeList;

    public HotelAttributeList getHotelAttributeList() {
        return hotelAttributeList;
    }

}
