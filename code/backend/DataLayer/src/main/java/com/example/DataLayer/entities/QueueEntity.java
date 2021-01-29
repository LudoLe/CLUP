package com.example.DataLayer.entities;

import javax.persistence.*;

@Entity
@Table(name = "Queue", schema = "Clup", catalog = "")
public class QueueEntity {
    private int idQueue;
    private int idShop;
    private ShopEntity shopByIdShop;

    @Id
    @Column(name = "idQueue", nullable = false)
    public int getIdQueue() {
        return idQueue;
    }

    public void setIdQueue(int idQueue) {
        this.idQueue = idQueue;
    }

    @Basic
    @Column(name = "id_shop", nullable = false)
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

        QueueEntity that = (QueueEntity) o;

        if (idQueue != that.idQueue) return false;
        if (idShop != that.idShop) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idQueue;
        result = 31 * result + idShop;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_shop", referencedColumnName = "idShop", nullable = false)
    public ShopEntity getShopByIdShop() {
        return shopByIdShop;
    }

    public void setShopByIdShop(ShopEntity shopByIdShop) {
        this.shopByIdShop = shopByIdShop;
    }
}
