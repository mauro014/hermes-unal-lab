package co.edu.unal.hermes.bd.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.unal.hermes.bd.IPropiedadIntelectualDAO;
import co.edu.unal.hermes.modelo.ArchivoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.CaracterPropiedadIntelectual;
import co.edu.unal.hermes.modelo.ClasificacionEstadoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.HistoricoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.ObraFonogramaPropiedadIntelectual;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PropiedadIntelectual;
import co.edu.unal.hermes.modelo.SubEstadoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.SubTipoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoPersonaPropiedadIntelectual;
import co.edu.unal.hermes.modelo.TipoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;

/**
 * Maneja el acceso a los atributos generales del sistema con Hibernate
 */
public class PropiedadIntelectualDAOHibernate extends HibernateDaoSupport implements IPropiedadIntelectualDAO {

    public List<TipoPropiedadIntelectual> obtenerTiposPropiedadIntelectual(String estado) {
        Session session = null;
        Query q = null;
        List<TipoPropiedadIntelectual> resultado = null;

        try {
            session = getSession();
            q = session.createQuery("select tpi from TipoPropiedadIntelectual tpi where " + "tpi.estado = '" + estado
                    + "' order by tpi.nombre");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        return resultado;
    }

    public List<SubTipoPropiedadIntelectual> obtenerSubTiposPropiedadIntelectual(Long tipo, String estado, Boolean esGestor) {
        Session session = null;
        Query q = null;
        List<SubTipoPropiedadIntelectual> resultado = null;
        String estadoSubTipoPI = "";
        
        if(!esGestor)
        	estadoSubTipoPI = "stpi.estado";
        else
        	estadoSubTipoPI = "stpi.estadoGestor";

        try {
            session = getSession();
            q = session.createQuery("select stpi from SubTipoPropiedadIntelectual stpi where " + "stpi.tipo.id = '"
                    + tipo + "' and "+estadoSubTipoPI+" = '" + estado + "' order by stpi.nombre");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        return resultado;
    }

    public PropiedadIntelectual obtenerRegistroPropiedadIntelectual(Long id) {
        Session session = null;
        Query q = null;
        List<PropiedadIntelectual> resultado = null;

        try {
            session = getSession();
            q = session.createQuery("select pi from PropiedadIntelectual pi where " + "pi.id = '" + id + "'");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        if (resultado!=null && !resultado.isEmpty()) {
            return (PropiedadIntelectual) resultado.get(0);
        } else {
            return null;
        }
    }

    public List<PropiedadIntelectual> obtenerPropiedadesRegistradasPersona(Persona persona) {
        List<?> propiedades;
        List<PropiedadIntelectual> result = new ArrayList<PropiedadIntelectual>();

        Session session = getSession();

        String query = "select distinct PI.PRI_ID AS id, PST.PIS_NOMBRE as tipo, SUBE.PSE_NOMBRE AS estado, pi.PRI_FECHA_REGISTRO as fecha, pi.PRI_TITULO as titulo, pi.PRI_FECHA_REVISION as fechaRevision, pi.INV_ID as docResponsable, pi.TDO_ID AS tipoDocResponsable, SUBE.pse_tramite as tramite, SUBE.pse_id as idSubestado "
                + "from HER_PROPIEDAD_INTELECTUAL PI "
                + "left join HER_PROPIEDAD_INT_SUBTIPO PST ON PST.PIS_ID = PI.PIS_ID "
                + "left join HER_PROPIEDAD_INT_SUBESTADO SUBE ON SUBE.PSE_ID = PI.PSE_ID,"
                + "HER_PROPIEDAD_INT_PERSONA pepi " + "where (pi.inv_id = '" + persona.getId().getDocumento()
                + "' and pi.tdo_id = '" + persona.getId().getTipoDocumento() + "') "
                + "or (pepi.PRI_ID = pi.PRI_ID and pepi.TDO_ID = '" + persona.getId().getTipoDocumento()
                + "' and pepi.INV_ID = '" + persona.getId().getDocumento() + "')  order by pi.PRI_FECHA_REGISTRO DESC";

        SQLQuery sqlQuery1 = session.createSQLQuery(query);

        propiedades = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("tipo", Hibernate.STRING)
                .addScalar("estado", Hibernate.STRING).addScalar("fecha", Hibernate.DATE)
                .addScalar("titulo", Hibernate.STRING).addScalar("fechaRevision", Hibernate.DATE)
                .addScalar("docResponsable", Hibernate.STRING).addScalar("tipoDocResponsable", Hibernate.STRING).addScalar("tramite", Hibernate.STRING).addScalar("idSubestado", Hibernate.LONG).list();

        session.close();

        Iterator<?> it = propiedades.iterator();

        while (it.hasNext()) {
            Object[] name = (Object[]) it.next();
            PropiedadIntelectual propiedad = new PropiedadIntelectual();

            propiedad.setId((Long) name[0]);
            SubTipoPropiedadIntelectual subtipo = new SubTipoPropiedadIntelectual();
            subtipo.setNombre((String) name[1]);
            propiedad.setSubTipo(subtipo);
            SubEstadoPropiedadIntelectual subestado = new SubEstadoPropiedadIntelectual();
            subestado.setId((Long) name[9]);
            subestado.setTramite((String) name[8]);
            subestado.setNombre((String) name[2]);
            propiedad.setSubEstado(subestado);
            propiedad.setFechaRegistro((Date) name[3]);
            propiedad.setTitulo((String) name[4]);
            propiedad.setFechaRevision((Date) name[5]);
            InvestigadorInterno responsable = new InvestigadorInterno();
            IdPersona id = new IdPersona();
            id.setDocumento((String) name[6]);
            id.setTipoDocumento((String) name[7]);
            responsable.setId(id);
            propiedad.setResponsableRegistro(responsable);

            result.add(propiedad);
        }
        return result;
    }

    public CaracterPropiedadIntelectual obtenerCaracterPropiedadIntelectual(Long id) {
        Session session = null;
        Query q = null;
        List<CaracterPropiedadIntelectual> resultado = null;

        try {
            session = getSession();
            q = session
                    .createQuery("select cpi from CaracterPropiedadIntelectual cpi where " + "cpi.id = '" + id + "'");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        return (CaracterPropiedadIntelectual) resultado.get(0);
    }

    public List<TipoPersonaPropiedadIntelectual> obtenerTiposPersonaPropiedadIntelectual(String subTipoPropiedad) {
        Session session = null;
        Query q = null;
        List<TipoPersonaPropiedadIntelectual> resultado = null;

        try {
            session = getSession();
            q = session.createQuery("select tp from TipoPersonaPropiedadIntelectual tp where tp.subTipoPropiedad.id = '"
                    + subTipoPropiedad + "' order by tp.nombre");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        return resultado;
    }

    public List<TipoPersonaPropiedadIntelectual> obtenerTiposPersonaPropiedadIntelectual(
            TipoPropiedadIntelectual tipo) {
        Session session = null;
        Query q = null;
        List<TipoPersonaPropiedadIntelectual> resultado = null;

        try {
            session = getSession();
            q = session.createQuery("select tp from TipoPersonaPropiedadIntelectual tp where tp.tipoPropiedad.id = '"
                    + tipo.getId() + "' order by tp.nombre");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        return resultado;
    }

    public TipoPersonaPropiedadIntelectual obtenerTipoPersonaPropiedadIntelectual(String id) {
        Session session = null;
        Query q = null;
        List<TipoPersonaPropiedadIntelectual> resultado = null;

        try {
            session = getSession();
            q = session.createQuery("select tp from TipoPersonaPropiedadIntelectual tp where tp.id = '" + id + "'");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        return (TipoPersonaPropiedadIntelectual) resultado.get(0);
    }

    public List<PropiedadIntelectual> obtenerPropiedadesPendientesNacional() {
        List<?> propiedades;
        List<PropiedadIntelectual> result = new ArrayList<PropiedadIntelectual>();

        Session session = getSession();

        String query = "select PI.PRI_ID AS id, PST.PIS_NOMBRE as tipo, SUBE.PSE_NOMBRE AS estado, pi.PRI_FECHA_REGISTRO as fecha, pi.PRI_TITULO as titulo, d.dpn_nombre as dependencia, "
                + "p.per_nombre1 as nombre1, p.per_nombre2 as nombre2, p.per_apellido1 as apellido1, p.per_apellido2 as apellido2 "
                + "from HER_PROPIEDAD_INTELECTUAL PI "
                + "left join HER_PROPIEDAD_INT_SUBTIPO PST ON PST.PIS_ID = PI.PIS_ID "
                + "left join HER_PROPIEDAD_INT_SUBESTADO SUBE ON SUBE.PSE_ID = PI.PSE_ID "
                + "left join HER_DEPENDENCIA d on d.dpn_id = pi.DPN_ID "
                + "left join HER_PERSONA p on p.per_id = pi.inv_id and p.tdo_id = pi.tdo_id "
                + "where (pi.dpn_id like '1%' or (pi.DPN_ID_APOYO is not null and pi.DPN_ID_APOYO = '1')) and sube.PSE_TRAMITE in ('"
                + SubEstadoPropiedadIntelectual.PROCESO_TRAMITE + "') order by pi.PRI_FECHA_REGISTRO ASC";

        SQLQuery sqlQuery1 = session.createSQLQuery(query);

        propiedades = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("tipo", Hibernate.STRING)
                .addScalar("estado", Hibernate.STRING).addScalar("fecha", Hibernate.DATE)
                .addScalar("titulo", Hibernate.STRING).addScalar("dependencia", Hibernate.STRING)
                .addScalar("nombre1", Hibernate.STRING).addScalar("nombre2", Hibernate.STRING)
                .addScalar("apellido1", Hibernate.STRING).addScalar("apellido2", Hibernate.STRING).list();

        session.close();

        Iterator<?> it = propiedades.iterator();

        while (it.hasNext()) {
            Object[] name = (Object[]) it.next();
            PropiedadIntelectual propiedad = new PropiedadIntelectual();

            propiedad.setId((Long) name[0]);
            SubTipoPropiedadIntelectual subtipo = new SubTipoPropiedadIntelectual();
            subtipo.setNombre((String) name[1]);
            propiedad.setSubTipo(subtipo);
            SubEstadoPropiedadIntelectual subestado = new SubEstadoPropiedadIntelectual();
            subestado.setNombre((String) name[2]);
            propiedad.setSubEstado(subestado);
            propiedad.setFechaRegistro((Date) name[3]);
            propiedad.setTitulo((String) name[4]);
            Dependencia dep = new Dependencia();
            dep.setNombre((String) name[5]);
            propiedad.setDependenciaSolicitante(dep);
            InvestigadorInterno inv = new InvestigadorInterno();
            inv.setNombre1((String) name[6]);
            inv.setNombre2((String) name[7]);
            inv.setApellido1((String) name[8]);
            inv.setApellido2((String) name[9]);
            propiedad.setResponsableRegistro(inv);
            result.add(propiedad);
        }
        return result;
    }

    public List<PropiedadIntelectual> obtenerPropiedadesPendientesSede(Long id) {
        List<?> propiedades;
        List<PropiedadIntelectual> result = new ArrayList<PropiedadIntelectual>();

        Session session = getSession();

        String query = "select PI.PRI_ID AS id, PST.PIS_NOMBRE as tipo, SUBE.PSE_NOMBRE AS estado, pi.PRI_FECHA_REGISTRO as fecha, pi.PRI_TITULO as titulo, d.dpn_nombre as dependencia, "
                + "p.per_nombre1 as nombre1, p.per_nombre2 as nombre2, p.per_apellido1 as apellido1, p.per_apellido2 as apellido2 "
                + "from HER_PROPIEDAD_INTELECTUAL PI "
                + "left join HER_PROPIEDAD_INT_SUBTIPO PST ON PST.PIS_ID = PI.PIS_ID "
                + "left join HER_PROPIEDAD_INT_SUBESTADO SUBE ON SUBE.PSE_ID = PI.PSE_ID "
                + "left join HER_DEPENDENCIA d on d.dpn_id = pi.DPN_ID "
                + "left join HER_PERSONA p on p.per_id = pi.inv_id and p.tdo_id = pi.tdo_id "
                + "where (pi.dpn_id like '" + id + "%' or (pi.DPN_ID_APOYO is not null and pi.DPN_ID_APOYO = '" + id
                + "')) and sube.PSE_TRAMITE in ('" + SubEstadoPropiedadIntelectual.PROCESO_TRAMITE
                + "') order by pi.PRI_FECHA_REGISTRO ASC";

        SQLQuery sqlQuery1 = session.createSQLQuery(query);

        propiedades = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("tipo", Hibernate.STRING)
                .addScalar("estado", Hibernate.STRING).addScalar("fecha", Hibernate.DATE)
                .addScalar("titulo", Hibernate.STRING).addScalar("dependencia", Hibernate.STRING)
                .addScalar("nombre1", Hibernate.STRING).addScalar("nombre2", Hibernate.STRING)
                .addScalar("apellido1", Hibernate.STRING).addScalar("apellido2", Hibernate.STRING).list();

        session.close();

        Iterator<?> it = propiedades.iterator();

        while (it.hasNext()) {
            Object[] name = (Object[]) it.next();
            PropiedadIntelectual propiedad = new PropiedadIntelectual();

            propiedad.setId((Long) name[0]);
            SubTipoPropiedadIntelectual subtipo = new SubTipoPropiedadIntelectual();
            subtipo.setNombre((String) name[1]);
            propiedad.setSubTipo(subtipo);
            SubEstadoPropiedadIntelectual subestado = new SubEstadoPropiedadIntelectual();
            subestado.setNombre((String) name[2]);
            propiedad.setSubEstado(subestado);
            propiedad.setFechaRegistro((Date) name[3]);
            propiedad.setTitulo((String) name[4]);
            Dependencia dep = new Dependencia();
            dep.setNombre((String) name[5]);
            propiedad.setDependenciaSolicitante(dep);
            InvestigadorInterno inv = new InvestigadorInterno();
            inv.setNombre1((String) name[6]);
            inv.setNombre2((String) name[7]);
            inv.setApellido1((String) name[8]);
            inv.setApellido2((String) name[9]);
            propiedad.setResponsableRegistro(inv);
            result.add(propiedad);
        }
        return result;
    }

    public List<ClasificacionEstadoPropiedadIntelectual> obtenerClasificacionEstadosPropiedadIntelectual(Long numeroOrden) {
        Session session = null;
        Query q = null;
        List<ClasificacionEstadoPropiedadIntelectual> resultado = null;

        try {
            session = getSession();
            q = session.createQuery(
                    "select cp from ClasificacionEstadoPropiedadIntelectual cp where cp.id not in ('1','4') and cp.ordenLogico >= "+numeroOrden+" order by cp.ordenLogico");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        return resultado;
    }

    public List<EstadoPropiedadIntelectual> obtenerEstadosPropiedadIntelectual(String clasficacion,
            Long tipoPropiedad) {
        Session session = null;
        Query q = null;
        List<EstadoPropiedadIntelectual> resultado = null;

        try {
            session = getSession();
            q = session.createQuery("select ep from EstadoPropiedadIntelectual ep where ep.clasificacion.id = '"
                    + clasficacion + "' and ep.tipoPropiedad.id = '" + tipoPropiedad + "' order by ep.orden");
            resultado = q.list();
            
//            for (EstadoPropiedadIntelectual estadoPropiedadIntelectual : resultado) {
//            	if(estadoPropiedadIntelectual.getId().equals(EstadoPropiedadIntelectual.MANTENIMIENTO_PROPIEDAD_INDUSTRIAL) && (tipoPropiedad.equals(SubTipoPropiedadIntelectual.PATENTE_INVENCION_PROPIEDAD_INDUSTRIAL) || tipoPropiedad.equals(SubTipoPropiedadIntelectual.PATENTE_MODELO_UTILIDAD_PROPIEDAD_INDUSTRIAL)))
//            		resultado.remove(estadoPropiedadIntelectual);
//			}
            
//            for (int i = 0; i < resultado.size(); i++) {
//            	if(resultado.get(i).getId().equals(EstadoPropiedadIntelectual.MANTENIMIENTO_PROPIEDAD_INDUSTRIAL) && (tipoPropiedad.equals(SubTipoPropiedadIntelectual.PATENTE_INVENCION_PROPIEDAD_INDUSTRIAL) || tipoPropiedad.equals(SubTipoPropiedadIntelectual.PATENTE_MODELO_UTILIDAD_PROPIEDAD_INDUSTRIAL)))
//            		resultado.remove(resultado.get(i));
//			}
//            
//            EstadoPropiedadIntelectual epi = obtenerEstadoPropiedadIntelectual(EstadoPropiedadIntelectual.MANTENIMIENTO_PROPIEDAD_INDUSTRIAL);
//            resultado.remove(epi);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        return resultado;
    }
    
    public EstadoPropiedadIntelectual obtenerEstadoPropiedadIntelectual(Long id) throws DataAccessException {
		String query = "FROM EstadoPropiedadIntelectual EPI where EPI.id = " + id;
		List<EstadoPropiedadIntelectual> lista = getHibernateTemplate().find(query);
		if (lista.size() != 0)
			return lista.get(0);
		else
			return null;
	}

    public List<SubEstadoPropiedadIntelectual> obtenerSubEstadosPropiedadIntelectual(String estado) {
        Session session = null;
        Query q = null;
        List<SubEstadoPropiedadIntelectual> resultado = null;

        try {
            session = getSession();
            q = session.createQuery("select se from SubEstadoPropiedadIntelectual se where se.estado.id = '" + estado
                    + "' order by se.orden");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        return resultado;
    }

    public List<HistoricoPropiedadIntelectual> obtenerHistoricoPropiedadIntelectual(Long id) {
        Session session = null;
        Query q = null;
        List<HistoricoPropiedadIntelectual> resultado = null;

        try {
            session = getSession();
            q = session.createQuery("select h from HistoricoPropiedadIntelectual h where h.propiedad.id = '" + id
                    + "' order by h.fecha");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        return resultado;
    }

    public ObraFonogramaPropiedadIntelectual obtenerObraFijadaFonograma(Long id) {
        Session session = null;
        Query q = null;
        List<ObraFonogramaPropiedadIntelectual> resultado = null;

        try {
            session = getSession();
            q = session.createQuery("select o from ObraFonogramaPropiedadIntelectual o where o.id = '" + id + "'");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        return (ObraFonogramaPropiedadIntelectual) resultado.get(0);
    }

    public List<SubTipoPropiedadIntelectual> obtenerSubTiposPropiedadIntelectual() {
        Session session = null;
        Query q = null;
        List<SubTipoPropiedadIntelectual> resultado = null;

        try {
            session = getSession();
            q = session.createQuery("select stpi from SubTipoPropiedadIntelectual stpi order by stpi.nombre");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        return resultado;
    }

    public SubEstadoPropiedadIntelectual obtenerSubEstadoPropiedadIntelectual(Long id) {
        Session session = null;
        Query q = null;
        List<SubEstadoPropiedadIntelectual> resultado = null;

        try {
            session = getSession();
            q = session.createQuery("select sub from SubEstadoPropiedadIntelectual sub where sub.id = '" + id + "'");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        return (SubEstadoPropiedadIntelectual) resultado.get(0);
    }

    public List<Object[]> obtenerIndicadoresRapidos() {
        List<?> datosClasificacionEstado;
        List<?> datosSubEstados;
        List<Object[]> listaIndicadores = new ArrayList<Object[]>();


        Session session = getSession();
        
        String query0 = "select pise.pse_nombre as subestado, count(*) as total from her_propiedad_intelectual pi, her_propiedad_int_subestado pise "
                + "where pi.pse_id = pise.pse_id and pise.pse_id in ("+SubEstadoPropiedadIntelectual.SUB_ESTADO_CONCESION+","+SubEstadoPropiedadIntelectual.SUB_ESTADO_DOMINIO+") group by pise.pse_nombre";

        String query1 = "select ce.pce_nombre as estado_general, count(*) as total from her_propiedad_intelectual pi, her_propiedad_int_clas_estado ce, her_propiedad_int_estado pie, her_propiedad_int_subestado pise "
                + "where pi.pse_id = pise.pse_id and pise.pie_id = pie.pie_id and pie.pce_id = ce.pce_id and ce.pce_id in ('"+ClasificacionEstadoPropiedadIntelectual.EN_TRAMITE_INTERNO+"','"+ClasificacionEstadoPropiedadIntelectual.EN_TRAMITE_EXTERNO+"') group by ce.pce_nombre";

        SQLQuery sqlQuery0 = session.createSQLQuery(query0);
        SQLQuery sqlQuery1 = session.createSQLQuery(query1);

        datosSubEstados = sqlQuery0.addScalar("subestado", Hibernate.STRING).addScalar("total", Hibernate.LONG).list();
        datosClasificacionEstado = sqlQuery1.addScalar("estado_general", Hibernate.STRING).addScalar("total", Hibernate.LONG).list();

        session.close();
        
        Iterator<?> it = datosClasificacionEstado.iterator();

        while (it.hasNext()) {
            Object[] indicador = (Object[]) it.next();
            listaIndicadores.add(indicador);
        }
        
        Iterator<?> itSub = datosSubEstados.iterator();

        while (itSub.hasNext()) {
            Object[] indicador = (Object[]) itSub.next();
            listaIndicadores.add(indicador);
        }
        
        return listaIndicadores;
    }
    
    public List<TipoArchivo> obtenerTiposArchivosPropiedadIntelectual() {
        Session session = null;
        Query q = null;
        List<TipoArchivo> resultado = null;

        try {
            session = getSession();
            q = session.createQuery("select tia from TipoArchivo tia where tia.parametro = '"+ArchivoPropiedadIntelectual.PARAMETRO_TIPO_ARCHIVO+"' and tia.estado = 'A' order by tia.nombre");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        return resultado;
    }

}
