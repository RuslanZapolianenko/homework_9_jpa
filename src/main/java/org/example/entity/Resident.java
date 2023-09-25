package org.example.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "residents")
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ResidentID")
    private int id;

    @Column(name = "MemberID")
    private int memberId;

    @Column(name = "ApartmentID")
    private int apartmentId;

    @Column(name = "HasCarAccess")
    private boolean hasCarAccess;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    public boolean isHasCarAccess() {
        return hasCarAccess;
    }

    public void setHasCarAccess(boolean hasCarAccess) {
        this.hasCarAccess = hasCarAccess;
    }
}

