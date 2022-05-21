package com.example.demo.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Upload.ImageUpload;
import com.example.demo.model.Contrat;
import com.example.demo.model.Sinistre;
import com.example.demo.model.User;
import com.example.demo.repository.ContratRepo;
import com.example.demo.repository.SinistreRepo;
import com.example.demo.repository.UserRepository;

import org.springframework.http.MediaType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/sinistres")
@CrossOrigin("*")
public class SinistreController {

    @Autowired
    private SinistreRepo sinistreR;
    @Autowired
    private ContratRepo contratRepo;
	@Autowired
    private UserRepository userRepository;
    
	@GetMapping
    public List<Sinistre> getAllSinistres(){
        return sinistreR.findAll();
    }
	
	@GetMapping("/username/{username}")
    public List<Sinistre> getAllSinistresByUsername(@PathVariable(value = "username")String username){
        return sinistreR.findByUsername(username);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Sinistre> getSinistreById(@PathVariable  long id){
        Sinistre sinistre = sinistreR.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sinistre not exist with id:" + id,"Sinistre not exist with id:" + id));
        return ResponseEntity.ok(sinistre);
    }
   
    
    @PostMapping("/Upload/{numPolicecontrat}/{username}")
   	public Sinistre uploadFileImage(@RequestParam("file") MultipartFile file,
   	@RequestParam String etat,
   	@RequestParam String lieu,
   	@RequestParam String dateSurvenance,
   	@RequestParam String type,
   	@PathVariable(name = "numPolicecontrat") int numPolicecontrat,
   	@PathVariable(name="username") String username
 
   	
 ) throws IOException { 
    	Sinistre sinistre =new Sinistre();
    	String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if(fileName.contains(".."))
		{
			System.out.println("not a a valid file");
		}
		try {
			sinistre.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		sinistre.setEtat(etat);
		sinistre.setLieu(lieu);
        Contrat contrat =contratRepo.findByNumPolice(numPolicecontrat);
        sinistre.setNumPolicecontrat(numPolicecontrat);
        sinistre.setContrat(contrat);
        sinistre.setDateSurvenance(dateSurvenance);
        sinistre.setType(type);
        User user=userRepository.findByUsername(username);
        sinistre.setUserSinistre(user);
        sinistre.setUsername(username);
   	  	sinistre.setDateDeclaration(new Date());
       	sinistre.setUpdatedAt(null);
       		
   		 return sinistreR.save(sinistre);
   	}
    
  
    
  
    
   
    @PutMapping("{id}")
    public ResponseEntity<Sinistre> updateSinistre(@PathVariable long id,@RequestParam("file") MultipartFile file,
    	   	@RequestParam String etat,
    	   	@RequestParam String lieu,
    	   	@RequestParam String dateSurvenance,
    	   	@RequestParam String type
    	   	) {
        Sinistre updateSinistre = sinistreR.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sinistre not exist with id: " + id, "Sinistre not exist with id: " + id));
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if(fileName.contains(".."))
		{
			System.out.println("not a a valid file");
		}
		try {
			updateSinistre.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
        updateSinistre.setDateSurvenance(dateSurvenance);
        updateSinistre.setEtat(etat);
		updateSinistre.setLieu(lieu);
		updateSinistre.setType(type);
		updateSinistre.setUpdatedAt(new Date());
        sinistreR.save(updateSinistre);

        return ResponseEntity.ok(updateSinistre);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteSinistre(@PathVariable long id){

        Sinistre sinistre = sinistreR.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sinistre not exist with id: " + id, null));

        sinistreR.delete(sinistre);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    
}