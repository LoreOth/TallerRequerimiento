package com.taller.Proyecto.service;

import com.taller.Proyecto.entity.Campus;
import com.taller.Proyecto.entity.ObligatorySpace;

public class CardioAssistedCertifiedExpired extends StateSpace {

	@Override
	void handle(Campus campus) {
		campus.setState(new CardioAssistedCertifiedExpired());
		
	}

}