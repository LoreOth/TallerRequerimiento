package com.taller.Proyecto.service;

import com.taller.Proyecto.entity.Campus;

//Interfaz EstadoEspacio
public interface  StateSpace {
    abstract void handle(Campus campus);

	String getName();
    
}


