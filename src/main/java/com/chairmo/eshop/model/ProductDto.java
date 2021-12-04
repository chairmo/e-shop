package com.chairmo.eshop.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDto {
	private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String description;

    private Integer stock;

    @Size(max = 255)
    private String imageUrl;

    private Integer sold;

    private Float cost;
}
