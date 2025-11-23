package co.edu.unal.hermes.modelo.anotaciones;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface FieldProperties.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldProperties {

    /**
     * Min len.
     *
     * @return the int
     */
    int minLen() default 1;

    /**
     * Max len.
     *
     * @return the int
     */
    int maxLen() default Integer.MAX_VALUE;

    /**
     * Min value.
     *
     * @return the int
     */
    int minValue() default Integer.MIN_VALUE;

    /**
     * Max value.
     *
     * @return the int
     */
    int maxValue() default Integer.MAX_VALUE;

    /**
     * Amount words.
     *
     * @return the int
     */
    int amountWords() default 0;

}
