/**
 * @author Martha L. Correa - Mauricio Amaya 
 */

package co.edu.unal.hermes.vista.correos;

import java.util.Iterator;
import java.util.List;

import co.edu.unal.hermes.modelo.Coleccion;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.servicios.imp.ServicioBiodiversidad;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorEnviarAlertasColecciones extends ManejadorBase {

    private static final long serialVersionUID = 1101308042894155431L;
    private String resultadoEnvio;

    public ManejadorEnviarAlertasColecciones() {
        super();
        resultadoEnvio = "";
    }

    public void enviarAlertas() {

        List<Coleccion> colecciones = servicioBiodiversidad.obtenerColeccionesAlertas();
        CorreoPlantilla correoPlantilla = cargarPlantilla(ServicioBiodiversidad.CORREO_PLANTILLA_ALERTA);
        int totalMensajesAEnviar = 0;
        int correosEnviados = 0;

        if (!esListaVacia(colecciones)) {
            Iterator<Coleccion> i = colecciones.iterator();
            while (i.hasNext()) {
                Coleccion coleccion = i.next();

                Correo correo = new Correo();
                correo.setOrigen(Correo.CORREO_HERMES);
                correo.setAsunto(correoPlantilla.getAsunto());
                correo.adicionarDireccion(coleccion.getCuradorGeneral().getEmail());
                //correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
                correo.setCuerpo(correoPlantilla.getCuerpo());

                correo.setCuerpo(
                        reemplazarCadenaSinReplace(correo.getCuerpo(), ServicioBiodiversidad.CORREO_NOMBRE_CURADOR,
                                coleccion.getCuradorGeneral().getNombreCompletoMinusculas()));

                correo.setCuerpo(reemplazarCadenaSinReplace(correo.getCuerpo(),
                        ServicioBiodiversidad.CORREO_NOMBRE_COLECCION, coleccion.getNombre()));

                correo.setCuerpo(reemplazarCadenaSinReplace(correo.getCuerpo(),
                        ServicioBiodiversidad.CORREO_FECHA_VENCIMIENTO_ACTUALIZACION,
                        coleccion.getFechaProximoRegistro().toString()));
                
                List<Persona> encargadosVicerrectoria = servicioBiodiversidad.obtenerPersonasEncargadasColeccionesBiologicas();
                for (int j = 0; j < encargadosVicerrectoria.size(); j++) {
                    Persona per = encargadosVicerrectoria.get(j);
                    correo.adicionarDireccion(per.getEmail());
                }

                totalMensajesAEnviar++;

                try {
                    if (servicioCorreo.enviarCorreo(correo)) {
                        coleccion.setControlAlertas(coleccion.getControlAlertas() + 1);
                        if (coleccion.getControlAlertas() >= 2) {
                            coleccion.setControlAlertas(0L);
                        }
                        servicioGeneral.guardarObjeto(coleccion);
                        correosEnviados++;
                    }
                } catch (Exception e) {
                    mensajeError("Error al enviar mensaje de colección con id " + coleccion.getId());
                }

            }
        }
        mensajeInfo(correosEnviados + " de " + totalMensajesAEnviar + " Correo(s) de Alerta Enviado(s).");
    }

    public CorreoPlantilla cargarPlantilla(int cod_id) {
        CorreoPlantilla correoActualAux = new CorreoPlantilla();
        String hql = "FROM CorreoPlantilla WHERE id='" + cod_id + "'";
        List<CorreoPlantilla> lista = servicioGeneral.obtenerObjetos(CorreoPlantilla.class, hql);
        if (lista != null && lista.size() > 0) {
            correoActualAux = (CorreoPlantilla) lista.get(0);
        }
        return correoActualAux;
    }

    /**
     * @return the resultadoEnvio
     */
    public String getResultadoEnvio() {
        return resultadoEnvio;
    }

    /**
     * @param resultadoEnvio
     *            the resultadoEnvio to set
     */
    public void setResultadoEnvio(String resultadoEnvio) {
        this.resultadoEnvio = resultadoEnvio;
    }

}
