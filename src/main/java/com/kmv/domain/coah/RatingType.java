package com.kmv.domain.coah;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 *
 * @author khosro.makari@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RatingType {

    private Float overall;
    private Float single;
    private Float couple;
    private Float family;
    private Float friends;
    private Float beach;
    private Float wintersport;
    private Float other;

    public Float getFriends() {
        return friends;
    }

}
