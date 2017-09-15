package demo.numbers;

import org.junit.Assert;
import org.junit.Test;

public class PrimeNumbersAdderTest
{

   @Test
   public void testCalculate()
   {
      Assert.assertEquals( 11, new PrimeNumbersAdder( 6 ).calculate() );
      Assert.assertEquals( 10, new PrimeNumbersAdder( 2, 6 ).calculate() );
      Assert.assertEquals( 0, new PrimeNumbersAdder( 6, 6 ).calculate() );
      Assert.assertEquals( 3, new PrimeNumbersAdder( 1, 2 ).calculate() );

   }
}
