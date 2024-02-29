package com.cibertec.assessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.assessment.beans.PolygonBean;
import com.cibertec.assessment.model.Polygon;
import com.cibertec.assessment.service.PolygonService;

@RestController
public class PolygonController {

	 	@Autowired
	    private PolygonService polygonService;

	    @PostMapping("/polygon/create")
	    public void createPolygon(@RequestBody Polygon polygon) {
	        polygonService.create(polygon);
	    }

	    @PostMapping("/polygon/createBatch")
	    public void createPolygons(@RequestBody List<Polygon> polygons) {
	        polygonService.create(polygons);
	    }

	    @GetMapping("/polygon/list")
	    public List<PolygonBean> listPolygons() {
	        return polygonService.list();
	    }
	    
		@PutMapping
		public ResponseEntity<Polygon> update( @RequestBody Polygon m) {
			return new ResponseEntity<Polygon>(polygonService.update(m),HttpStatus.CREATED);
		}
		
		@DeleteMapping("/{id}")
		public ResponseEntity<Void> delete(@PathVariable("id") int id) {
			polygonService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	
}
