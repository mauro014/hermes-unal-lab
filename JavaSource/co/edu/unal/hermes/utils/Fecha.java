
package co.edu.unal.hermes.utils;

import java.util.Calendar;
import java.util.Date;


public class Fecha {

    public static String fechaActual()
    {
        Calendar c =Calendar.getInstance();
        c.setTime(new Date());
        return (c.get(Calendar.DAY_OF_MONTH)+"/"+
                (c.get(Calendar.MONTH)+1)
                +"/"+c.get(Calendar.YEAR));
    }
}
