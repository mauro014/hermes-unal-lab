package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import co.edu.unal.hermes.modelo.InvestigadorInterno;

public class InvestigadorInternoVista {
	InvestigadorInterno investigadorInterno;
	boolean elegible=false;
	
	public InvestigadorInternoVista(InvestigadorInterno invI){
		investigadorInterno=invI;
	}
	
	public boolean isElegible() {
		return elegible;
	}
	public void setElegible(boolean elegible) {
		this.elegible = elegible;
	}
	public InvestigadorInterno getInvestigadorInterno() {
		return investigadorInterno;
	}
	public void setInvestigadorInterno(InvestigadorInterno investigadorInterno) {
		this.investigadorInterno = investigadorInterno;
	}
	
	public boolean equals(Object o)
	{
	    if(o instanceof InvestigadorInternoVista )
	    {
	        InvestigadorInternoVista iiv=(InvestigadorInternoVista) o;
	        if(iiv.getInvestigadorInterno()!=null)
	        {
	            return iiv.getInvestigadorInterno().equals(this.getInvestigadorInterno());
	        }
	    }
	    return false;
	}
}
