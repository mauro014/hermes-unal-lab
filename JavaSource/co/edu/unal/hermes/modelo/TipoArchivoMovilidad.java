package co.edu.unal.hermes.modelo;


/********************************************************************************
Autor 		: Ing. Wilver Alexander Martínez Martínez - wam² - UN.
Clase    	: co.edu.unal.hermes.modelo.TipoInforme
Objetivo 	: Clase POJO para la tabla HER_TIPO_INFORME en la cual se encuentran  
			  los tipos de informes para un proyecto de investigación.
Creación	: Julio 09 de 2010
Modificación:
Detalle		:
********************************************************************************/

import java.io.Serializable;

public class TipoArchivoMovilidad implements Serializable{
	
	private static final long serialVersionUID = -979837507811169127L;
	public static String REVISION_REQUISITOS = "41";
	public static String REVISION_REQUISITOS_SEDE = "42";
	
	
	private Long id;
	private String nombre;
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}	
	
	@Override
	public boolean equals(Object otroObjeto) {
		TipoArchivoMovilidad otroDetalle = (TipoArchivoMovilidad) otroObjeto;
		
		if(id == null)
			return false;
		else
		{	
			if(id.equals(otroDetalle.getId())){
				return true;
			}else{
				return false;
			}
		}
		
	}
 
}
