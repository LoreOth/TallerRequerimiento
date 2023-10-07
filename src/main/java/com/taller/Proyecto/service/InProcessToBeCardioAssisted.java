package com.taller.Proyecto.service;

import com.taller.Proyecto.entity.Campus;
import com.taller.Proyecto.entity.ObligatorySpace;

//Concrete States:
public class InProcessToBeCardioAssisted extends StateSpace {

	@Override
	void handle(Campus campus) {
		campus.setState(new InProcessToBeCardioAssisted());
		
	}

 
}
