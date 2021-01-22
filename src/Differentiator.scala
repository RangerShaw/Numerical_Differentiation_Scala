import Math.{log, pow, round}

class Differentiator {
  val h0: Double = 1.0
  val eps: Double = 0.00000001

  def log2(x: Double): Double = log(x) / log(2)

  /**
   * Repeat traversing the stream
   * until previous and current elements are within Eps
   */
  def repeatTillWithinEps(ls: Stream[Double]): Double = {
    def withinEps(a: Double, b: Double): Boolean = Math.abs(a - b) < eps
    ls.reduceLeft { (prev, i) =>
      if (withinEps(prev, i)) return prev
      else i
    }
  }

  /**
   * Generate the guess stream of input function f at horizontal ordinate x
   */
  def getGuessStream(f: Double => Double, x: Double): Stream[Double] = {
    def easyDiff(h: Double): Double = (f(x + h) - f(x)) / h
    def halve(h: Double): Double = h / 2.0

    lazy val heights: Stream[Double] = easyDiff(h0) #:: (heights map halve)
    lazy val differentiate_g: Stream[Double] = heights map easyDiff

    differentiate_g
  }

  /**
   * Conventional algorithm to calculate the differentiation
   */
  def differentiate(f: Double => Double, x: Double): Double = {
    repeatTillWithinEps(getGuessStream(f, x))
  }

  /**
   * Improved algorithm to calculate the differentiation
   */
  def improvedDifferentiate(f: Double => Double, x: Double): Double = {
    def order(ls: Stream[Double]): Double = round(log2((ls(0) - ls(2)) / (ls(1) - ls(2)) - 1))

    def elimError(n: Double, ls: Stream[Double]): Stream[Double] = {
      def eliminate(a: Double, b: Double, n: Double) = (b * pow(2.0, n) - a) / (pow(2.0, n) - 1.0)

      lazy val elimerror: Stream[Double] =
        eliminate(ls(0), ls(1), n) #::
        eliminate(ls(1), ls(2), n) #::
        elimerror
          .zip(elimerror.tail)
          .map { a => eliminate(a._1, a._2, n) }

      elimerror
    }

    def improve(ls: Stream[Double]): Stream[Double] = elimError(order(ls), ls)

    repeatTillWithinEps(improve(getGuessStream(f, x)))
  }

}
