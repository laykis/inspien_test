package entity.Test;

import java.time.LocalDateTime;

//엔티티 입니다.
public class Test {

    public Test(){

    }

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

    public String getOrderNum() {
        return orderNum;
    }

    public String getItemSeq() {
        return itemSeq;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public String getOrderQty() {
        return orderQty;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverNo() {
        return receiverNo;
    }

    public String getEtaDate() {
        return etaDate;
    }

    public String getDestination() {
        return destination;
    }

    public String getDescription() {
        return description;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemQty() {
        return itemQty;
    }

    public String getItemColor() {
        return itemColor;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getSender() {
        return sender;
    }

    public LocalDateTime getCurrentDt() {
        return currentDt;
    }

    public TestDto entityToDto(){
        TestDto testDto = new TestDto();
        testDto.setOrderNum(this.orderNum);
        testDto.setItemSeq(this.itemSeq);
        testDto.setOrderId(this.orderId);
        testDto.setOrderDate(this.orderDate);
        testDto.setOrderPrice(this.orderPrice);
        testDto.setOrderQty(this.orderQty);
        testDto.setReceiverName(this.receiverName);
        testDto.setReceiverNo(this.receiverNo);
        testDto.setEtaDate(this.etaDate);
        testDto.setDestination(this.destination);
        testDto.setDescription(this.description);
        testDto.setItemName(this.itemName);
        testDto.setItemQty(this.itemQty);
        testDto.setItemColor(this.itemColor);
        testDto.setItemPrice(this.itemPrice);
        testDto.setSender(this.sender);
        testDto.setCurrentDt(this.currentDt);
        return testDto;
    }

    public static class Builder {

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


        public Builder orderNum(String orderNum) {
            this.orderNum = orderNum;
            return this;
        }
        public Builder itemSeq(String itemSeq) {
            this.itemSeq = itemSeq;
            return this;
        }
        public Builder orderId(String orderId) {
            this.orderId = orderId;
            return this;
        }
        public Builder orderDate(String orderDate) {
            this.orderDate = orderDate;
            return this;
        }
        public Builder orderPrice(String orderPrice) {
            this.orderPrice = orderPrice;
            return this;
        }
        public Builder orderQty(String orderQty) {
            this.orderQty = orderQty;
            return this;
        }
        public Builder receiverName(String receiverName) {
            this.receiverName = receiverName;
            return this;
        }
        public Builder receiverNo(String receiverNo) {
            this.receiverNo = receiverNo;
            return this;
        }
        public Builder etaDate(String etaDate) {
            this.etaDate = etaDate;
            return this;
        }
        public Builder destination(String destination) {
            this.destination = destination;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }
        public Builder itemQty(String itemQty) {
            this.itemQty = itemQty;
            return this;
        }
        public Builder itemColor(String itemColor) {
            this.itemColor = itemColor;
            return this;
        }
        public Builder itemPrice(String itemPrice) {
            this.itemPrice = itemPrice;
            return this;
        }
        public Builder sender(String sender) {
            this.sender = sender;
            return this;
        }
        public Builder currentDt(LocalDateTime currentDt) {
            this.currentDt = currentDt;
            return this;
        }
        public Test build() {
            Test t = new Test();
            t.orderNum = orderNum;
            t.itemSeq = itemSeq;
            t.orderId = orderId;
            t.orderDate = orderDate;
            t.orderPrice = orderPrice;
            t.orderQty = orderQty;
            t.receiverName = receiverName;
            t.receiverNo = receiverNo;
            t.etaDate = etaDate;
            t.destination = destination;
            t.description = description;
            t.itemName = itemName;
            t.itemQty = itemQty;
            t.itemColor = itemColor;
            t.itemPrice = itemPrice;
            t.sender = sender;
            t.currentDt = currentDt;
            return t;
        }


    }
}
