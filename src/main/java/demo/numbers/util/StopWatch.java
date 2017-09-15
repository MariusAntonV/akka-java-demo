package demo.numbers.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class StopWatch
{
   private LocalDateTime start;

   private LocalDateTime end;


   public void start()
   {
      this.start = LocalDateTime.now();
   }


   public void stop()
   {
      this.end = LocalDateTime.now();

      final Duration dur = Duration.between( this.start, this.end );
      System.out.println( "It took " + dur.toMillis() + " ms to run this program." );
   }

}
