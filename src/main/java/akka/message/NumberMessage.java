package akka.message;

import java.io.Serializable;

public class NumberMessage implements Serializable
{
   private final int number;


   public NumberMessage( final int number )
   {
      super();
      this.number = number;
   }


   public int getNumber()
   {
      return this.number;
   }
}
