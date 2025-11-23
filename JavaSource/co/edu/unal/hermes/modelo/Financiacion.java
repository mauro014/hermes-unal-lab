/*
 * Created on 31-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import co.edu.unal.hermes.modelo.mapeo.GastoFM;

/**
 * The Class Financiacion.
 */
public class Financiacion {

    public static final String SI_ES_LEGALIZACION = "SI";

    public static final String LEGALIZACION_TIPO_FIRMANTE_CONVENIO = "FM";

    /** The id. */
    protected Long id;

    /** The fuente. */
    protected FuenteFinanciacion fuente;

    /** The proyecto. */
    protected Proyecto proyecto;

    /** The valor. */
    protected Long valor;

    /** The borrable. */
    protected boolean borrable = false;

    /** The valor pendiente. */
    private Long valorPendiente;

    /** The nit. */
    protected String nit;

    /** The contrato. */
    protected String contrato;

    /** The fecha contrato. */
    protected Date fechaContrato;

    /** The convenio. */
    protected String convenio;

    /** The gastos listados. */
    protected ArrayList<Gasto> gastosListados;

    /** The rol. */
    private String rol;

    /** The es legalizacion. */
    private String esLegalizacion;

    /** The gastos. */
    // Los gastos de cada fuente de financiacion
    private Set<Gasto> gastos = new HashSet<Gasto>();

    /** The tipo entidad. */
    // Convoc externa
    private String tipoEntidad;

    /** The valor especie. */
    private Long valorEspecie;

    /** The modalidad fuente financiacion. */
    private ModalidadFuenteFinanciacion modalidadFuenteFinanciacion;

    /** The rubros financiable arbol. */
    private List<RubroFinanciableArbol> rubrosFinanciableArbol;

    /** The tree financiacion. */
    private TreeNode treeFinanciacion;

    private TreeNode nodeTotal;

    private Tipos subtipoFinanciacion;

    /**
     * Adicionar gasto.
     *
     * @param gasto
     *            the gasto
     */
    public void adicionarGasto(Gasto gasto) {
        if (gastos == null) {
            gastos = new HashSet<Gasto>();
        }
        gasto.setFinanciacion(this);
        gastos.add(gasto);
    }

    /**
     * Adicionar gasto2.
     *
     * @param gasto
     *            the gasto
     */
    public void adicionarGasto2(Gasto gasto) {
        gasto.setFinanciacion(this);
        if (gastosListados == null) {
            gastosListados = new ArrayList<Gasto>();
        }
        gastosListados.add(gasto);
    }

    /**
     * Actualizar valores tree table desde gastos.
     */
    public void actualizarValoresTreeTableDesdeGastos() {
        if (gastos != null) {
            Iterator<Gasto> i = gastos.iterator();
            while (i.hasNext()) {
                Gasto gasto = i.next();
                GastoFM gastoFm = obtenerGastoDesdeTree(gasto.getTipoRubro().getId().toString());
                if (gastoFm != null) {
                    gastoFm.setAnio1(gasto.getValor() != null ? gasto.getValor() : 0L);
                    gastoFm.setAnio2(gasto.getValor2() != null ? gasto.getValor2() : 0L);
                    gastoFm.setAnio3(gasto.getValor3() != null ? gasto.getValor3() : 0L);
                    gastoFm.setAnio4(gasto.getValor4() != null ? gasto.getValor4() : 0L);
                    gastoFm.setAnio5(gasto.getValor5() != null ? gasto.getValor5() : 0L);
                    gastoFm.setAnio6(gasto.getValor6() != null ? gasto.getValor6() : 0L);
                    gastoFm.setGasto(gasto);
                }
            }
        }
    }
    
    /**
     * Verificar si tiene rubros antes de 2022
     */
    public boolean tieneGastosAntesCCP2022() {
    	 if (gastos != null) {
    		 Iterator<Gasto> i = gastos.iterator();
             while (i.hasNext()) {
            	 Gasto gasto = i.next();
                 if (gasto != null && gasto.getTipoRubro()!= null && gasto.getTipoRubro().getDescripcion()!= null && !gasto.getTipoRubro().getDescripcion().equals(TipoRubro.DESCRIPCION_CCP2022) && !gasto.getTipoRubro().isRubroContrapartida()) {
                	 return true;
                 }
             }
    	 }
    	return false;
    }
    
    /**
     * Verificar si tiene rubros antes de 2022
     */
    public boolean verificarGastosPersonalCCP2022() {
    	 if (gastos != null) {
    		 Iterator<Gasto> i = gastos.iterator();
             while (i.hasNext()) {
            	 Gasto gasto = i.next();
                 if (gasto != null && gasto.getTipoRubro()!= null && gasto.getTipoRubro().getPadre() != null && gasto.getTipoRubro().getPadre().getId() != null && gasto.getTipoRubro().getPadre().getId().equals(431L)) {
                	 return true;
                 }
             }
    	 }
    	return false;
    }

    /**
     * Limpiar gastos cero lista temporal.
     */
    public void limpiarGastosCeroListaTemporal() {
        if (gastos != null) {
            Iterator<Gasto> i = gastos.iterator();
            List<Gasto> listaGastosCero = new ArrayList<Gasto>();
            while (i.hasNext()) {
                Gasto gasto = i.next();
                if (gasto.getSumaCampos() <= 0 && !(gasto.getDescripcion()!=null && gasto.getDescripcion().contains("Solicitud"))){ 
                    listaGastosCero.add(gasto);
                }
            }

            Iterator<Gasto> j = listaGastosCero.iterator();
            while (j.hasNext()) {
                Gasto gasto = j.next();
                gastos.remove(gasto);
            }

        }
    }

    /**
     * Se calcula la suma de la financiación de acuerdo al total de gastos.
     */
    public Long calcularValorEfectivoFinanciacion() {
        Long valorTotal = 0L;
        if (gastos != null && gastos.size() > 0) {
            for (Object gasto : gastos) {
                Gasto gast = (Gasto) gasto;
                if(!gast.getTipoRubro().isRubroContrapartida()) {
                	valorTotal += gast.getValor();
                }
            }
        }
        valor = valorTotal;
        return valor;
    }
    
    public void calcularValorFinanciacion() {
        Long valorTotal = 0L;
        if (gastos != null && gastos.size() > 0) {
            for (Object gasto : gastos) {
                Gasto gast = (Gasto) gasto;
                if(!gast.getTipoRubro().isRubroContrapartida()) {
                	valorTotal += gast.getValor();
                }
            }
        }
        valor = valorTotal;
    }
    
    public void calcularValorEspecieFinanciacion() {
    	
    	Long valorTotal = 0L;
        if (gastos != null && gastos.size() > 0) {
            for (Object gasto : gastos) {
                Gasto gast = (Gasto) gasto;
                if(gast.getTipoRubro().isRubroContrapartida()) {
                valorTotal += gast.getValor();
            }
            }
        }
        valorEspecie = valorTotal;
    }

    /**
     * Construir tree gasto.
     */
    public void construirTreeGasto() {
        Convocatoria convocatoria = (Convocatoria) proyecto.getModalidad();
        if (proyecto.getId() != null && proyecto.getId().equals(26468L)) {
            fuente.construirTreeGastoFM_Especial();
        } else if ("CCT".equals(convocatoria.getTipo().getId())) {
            fuente.construirTreeGastoFM_CCT();
		} else if (proyecto.getId() != null && modalidadFuenteFinanciacion == null
				&& (convocatoria.getPadre().getId().equals(11L) || convocatoria.getPadre().getId().equals(17L))) {
			treeFinanciacion = crearTreeConRubrosFinanciablesv2(rubrosFinanciableArbol);
			fuente.setRoot(treeFinanciacion);
			if (fuente.getInternaExterna().equals(FuenteFinanciacion.interna)
					|| fuente.getInternaExterna().equals(FuenteFinanciacion.OCULTA_INTERNA)) {
				fuente.construirTreeContrapartida(fuente.getRoot(), false);
			}
			//fuente.construirTreeGasto(convocatoria.isIncluirContrapartidaFinanacion());
		} else if (rubrosFinanciableArbol != null && rubrosFinanciableArbol.size() > 0) {
            treeFinanciacion = crearTreeConRubrosFinanciablesv2(rubrosFinanciableArbol);
            fuente.setRoot(treeFinanciacion);
				if(modalidadFuenteFinanciacion != null && modalidadFuenteFinanciacion.isIncluirContrapartida()) {
					fuente.construirTreeContrapartida(fuente.getRoot(), false);
				}
        } else {
            fuente.construirTreeGasto(convocatoria.isIncluirContrapartidaFinanacion());
        }
        TreeNode arbol = getTreeFinanciacion();

        // Se agrega item de total.
        GastoFM gastoFM = new GastoFM(146, "Total");
        gastoFM.setEditable(false);
        nodeTotal = new DefaultTreeNode(gastoFM, arbol);
    }

    /**
     * Gets the tree financiacion.
     *
     * @return the tree financiacion
     */
    public TreeNode getTreeFinanciacion() {
		if (treeFinanciacion != null && !proyecto.getModalidad().getId().equals(10L)) {
			return treeFinanciacion;
		} else
			return fuente.getRoot();
    }
    
	private GastoFM verificarGastoEnTree(TreeNode nodo, String idTipoRubro) {
		if (nodo.getChildren() != null && !nodo.getChildren().isEmpty()) {
			for (TreeNode child : nodo.getChildren()) {
				GastoFM returnValue = verificarGastoEnTree(child, idTipoRubro);
				if (returnValue != null) {
					return returnValue;
				}
			}
		} else {
			GastoFM gastofm = (GastoFM) nodo.getData();
			if (String.valueOf(gastofm.getIdRubro()).equals(idTipoRubro)) {
				return gastofm;
			}
		}
		return null;
	}

    public GastoFM obtenerGastoDesdeTree(String idTipoRubro) {

        // Se obtienen los elementos de segundo nivel.
        if (getTreeFinanciacion() != null) {
        	return verificarGastoEnTree(getTreeFinanciacion(),idTipoRubro);
            /*List<TreeNode> treeChildPpal = getTreeFinanciacion().getChildren();
            Iterator<TreeNode> i = treeChildPpal.iterator();

            while (i.hasNext()) {
                TreeNode treeSegundoNivel = i.next();

                // Se obtienen los elementos del tercer nivel.
                List<TreeNode> treeChild = treeSegundoNivel.getChildren();
                Iterator<TreeNode> j = treeChild.iterator();
                while (j.hasNext()) {
                    TreeNode treeTercerNivel = j.next();

                    GastoFM gastofm = (GastoFM) treeTercerNivel.getData();
                    if (String.valueOf(gastofm.getIdRubro()).equals(idTipoRubro)) {
                        return gastofm;
                    }
                }
            }*/
        }
        return null;
    }

    /**
     * Crear tree con rubros financiables.
     *
     * @param arbol
     *            the arbol
     * @return the tree node
     */
    private TreeNode crearTreeConRubrosFinanciables(List<RubroFinanciableArbol> arbol) {
        TreeNode root = new DefaultTreeNode("root", null);
        for (RubroFinanciableArbol rubroFinanciableArbolPrimerNivel : arbol) {
            GastoFM nuevoGastoFM = new GastoFM(rubroFinanciableArbolPrimerNivel.getTipoRubro());
            nuevoGastoFM.setEditable(false);
            TreeNode nodoPrimerNivel = new DefaultTreeNode(nuevoGastoFM, root);
            if (rubroFinanciableArbolPrimerNivel.getHijos() != null) {
                for (RubroFinanciableArbol rubroFinanciableArbolSegundoNivel : rubroFinanciableArbolPrimerNivel
                        .getHijos()) {
                    GastoFM gastoFM = new GastoFM(rubroFinanciableArbolSegundoNivel.getTipoRubro().getId().intValue(),
                            rubroFinanciableArbolSegundoNivel.getTipoRubro().getNombre());
                    if (rubroFinanciableArbolSegundoNivel.getTipo().equals(RubroFinanciableArbol.CONTRAPARTIDA)) {
                        gastoFM.setEsContrapartida(true);
                    }
                    new DefaultTreeNode(gastoFM, nodoPrimerNivel);
                }
            }

        }
        return root;
    }
    
	private TreeNode crearTreeConRubrosFinanciablesv2(List<RubroFinanciableArbol> arbol) {
		TreeNode root = new DefaultTreeNode("root", null);
		
		boolean tieneGastosAntesCCP2022 = tieneGastosAntesCCP2022();
		
		for (RubroFinanciableArbol rubroFinanciableArbolPrimerNivel : arbol) {
			
			boolean rubroEsCCP2022 = rubroFinanciableArbolPrimerNivel.getTipoRubro().getDescripcion() != null
					&& (rubroFinanciableArbolPrimerNivel.getTipoRubro().getDescripcion().equals(TipoRubro.DESCRIPCION_CCP2022)|| rubroFinanciableArbolPrimerNivel.getTipoRubro().getDescripcion().equals("GASTO_ESPECIAL")) && rubroFinanciableArbolPrimerNivel.getTipoRubro().isEstado();
			
			if(tieneGastosAntesCCP2022 || rubroEsCCP2022 || verificarGastosPersonalCCP2022()) {
				
				GastoFM nuevoGastoFM = new GastoFM(rubroFinanciableArbolPrimerNivel.getTipoRubro());
				nuevoGastoFM.setEditable(false);
				TreeNode nodoPrimerNivel = new DefaultTreeNode(nuevoGastoFM, root);
				if (rubroFinanciableArbolPrimerNivel.getHijos() != null
					&& rubroFinanciableArbolPrimerNivel.getHijos().size() != 0) {
				crearTreeConRubrosFinanciablesv2(rubroFinanciableArbolPrimerNivel.getHijos(), nodoPrimerNivel);
				}
			}
		}
		return root;
	}

	private void crearTreeConRubrosFinanciablesv2(Set<RubroFinanciableArbol> arbol, TreeNode root) {
		for (RubroFinanciableArbol rubroFinanciableArbolPrimerNivel : arbol) {
			GastoFM gastoFM = new GastoFM(rubroFinanciableArbolPrimerNivel.getTipoRubro().getId().intValue(),
					rubroFinanciableArbolPrimerNivel.getTipoRubro().getNombre());
			TreeNode nodoPrimerNivel = new DefaultTreeNode(gastoFM, root);
			if (rubroFinanciableArbolPrimerNivel.getHijos() != null
					&& rubroFinanciableArbolPrimerNivel.getHijos().size() != 0) {
				gastoFM.setEditable(false);
				crearTreeConRubrosFinanciablesv2(rubroFinanciableArbolPrimerNivel.getHijos(), nodoPrimerNivel);
			} else if (rubroFinanciableArbolPrimerNivel.getTipo().equals(RubroFinanciableArbol.CONTRAPARTIDA)) {
				gastoFM.setEsContrapartida(true);
			}
		}
	}

    /**
     * Borrar gasto.
     *
     * @param gasto
     *            the gasto
     */
    public void borrarGasto(Gasto gasto) {
        gastos.remove(gasto);
        calcularValorEfectivoFinanciacion();
        calcularValorEspecieFinanciacion();
    }

    /**
     * Gets the fuente.
     *
     * @return the fuente
     */
    public FuenteFinanciacion getFuente() {
        return fuente;
    }

    /**
     * Sets the fuente.
     *
     * @param fuente
     *            the new fuente
     */
    public void setFuente(FuenteFinanciacion fuente) {
        this.fuente = fuente;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the valor.
     *
     * @return the valor
     */
    public Long getValor() {
        return valor;
    }

    /**
     * Sets the valor.
     *
     * @param valor
     *            the new valor
     */
    public void setValor(Long valor) {
        this.valor = valor;
    }

    /**
     * Gets the proyecto.
     *
     * @return the proyecto
     */
    public Proyecto getProyecto() {
        return proyecto;
    }

    /**
     * Sets the proyecto.
     *
     * @param proyecto
     *            the new proyecto
     */
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * Checks if is borrable.
     *
     * @return true, if is borrable
     */
    public boolean isBorrable() {
        return borrable;
    }

    /**
     * Sets the borrable.
     *
     * @param borrable
     *            the new borrable
     */
    public void setBorrable(boolean borrable) {
        this.borrable = borrable;
    }

    /**
     * Gets the contrato.
     *
     * @return the contrato
     */
    public String getContrato() {
        return contrato;
    }

    /**
     * Sets the contrato.
     *
     * @param contrato
     *            the new contrato
     */
    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    /**
     * Gets the convenio.
     *
     * @return the convenio
     */
    public String getConvenio() {
        return convenio;
    }

    /**
     * Sets the convenio.
     *
     * @param convenio
     *            the new convenio
     */
    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    /**
     * Gets the fecha contrato.
     *
     * @return the fecha contrato
     */
    public Date getFechaContrato() {
        return fechaContrato;
    }

    /**
     * Sets the fecha contrato.
     *
     * @param fechaContrato
     *            the new fecha contrato
     */
    public void setFechaContrato(Date fechaContrato) {
        this.fechaContrato = fechaContrato;
    }

    /**
     * Gets the nit.
     *
     * @return the nit
     */
    public String getNit() {
        return nit;
    }

    /**
     * Sets the nit.
     *
     * @param nit
     *            the new nit
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

    /**
     * Gets the gastos.
     *
     * @return the gastos
     */
    public Set<Gasto> getGastos() {
        return gastos;
    }

    /**
     * Sets the gastos.
     *
     * @param gastos
     *            the new gastos
     */
    public void setGastos(Set<Gasto> gastos) {
        this.gastos = gastos;
    }

    /**
     * Gets the rol.
     *
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * Sets the rol.
     *
     * @param rol
     *            the new rol
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Gets the lista gastos.
     *
     * @return the lista gastos
     */
    public List<Gasto> getListaGastos() {
        List<Gasto> listaGastos = new ArrayList<Gasto>();
        listaGastos.addAll(gastos);
        return listaGastos;
    }

    /**
     * Eliminar gastos.
     */
    public void eliminarGastos() {
        gastos = null;
    }

    /**
     * Eliminar gasto.
     *
     * @param gastoEliminar
     *            the gasto eliminar
     */
    public void eliminarGasto(Gasto gastoEliminar) {
        Object gastoEl = null;
        int i;
        for (i = 0; i < gastosListados.size(); i++) {
            Gasto gast = (Gasto) gastosListados.get(i);
            if (gast.getValor() == gastoEliminar.getValor()
                    && gast.getTipoRubro().getId() == gastoEliminar.getTipoRubro().getId()
                    && gast.getDescripcion().equals(gastoEliminar.getDescripcion())
                    && gast.getVigencia() == gastoEliminar.getVigencia()) {
                gastoEl = gastosListados.get(i);
                break;
            }
        }
        if (gastoEl != null) {
            gastosListados.remove(i);
        }
    }

    /**
     * Gets the gastos listados.
     *
     * @return the gastos listados
     */
    public ArrayList<Gasto> getGastosListados() {
        return gastosListados;
    }

    /**
     * Sets the gastos listados.
     *
     * @param gastosListados
     *            the new gastos listados
     */
    public void setGastosListados(ArrayList<Gasto> gastosListados) {
        this.gastosListados = gastosListados;
    }

    /**
     * Gets the valor pendiente.
     *
     * @return the valor pendiente
     */
    public Long getValorPendiente() {
        return valorPendiente;
    }

    /**
     * Sets the valor pendiente.
     *
     * @param valorPendiente
     *            the new valor pendiente
     */
    public void setValorPendiente(Long valorPendiente) {
        this.valorPendiente = valorPendiente;
    }

    /**
     * Gets the tipo entidad.
     *
     * @return the tipo entidad
     */
    public String getTipoEntidad() {
        return tipoEntidad;
    }

    /**
     * Sets the tipo entidad.
     *
     * @param tipoEntidad
     *            the new tipo entidad
     */
    public void setTipoEntidad(String tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }

    /**
     * Gets the valor especie.
     *
     * @return the valor especie
     */
    public Long getValorEspecie() {
        return valorEspecie;
    }

    /**
     * Sets the valor especie.
     *
     * @param valorEspecie
     *            the new valor especie
     */
    public void setValorEspecie(Long valorEspecie) {
        this.valorEspecie = valorEspecie;
    }

    /**
     * Gets the es legalizacion.
     *
     * @return the es legalizacion
     */
    public String getEsLegalizacion() {
        return esLegalizacion;
    }

    /**
     * Sets the es legalizacion.
     *
     * @param esLegalizacion
     *            the new es legalizacion
     */
    public void setEsLegalizacion(String esLegalizacion) {
        this.esLegalizacion = esLegalizacion;
    }

    /**
     * Gets the rubros financiable arbol.
     *
     * @return the rubros financiable arbol
     */
    public List<RubroFinanciableArbol> getRubrosFinanciableArbol() {
        return rubrosFinanciableArbol;
    }

    /**
     * Sets the rubros financiable arbol.
     *
     * @param rubrosFinanciableArbol
     *            the new rubros financiable arbol
     */
    public void setRubrosFinanciableArbol(List<RubroFinanciableArbol> rubrosFinanciableArbol) {
        this.rubrosFinanciableArbol = rubrosFinanciableArbol;
    }

    /**
     * Sets the tree financiacion.
     *
     * @param treeFinanciacion
     *            the new tree financiacion
     */
    public void setTreeFinanciacion(TreeNode treeFinanciacion) {
        this.treeFinanciacion = treeFinanciacion;
    }

    /**
     * Gets the modalidad fuente financiacion.
     *
     * @return the modalidad fuente financiacion
     */
    public ModalidadFuenteFinanciacion getModalidadFuenteFinanciacion() {
        return modalidadFuenteFinanciacion;
    }

    /**
     * Sets the modalidad fuente financiacion.
     *
     * @param modalidadFuenteFinanciacion
     *            the new modalidad fuente financiacion
     */
    public void setModalidadFuenteFinanciacion(ModalidadFuenteFinanciacion modalidadFuenteFinanciacion) {
        this.modalidadFuenteFinanciacion = modalidadFuenteFinanciacion;
    }

    public boolean isMostrarContrapartida() {
        Convocatoria convocatoria = (Convocatoria) proyecto.getModalidad();
        return (modalidadFuenteFinanciacion != null && modalidadFuenteFinanciacion.isIncluirContrapartida())
                || (modalidadFuenteFinanciacion == null
                        && (fuente.getInternaExterna().equals("I") || fuente.getInternaExterna().equals("O")))
                        && convocatoria.isIncluirContrapartidaFinanacion();
    }

    public TreeNode getNodeTotal() {
        return nodeTotal;
    }

    public void setNodeTotal(TreeNode nodeTotal) {
        this.nodeTotal = nodeTotal;
    }

    public Tipos getSubtipoFinanciacion() {
        return subtipoFinanciacion;
    }

    public void setSubtipoFinanciacion(Tipos subtipoFinanciacion) {
        this.subtipoFinanciacion = subtipoFinanciacion;
    }

    public Financiacion crearCopia() {
        Financiacion finNueva = new Financiacion();
        finNueva.setContrato(this.getContrato());
        finNueva.setConvenio(this.getConvenio());
        finNueva.setFuente(this.getFuente());
        finNueva.setValorEspecie(this.getValorEspecie());
        finNueva.setValor(this.getValor());
        finNueva.setFechaContrato(this.getFechaContrato());
        finNueva.setNit(this.getNit());
        finNueva.setTipoEntidad(this.getTipoEntidad());
        finNueva.setRol(this.getRol());
        finNueva.setValorPendiente(this.getValorPendiente());
        return finNueva;
    }

    public List<Gasto> getGastosTipoRubro(Long idTipo) {
        List<Gasto> gastosTipoRubro = new ArrayList<Gasto>();
        Iterator<Gasto> i = getListaGastos().iterator();
        while (i.hasNext()) {
            Gasto gasto = i.next();
            if (idTipo.equals(gasto.getTipoRubro().getId())) {
                gastosTipoRubro.add(gasto);
            }
        }
        return gastosTipoRubro;
    }

    public List<Gasto> getGastosDesembolso() {
        return getGastosTipoRubro(TipoRubro.DESEMBOLSO);
    }

    public List<Gasto> getGastosIngresos() {
        List<Gasto> gastosIngresos = new ArrayList<Gasto>();
        Iterator<Gasto> i = getListaGastos().iterator();
        while (i.hasNext()) {
            Gasto gasto = i.next();
			if ((StringUtils.isNotBlank(gasto.getTipoRubro().getDescripcion())
					&& (gasto.getTipoRubro().getDescripcion().equals(TipoRubro.INGRESO)
							|| gasto.getTipoRubro().getDescripcion().equals("INGRESOS_CP_2022")))
					|| gasto.getTipoRubro().getId() == 171L) {
				gastosIngresos.add(gasto);
			}
        }
        return gastosIngresos;
    }

}
