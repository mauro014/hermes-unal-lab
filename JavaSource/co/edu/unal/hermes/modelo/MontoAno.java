package co.edu.unal.hermes.modelo;

public class MontoAno implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1725494934110254583L;
    Long id;
    Aval aval;
    Long monto;
    String ano = "";
    String region = "";
    String subregion = "";
    String regiontxt = "";
    String subregiontxt = "";

    public Long getMonto() {
        return monto;
    }

    public void setMonto(Long monto) {
        this.monto = monto;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    @Override
    public boolean equals(Object obj) {

        if ((this.ano.equals(((MontoAno) obj).ano)) && this.monto.equals(((MontoAno) obj).monto)) {
            return true;
        } else {
            return false;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aval getAval() {
        return aval;
    }

    public void setAval(Aval aval) {
        this.aval = aval;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getRegiontxt() {
        return regiontxt;
    }

    public void setRegiontxt(String regiontxt) {
        this.regiontxt = regiontxt;
    }

    public String getSubregiontxt() {
        return subregiontxt;
    }

    public void setSubregiontxt(String subregiontxt) {
        this.subregiontxt = subregiontxt;
    }
}
