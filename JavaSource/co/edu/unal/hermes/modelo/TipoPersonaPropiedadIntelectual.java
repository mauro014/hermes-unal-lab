/**
 * @author Martha Liliana Correa O.
 * @date 21/07/2016
 */

package co.edu.unal.hermes.modelo;

public class TipoPersonaPropiedadIntelectual implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Long AUTOR_OBRA_LITERARIA = 1L;
	public static final Long COAUTOR_OBRA_LITERARIA = 2L;
	public static final Long AUTOR_SOFTWARE = 3L;
	public static final Long AUTOR_OBRA_ARTISTICA = 5L;
	public static final Long AUTOR_FONOGRAMA = 7L;
	public static final Long INTERPRETE_OBRA_FIJADA_FONOGRAMA = 9L;
	public static final Long AUTOR_OBRA_FIJADA_FONOGRAMA = 10L;
	public static final Long AUTOR_OBRA_MUSICAL = 11L;
	public static final Long PRODUCTOR_OBRA_AUDIOVISUAL = 13L;
	public static final Long PRINCIPAL_PROPIEDAD_INDUSTRIAL = 19L;

	private Long id;
	private String nombre;
	private SubTipoPropiedadIntelectual subTipoPropiedad;
	private TipoPropiedadIntelectual tipoPropiedad;

	/** default constructor */
	public TipoPersonaPropiedadIntelectual() {

	}

	public TipoPersonaPropiedadIntelectual(Long id) {
		this.setId(id);
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

	public SubTipoPropiedadIntelectual getSubTipoPropiedad() {
		return subTipoPropiedad;
	}

	public void setSubTipoPropiedad(SubTipoPropiedadIntelectual subTipoPropiedad) {
		this.subTipoPropiedad = subTipoPropiedad;
	}

	public boolean isEsPersonaPrincipal() {
		if (this.id != null) {
			if (this.id.equals(AUTOR_OBRA_LITERARIA)) {
				return true;
			} else if (this.id.equals(AUTOR_SOFTWARE)) {
				return true;
			} else if (this.id.equals(AUTOR_OBRA_ARTISTICA)) {
				return true;
			} else if (this.id.equals(AUTOR_FONOGRAMA)) {
				return true;
			} else if (this.id.equals(AUTOR_OBRA_MUSICAL)) {
				return true;
			}
		}
		return false;
	}

	public boolean isEsAutorObraFijada() {
		if (this.id != null) {
			if (this.id.equals(AUTOR_OBRA_FIJADA_FONOGRAMA)) {
				return true;
			}
		}
		return false;
	}

	public boolean isEsInterprete() {
		if (this.id != null) {
			if (this.id.equals(INTERPRETE_OBRA_FIJADA_FONOGRAMA)) {
				return true;
			}
		}
		return false;
	}

	public TipoPropiedadIntelectual getTipoPropiedad() {
		return tipoPropiedad;
	}

	public void setTipoPropiedad(TipoPropiedadIntelectual tipoPropiedad) {
		this.tipoPropiedad = tipoPropiedad;
	}

}