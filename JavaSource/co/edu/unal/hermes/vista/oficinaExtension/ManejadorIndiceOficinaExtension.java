package co.edu.unal.hermes.vista.oficinaExtension;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorIndiceOficinaExtension extends ManejadorBase implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4700956024950465436L;
    private final static Logger logger = Logger.getLogger(ManejadorIndiceOficinaExtension.class.getName());

    private final static String[] colors;

    private final static String[] manufacturers;

    static {
	colors = new String[10];
	colors[0] = "Black";
	colors[1] = "White";
	colors[2] = "Green";
	colors[3] = "Red";
	colors[4] = "Blue";
	colors[5] = "Orange";
	colors[6] = "Silver";
	colors[7] = "Yellow";
	colors[8] = "Brown";
	colors[9] = "Maroon";

	manufacturers = new String[10];
	manufacturers[0] = "Mercedes";
	manufacturers[1] = "BMW";
	manufacturers[2] = "Volvo";
	manufacturers[3] = "Audi";
	manufacturers[4] = "Renault";
	manufacturers[5] = "Opel";
	manufacturers[6] = "Volkswagen";
	manufacturers[7] = "Chrysler";
	manufacturers[8] = "Ferrari";
	manufacturers[9] = "Ford";
    }

    private List<Test> carsSmall;

    public class Test {
	private String test;

	public String getTest() {
	    return test;
	}

	public void setTest(String test) {
	    this.test = test;
	}
    }

    public ManejadorIndiceOficinaExtension() {
	carsSmall = new ArrayList<Test>();

	populateRandomCars(carsSmall, 5);
    }

    private void populateRandomCars(List<Test> list, int size) {
	for (int i = 0; i < size; i++){
	    Test t = new Test();
	    t.setTest(getRandomColor());
	    list.add(t);
	}
	    
	    
    }

    public List<Test> getCarsSmall() {
	return carsSmall;
    }

    private int getRandomYear() {
	return (int) (Math.random() * 50 + 1960);
    }

    private String getRandomColor() {
	return colors[(int) (Math.random() * 10)];
    }

    private String getRandomManufacturer() {
	return manufacturers[(int) (Math.random() * 10)];
    }

    private String getRandomModel() {
	return UUID.randomUUID().toString().substring(0, 8);
    }

}
