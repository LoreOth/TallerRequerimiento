package com.taller.Proyecto.mappers;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ApiResponse<T> {
    private T data;
    private String error;
}
