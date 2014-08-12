# undelay

> You can't stop the future but you _can_ ask it to move a bit more quickly

# usage

### long division

Scala [Futures](http://www.scala-lang.org/api/current/index.html#scala.concurrent.Future) provide a nice interface referencing
a deferred value. This deferral will _not_ make your slow operation run any faster. A slow operation scheduled in a Future will result in a future
that will be slow to satisfy.

```scala
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

val longDivision = Future {
  Thread.sleep(10.seconds.toMillis)
  1 / 1
}

longDivision.onComplete {
  case res => println(s"long div completed with $res")
}
```

### Awaiting our hero

An application that needs a response quickly can demand the future to respond quickly using [Await](http://www.scala-lang.org/api/current/index.html#scala.concurrent.Await$) with a timeout duration.

```scala
import scala.concurrent.Await
val result = Await.result(longDivision, 1.second)
```

There are a few drawbacks to this approach.

- you are giving up any result composability you get with Futures
- you are blocking the current thread until the future is satisfied
- you are now back to a world where throwing exceptions is the norm

### a Complete solution

Undelay, provides combinator which allows you do specify a maximum duration of time a future has to complete is operation.

```scala
import undelay._
val shortDivision = longDivision.within(1.second)
shortDivision.onComplete {
  case res => println(s"should division completed with result of $res")
}
```

The `undelay` package defines an implicit value class called `Complete` which takes a single future argument. Complete instances may declare
a finite duration suitable to complete your task with the `within(deadline)` method. By default, the future will be failed with a [TimeoutException](http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/TimeoutException.html). You may optional provide your own exception defining function which takes the provided duration and returns a suitable Throwable. Calling `within` will not block your current thread, nor cost you future compatibility, nor throw an exception in your current thread.

```scala
val recovery = shortDivsion.recover {
  case e: TimeoutException => checkNeighborsAnswer()
}
recovery.onComplete {
  case res => println(s"we got $res quickly without blocking and without interupting the current thread")
}
```

The wildcard import above is there only for the aesthetic of making it look like you can call `within` on a Scala future. If this is not your thing, you may
be more explicit in your travels.

```scala
val shortDivision = undelay.Complete(longDivision).within(1.second)
shortDivision.onComplete {
  case res => println(s"should division completed with result of $res")
}
```


Doug Tangren (softprops) 2014
