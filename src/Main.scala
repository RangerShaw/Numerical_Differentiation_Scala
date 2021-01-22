import Math.pow
import System.currentTimeMillis

object Main {
  val differentiator = new Differentiator()
  val testFunc: Double => Double = (x: Double) => pow(x, 2)   // f(x)=x^2

  def main(args: Array[String]): Unit = {
    // Test the correctness of the algorithm
    resultTest()

    // Compare the running time of conventional and improved algorithms
    timeTest()
  }

  def resultTest(): Unit = {
    val res: Double = differentiator.differentiate(testFunc, 100)
    println()
    println("[Result Test]")
    println("  The differentiation of x^2 at x=100 is: %.2f".format(res))
    println()
  }

  def timeTest(): Unit = {
    val t1 = getTime(differentiator.differentiate)
    val t2 = getTime(differentiator.improvedDifferentiate)
    println("[Time Test]")
    println("  Compare the running time of conventional and improved algorithms: ")
    println("  Conventional: %dms".format(t1))
    println("  Improved:     %dms".format(t2))
    println()
  }

  def getTime(f: (Double => Double, Double) => Double): Long = {
    val ts = currentTimeMillis()
    for (i <- 1 to 100000) {
      f(testFunc, i)
    }
    val te = currentTimeMillis()
    te - ts
  }

}

