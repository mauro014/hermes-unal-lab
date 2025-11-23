package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import co.edu.unal.hermes.modelo.InvestigadorExterno;

public class InvestigadorExternoVista {
	InvestigadorExterno investigadorExterno;
	boolean elegible=false;
	
	public InvestigadorExternoVista(InvestigadorExterno invE){
		investigadorExterno=invE;
	}
	
	public boolean isElegible() {
		return elegible;
	}
	public void setElegible(boolean elegible) {
		this.elegible = elegible;
	}
	
	public boolean equals(Object o)
	{
	    if(o instanceof InvestigadorExternoVista )
	    {
	        InvestigadorExternoVista iiv=(InvestigadorExternoVista) o;
	        if(iiv.getInvestigadorExterno()!=null)
	        {
	            return iiv.getInvestigadorExterno().equals(this.getInvestigadorExterno());
	        }
	    }
	    return false;
	}

	public InvestigadorExterno getInvestigadorExterno() {
		return investigadorExterno;
	}

	public void setInvestigadorExterno(InvestigadorExterno investigadorExterno) {
		this.investigadorExterno = investigadorExterno;
	}
}
