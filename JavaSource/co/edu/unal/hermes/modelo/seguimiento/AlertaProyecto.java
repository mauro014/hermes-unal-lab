package co.edu.unal.hermes.modelo.seguimiento;

import java.io.Serializable;
import java.util.Date;

import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoProducto;

public class AlertaProyecto implements Serializable {

	private static final long serialVersionUID = 8782831917193575610L;

    public static final String EN_PROCESO = "E";
    public static final String EN_PROCESO_ANLA = "A";
	
	private Long id;
	private String estado;
	private Date fechaGenera;
	private String mensaje;
	private Date fechaCierre;
	private Persona asesor;
	private Proyecto proyecto;
	private TipoAlerta tipoAlerta;
	private Solicitud solicitud;
	private String estadoMostrar;
	private ProyectoProducto proyectoProducto;
	private Long productosMora;
	private Long numeroNotificaciones;
	private Date fechaNotificacion;

	private String colorAlerta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;

		if (this.estado.equals("P")) {
			this.colorAlerta = "#FFC0CB";
			this.estadoMostrar = "Pendiente";
		} else if (this.estado.equals("E")) {
			this.colorAlerta = "#EEE8AA";
			this.estadoMostrar = "En proceso";
		} else if (this.estado.equals("A")) {
			this.colorAlerta = "#00CCFF";
			this.estadoMostrar = "En proceso (En estudio ANLA)";
		}else {
			this.colorAlerta = "#E6EEF7";
			this.estadoMostrar = "Cerrado";
		}
	}

	public Date getFechaGenera() {
		return fechaGenera;
	}

	public void setFechaGenera(Date fechaGenera) {
		this.fechaGenera = fechaGenera;
	}

	public Persona getAsesor() {
		return asesor;
	}

	public void setAsesor(Persona asesor) {
		this.asesor = asesor;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public TipoAlerta getTipoAlerta() {
		return tipoAlerta;
	}

	public void setTipoAlerta(TipoAlerta tipoAlerta) {
		this.tipoAlerta = tipoAlerta;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public String getColorAlerta() {
		return colorAlerta;
	}

	public void setColorAlerta(String colorAlerta) {
		this.colorAlerta = colorAlerta;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getEstadoMostrar() {
		return estadoMostrar;
	}

	public void setEstadoMostrar(String estadoMostrar) {
		this.estadoMostrar = estadoMostrar;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public ProyectoProducto getProyectoProducto() {
		return proyectoProducto;
	}

	public void setProyectoProducto(ProyectoProducto proyectoProducto) {
		this.proyectoProducto = proyectoProducto;
	}

	public Long getProductosMora() {
		return productosMora;
	}

	public void setProductosMora(Long productosMora) {
		this.productosMora = productosMora;
	}

	public Long getNumeroNotificaciones() {
		return numeroNotificaciones;
	}

	public void setNumeroNotificaciones(Long numeroNotificaciones) {
		this.numeroNotificaciones = numeroNotificaciones;
	}

	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}
}
