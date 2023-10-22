package com.taller.Proyecto.service;

import com.taller.Proyecto.entity.Campus;
import com.taller.Proyecto.entity.ObligatorySpace;

//Concrete States:
public class InProcessToBeCardioAssisted implements StateSpace {


	    @Override
	    public String getName() {
	        return "En proceso de ser asistido";
	    }

		@Override
		public void handle(Campus campus) {
			// TODO Auto-generated method stub
			
		}

 
}
