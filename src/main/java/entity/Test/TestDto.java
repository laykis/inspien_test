package entity.Test;

import java.time.LocalDateTime;

public class TestDto {

    private String orderNum;
    private String itemSeq;
    private String orderId;
    private String orderDate;
    private String orderPrice;
    private String orderQty;
    private String receiverName;
    private String receiverNo;
    private String etaDate;
    private String destination;
    private String description;
    private String itemName;
    private String itemQty;
    private String itemColor;
    private String itemPrice;
    private String sender;
    private LocalDateTime currentDt;

    public Test dtoToEntity(){

        return new Test.Builder()
                .orderNum(this.orderNum)
                .itemSeq(this.itemSeq)
                .orderId(this.orderId)
                .orderDate(this.orderDate)
                .orderPrice(this.orderPrice)
                .orderQty(this.orderQty)
                .receiverName(this.receiverName)
                .receiverNo(this.receiverNo)
                .etaDate(this.etaDate)
                .destination(this.destination)
                .description(this.description)
                .itemName(this.itemName)
                .itemQty(this.itemQty)
                .itemColor(this.itemColor)
                .itemPrice(this.itemPrice)
                .sender(this.sender)
                .currentDt(this.currentDt)
                .build();
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(String itemSeq) {
        this.itemSeq = itemSeq;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(String orderQty) {
        this.orderQty = orderQty;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverNo() {
        return receiverNo;
    }

    public void setReceiverNo(String receiverNo) {
        this.receiverNo = receiverNo;
    }

    public String getEtaDate() {
        return etaDate;
    }

    public void setEtaDate(String etaDate) {
        this.etaDate = etaDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public LocalDateTime getCurrentDt() {
        return currentDt;
    }

    public void setCurrentDt(LocalDateTime currentDt) {
        this.currentDt = currentDt;
    }
}
