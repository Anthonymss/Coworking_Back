package com.coworking.spaces_service.controller;

import com.coworking.spaces_service.dto.EquipmentDto;
import com.coworking.spaces_service.dto.SpaceDto;
import com.coworking.spaces_service.service.impl.SpaceManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/management-spaces")
@RestController
@RequiredArgsConstructor
public class ManagementSpacesController {
    private final SpaceManagementService service;
    //spaces
    @PostMapping("space")
    public ResponseEntity<String> createSpace(){
        return new ResponseEntity<>(this.service.createSpace(), HttpStatus.CREATED);
    }
    /*@GetMapping("space")
    public ResponseEntity<List<SpaceDto>> getAllSpace(){
        return new ResponseEntity<>(this.service.getAllSpace(),HttpStatus.OK);
    }
    @GetMapping("space/types")
    public ResponseEntity<List<String>> getAllTypeSpace(){
        return new ResponseEntity<>(this.service.getAllTypeSpace(),HttpStatus.OK);
    }
    @PutMapping("space")
    public ResponseEntity<String> updateSpace(){
        return  new ResponseEntity<>(this.service.updateSpace(),HttpStatus.OK);
    }
    @DeleteMapping("space")
    public ResponseEntity<String> deleteSpaceById(){
        return  new ResponseEntity<>(this.service.deleteSpaceById(),HttpStatus.NO_CONTENT);
    }
    //site
    @PostMapping("site")
    public ResponseEntity<String> createSite() {
        return  new ResponseEntity<>(this.service.createSite(),HttpStatus.CREATED);
    }

    @GetMapping("site")
    public ResponseEntity<List<SiteDto>> getAllSite(){
        return  new ResponseEntity<>(this.service.getAllSite(),HttpStatus.OK);
    }
    @PutMapping("site")
    public ResponseEntity<String> updateSite(){
        return new ResponseEntity<>(this.service.updateSite(),HttpStatus.OK);
    }
    //equipment
    @PostMapping("equipment")
    public ResponseEntity<String> createEquipment(){
        return new ResponseEntity<>(this.service.createEquipment(),HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<List<EquipmentDto>> getAllEquipment(){
        return new ResponseEntity<>(this.service.getAllEquipment(),HttpStatus.OK);
    }
*/

}
