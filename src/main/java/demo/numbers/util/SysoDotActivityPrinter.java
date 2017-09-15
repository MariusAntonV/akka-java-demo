
package demo.numbers.util;

/**
 * Prints a dot in console.
 * 
 * @author manton
 *
 */
public class SysoDotActivityPrinter implements ActivityPrinter
{

   /** Determines when to print the dot. */
   private static int MILSTONE = 1000;


   /**
    * Prints a dot every MILESTONE
    * 
    * {@inheritDoc}
    */
   public void printActivity( final int i )
   {
      if ( i % SysoDotActivityPrinter.MILSTONE == 0 )
      {
         System.out.print( "." );
      }
   }
}
