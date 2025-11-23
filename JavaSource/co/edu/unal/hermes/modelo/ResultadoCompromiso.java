package co.edu.unal.hermes.modelo;


public class ResultadoCompromiso {
    
    private String objetivo;
    private String resultadoEsperado;
    private String resultadoObtenido;  
    

    public ResultadoCompromiso(){
	
    }


    public String getResultadoEsperado() {
        return resultadoEsperado;
    }


    public void setResultadoEsperado(String resultadoEsperado) {
        this.resultadoEsperado = resultadoEsperado;
    }


    public String getResultadoObtenido() {
        return resultadoObtenido;
    }


    public void setResultadoObtenido(String resultadoObtenido) {
        this.resultadoObtenido = resultadoObtenido;
    }


    public String getObjetivo() {
        return objetivo;
    }


    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }


    
    
}
