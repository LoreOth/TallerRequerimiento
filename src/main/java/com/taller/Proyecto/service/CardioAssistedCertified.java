package com.taller.Proyecto.service;

import com.taller.Proyecto.entity.Campus;
import com.taller.Proyecto.entity.ObligatorySpace;

public class CardioAssistedCertified extends StateSpace {

	@Override
	void handle(Campus campus) {
		campus.setState(new CardioAssistedCertified());
		
	}


    
}