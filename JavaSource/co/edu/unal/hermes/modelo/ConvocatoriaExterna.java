package co.edu.unal.hermes.modelo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.PropertyAccessException;

public class ConvocatoriaExterna implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected static final Long CONVOCATORIA_INTERNACIONAL = 376L;
    private Long id;
    private String nombre = "";
    private String numero;
    private FuenteFinanciacion entidad;
    private String estado;
    private Date fechaApertura;
    private Date fechaCierre;
    private Date fechaResultados;
    private Date fechaMaxRegistro;    
    private Persona personaAbre;
    private Persona personaCierra;
    private Persona personaSolicita;
    private String vinculo; // Variable temporal no mapeada
    private String tipo; // variable temporal no mapeada
    private String urlExterna;
    private boolean visibilidad;
    private Tipos naturaleza;
    private Long convocatoriaGrupos = 0L;
    private int notificaciones;
    private Date fechaNotificacion;
    private Long exigeContrapartida = 0L;
    private Long contrapartidaCriterioEvaluacion;
    private Boolean visible;
    private boolean convocatoriaCerrada;
    private String tipoAvalConvocatoria;
    private String mensajeAvalConvocatoria;
    
    private Set<CorteConvocatoriaExterna> cortes = new HashSet<CorteConvocatoriaExterna>();

    /** default constructor */
    public ConvocatoriaExterna() {
        // Para objeto vacio
    }

    public ConvocatoriaExterna(Long id, String estado, Date fechaCierre) {
        this.id = id;
        this.estado = estado;
        this.fechaCierre = fechaCierre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public FuenteFinanciacion getEntidad() {
        return entidad;
    }

    public void setEntidad(FuenteFinanciacion entidad) {
        this.entidad = entidad;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    /**
	 * @return the notificaciones
	 */
	public int getNotificaciones() {
		return notificaciones;
	}

	/**
	 * @param notificaciones the notificaciones to set
	 */
	public void setNotificaciones(int notificaciones) {
		this.notificaciones = notificaciones;
	}

	public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Persona getPersonaAbre() {
        return personaAbre;
    }

    public void setPersonaAbre(Persona personaAbre) {
        this.personaAbre = personaAbre;
    }

    public Persona getPersonaCierra() {
        return personaCierra;
    }

    public void setPersonaCierra(Persona personaCierra) {
        this.personaCierra = personaCierra;
    }

    public boolean isVisibilidad() {
        if ("A".equals(this.estado)) {
            visibilidad = true;
        } else if ("I".equals(this.estado) || "S".equals(this.estado)) {
            visibilidad = false;
        }
        if (this.fechaCierre != null && "A".equals(this.estado)) {
            Date hoy = new Date();
            if (this.fechaCierre.before(hoy)) {
                visibilidad = false;
            }
        }

        return visibilidad;
    }

    public static Comparator<ConvocatoriaExterna> ConvNombreEstado = new Comparator<ConvocatoriaExterna>() {

        public int compare(ConvocatoriaExterna s1, ConvocatoriaExterna s2) {
            String nombre1 = s1.getEstado().toUpperCase();
            String nombre2 = s2.getEstado().toUpperCase();

            // ascending order
            return nombre1.compareTo(nombre2);

        }
    };

    public void setVisibilidad(boolean visibilidad) {
        this.visibilidad = visibilidad;
    }

    public Persona getPersonaSolicita() {
        return personaSolicita;
    }

    public void setPersonaSolicita(Persona personaSolicita) {
        this.personaSolicita = personaSolicita;
    }

    public String getVinculo() {
        return vinculo;
    }

    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUrlExterna() {
        return urlExterna;
    }

    public void setUrlExterna(String urlExterna) {
        this.urlExterna = urlExterna;
    }

    public Date getFechaMaxRegistro() {
        if (this.fechaMaxRegistro == null) {
            if (this.fechaCierre != null) {

                Calendar fechaLimite = new GregorianCalendar();
                fechaLimite.setTime(this.fechaCierre);

                int dias = 2;

                if (this.isEsInternacional()) {
                    dias = 3;
                }

                if (fechaLimite.get(Calendar.DAY_OF_WEEK) == 7 || fechaLimite.get(Calendar.DAY_OF_WEEK) == 1) {
                    dias = dias + 1;
                } else if (fechaLimite.get(Calendar.DAY_OF_WEEK) == 2 || fechaLimite.get(Calendar.DAY_OF_WEEK) == 3) {
                    dias = dias + 2;
                }
                fechaLimite.add(Calendar.DAY_OF_YEAR, dias * -1);
                fechaMaxRegistro = fechaLimite.getTime();
            } else {
                fechaMaxRegistro = new Date();
            }
            Calendar fecha = new GregorianCalendar();
            fecha.setTime(fechaMaxRegistro);
            fecha.set(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH), fecha.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
            fechaMaxRegistro = fecha.getTime();
        }
        return fechaMaxRegistro;
    }

    public void setFechaMaxRegistro(Date fechaMaxRegistro) {
        this.fechaMaxRegistro = fechaMaxRegistro;
    }

    public Tipos getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(Tipos naturaleza) {
        this.naturaleza = naturaleza;
    }

    public boolean isEsInternacional() {
        if (this.naturaleza != null && this.naturaleza.getId() != null
                && this.naturaleza.getId().equals(CONVOCATORIA_INTERNACIONAL)) {
            return true;
        }
        return false;
    }

    public Long getConvocatoriaGrupos() {
        return convocatoriaGrupos;
    }
    
    public void setConvocatoriaGrupos(Long convocatoriaGrupos) {
    	if(convocatoriaGrupos != null)	this.convocatoriaGrupos = convocatoriaGrupos;    	
    }

	/**
	 * @return the fechaResultados
	 */
	public Date getFechaResultados() {
		return fechaResultados;
	}

	/**
	 * @param fechaResultados the fechaResultados to set
	 */
	public void setFechaResultados(Date fechaResultados) {
		this.fechaResultados = fechaResultados;
	}

	/**
	 * @return the fechaNotificaciones
	 */
	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	/**
	 * @param fechaNotificaciones the fechaNotificaciones to set
	 */
	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Long getExigeContrapartida() {
		return exigeContrapartida;
	}

	public void setExigeContrapartida(Long exigeContrapartida) {
		this.exigeContrapartida = exigeContrapartida;
	}

	public Long getContrapartidaCriterioEvaluacion() {
		return contrapartidaCriterioEvaluacion;
	}

	public void setContrapartidaCriterioEvaluacion(Long contrapartidaCriterioEvaluacion) {
		this.contrapartidaCriterioEvaluacion = contrapartidaCriterioEvaluacion;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public boolean isConvocatoriaCerrada() {
		// Get the current date
        Date today = new Date();
        convocatoriaCerrada = false;
        
        if(getFechaCierre()!=null) {
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
	        c.setTime(getFechaCierre());		
	
	        // Create a date object to compare
	        Date dateToCompare = c.getTime(); // Replace with your desired date
	
	        // Compare the dates
	        if (today.after(dateToCompare)) {
	            System.out.println("The date is after today.");
	            convocatoriaCerrada = true;
	        } 
        }
        
		return convocatoriaCerrada;
	}

	public void setConvocatoriaCerrada(boolean convocatoriaCerrada) {
		this.convocatoriaCerrada = convocatoriaCerrada;
	}

	public Set<CorteConvocatoriaExterna> getCortes() {
		return cortes;
	}

	public void setCortes(Set<CorteConvocatoriaExterna> cortes) {
		this.cortes = cortes;
	}
	
    public List<CorteConvocatoriaExterna> getListaCortes() {
        List listaCortes = new ArrayList();
        listaCortes.addAll(cortes);
        return listaCortes;
    }
    
    public void adicionarCorte(CorteConvocatoriaExterna corte) {
        if (cortes == null) {
        	cortes = new HashSet<CorteConvocatoriaExterna>();
        }
        corte.setConvocatoriaExterna(this);
        cortes.add(corte);
    }

	public String getTipoAvalConvocatoria() {
		return tipoAvalConvocatoria;
	}

	public void setTipoAvalConvocatoria(String tipoAvalConvocatoria) {
		this.tipoAvalConvocatoria = tipoAvalConvocatoria;
	}

	public String getMensajeAvalConvocatoria() {
		return mensajeAvalConvocatoria;
	}

	public void setMensajeAvalConvocatoria(String mensajeAvalConvocatoria) {
		this.mensajeAvalConvocatoria = mensajeAvalConvocatoria;
	}
	
}