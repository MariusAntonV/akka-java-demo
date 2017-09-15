package akka.messages;

import java.io.Serializable;

/**
 * Holds an initial number and the sum of prime numbers lower then that number.
 * 
 * @author manton
 *
 */
public class ResultMessage implements Serializable
{
   private final int number;

   private final int sumOfPrimeNumbers;


   public ResultMessage( final int number, final int sumOfPrimeNumbers )
   {
      super();
      this.number = number;
      this.sumOfPrimeNumbers = sumOfPrimeNumbers;
   }


   public int getNumber()
   {
      return this.number;
   }


   public int getSumOfPrimeNumbers()
   {
      return this.sumOfPrimeNumbers;
   }
}
