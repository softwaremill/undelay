import odelay.{Delay, Timer}
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.concurrent.duration.FiniteDuration
import java.util.concurrent.TimeoutException

package object undelay {
  implicit class Complete[+T](val future: Future[T]) extends AnyVal {

    /** @return a future that will be guaranteed to be satisfied within the given deadline */
    def within(deadline: FiniteDuration,
               orElse: FiniteDuration => Throwable = timeout)(
        implicit ec: ExecutionContext,
        timer: Timer) = {
      val bail = Promise[T]()
      val delay = Delay(deadline) {
        bail.failure(orElse(deadline))
      }
      future.onComplete {
        case _ => delay.cancel()
      }
      Future.firstCompletedOf(future :: bail.future :: Nil)
    }
  }

  private[this] def timeout(deadline: FiniteDuration) =
    new TimeoutException(s"timed out after $deadline")
}
