
package demo.numbers.util;

public class SysoDotActivityPrinter implements ActivityPrinter
{

   private int milstone = 1000;


   public SysoDotActivityPrinter( final int milstone )
   {
      this.milstone = milstone;
   }


   public void printActivity( final int i )
   {
      if ( i % this.milstone == 0 )
      {
         System.out.print( "." );
      }
   }
}
