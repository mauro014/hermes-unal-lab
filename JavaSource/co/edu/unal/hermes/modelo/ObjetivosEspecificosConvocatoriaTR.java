/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Iterator;


/**
 * Maneja los objetivos específicos que enmarcan cada proyecto
 */
public class ObjetivosEspecificosConvocatoriaTR{
    
    private Long id;   
    private ConvocatoriaTerminosReferencia convocatoriaTR;
    private String descripcion;
    private Long orden;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ConvocatoriaTerminosReferencia getConvocatoriaTR() {
		return convocatoriaTR;
	}
	public void setConvocatoriaTR(ConvocatoriaTerminosReferencia convocatoriaTR) {
		this.convocatoriaTR = convocatoriaTR;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getOrden() {
		return orden;
	}
	public void setOrden(Long orden) {
		this.orden = orden;
	}
    
    
	
	
}
