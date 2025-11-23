package co.edu.unal.hermes.modelo;

import java.util.Date;

public class Gasto implements Cloneable {

    private Long id;
    private TipoRubro tipoRubro;
    private int vigencia;
    private int cantidad;
    private Long valor;
    private Long valor2;
    private Long valor3;
    private Long valor4;
    private Long valor5;
    private Long valor6;
    private String descripcion = "";
    private Date fechaDesembolso;
    private Date fechaRealDesembolso;
    private Long anioVigencia;
    private Dependencia dependenciaIngreso;

    private Financiacion financiacion;

    // Hernan: un arreglo para la vista por los componentes
    private boolean borrable;
    
    private Float valorEjecutado;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public TipoRubro getTipoRubro() {
        return tipoRubro;
    }

    public void setTipoRubro(TipoRubro tipoRubro) {
        this.tipoRubro = tipoRubro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public int getVigencia() {
        return vigencia;
    }

    public void setVigencia(int vigencia) {
        this.vigencia = vigencia;
    }

    public boolean isBorrable() {
        return borrable;
    }

    public void setBorrable(boolean borrable) {
        this.borrable = borrable;
    }

    public Financiacion getFinanciacion() {
        return financiacion;
    }

    public void setFinanciacion(Financiacion financiacion) {
        this.financiacion = financiacion;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Gasto)) {
            return false;
        }
        Gasto gasto = (Gasto) o;
        if (gasto == null || gasto.getId() == null || this.getId() == null) {
            return false;
        }
        return gasto.getId().equals(this.getId());
    }

    public Long getValor2() {
    	if(valor2 == null) {
    		return 0L;
    	}
        return valor2;
    }

    public void setValor2(Long valor2) {
        this.valor2 = valor2;
    }

    public Long getValor3() {
        return valor3;
    }

    public void setValor3(Long valor3) {
        this.valor3 = valor3;
    }

    public Date getFechaDesembolso() {
        return fechaDesembolso;
    }

    public void setFechaDesembolso(Date fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso;
    }

    public Long getValor4() {
        return valor4;
    }

    public void setValor4(Long valor4) {
        this.valor4 = valor4;
    }

    public Long getValor5() {
        return valor5;
    }

    public void setValor5(Long valor5) {
        this.valor5 = valor5;
    }

    public Long getValor6() {
        return valor6;
    }

    public void setValor6(Long valor6) {
        this.valor6 = valor6;
    }
    
	public Date getFechaRealDesembolso() {
		return fechaRealDesembolso;
	}

	public void setFechaRealDesembolso(Date fechaRealDesembolso) {
		this.fechaRealDesembolso = fechaRealDesembolso;
	}
	
	public Long getAnioVigencia() {
		return anioVigencia;
	}

	public void setAnioVigencia(Long anioVigencia) {
		this.anioVigencia = anioVigencia;
	}
	
	public Dependencia getDependenciaIngreso() {
		return dependenciaIngreso;
	}

	public void setDependenciaIngreso(Dependencia dependenciaIngreso) {
		this.dependenciaIngreso = dependenciaIngreso;
	}

    public Long getSumaCampos() {
        long valor = this.valor == null ? 0L : this.valor;
        long valor2 = this.valor2 == null ? 0L : this.valor2;
        long valor3 = this.valor3 == null ? 0L : this.valor3;
        long valor4 = this.valor4 == null ? 0L : this.valor4;
        long valor5 = this.valor5 == null ? 0L : this.valor5;
        long valor6 = this.valor6 == null ? 0L : this.valor6;
        return valor + valor2 + valor3 + valor4 + valor5 + valor6;
    }

    public Gasto crearCopia() {
        Gasto gas = new Gasto();
        gas.setValor(this.getValor());
        gas.setDescripcion(this.getDescripcion());
        gas.setTipoRubro(this.getTipoRubro());
        gas.setCantidad(this.getCantidad());
        gas.setFechaDesembolso(this.getFechaDesembolso());
        gas.setValor2(this.getValor2());
        gas.setValor3(this.getValor3());
        gas.setVigencia(this.getVigencia());
        return gas;
    }

	public Float getValorEjecutado() {
		return valorEjecutado;
	}

	public void setValorEjecutado(Float valorEjecutado) {
		this.valorEjecutado = valorEjecutado;
	}
}
