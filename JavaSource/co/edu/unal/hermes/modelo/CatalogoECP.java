package co.edu.unal.hermes.modelo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CatalogoECP {

    private Long id;
    private Dependencia dependencia;
    private String nombre;
    private String objeto;
    private String modalidad;
    private String tipoEvento;
    private String poblacionObjeto;
    private Long esOfertaPermanente;
    private Long AnioPrimeraVez;
    private Long MesPrimeraVez;
    private Long numVersiones;
    private AgendaConocimiento ejeTematico;
    private AgendaConocimiento areaGeneral;
    private AgendaConocimiento areaEspecifica;
    private Long registroCompleto;
    private Date fechaRegistro;
    //private String persona;
    private Long persona;
    private Long tieneCertificadoCalidad;
    //private Long copiaCertificadoCalidad;
    private String copiaCertificadoCalidad;
    private Set<OfertaECP> ofertaECP = new HashSet<OfertaECP>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Dependencia getDependencia() {
		return dependencia;
	}
	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getObjeto() {
		return objeto;
	}
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}
	public String getModalidad() {
		return modalidad;
	}
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	public String getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	public String getPoblacionObjeto() {
		return poblacionObjeto;
	}
	public void setPoblacionObjeto(String poblacionObjeto) {
		this.poblacionObjeto = poblacionObjeto;
	}
	public Long getEsOfertaPermanente() {
		return esOfertaPermanente;
	}
	public void setEsOfertaPermanente(Long esOfertaPermanente) {
		this.esOfertaPermanente = esOfertaPermanente;
	}
	public Long getAnioPrimeraVez() {
		return AnioPrimeraVez;
	}
	public void setAnioPrimeraVez(Long anioPrimeraVez) {
		AnioPrimeraVez = anioPrimeraVez;
	}
	public Long getMesPrimeraVez() {
		return MesPrimeraVez;
	}
	public void setMesPrimeraVez(Long mesPrimeraVez) {
		MesPrimeraVez = mesPrimeraVez;
	}
	public Long getNumVersiones() {
		return numVersiones;
	}
	public void setNumVersiones(Long numVersiones) {
		this.numVersiones = numVersiones;
	}
	public AgendaConocimiento getEjeTematico() {
		return ejeTematico;
	}
	public void setEjeTematico(AgendaConocimiento ejeTematico) {
		this.ejeTematico = ejeTematico;
	}
	public AgendaConocimiento getAreaGeneral() {
		return areaGeneral;
	}
	public void setAreaGeneral(AgendaConocimiento areaGeneral) {
		this.areaGeneral = areaGeneral;
	}
	public AgendaConocimiento getAreaEspecifica() {
		return areaEspecifica;
	}
	public void setAreaEspecifica(AgendaConocimiento areaEspecifica) {
		this.areaEspecifica = areaEspecifica;
	}
	public Long getRegistroCompleto() {
		return registroCompleto;
	}
	public void setRegistroCompleto(Long registroCompleto) {
		this.registroCompleto = registroCompleto;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Long getPersona() {
		return persona;
	}
	public void setPersona(Long persona) {
		this.persona = persona;
	}
	public Long getTieneCertificadoCalidad() {
		return tieneCertificadoCalidad;
	}
	public void setTieneCertificadoCalidad(Long tieneCertificadoCalidad) {
		this.tieneCertificadoCalidad = tieneCertificadoCalidad;
	}
	public String getCopiaCertificadoCalidad() {
		return copiaCertificadoCalidad;
	}
	public void setCopiaCertificadoCalidad(String copiaCertificadoCalidad) {
		this.copiaCertificadoCalidad = copiaCertificadoCalidad;
	}
	public Set<OfertaECP> getOfertaECP() {
		return ofertaECP;
	}
	public void setOfertaECP(Set<OfertaECP> ofertaECP) {
		this.ofertaECP = ofertaECP;
	}

    /*
    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Dependencia getDependencia() {
	return dependencia;
    }

    public void setDependencia(Dependencia dependencia) {
	this.dependencia = dependencia;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public String getObjeto() {
	return objeto;
    }

    public void setObjeto(String objeto) {
	this.objeto = objeto;
    }

    public String getModalidad() {
	return modalidad;
    }

    public void setModalidad(String modalidad) {
	this.modalidad = modalidad;
    }

    public String getTipoEvento() {
	return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
	this.tipoEvento = tipoEvento;
    }

    public String getPoblacionObjeto() {
	return poblacionObjeto;
    }

    public void setPoblacionObjeto(String poblacionObjeto) {
	this.poblacionObjeto = poblacionObjeto;
    }

    public Long getEsOfertaPermanente() {
	return esOfertaPermanente;
    }

    public void setEsOfertaPermanente(Long esOfertaPermanente) {
	this.esOfertaPermanente = esOfertaPermanente;
    }

    public Long getAnioPrimeraVez() {
	return AnioPrimeraVez;
    }

    public void setAnioPrimeraVez(Long anioPrimeraVez) {
	AnioPrimeraVez = anioPrimeraVez;
    }

    public Long getMesPrimeraVez() {
	return MesPrimeraVez;
    }

    public void setMesPrimeraVez(Long mesPrimeraVez) {
	MesPrimeraVez = mesPrimeraVez;
    }

    public Long getNumVersiones() {
	return numVersiones;
    }

    public void setNumVersiones(Long numVersiones) {
	this.numVersiones = numVersiones;
    }

    public Long getRegistroCompleto() {
	return registroCompleto;
    }

    public void setRegistroCompleto(Long registroCompleto) {
	this.registroCompleto = registroCompleto;
    }

    public Date getFechaRegistro() {
	return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
	this.fechaRegistro = fechaRegistro;
    }

    public String getPersona() {
	return persona;
    }

    public void setPersona(String persona) {
	this.persona = persona;
    }

    public Long getTieneCertificadoCalidad() {
	return tieneCertificadoCalidad;
    }

    public void setTieneCertificadoCalidad(Long tieneCertificadoCalidad) {
	this.tieneCertificadoCalidad = tieneCertificadoCalidad;
    }

    public Long getCopiaCertificadoCalidad() {
	return copiaCertificadoCalidad;
    }

    public void setCopiaCertificadoCalidad(Long copiaCertificadoCalidad) {
	this.copiaCertificadoCalidad = copiaCertificadoCalidad;
    }

    public AgendaConocimiento getEjeTematico() {
        return ejeTematico;
    }

    public void setEjeTematico(AgendaConocimiento ejeTematico) {
        this.ejeTematico = ejeTematico;
    }

    public AgendaConocimiento getAreaGeneral() {
        return areaGeneral;
    }

    public void setAreaGeneral(AgendaConocimiento areaGeneral) {
        this.areaGeneral = areaGeneral;
    }

    public AgendaConocimiento getAreaEspecifica() {
        return areaEspecifica;
    }

    public void setAreaEspecifica(AgendaConocimiento areaEspecifica) {
        this.areaEspecifica = areaEspecifica;
    }

	public Set<OfertaECP> getOfertaECP() {
		return ofertaECP;
	}

	public void setOfertaECP(Set<OfertaECP> ofertaECP) {
		this.ofertaECP = ofertaECP;
	}
	*/

    
}
