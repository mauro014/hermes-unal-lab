package co.edu.unal.hermes.modelo;

import java.util.HashSet;
import java.util.Set;

public class SesionCurso {
    private Long id;
    private Proyecto proyecto;
    private Long numero;
    private String tema;
    private String contenido;

    // Los gastos de cada fuente de financiacion
    private Set materiales = new HashSet();
    
    public void adicionarMaterialSesion(materialSesion materialSesion){
	materialSesion.setSesion(this);
	materiales.add(materialSesion);
    }
    
    public void borrarMaterialSesion(materialSesion materialSesion){
	materiales.remove(materialSesion);
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Proyecto getProyecto() {
	return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
	this.proyecto = proyecto;
    }

    public Long getNumero() {
	return numero;
    }

    public void setNumero(Long numero) {
	this.numero = numero;
    }

    public String getTema() {
	return tema;
    }

    public void setTema(String tema) {
	this.tema = tema;
    }

    public String getContenido() {
	return contenido;
    }

    public void setContenido(String contenido) {
	this.contenido = contenido;
    }

    public Set getMateriales() {
	return materiales;
    }

    public void setMateriales(Set materiales) {
	this.materiales = materiales;
    }

}