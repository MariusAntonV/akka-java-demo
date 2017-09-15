package akka.messages;

/**
 * Holds the initial number and the limits of an interval.
 * 
 * @author manton
 *
 */
public class NumberSeqMessage
{
   private final int initialNumber;

   private final int start;

   private final int end;


   public NumberSeqMessage( final int start, final int end, final int initialNumber )
   {
      super();
      this.initialNumber = initialNumber;
      this.start = start;
      this.end = end;
   }


   public int getStart()
   {
      return this.start;
   }


   public int getEnd()
   {
      return this.end;
   }


   public int getInitialNumber()
   {
      return this.initialNumber;
   }
}
