package subutai.breeze.zzz

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import breeze.linalg._

class TestSpec_10_breeze_Quickstart extends AnyFlatSpec with Matchers {
// https://github.com/scalanlp/breeze/wiki/Quickstart

  "Breeze linalg Vector" should "create DenseVector and SparseVector" in {
    // Let's create a vector:
    val x = DenseVector.zeros[Double](size = 5)
    // println(x)
    x shouldBe DenseVector(0.0, 0.0, 0.0, 0.0, 0.0)

    val y = SparseVector.zeros[Double](size = 5)
    // println(y)
    y shouldBe SparseVector(length = 5)((0, 0))
    y shouldBe SparseVector[Double](length = 5)(Nil: _*)
  }

  it should "support negative indices" in {
    /* The vector object supports accessing and updating data elements by their
    index in 0 to x.length-1. Like Numpy, negative indices are supported, with
    the semantics that for an index i < 0 we operate on the i-th element from
    the end (x(i) == x(x.length + i)). */
    val x = DenseVector.zeros[Double](size = 5)
    x(4) = 2
    // println(x)
    x shouldBe DenseVector(0.0, 0.0, 0.0, 0.0, 2.0)
    x(-1) shouldBe 2.0
  }

  it should "support slicing" in {
    /* Breeze also supports slicing. Note that slices using a Range are much,
    much faster than those with an arbitrary sequence.
    The slice operator constructs a read-through and write-through view of the
    given elements in the underlying vector. You set its values using the
    vectorized-set operator :=. You could as well have set it to a compatibly
    sized Vector.*/
    val x = DenseVector.zeros[Double](size = 5)
    x(1) = 2
    x(3 to 4) := .5
    // println(x)
    x shouldBe DenseVector(0.0, 2.0, 0.0, 0.5, 0.5)

    x(0 to 1) := DenseVector(.1, .2)
    x shouldBe DenseVector(0.1, 0.2, 0.0, 0.5, 0.5)
  }

  "Breeze linalg Matrix" should "create DenseMatrix" in {
    val m = DenseMatrix.zeros[Int](rows = 5, cols = 5)
    // println(m)
    m.toString shouldBe
      s"""0  0  0  0  0  
         |0  0  0  0  0  
         |0  0  0  0  0  
         |0  0  0  0  0  
         |0  0  0  0  0  """.stripMargin
    m.rows shouldBe 5
    m.cols shouldBe 5
  }

  it should "access columns as DenseVectors, and the rows as DenseMatrices" in {
    val m = DenseMatrix.zeros[Int](rows = 5, cols = 5)
    // column
    m(::, 1) shouldBe DenseVector(0, 0, 0, 0, 0)
    // row
    // println(m(4,::))
    m(4, ::) shouldBe Transpose(DenseVector(0, 0, 0, 0, 0))

    m(4, ::) := DenseVector(1, 2, 3, 4, 5).t // transpose to match row shape
    // println(m)
    m.toString shouldBe
      s"""0  0  0  0  0  
         |0  0  0  0  0  
         |0  0  0  0  0  
         |0  0  0  0  0  
         |1  2  3  4  5  """.stripMargin
  }

  it should "throw an exception for assignments with incompatible size" in {
    val m = DenseMatrix.zeros[Int](rows = 5, cols = 5)
    assertThrows[IllegalArgumentException] {
      m := DenseMatrix.zeros[Int](rows = 3, cols = 3)
      /* java.lang.IllegalArgumentException: requirement failed:
      Matrices must have same number of row */
    }
  }

  it should "support literal matrix creation using a simple tuple-based syntax" in {
    val m = DenseMatrix((3, 1), (-1, -2))
    // println(m)
    m.toString shouldBe
      s"""3   1   
         |-1  -2  """.stripMargin
  }

  it should "support slicing and updating sub-matrices" in {
    val m = DenseMatrix.zeros[Int](rows = 5, cols = 5)

    val sub = m(0 to 1, 0 to 1)
    // println(sub)
    sub.toString shouldBe
      s"""0  0  
         |0  0  """.stripMargin

    m(0 to 1, 0 to 1) := DenseMatrix((3, 1), (-1, -2))
    // println(m)
    m.toString shouldBe
      s"""3   1   0  0  0  
         |-1  -2  0  0  0  
         |0   0   0  0  0  
         |0   0   0  0  0  
         |0   0   0  0  0  """.stripMargin
  }

  "Breeze linalg operators" should "support Vector dot product" in {
    val a = DenseVector.ones[Double](size = 5)
    val b = DenseVector.fill(size = 5)(v = 2d)
    // println(a.dot(b))
    a.dot(b) shouldBe 10d
  }

  "Breeze linalg" should "support broadcasting" in {
    /* Sometimes we want to apply an operation to every row or column of a matrix,
    as a unit. For instance, you might want to compute the mean of each row,
    or add a vector to every column. Adapting a matrix so that operations can be
    applied column-wise or row-wise is called broadcasting.
    Languages like R and numpy automatically and implicitly do broadcasting,
    meaning they won't stop you if you accidentally add a matrix and a vector.
    In Breeze, you have to signal your intent using the broadcasting operator *.
    The * is meant to evoke "foreach" visually. Here are some examples: */
    import breeze.stats.mean
    val dm = DenseMatrix((1.0, 2.0, 3.0), (4.0, 5.0, 6.0))
    // "foreach" column:
    val res = dm(::, *) + DenseVector(3.0, 4.0)
    // println(res)
    res.toString shouldBe
      s"""4.0  5.0  6.0   
         |8.0  9.0  10.0  """.stripMargin
    res(::, *) := DenseVector(13.0, 14.0)
    // println(res)
    res.toString shouldBe
      s"""13.0  13.0  13.0  
         |14.0  14.0  14.0  """.stripMargin
    // "foreach" row:
    val meanRows = mean(dm(*, ::))
    meanRows shouldBe DenseVector(2.0, 5.0)
  }

  "breeze.stats.distributions" should "provide Poisson probability distribution" in {
    import breeze.stats.distributions.{ Poisson, Rand }
    import breeze.stats.distributions.Rand.FixedSeed._
    val mean     = 3d
    val variance = mean
    val n        = 10000
    val poi      = new Poisson(mean = mean)
    (poi.mean, poi.variance) shouldBe (mean, variance)
    // Poisson samples over Ints
    val sample5: IndexedSeq[Int] = poi.sample(n = 5) // like Vector(5, 4, 5, 7, 4)
    val probs: Vector[Double]    = Vector(5, 4, 5, 7, 4).map(poi.probabilityOf)
    probs shouldBe DenseVector(
      0.10081881344492458, 0.16803135574154085, 0.10081881344492458,
      0.02160403145248382, 0.16803135574154085
    )
    // meanAndVariance requires doubles, but Poisson samples over Ints
    val doublePoi: Rand[Double] = for (x <- poi) yield x.toDouble
    import breeze.stats.meanAndVariance, meanAndVariance.MeanAndVariance
    val mav: MeanAndVariance = meanAndVariance(doublePoi.samples.take(n = n))
    // println(mav) // like MeanAndVariance(3.004400000000006,2.989879627962779,10000)
    mav.count.toInt shouldBe n
    assert(math.abs(mav.mean - mean) < 0.2)
    assert(math.abs(mav.variance - variance) < 0.2)
  }

  it should "provide Exponential probability distribution" in {
    /* NOTE: Below, there is a possibility of confusion for the term rate in the
    family of exponential distributions.
    For versions 1.0 and before, Breeze parameterizes the distribution with
    the mean, but refers to it as the rate.
    For versions 1.1 and after, Breeze parameterizes the distribution with
    the rate, while the mean equals to inverse rate (as it should be). */
    import breeze.stats.distributions.Exponential
    import breeze.stats.distributions.Rand.FixedSeed._
    val rate = 0.5
    val expo = new Exponential(rate = rate)
    expo.rate shouldBe rate
    expo.mean shouldBe 1 / rate
    expo.variance shouldBe 1 / (rate * rate)

    /* A characteristic of exponential distributions is its half-life, but we can
    compute the probability a value falls between any two numbers. */
    import breeze.numerics.log
    val halfLife = log(2) / expo.rate // probability = 0.5
    halfLife shouldBe 1.3862943611198906
    // tests below fail for breeze 1.0 (and thus for Scala 2.11),
    // but pass for breeze 1.1 and after (for Scala 2.12 and 2.13)
    // expo.probability(0.0, log(2) / expo.rate) shouldBe 0.5
    // expo.probability(0.0, 6) shouldBe 0.950212931632136
  }

  "breeze.optimize" should "provide LBFGS optimization routine" in {
    /* Breeze's optimization package includes several convex optimization routines
    and a simple linear program solver. Convex optimization routines typically take
    a DiffFunction[T], which is a Function1 extended to have a gradientAt method,
    which returns the gradient at a particular point. Most routines will require a
    breeze.linalg-enabled type: something like a Vector or a Counter. */
    import breeze.optimize._
    /* Here's a simple DiffFunction: a parabola along each vector's coordinate.
    Note that this function takes its minimum when all values are 3. */
    val f = new DiffFunction[DenseVector[Double]] {
      def calculate(x: DenseVector[Double]): (Double, DenseVector[Double]) =
        ((x - 3d).dot(x - 3d), (x * 2d) - 6d)
    }
    f.valueAt(DenseVector(3, 3, 3)) shouldBe 0d
    f.gradientAt(DenseVector(3, 0, 1)) shouldBe DenseVector(0d, -6d, -4d)
    f.calculate(DenseVector(0, 0)) shouldBe (18d, DenseVector(-6d, -6d))
    /* You can also use approximate derivatives, if your function is easy enough
    to compute: */
    def g(x: DenseVector[Double]): Double = (x - 3d).dot(x - 3d)
    g(DenseVector(0d, 0d, 0d)) shouldBe 27d
    val diffg: ApproximateGradientFunction[Int, DenseVector[Double]] =
      new ApproximateGradientFunction(g)
    diffg.gradientAt(DenseVector(3, 0, 1)) shouldBe
      DenseVector(1.000000082740371e-5, -5.999990000127297, -3.999990000025377)
    /* Ok, now let's optimize f. The easiest routine to use is just LBFGS, which is
    a quasi-Newton method that works well for most problems. */
    /* m is the memory. anywhere between 3 and 7 is fine.
    The larger m, the more memory is needed. */
    val lbfgs: LBFGS[DenseVector[Double]] =
      new LBFGS[DenseVector[Double]](maxIter = 100, m = 3)
    val optimum: DenseVector[Double] =
      lbfgs.minimize(f = f, init = DenseVector(0, 0, 0))
    f(optimum) shouldBe 0d
  }

  "breeze.optimize.linear" should "provide Simplex Solver" in {
    /* Breeze provides a DSL for solving linear programs, using Apache's
    Simplex Solver as the backend. This package isn't industrial strength
    yet by any means, but it's good for simple problems. */
    import breeze.optimize.linear._
    val lp: LinearProgram = new LinearProgram()
    import lp._
    val x0 = Real()
    val x1 = Real()
    val x2 = Real()
    val lpp: Problem = (x0 + x1 * 2 + x2 * 3)
      .subjectTo(x0 * -1 + x1 + x2 <= 20)
      .subjectTo(x0 - x1 * 3 + x2 <= 30)
      .subjectTo(x0 <= 40)
    val result: Result = maximize(lpp)
    // println(result)
    result.valueOf(x0) shouldBe 40d
    result.result shouldBe DenseVector(40d, 17.5, 42.5)
    result.value shouldBe 202.5
  }

}
