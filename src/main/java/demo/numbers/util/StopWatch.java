package demo.numbers.util;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Class used to measure duration between two events.
 * 
 * @author manton
 *
 */
public class StopWatch
{
   /** Start  */
   private LocalDateTime start;

   /** End  */
   private LocalDateTime end;


   /**
    * Start timer.
    */
   public void start()
   {
      this.start = LocalDateTime.now();
   }


   /**
    * Stop timer and print result.
    */
   public void stop()
   {
      this.end = LocalDateTime.now();

      final Duration dur = Duration.between( this.start, this.end );
      System.out.println( "It took " + dur.toMillis() + " ms to run this program." );
   }

}
