package com.example.DataLayer.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Shop", schema = "Clup", catalog = "")
public class ShopEntity {
    private int idShop;
    private Collection<QueueEntity> queuesByIdShop;

    @Id
    @Column(name = "idShop", nullable = false)
    public int getIdShop() {
        return idShop;
    }

    public void setIdShop(int idShop) {
        this.idShop = idShop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopEntity that = (ShopEntity) o;

        if (idShop != that.idShop) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return idShop;
    }

    @OneToMany(mappedBy = "shopByIdShop")
    public Collection<QueueEntity> getQueuesByIdShop() {
        return queuesByIdShop;
    }

    public void setQueuesByIdShop(Collection<QueueEntity> queuesByIdShop) {
        this.queuesByIdShop = queuesByIdShop;
    }
}
