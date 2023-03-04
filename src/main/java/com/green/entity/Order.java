package com.green.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.qos.logback.core.status.Status;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "Orders")
public class Order  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@NotNull(message = "Địa chỉ không được để trống!")
	String address;

	
	@NotNull(message = "Số điện thoại không được để trống!")
   // @Pattern(regexp = "^(0|\\\\+84)(\\\\s|\\\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\\\d)(\\\\s|\\\\.)?(\\\\d{3})(\\\\s|\\\\.)?(\\\\d{3})$", message = "Không đúng định dạng!")
	String phone;
	String status = "Chờ xác nhận";
	@Temporal(TemporalType.DATE)
	@Column(name = "Createdate")
	Date createDate = new Date();
	@ManyToOne
	@JoinColumn(name = "Username")
	Account account;
	
	@JsonIgnore
	@OneToMany(mappedBy = "order")
	List<OrderDetail> orderDetails;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Order(Long id, String address, Date createDate, Account account, List<OrderDetail> orderDetails) {
		super();
		this.id = id;
		this.address = address;
		this.createDate = createDate;
		this.account = account;
		this.orderDetails = orderDetails;
	}

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}