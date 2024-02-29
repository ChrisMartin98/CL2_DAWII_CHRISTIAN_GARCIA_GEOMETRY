package com.cibertec.assessment.service.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.assessment.beans.PolygonBean;
import com.cibertec.assessment.model.Polygon;
import com.cibertec.assessment.model.Square;
import com.cibertec.assessment.repo.SquareRepo;
import com.cibertec.assessment.service.PolygonService;
import com.cibertec.assessment.service.SquareService;

@Service
public class SquareServiceImpl implements SquareService{

	@Autowired 
	SquareRepo squareRepo;
	
	@Autowired
	PolygonService polygonService;
	
	//Al momento de crear se debe validar si 
	//alguno de parte del cuadrado se encuentra dentro de algun
	//poligono y de ser asi se debe capturar el id de los poligonos y 
	//guardar como un string pero con formato de array
	//Ejemplo polygons = "["1","2"] cumplen con la condicion"
	//Se guardan los ids correspondites
	//usar los metodos ya existentes para listar polygonos
	@Override
    public Square create(Square s) {
	    List<PolygonBean> polygons = polygonService.list();
	    List<Integer> intersectedPolygonIds = new ArrayList<>();

	    // Eliminar los espacios en blanco y corchetes de los puntos x y convertirlos a int[]
	    int[] xPoints = Arrays.stream(s.getXPoints().replaceAll("[\\[\\] ]", "").split(",")).mapToInt(Integer::parseInt).toArray();
	    int[] yPoints = Arrays.stream(s.getYPoints().replaceAll("[\\[\\] ]", "").split(",")).mapToInt(Integer::parseInt).toArray();

	    // Verificar la intersección con cada polígono
	    for (PolygonBean polygon : polygons) {
	        if (isIntersectingPolygon(xPoints, yPoints, polygon)) {
	            intersectedPolygonIds.add(polygon.getId());
	        }
	    }

	    // Guardar los IDs de los polígonos interceptados en el cuadrado
	    s.setPolygons(intersectedPolygonIds.toString());

	    return squareRepo.save(s);
    }



	private boolean isIntersectingPolygon(int[] xPoints, int[] yPoints, PolygonBean polygon) {
		int polySides = polygon.getNpoints();
		int[] polyXPoints = Arrays.stream(polygon.getXPoints()).mapToInt(Integer::intValue).toArray();
		int[] polyYPoints = Arrays.stream(polygon.getYPoints()).mapToInt(Integer::intValue).toArray();

	    // Verificar si algún punto del cuadrado está dentro del polígono
	    for (int i = 0, j = polySides - 1; i < polySides; j = i++) {
	        if (isInside(xPoints[0], yPoints[0], polyXPoints[i], polyYPoints[i], polyXPoints[j], polyYPoints[j])) {
	            return true;
	        }
	    }

	    // Verificar si alguna línea del cuadrado intersecta con alguna línea del polígono
	    for (int i = 0, j = polySides - 1; i < polySides; j = i++) {
	        for (int k = 0; k < xPoints.length; k++) {
	            if (lineIntersects(xPoints[k], yPoints[k], xPoints[(k + 1) % xPoints.length], yPoints[(k + 1) % xPoints.length],
	                    polyXPoints[i], polyYPoints[i], polyXPoints[j], polyYPoints[j])) {
	                return true;
	            }
	        }
	    }

	    return false;
	}



	// Método para verificar si un punto está dentro de un polígono (algoritmo de Ray Casting)
	private boolean isInside(int testX, int testY, int x1, int y1, int x2, int y2) {
	    return (y1 > testY) != (y2 > testY) && testX < (x2 - x1) * (testY - y1) / (y2 - y1) + x1;
	}

	// Método para verificar si dos líneas se intersectan
	private boolean lineIntersects(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
	    int d1 = direction(x3, y3, x4, y4, x1, y1);
	    int d2 = direction(x3, y3, x4, y4, x2, y2);
	    int d3 = direction(x1, y1, x2, y2, x3, y3);
	    int d4 = direction(x1, y1, x2, y2, x4, y4);
	    return ((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) && ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0));
	}

	// Método para calcular la dirección del vector formado por tres puntos
	private int direction(int x1, int y1, int x2, int y2, int x3, int y3) {
	    return (x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1);
	}


	@Override
    public List<Square> list() {
        return squareRepo.findAll();
    }



	@Override
	public Square update(Square s) {
		return squareRepo.save(s);
	}



	@Override
	public void delete(int s) {
		squareRepo.deleteById(s);
		
	}

   
	

}
