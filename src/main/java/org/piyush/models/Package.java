package org.piyush.models;

import java.util.Random;

public class Package {
	private long id;
	private long orderId;
	private String status;
	private String trackingNumber;
	private String deliveryAddress;
	
	public Package() {
		
	}
	
	public Package(long orderId, String status, String trackingNumber, String deliveryAddress) {
		super();
		this.orderId = orderId;
		this.status = status;
		this.trackingNumber = trackingNumber;
		this.deliveryAddress = deliveryAddress;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	public String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numchars);
    }

	@Override
	public String toString() {
		return "Package [id=" + id + ", orderId=" + orderId + ", status=" + status + ", trackingNumber="
				+ trackingNumber + ", deliveryAddress=" + deliveryAddress + "]";
	}
	
}
