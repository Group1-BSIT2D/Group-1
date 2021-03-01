package com.oopclass.breadapp.models;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Entity
@Table(name = "reservations")
public class Problem {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private String first_name;
	private String last_name;
        private String middle_name;
        private String address;
	private LocalDate dor;
        private String service;
        private String late_customer;
        private String payment_time;
        private LocalDate created_at;
        private String status;
        private String refund;
        private String cancel;
        private String reason;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}
        
        public String getMiddleName() {
		return middle_name;
	}

	public void setMiddleName(String middle_name) {
		this.middle_name = middle_name;
	}
        
        public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public LocalDate getDor() {
		return dor;
	}

	public void setDor(LocalDate dor) {
		this.dor = dor;
	}
        
        public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}
        
        public String getRefund() {
		return refund;
	}

	public void setRefund(String refund) {
		this.refund = refund;
	}
        
        public String getPaymentTime() {
		return payment_time;
	}

	public void setPaymentTime(String payment_time) {
		this.payment_time = payment_time;
	}
        
        public LocalDate getCreatedAt() {
		return created_at;
	}

	public void setCreatedAt(LocalDate created_at) {
		this.created_at = created_at;
	}
        
        public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
        
        public String getCancelAnswer() {
		return cancel;
	}

	public void setCancelAnswer(String cancel) {
		this.cancel = cancel;
	}
        
        public String getCancelReason() {
		return cancel;
	}

	public void setCancelReason(String reason) {
		this.reason = reason;
	}
        
        public String getLateCustomer() {
		return late_customer;
	}
        
        public void setLateCustomer(String late_customer) {
		this.late_customer = late_customer;
	}

	@Override
	public String toString() {
		return "Problem [id=" + id + ", firstName=" + first_name + ", lastName=" + last_name + ", middleName=" + middle_name + ", address=" + address + ", dor=" + dor + ", service=" + service + ", lateCustomer=" + late_customer + ", paymentTime=" + payment_time + ", createdAt=" + created_at + "]";
	}

	
}
