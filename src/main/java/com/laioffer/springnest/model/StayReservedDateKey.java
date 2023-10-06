package com.laioffer.springnest.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

// It's used to represent a composite primary key (a primary key composed of multiple columns) in the StayReservedDate entity.
@Embeddable
public class StayReservedDateKey implements Serializable
{
    private Long stay_id;
    private LocalDate date;

    public StayReservedDateKey() {
    }

    public StayReservedDateKey(Long stay_id, LocalDate date) {
        this.stay_id = stay_id;
        this.date = date;
    }

    public Long getStay_id() {
        return stay_id;
    }

    public void setStay_id(Long stay_id) {
        this.stay_id = stay_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    // The equals and hashCode are overridden to ensure that two StayReservedDateKey objects are considered equal if they have the same stay_id and date.
    // This equality check is crucial for JPA to work correctly with composite primary keys.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StayReservedDateKey that = (StayReservedDateKey) o;
        return Objects.equals(stay_id, that.stay_id) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stay_id, date);
    }
}

