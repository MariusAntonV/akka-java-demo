package demo.numbers;

import org.apache.log4j.Logger;

import demo.numbers.util.ActivityPrinter;
import demo.numbers.util.SysoDotActivityPrinter;

/**
 * Calculate the sum of prime numbers between two limits.
 * 
 * @author manton
 *
 */
public class PrimeNumbersAdder
{
   /** The logger */
   final static Logger LOG = Logger.getLogger( PrimeNumbersAdder.class );

   /** First number in the sequence*/
   private final int first;

   /** Last number in the sequence*/
   private final int last;

   /** Print activity  */
   ActivityPrinter activityPrinter = new SysoDotActivityPrinter();


   /**
    * Instantiate PrimeNumbersAdder using only the end number in the sequence
    * @param last last number in the sequence
    */
   public PrimeNumbersAdder( final int last )
   {
      this.first = 0; //just for visibility
      this.last = last;
   }


   /**
    * Instantiate PrimeNumbersAdder using only the end number in the sequence
    * @param first first number in the sequence
    * @param last last number in the sequence
    */
   public PrimeNumbersAdder( final int first, final int last )
   {
      this.first = first;
      this.last = last;
   }


   /**
    * Calculate the sum of prime numbers between first and last numbers in the sequence.
    */
   public int calculate()
   {
      int primeNumbersSum = 0;

      for ( int i = this.first; i <= this.last; i++ )
      {
         this.activityPrinter.printActivity( i );

         if ( isPrime( i ) )
         {
            primeNumbersSum += i;
         }
      }

      return primeNumbersSum;
   }


   /**
    * Checks if given number is prime.
    * @param n number to check if it is prime 
    * @return true if given parameter is a prime number
    */
   private boolean isPrime( final int n )
   {
      for ( int i = 2; i < n; i++ )
      {
         if ( n % i == 0 )
         {
            return false;
         }
      }
      return true;
   }
}
