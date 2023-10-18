package com.taller.Proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.taller.Proyecto.dto.ObligatorySpaceResponseDto;
import com.taller.Proyecto.dto.ObligatoySpaceDto;
import com.taller.Proyecto.entity.ObligatorySpace;
import com.taller.Proyecto.mappers.ObligatorySpaceConverter;
import com.taller.Proyecto.service.ObligatorySpaceService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ObligatorySpaceController {
	@Autowired
    private final ObligatorySpaceService obligatorySpaceService;
    
   
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/obligatory-spaces/create")
    public ResponseEntity<ObligatoySpaceDto> createObligatorySpace(@RequestBody ObligatoySpaceDto dto) {
        ObligatorySpace obligatorySpace = obligatorySpaceService.createNewObligatorySpace(dto);
        System.out.println(obligatorySpace);
        
        ObligatoySpaceDto responseDto = convertToDto(obligatorySpace);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    
    
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/obligatory-spaces/cuit/{cuit}")
    public ResponseEntity<ObligatorySpaceResponseDto> getObligatorySpaceByCUIT(@PathVariable String cuit) {
        ObligatorySpace obligatorySpace = obligatorySpaceService.findByCUIT(cuit);
        if (obligatorySpace != null) {
        	ObligatorySpaceResponseDto dto = ObligatorySpaceConverter.toDTO(obligatorySpace);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    

    @Autowired
    public ObligatorySpaceController(ObligatorySpaceService obligatorySpaceService) {
        this.obligatorySpaceService = obligatorySpaceService;
    }
	// http://localhost:8080/obligatory-spaces/
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/obligatory-spaces")
	public ResponseEntity<List<ObligatoySpaceDto>> getAllObligatorySpaces() {
	    List<ObligatorySpace> obligatorySpaces = obligatorySpaceService.getAllObligatorySpaces();
	    List<ObligatoySpaceDto> obligatorySpaceDtos = convertToDto(obligatorySpaces);
	    return ResponseEntity.ok(obligatorySpaceDtos);
	}

	private List<ObligatoySpaceDto> convertToDto(List<ObligatorySpace> obligatorySpaces) {
	    List<ObligatoySpaceDto> dtos = new ArrayList<>();
	    for (ObligatorySpace space : obligatorySpaces) {
	    	ObligatoySpaceDto dto = new ObligatoySpaceDto();
	        dto.setName( space.getName());
	        dto.setProvince(space.getProvince());
	        dto.setCUIT(space.getCUIT());
	        dto.setId(space.getId());
	        dtos.add(dto);
	    }
	    return dtos;
	}
	private ObligatoySpaceDto convertToDto(ObligatorySpace space) {
	    ObligatoySpaceDto dto = new ObligatoySpaceDto();
	    dto.setName(space.getName());
	    dto.setProvince(space.getProvince());
	    dto.setCUIT(space.getCUIT());
	    dto.setId(space.getId());
	    return dto;
	}
}
	
	
	


