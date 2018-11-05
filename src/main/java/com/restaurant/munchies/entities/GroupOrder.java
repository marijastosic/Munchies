package com.restaurant.munchies.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "group_order")
public class GroupOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Basic(optional = false)
    @Column(name = "timeout")
    private int timeout;
    @Column(name = "created")
    private Date created;
    @Basic(optional = false)
    @Column(name = "creator")
    private String creator;
    @OneToMany(mappedBy = "groupOrderId")
    private List<Orders> ordersList;
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    @ManyToOne
    private Restaurant restaurantId;

//    @Transient
//    private String restaurantIdString;
    public GroupOrder() {
    }

    public GroupOrder(UUID id) {
        this.id = id;
    }

    public GroupOrder(GroupOrder groupOrder) {
        this.id = groupOrder.id;
        this.timeout = groupOrder.timeout;
        this.created = groupOrder.created;
        this.creator = groupOrder.creator;
        this.restaurantId = groupOrder.restaurantId;
    }

    public GroupOrder(UUID id, int timeout, String creator) {
        this.id = id;
        this.timeout = timeout;
        this.creator = creator;
    }

    public GroupOrder(@NotNull int timeout, Date created, @NotNull @Size(min = 1, max = 1000) String creator) {
        this.timeout = timeout;
        this.created = created;
        this.creator = creator;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    public Restaurant getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Restaurant restaurantId) {
        this.restaurantId = restaurantId;
    }

//    @Transient
//    public String getRestaurantIdString() {
//        return restaurantIdString;
//    }
//
//    public void setRestaurantIdString(String restaurantIdString) {
//        this.restaurantIdString = restaurantIdString;
//    }


    public double getTotal() {
        double total = 0;
        for(Orders orders : getOrdersList()) {
            total += orders.getPrice();
        }
        return total;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GroupOrder)) {
            return false;
        }
        GroupOrder other = (GroupOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GroupOrder{" +
                "id=" + id +
                ", timeout=" + timeout +
                ", created=" + created +
                ", creator='" + creator + '\'' +
                ", ordersList=" + ordersList +
                ", restaurantId=" + restaurantId +
                '}';
    }
}

