package com.restaurant.munchies.entities;

import
        javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "employee_name")
    private String employeeName;
    @Basic(optional = false)
    @Column(name = "item_name")
    private String itemName;
    @Basic(optional = false)
    @Column(name = "price")
    private double price;
    @JoinColumn(name = "group_order_id", referencedColumnName = "id")
    @ManyToOne
    private GroupOrder groupOrderId;

    public Orders() {
    }

    public Orders(Integer id) {
        this.id = id;
    }

    public Orders(Integer id, String employeeName, String itemName, double price) {
        this.id = id;
        this.employeeName = employeeName;
        this.itemName = itemName;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public GroupOrder getGroupOrderId() {
        return groupOrderId;
    }

    public void setGroupOrderId(GroupOrder groupOrderId) {
        this.groupOrderId = groupOrderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Employee name: " + employeeName + " " + "Item name: " + itemName + " " + "Price: " + price + "" + "\n";
    }
}