



object ch5 {
/*<script>*/// ======== 5.1 Case Classes and Sealed Traits ========
// ----- 5.1.1 Case Classes -----
case class Employee(
    reportTo: Option[Employee],
    salary: Int,
    bonus: Int,
    contractOrNot: Boolean,
    reportFrom: Option[Employee],
    joinDate: String
) {
  def totalSalary = salary + bonus
}

val e1 = Employee(None, 25000, 0, true, None, "2021-01-03")
// println(e1.salary)
// println(e1.bonus)
// println(e1.totalSalary)

// ----- 5.1.2 Sealed Traits -----
// sealed trait Point
// case class Point2D(x: Double, y: Double) extends Point
// case class Point3D(x: Double, y: Double, z: Double) extends Point

// def hypotenuse(p: Point) = p match {
//   case Point2D(x, y)    => math.sqrt(x * x + y * y)
//   case Point3D(x, y, z) => math.sqrt(x * x + y * y + z * z)
// }

// val points: Array[Point] = Array(Point2D(3, 4), Point3D(3, 4, 12))

// for (p <- points) {
//   println(hypotenuse(p))
// }

// ======== 5.2 Pattern Matching ========
// ----- 5.2.1 Match -----

def dayOfWeek(x: Int) = x match {
  case 1 => "Mon"; case 2 => "Tue"
  case 3 => "Wed"; case 4 => "Thurs"
  case _ => "Unknown"
}

// println(dayOfWeek(3))

for (i <- Range.inclusive(1, 100)) {
  val s = (i % 3, i % 7) match {
    case (0, 0) => "BloomBang"
    case (0, _) => "Bloom"
    case (_, 0) => "Bang"
    case _      => i
  }
  // println(s)
}

// ----- 5.2.2 Nested Match -----
case class Person(name: String, title: String)

def greet(p: Person) = p match {
  case Person(s"$firstName $lastName", title) =>
    println(s"Hello $title $lastName")
  case Person(name, title) => println(s"Hello $title $name")
}

// greet(Person("Anthony Kwok", "Mr"))

// ----- 5.2.3 Loops and Vals -----
case class Point(x: Int, y: Int)

val p = Point(123, 789)

val Point(a, b) = p
// println(s"a = $a")
// println(s"b = $b")

// ----- 5.2.4 Pattern Matching on Sealed Traits and Case Classes -----
sealed trait Express
case class BinOp(left: Express, op: String, right: Express) extends Express
case class Literal(value: Int) extends Express
case class Variable(name: String) extends Express

def stringify(expr: Express): String = expr match {
  case BinOp(left, op, right) => s"${stringify(left)} ${op} ${stringify(right)}"
  case Literal(value)         => value.toString
  case Variable(name)         => name
}

val shortExpr = BinOp(Variable("num_a"), "+", Literal(9))
val longExpr = BinOp(Variable("x"), "*", Variable("y"))
val testExpr = BinOp(Variable("x"), "/", Literal(3))

// println(stringify(shortExpr))
// println(stringify(longExpr))

def evaluate(expr: Express, values: Map[String, Int]): Int = expr match {
  case BinOp(left, "+", right) =>
    evaluate(left, values) + evaluate(right, values)
  case BinOp(left, "-", right) =>
    evaluate(left, values) - evaluate(right, values)
  case BinOp(left, "*", right) =>
    evaluate(left, values) * evaluate(right, values)
  case Literal(value) => value
  case Variable(name) => values(name)
  // case BinOp(left, _, right) => 0
}

// println(evaluate(shortExpr, Map("num_a" -> 10)))
// println(evaluate(longExpr, Map("x" -> 7, "y" -> 17)))
// println(evaluate(testExpr, Map("x" -> 15)))

// ======== 5.3 By-Name Parameters ========

// ----- 5.3.1 Avoiding Evaluation -----
var logLevel = 1
def log(level: Int, msg: => String) = {
  if (level > logLevel) println(msg)
}

// log(2, "Hello " + 789 + " sman")

// ----- 5.3.2 Wrapping Evaluation -----
def measureTime(f: => Unit) = {
  val start = System.currentTimeMillis()
  f
  val end = System.currentTimeMillis()
  println("Time Elpased: " + (end - start) + " millisec")
}
// measureTime(new Array[String](100 * 100 * 100 * 100).hashCode())

// ----- 5.3.3 Repeating Evaluation -----
def retry[T](max: Int)(f: => T): T = {
  var tries = 0
  var result: Option[T] = None
  while (result == None) {
    try {
      result = Some(f)
    } catch {
      case e: Throwable =>
        tries += 1
        if (tries > max) throw e
        else println(s"failed !! retry #$tries")
    }
  }
  result.get
}

// val httpbin = "https://httpbin.org"
// retry(max = 5) {
//   requests.get(
//     s"$httpbin/status/200,400,500"
//   )
// }

// ======== 5.4 Implicit Parameters ========
class Foo(val value: Int)

def bar(implicit foo: Foo) = {
  println(foo.value + 5)
}
// Option 1
// val foo: Foo = new Foo(100)
// bar(foo)

// Option 2
// implicit val foo: Foo = new Foo(200)
// bar

// ======== 5.5 Typeclass Inference ========
// ----- 5.5.2 Separate Parser Objects -----

// trait StrParser[T] {
//   def parse(s: String): T
// }
// object ParseInt extends StrParser[Int] {
//   def parse(s: String) = s.toInt
// }
// object ParseBoolean extends StrParser[Boolean] {
//   def parse(s: String) = s.toBoolean
// }
// object ParseDouble extends StrParser[Double] {
//   def parse(s: String) = s.toDouble
// }
// val args = Seq("123", "true", "7.5")
// val myInt = ParseInt.parse(args(0))
// val myBoolean = ParseBoolean.parse(args(1))
// val myDouble = ParseDouble.parse(args(2))
// println(myInt)
// println(myBoolean)
// println(myDouble)

// ----- 5.5.3 Solution: Implicit StrParser -----
trait StrParser[T] { def parse(s: String): T }
object StrParser {
  implicit object ParseString extends StrParser[String] {
    def parse(s: String) = s
  }
  implicit object ParseInt extends StrParser[Int] {
    def parse(s: String) = s.toInt
  }
  implicit object ParseBoolean extends StrParser[Boolean] {
    def parse(s: String) = s.toBoolean
  }
  implicit object ParseDouble extends StrParser[Double] {
    def parse(s: String) = s.toDouble
  }
}

def parseFromString[T](s: String)(implicit parser: StrParser[T]) = {
  parser.parse(s)
}
// val args = Seq("135", "false", "1237.5")
// val myInt = parseFromString[Int](args(0))
// val myBoolean = parseFromString[Boolean](args(1))
// val myDouble = parseFromString[Double](args(2))
// println(myInt)
// println(myBoolean)
// println(myDouble)

// def parseFromConsole[T](s: String)(implicit parser: StrParser[T]) = {
//   parser.parse(scala.Console.in.readLine())
// }
// val myConsoleInt = parseFromConsole[Int]
// println(myConsoleInt)

// ----- 5.5.4 REcursive Typeclass Inference -----
// Parsing Sequence
implicit def ParseSeq[T](implicit p: StrParser[T]) = new StrParser[Seq[T]] {
  def parse(s: String) = s.split(',').toSeq.map(p.parse)
}
// println(parseFromString[Seq[Boolean]]("true,false,true"))

// Parsing Tuple
implicit def ParseTuple[T, V](implicit
    left_par: StrParser[T],
    right_par: StrParser[V]
) = {
  new StrParser[(T, V)] {
    def parse(s: String) = {
      val Array(left_val, right_val) = s.split('+')
      (left_par.parse(left_val), right_par.parse(right_val))
    }
  }
}
// println(parseFromString[(String, Int)]("Hello+321"))

// Parsing Nested Structures
println(parseFromString[Seq[(Int, Boolean)]]("1+true,2+false,3+true,4+false"))
println(parseFromString[(Seq[Int], Seq[Boolean])]("1,2,3,4,5+true,false,true"))
/*</script>*/ /*<generated>*/
def args = ch5_sc.args$
  /*</generated>*/
}

object ch5_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }
  def main(args: Array[String]): Unit = {
    args$set(args)
    ch5.hashCode() // hasCode to clear scalac warning about pure expression in statement position
  }
}

