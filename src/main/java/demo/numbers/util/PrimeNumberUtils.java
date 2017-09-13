package demo.numbers.util;

public class PrimeNumberUtils
{
   public static boolean isPrime( final int n )
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
