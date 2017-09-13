import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

import org.apache.log4j.Logger;

import demo.numbers.PrimeNumbersAdder;

/**
 * @author MariusAnton
 */
public class Main
{
   final static Logger LOG = Logger.getLogger( Main.class );


   public static void main( final String... args )
   {
      final LocalDateTime start = LocalDateTime.now();

      final Random rand = new Random( 324 );
      for ( int i = 0; i < 30; i++ )
      {
         final int randomNumber = rand.nextInt( 100000 );
         final PrimeNumbersAdder adder = new PrimeNumbersAdder( randomNumber );
         final int sumOfPrimeNumbers = adder.calculate();

         System.out.println( "" );
         Main.LOG.info( "Sum of prime numbers lower then " + randomNumber + " is " + sumOfPrimeNumbers );
      }

      final LocalDateTime end = LocalDateTime.now();

      final Duration dur = Duration.between( start, end );
      System.out.println( "It took " + dur.toMillis() + " ms to run this program." );
   }
}
