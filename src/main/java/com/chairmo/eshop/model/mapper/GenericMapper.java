package com.chairmo.eshop.model.mapper;

import java.util.List;


public interface GenericMapper<D, E> {

    D toDto(E entity);

    E toEntity(D dto);

    List<D> toListDto(List<E> entities);

    List<E> toListEntity(List<D> dtos);
}
