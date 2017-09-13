package demo.numbers;

import org.apache.log4j.Logger;

import demo.numbers.util.ActivityPrinter;
import demo.numbers.util.PrimeNumberUtils;
import demo.numbers.util.SysoDotActivityPrinter;
import demo.numbers.validation.SumOfDigitsValidator;
import demo.numbers.validation.Validator;

/**
 * Calculate the sum of prime numbers lower then the given parameter.
 * @author manton
 *
 */
public class PrimeNumbersAdder
{
   final static Logger LOG = Logger.getLogger( PrimeNumbersAdder.class );

   private final int number;

   Validator validator = new SumOfDigitsValidator( 2 );

   ActivityPrinter activityPrinter = new SysoDotActivityPrinter( 1000 );


   public PrimeNumbersAdder( final int number )
   {
      this.number = number;
   }


   /**
    * Calculate the sum of prime numbers lower then the given parameter.
    * @param number
    */
   public int calculate()
   {
      int primeNumbersSum = 0;

      if ( this.validator.validate( this.number ) )
      {
         for ( int i = 0; i <= this.number; i++ )
         {
            this.activityPrinter.printActivity( i );

            if ( PrimeNumberUtils.isPrime( i ) )
            {
               primeNumbersSum += i;
            }
         }
      }
      else
      {
         PrimeNumbersAdder.LOG.error( "Number " + this.number + " did not pass validation !" );
      }

      return primeNumbersSum;
   }
}
