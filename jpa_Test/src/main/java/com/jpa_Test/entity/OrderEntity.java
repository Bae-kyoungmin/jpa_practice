package com.jpa_Test.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "ORDERS")
public class OrderEntity {

	// 직접적인 인스턴스화를 방지하기 위한 private 생성자
    OrderEntity() {
        // 기본 생성자
    }

    // 빌더 메서드
    public static OrderEntity builder() {
        return new OrderEntity();
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "SP")
	private String sp;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSp() {
		return sp;
	}

	public void setSp(String sp) {
		this.sp = sp;
	}

	public void OrderEntityBuilder() {
        // 기본 생성자
    }
	
	public OrderEntity orderId(Long id) {
        this.id = id;
        return this;
    }
	
	public OrderEntity name(String name) {
		this.name = name;
		return this;
	}
	
	public OrderEntity sp(String sp) {
		this.sp = sp;
		return this;
	}
	
	public OrderEntity build() {
		OrderEntity order = new OrderEntity();
        order.setId(this.id);
        order.setName(this.name);
        order.setSp(this.sp);
        return order;
    }
}
