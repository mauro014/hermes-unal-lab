/**
 * Modificado por Mauricio
 */

package co.edu.unal.hermes.vista;


/**
 * The Class ManejadorRedesSociales.
 */
public class ManejadorRedesSociales extends ManejadorBase {

    private static final long serialVersionUID = 1511420085848262039L;

    /** The twitter. */
	private boolean twitter = false;
	
	/** The facebook. */
	private boolean facebook = false;
	
	/** The google plus. */
	private boolean googlePlus = false;
	
	/** The youtube. */
	private boolean youtube = false;

	/**
	 * Activar twitter.
	 */
	public void activarTwitter(){
		limpiarCampos();
		twitter = true;
	}
	
	/**
	 * Activar facebook.
	 */
	public void activarFacebook(){
		limpiarCampos();
		facebook = true;
	}
	
	/**
	 * Activar youtube.
	 */
	public void activarYoutube(){
		limpiarCampos();
		youtube = true;
	}
	
	/**
	 * Activar google plus.
	 */
	public void activarGooglePlus(){
		limpiarCampos();
		googlePlus = true;
	}
	
	/**
	 * Limpiar campos.
	 */
	private void limpiarCampos(){
		twitter = false;
		facebook = false;
		googlePlus = false;
		youtube = false;
	}

	/**
	 * Sets the twitter.
	 *
	 * @param twitter the new twitter
	 */
	public void setTwitter(boolean twitter) {
		this.twitter = twitter;
	}

	/**
	 * Checks if is twitter.
	 *
	 * @return true, if is twitter
	 */
	public boolean isTwitter() {
		return twitter;
	}

	/**
	 * Sets the facebook.
	 *
	 * @param facebook the new facebook
	 */
	public void setFacebook(boolean facebook) {
		this.facebook = facebook;
	}

	/**
	 * Checks if is facebook.
	 *
	 * @return true, if is facebook
	 */
	public boolean isFacebook() {
		return facebook;
	}

	/**
	 * Sets the google plus.
	 *
	 * @param googlePlus the new google plus
	 */
	public void setGooglePlus(boolean googlePlus) {
		this.googlePlus = googlePlus;
	}

	/**
	 * Checks if is google plus.
	 *
	 * @return true, if is google plus
	 */
	public boolean isGooglePlus() {
		return googlePlus;
	}

	/**
	 * Sets the youtube.
	 *
	 * @param youtube the new youtube
	 */
	public void setYoutube(boolean youtube) {
		this.youtube = youtube;
	}

	/**
	 * Checks if is youtube.
	 *
	 * @return true, if is youtube
	 */
	public boolean isYoutube() {
		return youtube;
	}
	
	
	
}
