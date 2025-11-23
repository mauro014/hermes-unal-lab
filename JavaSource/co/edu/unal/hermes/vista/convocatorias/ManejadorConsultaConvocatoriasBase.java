package co.edu.unal.hermes.vista.convocatorias;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.CorteConvocatoria;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoConvocatoria;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.vista.ManejadorBase;


/**
 * The Class ManejadorConsultaConvocatoriasBase.
 */
public class ManejadorConsultaConvocatoriasBase extends ManejadorBase {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8082925157808673038L;

    /**
     * Se valida si la dependencia de la convocatoria permite al usuario que
     * esta actualmente registrado registrar propuestas.
     *
     * @param listaConvocatorias
     *            the lista convocatorias
     * @param dependencia
     *            the dependencia
     * @return the list
     */
    private List<Convocatoria> validarDependenciaConvocatorias(List<Convocatoria> listaConvocatorias,
            Dependencia dependencia) {
        List<Convocatoria> listaConvocatoriasValidas = new ArrayList<Convocatoria>();
        Iterator<Convocatoria> it = listaConvocatorias.iterator();
        while (it.hasNext()) {
            Convocatoria convocatoria = (Convocatoria) it.next();
            if (convocatoria.getDependenciaRestriccion() != null) {                 
                if(convocatoria.getEsInterDependencias() != null){
                	if(convocatoria.getEsInterDependencias()){
                		String deps = convocatoria.getInterDependenciasRestriccion();
                    	String [] valDeps = deps.split(",");
                    	for(int i = 0; i < valDeps.length; i++){
                    		Dependencia dependenciaConvocatoria = servicioGeneral.cargaDependencia(valDeps[i]);
                    		// Si la dependencia de la restriccion es una sede se busca la
                            // sede de la depencia del investigador y se compara.
                            if (dependenciaConvocatoria.getEsSede().booleanValue()) {
                                if (dependencia != null
                                        && dependencia.getSede().getId().toString().equals(dependenciaConvocatoria.getId())) {
                                    listaConvocatoriasValidas.add(convocatoria);
                                }
                            }
                            // Si la dependencia de la restriccion es una facultad se busca
                            // la sede de la depencia del investigador y comparan
                            if (dependenciaConvocatoria.getEsFacultad().booleanValue()) {
                                if (dependencia != null && dependencia.getFacultad() != null
                                        && dependencia.getFacultad().getId().equals(dependenciaConvocatoria.getId())) {
                                    listaConvocatoriasValidas.add(convocatoria);
                                }
                            }
                    	} 
                	}                	    	
                }else{
                	Dependencia dependenciaConvocatoria = convocatoria.getDependenciaRestriccion();
                    dependenciaConvocatoria = servicioGeneral.cargaDependencia(dependenciaConvocatoria.getId());

                    // Si la dependencia de la restriccion es una sede se busca la
                    // sede de la depencia del investigador y se compara.
                    if (dependenciaConvocatoria.getEsSede().booleanValue()) {
                        if (dependencia != null && dependencia.getSede().getId().toString().equals(dependenciaConvocatoria.getId())) {
                            listaConvocatoriasValidas.add(convocatoria);
                        }
                    } else if (dependenciaConvocatoria.getEsFacultad().booleanValue()){
                    	if (dependencia != null && dependencia.getFacultad() != null
                                && dependencia.getFacultad().getId().equals(dependenciaConvocatoria.getId())) {
                            listaConvocatoriasValidas.add(convocatoria);
                        }
                    }

                    // Si la dependencia de la restriccion es una facultad se busca
                    // la sede de la depencia del investigador y comparan
//                    if (dependenciaConvocatoria.getEsFacultad().booleanValue()) {
//                        if (dependencia != null && dependencia.getFacultad() != null
//                                && dependencia.getFacultad().getId().equals(dependenciaConvocatoria.getId())) {
//                            listaConvocatoriasValidas.add(convocatoria);
//                        }
//                    }
                }
                
            } else {
                listaConvocatoriasValidas.add(convocatoria);
            }
        }
        return listaConvocatoriasValidas;
    }

    /**
     * Validar vinculacion persona.
     *
     * @return true, if successful
     */
    protected boolean validarVinculacionPersona() {
        InvestigadorInterno invI = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
        return servicioProyecto.validarVinculacionPersona(invI, false);
    }

    /**
     * Validar convocatoria comunicado externo.
     *
     * @param convocatoriaId
     *            the convocatoria id
     * @return the string
     */
    protected String validarConvocatoriaComunicadoExterno(long convocatoriaId) {

        List<Parametro> listaParametro = this.servicioGeneral.obtenerObjetos(Parametro.class,
                "FROM Parametro WHERE nombre = '" + Convocatoria.CONVOCATORIA_COMUNICADO + "' and descripcion like '%=="
                        + convocatoriaId + "==%'");

        if (listaParametro != null && listaParametro.size() > 0) {
            Parametro parametro = listaParametro.get(0);
            return parametro.getValor();
        }

        return "";
    }

    /**
     * Se verifica que si es convocatoria permanente este no tenga cortes o
     * tenga cortes activos.
     *
     * @param convocatoria
     *            the convocatoria
     * @param dependenciaId
     *            the dependencia id
     * @return true, if successful
     */
    protected boolean validadCorteConvocatoriaPermanente(Convocatoria convocatoria, String dependenciaId) {
        if (convocatoria.getPadre() != null && convocatoria.getPadre().getEsPermanente() != null
                && convocatoria.getPadre().getEsPermanente().equals(ConvocatoriaPadre.PERMANENTE)) {
            String sql = "from CorteConvocatoria cc " + "where cc.convocatoriaPadre.id = '"
                    + convocatoria.getPadre().getId() + "'" + " and to_date(SYSDATE,'dd/mm/yyyy') between "
                    + "to_date(cc.fechaInicial,'dd/mm/yyyy') and " + "to_date(cc.fechaFinal,'dd/mm/yyyy')"
                    + " and cc.sede.id = '" + dependenciaId + "'" + " and cc.estado <> '"
                    + CorteConvocatoria.ESTADO_BORRADO + "'";
            List<CorteConvocatoria> cortes = servicioGeneral.obtenerObjetos(CorteConvocatoria.class, sql);
            if( cortes != null && cortes.size() > 0){
                CorteConvocatoria corteConvocatoria = cortes.get(0);
                sesion.setAttribute(CorteConvocatoria.ID_CORTE_CONVOCATORIA_SESSION, corteConvocatoria.getId());
                return true;
            }
            return false;
        } else {
            return true;
        }
    }
    
    protected CorteConvocatoria consultarCorteConvocatoriaPermanenteVigente(Convocatoria convocatoria, String dependenciaId) {
        if (convocatoria.getPadre() != null && convocatoria.getPadre().getEsPermanente() != null
                && convocatoria.getPadre().getEsPermanente().equals(ConvocatoriaPadre.PERMANENTE)) {
            String sql = "from CorteConvocatoria cc " + "where cc.convocatoriaPadre.id = '"
                    + convocatoria.getPadre().getId() + "'" + " and to_date(SYSDATE,'dd/mm/yyyy') between "
                    + "to_date(cc.fechaInicial,'dd/mm/yyyy') and " + "to_date(cc.fechaFinal,'dd/mm/yyyy')"
                    + " and cc.sede.id = '" + dependenciaId + "'" + " and cc.estado <> '"
                    + CorteConvocatoria.ESTADO_BORRADO + "'";
            List<CorteConvocatoria> cortes = servicioGeneral.obtenerObjetos(CorteConvocatoria.class, sql);
            if( cortes != null && cortes.size() > 0){
                CorteConvocatoria corteConvocatoria = cortes.get(0);
                sesion.setAttribute(CorteConvocatoria.ID_CORTE_CONVOCATORIA_SESSION, corteConvocatoria.getId());
                return corteConvocatoria;
            }
            
            return null;
            
        } else {
            return null;
        }
    }

    /**
     * Obtener listade convoctoria activas x padre.
     *
     * @param convocatoriaPadre
     *            the convocatoria padre
     * @return the list
     */
    /*
     * Se validan las convocatorias activas y que esten habilitados para la
     * dependencia de la persona actual.
     */
    protected List<Convocatoria> obtenerListadeConvoctoriaActivasXPadre(ConvocatoriaPadre convocatoriaPadre) {

        Dependencia dependencia = servicioDependencia.obtenerDependencia(personaActual.getId());

        List<Object> listaEstados = new Vector<Object>();
        listaEstados.add(EstadoConvocatoria.ACTIVA);

        // Se cargan las convocatorias en los estados definidos.
        List<Convocatoria> listaConvocatoriaEstado = new Vector<Convocatoria>();
        listaConvocatoriaEstado.addAll(
                servicioModalidad.obtenerConvocatoriasXPadreYListaEstado(convocatoriaPadre.getId(), listaEstados));

        // Se validan la dependencia de la convocatoria con la dependencia
        // actual
        List<Convocatoria> listaConvocatoriaDependencia = validarDependenciaConvocatorias(listaConvocatoriaEstado,
                dependencia);

        return listaConvocatoriaDependencia;
    }

    /**
     * On tab change.
     */
    public void onTabChange() {
        // Evitar exepcion en vista
    }

    /**
     * Metodo que permite verificar si en un listado de convocatorias hay un
     * tipo de modalidad especifico.
     * 
     * @param tipo
     * @param listaConvocatorias
     * @return
     */
    protected boolean verificarTipoConvocatoria(String tipo, List<Convocatoria> listaConvocatorias) {

        for (int j = 0; j < listaConvocatorias.size(); j++) {

            // se devuelve si alguna modalidad tiene el tipo.
            if (((Convocatoria) listaConvocatorias.get(j)).getTipo().getId().equals(tipo)) {
                return true;
            }
        }
        return false;
    }
    
    
    

}
