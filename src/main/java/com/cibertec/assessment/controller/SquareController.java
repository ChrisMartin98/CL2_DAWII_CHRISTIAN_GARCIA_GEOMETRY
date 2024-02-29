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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.assessment.model.Square;
import com.cibertec.assessment.service.SquareService;


@RestController
@RequestMapping("/square")
public class SquareController {


	@Autowired
    private SquareService squareService;

	@PostMapping("/create")
    public ResponseEntity<Square> createSquare(@RequestBody Square square) {
        Square createdSquare = squareService.create(square);
        return new ResponseEntity<>(createdSquare, HttpStatus.CREATED);
    }

	@GetMapping("/list")
    public ResponseEntity<List<Square>> getAllSquares() {
        List<Square> squares = squareService.list();
        return new ResponseEntity<>(squares, HttpStatus.OK);
    }
	
    // Controlador para actualizar un cuadrado
	@PutMapping("/update")
	public ResponseEntity<Square> update( @RequestBody Square m) {
		return new ResponseEntity<Square>(squareService.update(m),HttpStatus.CREATED);
	}

    // Controlador para eliminar un cuadrado
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") int id) {
		squareService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	
	
}
