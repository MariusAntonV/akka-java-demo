
package demo.numbers.util;

public class SysoDotActivityPrinter implements ActivityPrinter
{

   private static int MILSTONE = 1000;


   public SysoDotActivityPrinter( )
   {
   }


   public void printActivity( final int i )
   {
      if ( i % SysoDotActivityPrinter.MILSTONE == 0 )
      {
         System.out.print( "." );
      }
   }
}
