package akka.messages;

import java.io.Serializable;

/**
 * Holds a number.
 * 
 * @author manton
 */
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
