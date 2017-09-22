# akka-java-demo
Playing around with Akka's Actor Model

The scope of this small project is to test the performance of executing a time consuming task, with and without using Akka Actors.

The task is to calculate and log the sum of prime numbers smaller then a given random number. This task will be executed multiple times, for a sequence of generated numbers.

The following 3 test cases were created (can be found in PerformanceTest.java):

```testWithoutActors```: runs without using actors

```testWithActors```: the task of calculating the sum of prime numbers is performed by actors

```testWithActorsOptimised```: the task of calculating the sum is broken in smaller subtasks and distributed to multiple actors
