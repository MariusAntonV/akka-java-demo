package demo.numbers;

import org.apache.log4j.Logger;

import demo.numbers.util.ActivityPrinter;
import demo.numbers.util.PrimeNumberUtils;
import demo.numbers.util.SysoDotActivityPrinter;

/**
 * Calculate the sum of prime numbers lower then the given parameter.
 * @author manton
 *
 */
public class PrimeNumbersAdder
{
   final static Logger LOG = Logger.getLogger( PrimeNumbersAdder.class );

   private int start;

   private final int end;

   ActivityPrinter activityPrinter = new SysoDotActivityPrinter();


   public PrimeNumbersAdder( final int end )
   {
      this.end = end;
   }


   public PrimeNumbersAdder( final int start, final int end )
   {
      this.start = start;
      this.end = end;
   }


   /**
    * Calculate the sum of prime numbers lower then the given parameter.
    * @param number
    */
   public int calculate()
   {
      int primeNumbersSum = 0;

      for ( int i = this.start; i <= this.end; i++ )
      {
         this.activityPrinter.printActivity( i );

         if ( PrimeNumberUtils.isPrime( i ) )
         {
            primeNumbersSum += i;
         }
      }

      return primeNumbersSum;
   }
}
