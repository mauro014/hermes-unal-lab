/*
 * Created on 31-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.util.Date;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import co.edu.unal.hermes.modelo.mapeo.GastoFM;

public class FuenteFinanciacion implements Cloneable {
    public static String interna = "I";
    public static String externa = "E";
    public static String OCULTA_INTERNA = "O";
    public static String OCULTA_EXTERNA = "OE";

    public static final String ENTIDAD_PARTICIPANTE = "P";
    public static final String ENTIDAD_ESPECIE = "E";
    public static final String FINANCIADORA = "F";
    public static final String FINANCIADORA_M = "FM";

    public static final String FINANCIACION_GENERICA_INTERNA = "-1";
    public static final String FINANCIACION_GENERICA_EXTERNA = "-2";
    
    public static final String ID_SISTEMA_GENERAL_REGALIAS = "428";
    public static final String ID_MINCIENCIAS = "4586";
    public static final String ID_RNC = "23";

    private String id;
    private String descripcion;
    private String internaExterna;
    private String id_sige;
    private String nit;
    private String direccion;
    private String telefono;
    private String fax;
    private Pais pais;
    private Departamento departamento;
    private Ciudad ciudad;
    private String pagweb;
    private String naturaleza;
    private String sector;
    private String nvlterritorial;
    private String obsRechazo;
    private String est_entidad;
    private String reg_completo;
    private Date fec_registro;
    private Date fec_solicitud;
    private String est_solicitud;
    private Date fec_respuesta;
    private String observaciones;
    private String quipu;
    private String caracter;
    private String tipoFuente;
    private String estado;
    private boolean autoridadCompetenteBiodiversidad;
    private Boolean visibleAval;

    private TreeNode root;

    public void construirTreeGasto(boolean incluirContrapartida) {
        boolean generarArbolContrapartida = incluirContrapartida && !(getInternaExterna() != null
                && (externa.equals(getInternaExterna()) || OCULTA_EXTERNA.equals(getInternaExterna())));
        construirTreeGastoFM(generarArbolContrapartida);
    }

    public void construirTreeGastoFM(boolean incluirContrapartida) {
        // rubros para investigación fte interna

        // Base del tree node
		if (root == null) {
			root = (TreeNode) new DefaultTreeNode("root", null);
		}

        // Segundo nivel "Gastos de personal"
        construirTreeGastoPersonal(root);

        // Segundo nivel "Bienes y suministros"
        construirTreeGastoBienes(root);

        // Segundo nivel "Adquisición de servicios"
        construirTreeGastoServicios(root, false);

        // Segundo nivel "OperacionesInternas"
        construirTreeGastoOperacionesInternas(root);
        
        construirTreeGastoSeguimiento(root);

        // Segundo nivel "Sin contraprestación"
        construirTreeGastoSinContraprestacion(root);

        if (incluirContrapartida) {
            construirTreeContrapartida(root, false);
        }
    }

    private void construirTreeGastoPersonal(TreeNode root) {

        // Segundo nivel "Gastos de personal"
        GastoFM gastosPersonal = new GastoFM(130, "Gastos de personal");
        gastosPersonal.setEditable(false);
        TreeNode personal = new DefaultTreeNode(gastosPersonal, root);
        // Tercer nivel "Gastos de personal"
        new DefaultTreeNode(new GastoFM(14, "Estímulo estudiantes"), personal);
        new DefaultTreeNode(new GastoFM(119, "Estímulo evaluadores externos"), personal);
        new DefaultTreeNode(new GastoFM(3, "Remuneración por servicios técnicos"), personal);
        new DefaultTreeNode(new GastoFM(189, "Personal supernumerario"), personal);

    }

    private void construirTreeGastoBienes(TreeNode root) {

        // Segundo nivel "Adquisición de bienes"
        GastoFM adquisicionBienes = new GastoFM(128, "Adquisición de bienes");
        adquisicionBienes.setEditable(false);
        TreeNode bienes = new DefaultTreeNode(adquisicionBienes, root);
        // Tercer nivel "Adquisición de bienes"
        new DefaultTreeNode(new GastoFM(131, "Compra de equipo"), bienes);
        new DefaultTreeNode(new GastoFM(107, "Materiales y suministros"), bienes);
        new DefaultTreeNode(new GastoFM(58, "Operaciones internas - adquisición de bienes"), bienes);
        new DefaultTreeNode(new GastoFM(125, "Compra de semovientes"), bienes);
        new DefaultTreeNode(new GastoFM(190, "Compra de Material Bibliográfico"), bienes);
    }

    private void construirTreeGastoServicios(TreeNode root, boolean especial) {

        // Segundo nivel "Adquisición de servicios"
        GastoFM adquisicionServicios = new GastoFM(129, "Adquisición de servicios");
        adquisicionServicios.setEditable(false);
        TreeNode servicios = new DefaultTreeNode(adquisicionServicios, root);
        // Tercer nivel "Adquisición de servicios"
        new DefaultTreeNode(new GastoFM(9, "Mantenimiento"), servicios);
        new DefaultTreeNode(new GastoFM(70, "Arrendamientos"), servicios);
        new DefaultTreeNode(new GastoFM(10, "Viáticos y gastos de viaje"), servicios);
        new DefaultTreeNode(new GastoFM(12, "Impresos y publicaciones"), servicios);
        new DefaultTreeNode(new GastoFM(13, "Comunicaciones y transportes"), servicios);
        new DefaultTreeNode(new GastoFM(96, "Seguros"), servicios);
        new DefaultTreeNode(new GastoFM(111, "Capacitación"), servicios);
        new DefaultTreeNode(new GastoFM(60, "Impuestos, contribuciones y multas"), servicios);
        if (especial) {
            new DefaultTreeNode(new GastoFM(109, "Apoyo logístico a eventos"), servicios);
        }
        new DefaultTreeNode(new GastoFM(156, "Otros gastos generales por adquisición de servicios"), servicios);
        new DefaultTreeNode(new GastoFM(94, "Servicios públicos"), servicios);
        new DefaultTreeNode(new GastoFM(191, "Apoyo logístico"), servicios);
        new DefaultTreeNode(new GastoFM(192, "Comisiones bancarias"), servicios);

    }

    private void construirTreeGastoSeguimiento(TreeNode root) {

        // Segundo nivel "Seguimiento y evaluación"
        GastoFM seguimientoEvaluacion = new GastoFM(185, "Seguimiento y evaluación");
        seguimientoEvaluacion.setEditable(false);
        TreeNode seguimiento = new DefaultTreeNode(seguimientoEvaluacion, root);
        // Tercer nivel "Seguimiento y evaluación"
        new DefaultTreeNode(new GastoFM(185, "Seguimiento y evaluación"), seguimiento);
    }

    private void construirTreeGastoOperacionesInternas(TreeNode root) {

        // Segundo nivel "Operaciones internas por adquisición de servicios"
        GastoFM operacionesInternasRubro = new GastoFM(5, "Operaciones internas por adquisición de servicios");
        operacionesInternasRubro.setEditable(false);
        TreeNode operacionesInternasAdquisicionServicios = new DefaultTreeNode(operacionesInternasRubro, root);
        // Tercer nivel "Operaciones internas por adquisición de servicios"
        new DefaultTreeNode(new GastoFM(193, "Operaciones internas adquisición de servicios de extensión"),
                operacionesInternasAdquisicionServicios);
        new DefaultTreeNode(new GastoFM(194, "Operaciones internas adquisición de impresos y publicaciones"),
                operacionesInternasAdquisicionServicios);
        new DefaultTreeNode(new GastoFM(195, "Operaciones internas adquisición arrendamientos"),
                operacionesInternasAdquisicionServicios);
        new DefaultTreeNode(new GastoFM(196, "Operaciones internas adquisición servicios de comunicación"),
                operacionesInternasAdquisicionServicios);
        new DefaultTreeNode(new GastoFM(155, "Operaciones internas adquisición por otras ventas de servicios"),
                operacionesInternasAdquisicionServicios);
    }

    private void construirTreeGastoSinContraprestacion(TreeNode root) {

        // Segundo nivel "Transferencias entre fondos sin contraprestación"
        GastoFM sinContraprestacionRubro = new GastoFM(198, "Transferencias entre fondos sin contraprestación");
        sinContraprestacionRubro.setEditable(false);
        TreeNode transferenciasFondosSinContraprestacion = new DefaultTreeNode(sinContraprestacionRubro, root);
        // Tercer nivel "Operaciones internas sin contraprestación"
        new DefaultTreeNode(new GastoFM(157, "Operaciones internas sin contraprestación"),
                transferenciasFondosSinContraprestacion);
        new DefaultTreeNode(new GastoFM(163, "Becas para estudiantes"), transferenciasFondosSinContraprestacion);
        new DefaultTreeNode(new GastoFM(201, "Otras transferencias"), transferenciasFondosSinContraprestacion);

    }

    public void construirTreeContrapartida(TreeNode root, boolean honorarios) {
        boolean esContrapartida = true;

        // Segundo nivel "Contrapartidas"
        GastoFM sinContraprestacionRubro = new GastoFM(53, "Contrapartida en especie");
        sinContraprestacionRubro.setEditable(false);
        TreeNode contrapartida = new DefaultTreeNode(sinContraprestacionRubro, root);
        // Tercer nivel "Contrapartidas"
        new DefaultTreeNode(new GastoFM(120, "Uso de equipos", esContrapartida), contrapartida);
        new DefaultTreeNode(new GastoFM(7, "Materiales", esContrapartida), contrapartida);
        new DefaultTreeNode(new GastoFM(135, "Servicio de laboratorios", esContrapartida), contrapartida);
        new DefaultTreeNode(new GastoFM(141, "Uso de capacidad instalada", esContrapartida), contrapartida);
        new DefaultTreeNode(new GastoFM(82, "Servicios públicos", esContrapartida), contrapartida);
        new DefaultTreeNode(new GastoFM(143, "Material bibliográfico (contrapartida)", esContrapartida), contrapartida);
        new DefaultTreeNode(new GastoFM(144, "Bases de datos", esContrapartida), contrapartida);
        new DefaultTreeNode(new GastoFM(145, "Uso de software en general", esContrapartida), contrapartida);
        new DefaultTreeNode(new GastoFM(142, "Mobiliario y enseres", esContrapartida), contrapartida);
        if (honorarios) {
            new DefaultTreeNode(new GastoFM(172, "Honorarios", esContrapartida), contrapartida);
        }

    }

    public void construirTreeGastoFM_CCT() { // rubros para investigación fte
                                             // interna
        root = (TreeNode) new DefaultTreeNode("root", null);

        // Segundo nivel "Gastos de personal"
        construirTreeGastoPersonal(root);

        // Segundo nivel "Bienes y suministros"
        construirTreeGastoBienes(root);

        // Segundo nivel "Adquisición de servicios"
        construirTreeGastoServicios(root, false);

        // Segundo nivel "OperacionesInternas"
        construirTreeGastoOperacionesInternas(root);

        // Segundo nivel "Sin contraprestación"
        construirTreeGastoSinContraprestacion(root);

        // Segundo nivel "Contrapartida"
        construirTreeContrapartida(root, true);

        // EN BD PRODUCCIÓN
        TreeNode contrapartidaCO = new DefaultTreeNode(new GastoFM(184, "Contrapartidas en especie CORPOICA"), root);
        // transferencias = new DefaultTreeNode(new GastoFM(84,"Transferencias",

        // contrapartida CORPOICA
        boolean esContrapartida = true;
        new DefaultTreeNode(new GastoFM(173, "Uso de equipos", esContrapartida), contrapartidaCO);
        new DefaultTreeNode(new GastoFM(174, "Materiales", esContrapartida), contrapartidaCO);
        new DefaultTreeNode(new GastoFM(175, "Servicio de laboratorios", esContrapartida), contrapartidaCO);
        new DefaultTreeNode(new GastoFM(176, "Uso de capacidad instalada", esContrapartida), contrapartidaCO);
        new DefaultTreeNode(new GastoFM(177, "Servicios públicos", esContrapartida), contrapartidaCO);
        new DefaultTreeNode(new GastoFM(178, "Material bibliográfico (contrapartida)", esContrapartida),
                contrapartidaCO);
        new DefaultTreeNode(new GastoFM(179, "Bases de datos", esContrapartida), contrapartidaCO);
        new DefaultTreeNode(new GastoFM(180, "Uso de software en general", esContrapartida), contrapartidaCO);
        new DefaultTreeNode(new GastoFM(181, "Mobiliario y enseres", esContrapartida), contrapartidaCO);
        new DefaultTreeNode(new GastoFM(182, "Honorarios", esContrapartida), contrapartidaCO);

    }

    public void construirTreeGastoFM_Especial() { // rubros para investigación
                                                  // fte interna
        root = (TreeNode) new DefaultTreeNode("root", null);

        // Segundo nivel "Gastos de personal"
        construirTreeGastoPersonal(root);

        // Segundo nivel "Bienes y suministros"
        construirTreeGastoBienes(root);

        // Segundo nivel "Adquisición de servicios"
        construirTreeGastoServicios(root, true);

        // Segundo nivel "OperacionesInternas"
        construirTreeGastoOperacionesInternas(root);

        // Segundo nivel "Sin contraprestación"
        construirTreeGastoSinContraprestacion(root);

        // Segundo nivel "Contrapartida"
        construirTreeContrapartida(root, false);

    }

    // Extensión
    public void construirTreeGastoFMExt() { // rubros para extensión fte externa
        root = (TreeNode) new DefaultTreeNode("root", null);

        // Segundo nivel "Gastos de personal"
        construirTreeGastoPersonal(root);

        // Segundo nivel "Bienes y suministros"
        construirTreeGastoBienes(root);

        // Segundo nivel "Adquisición de servicios"
        construirTreeGastoServicios(root, true);

        // EN BD PRODUCCIÓN
        TreeNode costosIndirectos = new DefaultTreeNode(new GastoFM(83, "costos indirectos"), root);
        TreeNode transferencias = new DefaultTreeNode(new GastoFM(84, "Transferencias"), root);
        // contrapartida = new DefaultTreeNode(new GastoFM(53,"Contrapartidas",

        // Indirectos
        new DefaultTreeNode(new GastoFM(93, "Nivel facultad"), costosIndirectos);
        new DefaultTreeNode(new GastoFM(149, "Nivel sede"), costosIndirectos);
        new DefaultTreeNode(new GastoFM(150, "Nivel Nacional"), costosIndirectos);

        // Transferencias
        new DefaultTreeNode(new GastoFM(89, "Fondo extensión solidaria"), transferencias);
        new DefaultTreeNode(new GastoFM(90, "Fondo de riesgos para la extensión"), transferencias);
        new DefaultTreeNode(new GastoFM(86, "Fondo especial facultad"), transferencias);
        new DefaultTreeNode(new GastoFM(87, "Fondo de investigación UN"), transferencias);
        new DefaultTreeNode(new GastoFM(88, "Fondo especial dirección académica"), transferencias);
        new DefaultTreeNode(new GastoFM(91, "Dirección nacional de extensión"), transferencias);
        new DefaultTreeNode(new GastoFM(92, "Dirección extensión sede"), transferencias);
        new DefaultTreeNode(new GastoFM(157, "Operaciones internas aportes sin contraprestación"), transferencias);

    }

    public void construirTreeGastoFMExtInt() { // rubros para extensión fte
                                               // interna
        root = (TreeNode) new DefaultTreeNode("root", null);

        // Segundo nivel "Gastos de personal"
        construirTreeGastoPersonal(root);

        TreeNode personal = new DefaultTreeNode(new GastoFM(130, "Gastos de personal"), root);
        TreeNode bienes = new DefaultTreeNode(new GastoFM(128, "Adquisición de bienes"), root);
        TreeNode servicios = new DefaultTreeNode(new GastoFM(129, "Adquisición de servicios"), root);
        TreeNode costosIndirectos = new DefaultTreeNode(new GastoFM(83, "costos indirectos"), root);
        TreeNode contrapartida = new DefaultTreeNode(new GastoFM(53, "Contrapartidas"), root);

        TreeNode equipo = new DefaultTreeNode(new GastoFM(131, "Compra de equipo"), bienes);
        TreeNode materiales = new DefaultTreeNode(new GastoFM(107, "Materiales y suministros"), bienes);
        TreeNode internasBienes = new DefaultTreeNode(new GastoFM(58, "Operaciones internas - adquisición de bienes"),
                bienes);

        new DefaultTreeNode(new GastoFM(9, "Mantenimiento"), servicios);
        new DefaultTreeNode(new GastoFM(70, "Arrendamientos"), servicios);
        new DefaultTreeNode(new GastoFM(10, "Viáticos y gastos de viaje"), servicios);
        new DefaultTreeNode(new GastoFM(12, "Impresos y publicaciones"), servicios);
        new DefaultTreeNode(new GastoFM(13, "Comunicaciones y transportes"), servicios);
        new DefaultTreeNode(new GastoFM(96, "Seguros"), servicios);
        new DefaultTreeNode(new GastoFM(111, "Capacitación"), servicios);
        new DefaultTreeNode(new GastoFM(60, "Impuestos, contribuciones y multas"), servicios);
        new DefaultTreeNode(new GastoFM(155, "Operaciones internas - adquisición de servicios"), servicios);
        new DefaultTreeNode(new GastoFM(156, "Otros gastos generales por adquisición de servicios"), servicios);

        // Indirectos
        TreeNode costosIndirectosFac = new DefaultTreeNode(new GastoFM(93, "Nivel facultad"), costosIndirectos);

        new DefaultTreeNode(new GastoFM(149, "Nivel sede"), costosIndirectos);
        new DefaultTreeNode(new GastoFM(150, "Nivel Nacional"), costosIndirectos);

        // contrapartida
        TreeNode transferencias8 = new DefaultTreeNode(new GastoFM(120, "Uso de equipos"), contrapartida);
        TreeNode transferencias6 = new DefaultTreeNode(new GastoFM(7, "Materiales"), contrapartida);
        TreeNode laboratorio = new DefaultTreeNode(new GastoFM(135, "Servicio de laboratorios"), contrapartida);
        TreeNode semovientesE = new DefaultTreeNode(new GastoFM(141, "Uso de capacidad instalada"), contrapartida);
        TreeNode laboratorioE = new DefaultTreeNode(new GastoFM(82, "Servicios públicos"), contrapartida);
        TreeNode materialBE = new DefaultTreeNode(new GastoFM(143, "Material bibliográfico (contrapartida)"),
                contrapartida);
        TreeNode otrosE = new DefaultTreeNode(new GastoFM(144, "Bases de datos"), contrapartida);
        TreeNode softwareE = new DefaultTreeNode(new GastoFM(145, "Uso de software en general"), contrapartida);
        new DefaultTreeNode(new GastoFM(142, "Mobiliario y enseres"), contrapartida);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInternaExterna() {
        return internaExterna;
    }
    
    public boolean esInterna() {
    	if(internaExterna!=null && interna.equals(this.internaExterna) || OCULTA_INTERNA.equals(this.internaExterna) ) {
    		return true;
    	}
    	return false;
    }

    public void setInternaExterna(String internaExterna) {
        this.internaExterna = internaExterna;
    }

    public String getId_sige() {
        return id_sige;
    }

    public void setId_sige(String idSige) {
        id_sige = idSige;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public String getPagweb() {
        return pagweb;
    }

    public void setPagweb(String pagweb) {
        this.pagweb = pagweb;
    }

    public String getNaturaleza() {
        return naturaleza;
    }

    public String getNombreNaturaleza() {
        String nombreTipo = "";
        if (naturaleza != null) {
            if (naturaleza.equals("NAT_PUBLIC")) {
                nombreTipo = "Pública";
            } else if (naturaleza.equals("NAT_PRIVAD")) {
                nombreTipo = "Privada";
            } else if (naturaleza.equals("NAT_MIXTA")) {
                nombreTipo = "Mixta";
            } else if (naturaleza.equals("NAT_INTERNACIONAL")) {
                nombreTipo = "Internacional";
            } else {
                nombreTipo = "Sin definir";
            }
        } else {
            nombreTipo = "Sin definir";
        }
        return nombreTipo;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getNvlterritorial() {
        return nvlterritorial;
    }

    public void setNvlterritorial(String nvlterritorial) {
        this.nvlterritorial = nvlterritorial;
    }

    public String getObsRechazo() {
        return obsRechazo;
    }

    public void setObsRechazo(String obsRechazo) {
        this.obsRechazo = obsRechazo;
    }

    public String getEst_entidad() {
        return est_entidad;
    }

    public void setEst_entidad(String estEntidad) {
        est_entidad = estEntidad;
    }

    public String getReg_completo() {
        return reg_completo;
    }

    public void setReg_completo(String regCompleto) {
        reg_completo = regCompleto;
    }

    public Date getFec_registro() {
        return fec_registro;
    }

    public void setFec_registro(Date fec_registro) {
        this.fec_registro = fec_registro;
    }

    public Date getFec_solicitud() {
        return fec_solicitud;
    }

    public void setFec_solicitud(Date fec_solicitud) {
        this.fec_solicitud = fec_solicitud;
    }

    public Date getFec_respuesta() {
        return fec_respuesta;
    }

    public void setFec_respuesta(Date fec_respuesta) {
        this.fec_respuesta = fec_respuesta;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEst_solicitud() {
        return est_solicitud;
    }

    public void setEst_solicitud(String est_solicitud) {
        this.est_solicitud = est_solicitud;
    }

    public boolean equals(Object o) {
        if (!(o instanceof FuenteFinanciacion)) {
            return false;
        }
        FuenteFinanciacion ff = (FuenteFinanciacion) o;
        if (this.getId() == null || ff.getId() == null) {
            return false;
        }
        return this.getId().equals(ff.getId());
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public String getQuipu() {
        return quipu;
    }

    public void setQuipu(String quipu) {
        this.quipu = quipu;
    }

	public String getCaracter() {
		return caracter;
	}

	public void setCaracter(String caracter) {
		this.caracter = caracter;
	}

	public String getTipoFuente() {
		return tipoFuente;
	}

	public void setTipoFuente(String tipoFuente) {
		this.tipoFuente = tipoFuente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	/** minimal constructor */
	public FuenteFinanciacion(String id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
		 this.visibleAval = true;
	}
	
	/** default constructor */
	public FuenteFinanciacion() {
		/**
		 * Constructor por defecto de la clase aval
		 */
		 this.visibleAval = true;
		 this.pais = new Pais();
	}

	public boolean isAutoridadCompetenteBiodiversidad() {
		return autoridadCompetenteBiodiversidad;
	}

	public void setAutoridadCompetenteBiodiversidad(boolean autoridadCompetenteBiodiversidad) {
		this.autoridadCompetenteBiodiversidad = autoridadCompetenteBiodiversidad;
	}
	
    public Boolean getVisibleAval() {
    	if(visibleAval == null) {
    		visibleAval = true;
    	}
        return visibleAval;
    }

    public void setVisibleAval(Boolean visibleAval) {
    	if(visibleAval == null) {
    		visibleAval = true;
    	}
        this.visibleAval = visibleAval;
    }
    
    public String getInternaExternaNombre() {
        if (internaExterna == null) {
            return "";
        }
        
        if ("I".equals(internaExterna))  return "Interna";
        if ("O".equals(internaExterna))  return "Oculta Interna";
        if ("E".equals(internaExterna))  return "Externa";
        if ("OE".equals(internaExterna)) return "Oculta Externa";

        return internaExterna;
    }

}