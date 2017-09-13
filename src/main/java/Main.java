import java.util.Random;

import org.apache.log4j.Logger;

public class Main
{
   final static Logger LOG = Logger.getLogger( Main.class );


   public static void main( final String... args )
   {
      System.out.println( "Hello world!!!" );

      final Random rand = new Random( 324 );
      for ( int i = 0; i < 5; i++ )
      {
         Main.heavyNumberComputation( rand.nextInt( 100000 ) );
      }
   }


   /**
    * CAlculate the sum of prime numbers lower then the given parameter.
    * @param number
    */
   private static void heavyNumberComputation( final int number )
   {
      Main.LOG.debug( "START process number " + number );
      System.out.println( "" );
      int primeNumbersSum = 0;
      for ( int i = 0; i <= number; i++ )
      {
         if ( i % 1000 == 0 )
         {
            System.out.print( "." );
         }
         if ( Main.isPrime( i ) )
         {
            primeNumbersSum += i;
         }
      }
      System.out.println( "" );

      Main.LOG.info( "prime numbers sum is " + primeNumbersSum );

      Main.LOG.debug( "END process number " + number );
   }


   static boolean isPrime( final int n )
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

   //   private static void heavyNumberComputation( final int number )
   //   {
   //      Main.LOG.debug( "START process number " + number );
   //      int result = number * number;
   //      for ( int i = 0; i <= 100; i++ )
   //      {
   //         result = result + result / 234;
   //      }
   //
   //      result = result * result;
   //
   //      for ( int i = 0; i < result; i++ )
   //      {
   //         if ( result % 2 == 0 )
   //         {
   //            result += 1;
   //         }
   //         else
   //         {
   //            result -= 1;
   //         }
   //      }
   //
   //      Main.LOG.info( "result is " + result );
   //
   //      Main.LOG.debug( "END process number " + number );
   //   }
}
