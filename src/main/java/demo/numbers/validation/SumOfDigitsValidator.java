package demo.numbers.validation;

/**
 * Checks if the sum of given number digits is bigger then a limit.
 * 
 * @author manton
 *
 */
public class SumOfDigitsValidator implements Validator
{

   private final int limit;


   public SumOfDigitsValidator( final int limit )
   {
      this.limit = limit;
   }


   /**
    * {@inheritDoc}
    */
   public boolean validate( final int number )
   {
      return sumOfDigits( number ) > this.limit;
   }


   private int sumOfDigits( final int number )
   {
      int num = number;
      int sum = 0;
      while ( num > 0 )
      {
         sum = sum + num % 10;
         num = num / 10;
      }
      return sum;
   }
}
