
package it.polimi.tiw.bean;

public class ShippingPolicyBean {

    private String shipCost;

    public String getShipCost() {

        return shipCost;
    }

    public void setShipCost(String shipCost) {

        this.shipCost = shipCost;
    }

    @Override
    public String toString() {

        return "ShippingPolicyBean{" + "shipCost='" + shipCost + '\'' + '}';
    }
}
