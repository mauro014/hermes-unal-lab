package co.edu.unal.hermes.vista.proyectos;

import java.util.Iterator;

import org.apache.myfaces.custom.navmenu.NavigationMenuItem;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorMenuFormularios.
 */
public class ManejadorMenuFormularios extends ManejadorBase {
    
    public static final String MANEJADOR_MENU_FORMULARIOS_SESSION = "manejadorMenuFormularios";

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 164061198842230581L;

    /** The proyecto actual. */
    private Proyecto proyectoActual;
    
    /** The item proyecto. */
    private NavigationMenuItem[] itemProyecto;
    
    /** The model item proyecto. */
    private MenuModel modelItemProyecto;
    
    /** The menu item array. */
    private MenuItem[] menuItemArray;
    
    /** The posicion menu activo. */
    private boolean[] posicionMenuActivo = new boolean[15];
    
    /** The rutaestadobien. */
    String rutaestadobien = "";
    
    /** The rutaestadomal. */
    String rutaestadomal = "";

    /**
     * Instantiates a new manejador menu formularios.
     */
    public ManejadorMenuFormularios() {
        super();

        proyectoActual = (Proyecto) sesion.getAttribute("proyecto");
        modelItemProyecto = new DefaultMenuModel();

        Boolean consultaEvaluacion = (Boolean) sesion.getAttribute("consultaEvaluacion");

        if (proyectoActual.getModalidad().getTipo().getFormularios() != null
                && proyectoActual.getModalidad().getTipo().getFormularios().size() > 0) {
            NavigationMenuItem[] subItems = new NavigationMenuItem[proyectoActual.getModalidad().getTipo()
                    .getFormularios().size()];
            menuItemArray = new MenuItem[proyectoActual.getModalidad().getTipo().getFormularios().size()];

            Iterator<Formulario> iterador = proyectoActual.getModalidad().getTipo().getFormularios().iterator();
            int i = 0;

            while (iterador.hasNext()) {
                Formulario formulario = (Formulario) iterador.next();

                MenuItem item = new MenuItem();
                item.setValue(formulario.getNombre());
                item.setOutcome(formulario.getAccion());
                item.setDisabled(true);
                item.setIcon("/images/mal1.gif");
                menuItemArray[i] = item;

                if (formulario.getAccion().equals("irEmpresas") && this.getEsMostrarEmpresa() == false) {
                    modelItemProyecto = new DefaultMenuModel();
                    item.setValue(formulario.getNombre());
                    item.setDisabled(true);
                    item.setOutcome(formulario.getAccion());
                    item.setRendered(false);
                    item.setIcon(null);
                    menuItemArray[i] = item;
                }

                if (formulario.getAccion().equals("irActividadesPersona") && this.getEsMostrarEmpresa() == false) {
                    modelItemProyecto = new DefaultMenuModel();
                    item.setValue(formulario.getNombre());
                    item.setDisabled(true);
                    item.setOutcome(formulario.getAccion());
                    item.setRendered(false);
                    item.setIcon(null);
                    menuItemArray[i] = item;
                }

                i++;
            }

            activarItems();

            for (int j = 0; j < menuItemArray.length; j++) {
                modelItemProyecto.addMenuItem(menuItemArray[j]);
            }

            NavigationMenuItem itemWithChildren = new NavigationMenuItem("FORMULARIOS DEL PROYECTO", "");

            itemWithChildren.setNavigationMenuItems(subItems);
            itemProyecto = new NavigationMenuItem[1];
            itemProyecto[0] = itemWithChildren;

        } else {

            MenuItem item = new MenuItem();
            item.setValue("Datos Básicos");
            item.setDisabled(true);
            item.setUrl(this.iraDatosBasicos());
            item.setIcon("/images/mal1.gif");
            modelItemProyecto.addMenuItem(item);

            MenuItem item2 = new MenuItem();
            item.setValue("Imprimir Reporte");
            item.setDisabled(true);
            item.setUrl(this.iraSubirArchivo());
            item.setIcon("/images/mal1.gif");
            modelItemProyecto.addMenuItem(item2);

            activarItems();

            for (int j = 0; j < menuItemArray.length; j++) {
                modelItemProyecto.addMenuItem(menuItemArray[j]);
            }

            NavigationMenuItem[] subItems = new NavigationMenuItem[2];
            subItems[0] = new NavigationMenuItem("Datos Básicos", this.iraDatosBasicos());
            subItems[1] = new NavigationMenuItem("Imprimir Reporte", this.iraSubirArchivo());
            NavigationMenuItem itemWithChildren = new NavigationMenuItem("Formularios Proyecto", "");
            itemWithChildren.setNavigationMenuItems(subItems);
            itemProyecto = new NavigationMenuItem[1];
            itemProyecto[0] = itemWithChildren;
        }

        if (consultaEvaluacion != null && consultaEvaluacion.booleanValue()) {
            restringirOpcionesMenu();
        }
    }

    /**
     * Gets the item proyecto.
     *
     * @return the item proyecto
     */
    public NavigationMenuItem[] getItemProyecto() {
        return itemProyecto;
    }

    /**
     * Sets the item proyecto.
     *
     * @param itemProyecto the new item proyecto
     */
    public void setItemProyecto(NavigationMenuItem[] itemProyecto) {
        this.itemProyecto = itemProyecto;
    }

    /**
     * Activar items.
     */
    public void activarItems() {
        if (proyectoActual.getEstadoProyecto() != null) {
            actualizarMenuFormulariosDinamico(proyectoActual.getFase().intValue());
        } else {
            this.menuItemArray[0].setDisabled(false);
            this.menuItemArray[0].setIcon("/images/bien1.gif");

            posicionMenuActivo[0] = true;

        }
        restringirOpcionesMenu();
    }

    /**
     * Restringir opciones menu.
     */
    public void restringirOpcionesMenu() {
        if (proyectoActual.getTipoInvestigacion() != null && proyectoActual.getTipoInvestigacion().getId() != null
                && proyectoActual.getTipoInvestigacion().getId

        ().equals("2040100")) {
            posicionMenuActivo[1] = false;
        }
    }

    /**
     * Ir ficha minima.
     *
     * @return the string
     */
    public String irFichaMinima() {
        sesion.removeAttribute("manejadorFichaMinimaProyectos");
        activarItems();
        return "irFichaMinima";
    }

    /**
     * Ficha minima home.
     *
     * @return the string
     */
    public String fichaMinimaHome() {
        sesion.removeAttribute("consultaFichaMinina");
        sesion.removeAttribute("esProyectoFichaMinimaNueva");
        sesion.removeAttribute("proyectoFichaMinimaNueva");
        sesion.removeAttribute("proyectoFichaMinina");
        sesion.removeAttribute("manejadorFichaMinimaHome");
        activarItems();
        return "fichaMinimaHome";
    }

    /**
     * Ir mod_ j i_ sem.
     *
     * @return the string
     */
    public String irMod_JI_SEM() {
        sesion.removeAttribute("manejadorFichaMinimaHomeJovenes");
        activarItems();
        return "irMod_JI_SEM";
    }

    /**
     * Ir solicitud isbn.
     *
     * @return the string
     */
    public String irSolicitudISBN() {
        sesion.removeAttribute("ManejadorSolicitudISBN");
        activarItems();
        return "irSolicitudISBN";
    }

    /**
     * Ira datos basicos.
     *
     * @return the string
     */
    public String iraDatosBasicos() {
        sesion.removeAttribute("manejadorDatosBasicos");
        activarItems();
        return "irDatosBasicos";
    }

    /**
     * Ira empresas.
     *
     * @return the string
     */
    public String iraEmpresas() {
        sesion.removeAttribute("manejadorEmpresas");
        activarItems();
        return "irEmpresas";
    }

    /**
     * Ira investigadores.
     *
     * @return the string
     */
    public String iraInvestigadores() {
        sesion.removeAttribute("manejadorInvestigadores");
        activarItems();
        return "irInvestigadores";
    }

    /**
     * Ira lineas.
     *
     * @return the string
     */
    public String iraLineas() {
        sesion.removeAttribute("manejadorLineasProyecto");
        activarItems();
        return "irLineas";
    }

    /**
     * Ira objetivos resultados.
     *
     * @return the string
     */
    public String iraObjetivosResultados() {
        sesion.removeAttribute("manejadorObjetivosResultados");
        activarItems();
        return "irObjetivosResultados";
    }

    /**
     * Ira informacion especifica.
     *
     * @return the string
     */
    public String iraInformacionEspecifica() {
        sesion.removeAttribute("manejadorInformacionEspecifica");
        activarItems();
        return "irInformacionEspecifica";
    }

    /**
     * Ira actividades.
     *
     * @return the string
     */
    public String iraActividades() {
        sesion.removeAttribute("manejadorActividades");
        activarItems();
        return "irActividades";
    }

    /**
     * Ira actividades persona.
     *
     * @return the string
     */
    public String iraActividadesPersona() {
        sesion.removeAttribute("manejadorActividades");
        activarItems();
        return "irActividadesPersona";
    }

    /**
     * Ira bibliografia.
     *
     * @return the string
     */
    public String iraBibliografia() {
        sesion.removeAttribute("manejadorBibliografia");
        activarItems();
        return "irBibliografia";
    }

    /**
     * Ira areas tematicas.
     *
     * @return the string
     */
    public String iraAreasTematicas() {
        sesion.removeAttribute("manejadorAreasTematicas");
        activarItems();
        return "irAreasTematicas";
    }

    /**
     * Ira productos.
     *
     * @return the string
     */
    public String iraProductos() {
        sesion.removeAttribute("manejadorProductos");
        activarItems();
        return "irProductos";
    }

    /**
     * Ira evaluadores.
     *
     * @return the string
     */
    public String iraEvaluadores() {
        sesion.removeAttribute("manejadorEvaluadores");
        activarItems();
        return "irEvaluadores";
    }

    /**
     * Ira fuentes.
     *
     * @return the string
     */
    public String iraFuentes() {
        sesion.removeAttribute("manejadorFuentesFinancieras");
        activarItems();
        return "irFuentes";
    }

    /**
     * Ira rubros.
     *
     * @return the string
     */
    public String iraRubros() {
        sesion.removeAttribute("manejadorRubros");
        activarItems();
        return "irRubros";
    }

    /**
     * Ira subir archivo.
     *
     * @return the string
     */
    public String iraSubirArchivo() {
        sesion.removeAttribute("manejadorArchivos");
        activarItems();
        return "irSubirArchivo";
    }

    /**
     * Actualizar menu formularios.
     *
     * @param numFormulario the num formulario
     */
    public void actualizarMenuFormularios(int numFormulario) {
        for (int i = 0; i <= numFormulario; i++) {
            posicionMenuActivo[i] = true;
        }
    }

    /**
     * Actualizar menu formularios dinamico.
     *
     * @param numFormulario the num formulario
     */
    public void actualizarMenuFormulariosDinamico(int numFormulario) {
        if (menuItemArray != null) {
            for (int i = 0; i < this.menuItemArray.length; i++) {

                if (this.menuItemArray[i].isRendered() == false) {
                    numFormulario++;
                }

                if (this.menuItemArray[i].isRendered() && i <= numFormulario) {
                    this.menuItemArray[i].setDisabled(false);
                    this.menuItemArray[i].setIcon("/images/bien1.gif");

                }

                if (this.menuItemArray[i].isRendered() && i > numFormulario) {
                    this.menuItemArray[i].setDisabled(true);
                    this.menuItemArray[i].setIcon("/images/mal1.gif");
                }

            }
        }
    }

    /**
     * Actualizar menu formularios dinamico2.
     *
     * @param numFormulario the num formulario
     */
    public void actualizarMenuFormulariosDinamico2(int numFormulario) {
        if (itemProyecto != null) {
            for (int i = 0; i < this.itemProyecto[0].getNavigationMenuItems().length; i++) {

                if (this.itemProyecto[0].getNavigationMenuItems()[i].isRendered() == false) {
                    numFormulario++;
                }

                if (this.itemProyecto[0].getNavigationMenuItems()[i].isRendered() && i <= numFormulario) {
                    this.itemProyecto[0].getNavigationMenuItems()[i].setDisabled(false);
                    this.itemProyecto[0].getNavigationMenuItems()[i].setIcon("/images/bien1.gif");

                }

                if (this.itemProyecto[0].getNavigationMenuItems()[i].isRendered() && i > numFormulario) {
                    this.itemProyecto[0].getNavigationMenuItems()[i].setDisabled(true);
                    this.itemProyecto[0].getNavigationMenuItems()[i].setIcon("/images/mal1.gif");
                }

            }
        }
    }

    /**
     * Ira mis proyectos.
     *
     * @return the string
     */
    public String iraMisProyectos() {
        System.out.println("llamando a ir a mis proyectos");
        return "misProyectos";
    }

    /**
     * Gets the es mostrar empresa.
     *
     * @return the es mostrar empresa
     */
    public boolean getEsMostrarEmpresa() {
        return proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.SENA);
    }

    /**
     * Gets the es mostrar financiacion.
     *
     * @return the es mostrar financiacion
     */
    public boolean getEsMostrarFinanciacion() {
        return !proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.JORNADA_DOCENTE);
    }

    /**
     * Gets the es mostrar gastos.
     *
     * @return the es mostrar gastos
     */
    public boolean getEsMostrarGastos() {
        return !proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.JORNADA_DOCENTE);
    }

    /**
     * Gets the proyecto actual.
     *
     * @return the proyecto actual
     */
    public Proyecto getProyectoActual() {
        return proyectoActual;
    }

    /**
     * Sets the proyecto actual.
     *
     * @param proyectoActual the new proyecto actual
     */
    public void setProyectoActual(Proyecto proyectoActual) {
        this.proyectoActual = proyectoActual;
    }

    /**
     * Gets the posicion menu activo.
     *
     * @return the posicion menu activo
     */
    public boolean[] getPosicionMenuActivo() {
        return posicionMenuActivo;
    }

    /**
     * Sets the posicion menu activo.
     *
     * @param posicionMenuActivo the new posicion menu activo
     */
    public void setPosicionMenuActivo(boolean[] posicionMenuActivo) {
        this.posicionMenuActivo = posicionMenuActivo;
    }

    /**
     * Gets the model item proyecto.
     *
     * @return the model item proyecto
     */
    public MenuModel getModelItemProyecto() {
        return modelItemProyecto;
    }

    /**
     * Sets the model item proyecto.
     *
     * @param modelItemProyecto the new model item proyecto
     */
    public void setModelItemProyecto(MenuModel modelItemProyecto) {
        this.modelItemProyecto = modelItemProyecto;
    }

    /**
     * Gets the menu item array.
     *
     * @return the menu item array
     */
    public MenuItem[] getMenuItemArray() {
        return menuItemArray;
    }

    /**
     * Sets the menu item array.
     *
     * @param menuItemArray the new menu item array
     */
    public void setMenuItemArray(MenuItem[] menuItemArray) {
        this.menuItemArray = menuItemArray;
    }

}
