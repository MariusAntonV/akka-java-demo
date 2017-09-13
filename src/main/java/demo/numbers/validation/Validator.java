package demo.numbers.validation;

/**
 * Validates a number.
 * @author manton
 *
 */
public interface Validator
{
   /**
    * 
    * @param number
    * @return true if given number is validated.
    */
   public boolean validate( int number );
}
