package com.cibertec.assessment.beans;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SquareBean {

    private Integer id;
    private String name;
	private int npoints;	
	private Integer[] xPoints;
	private Integer[] yPoints;
	private String polygons; // se debe guardar un array con los poligonos que intercepte
}
