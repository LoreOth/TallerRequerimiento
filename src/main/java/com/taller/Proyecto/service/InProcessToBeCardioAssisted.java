package com.taller.Proyecto.service;

import com.taller.Proyecto.entity.ObligatorySpace;

//Concrete States:
public class InProcessToBeCardioAssisted extends StateSpace {
 @Override
 public void handle(ObligatorySpace space) {
     // Implement specific logic and transition for this state.
     // For example, change to the next state if certain conditions are met:
     // space.setState(new CardioAssisted());
 }
 
}
