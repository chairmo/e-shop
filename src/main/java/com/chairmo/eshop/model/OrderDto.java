package com.chairmo.eshop.model;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.chairmo.eshop.domain.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
	private Long id;
    private Float totalPrice;
    @Size(max = 255)
    private String trackingNumber;
    private Status status;
    @NotNull
    private Long userId;
    
    List<OrderDetailsDto> detailsDtos;

}
