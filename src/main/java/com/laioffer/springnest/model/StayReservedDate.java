package com.laioffer.springnest.model;

import javax.persistence.*;

@Entity
@Table(name = "stay_reserved_date")
public class StayReservedDate {

    @EmbeddedId
    private StayReservedDateKey id;
    // The stay_id in StayReservedDateKey corresponds to the ID of the Stay associated with a given StayReservedDate.
    @MapsId("stay_id")
    // This relationship means that for one stay, there can be multiple reserved dates.
    @ManyToOne
    private Stay stay;

    public StayReservedDate() {
    }

    public StayReservedDate(StayReservedDateKey id, Stay stay) {
        this.id = id;
        this.stay = stay;
    }

    public StayReservedDateKey getId() {
        return id;
    }

    public Stay getStay() {
        return stay;
    }
}
