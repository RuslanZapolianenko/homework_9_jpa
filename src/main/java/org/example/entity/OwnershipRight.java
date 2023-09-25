package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ownershiprights")
public class OwnershipRight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OwnershipID")
    private int id;

    @Column(name = "ApartmentID")
    private int apartmentId;

    @ManyToOne
    @JoinColumn(name = "MemberID")
    private OSBBMember osbbMember;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OSBBMember getOsbbMember() {
        return osbbMember;
    }

    public void setOsbbMember(OSBBMember osbbMember) {
        this.osbbMember = osbbMember;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }
}
