/*
 * Created on 28-marzo-2017
 */
package co.edu.unal.hermes.modelo.servicios;

import java.util.List;

import javax.servlet.http.HttpSession;

import co.edu.unal.hermes.modelo.Reporte;

/**
 * The Interface IServicioBiodiversidad.
 */
public interface IServicioAval {

    public void imprimirReporteAval(Long id, HttpSession sesion);

    public List<Reporte> obtenerListaCartas(String tipo);
}
