// https://docs.scala-lang.org/scala3/book/introduction.html

def forLoop() = {
  for (i <- Range.inclusive(1, 100)) {
    println(
      if (i % 3 == 0 && i % 5 == 0) "FizzBuzz"
      else if (i % 3 == 0) "Fizz"
      else if (i % 5 == 0) "Buzz"
      else i
    )
  }
}

def testVal01() = {
  val b: Float = 100
  var input: String = "Testing"
  println(b)
  println(input)
}

def testTuple01() = {
  var tuple01: (Byte, String, Double, Boolean) = (1, "abc. def", 1.9, true)
  println(tuple01)
  println(tuple01._3)
  println(tuple01._4)
}

def testArray01() = {
  val arr01 = Array[Int](1, 2, 3)
  println(arr01)
  println(arr01(0))
  var b = arr01(1)
  var c = arr01(2)
  println(b + c)
}

def testArray02() = {
  val arr02 = new Array[Int](5)
  println(arr02)
  arr02(2) = 99
  println(arr02(2))
  println(arr02.mkString(" "))
}

def testPower(base: Int, exponent: Option[Int]) = {
  var ans: Double = 0.0
  exponent match {
    case Some(value) => ans = math.pow(base.toDouble, value.toDouble)
    case None        => ans = math.pow(base.toDouble, 1.0)
  }
  println(s"Ans: $ans")
}

def forLoop02() = {
  var total: Int = 0
  val items = Array[Int](1, 2, 3, 4, 5)
  for (i <- items) {
    total += i
    println(total)
  }
  total * total
}

def comprehension() = {
  val a = Array[Int](1, 2, 3, 4, 5)
  val a2 = for (i <- a; if i > 2) yield i * i * i
  println(a2.mkString(" "))

}

def functionValue() = {
  var t: Float => Float = j => j * 3
  println(t(2.364f))
}

class MagicMachine(var x: Int, var y: Int) {
  def updateLocation(f1: Int => Int, f2: Int => Int) = {
    x = f1(x)
    y = f2(y)
  }

  def printLocation(msg: Option[String]) = {
    msg match {
      case Some(message) => println(s"$message | you are at ($x, $y)")
      case None          => println(s"You are at ($x, $y)")
    }
  }

}

// var m = new MagicMachine(0, 0)
// m.printLocation(None)
// m.updateLocation(i => i + 3, j => j - 2)
// m.printLocation(Some("Location Updated"))
// m.updateLocation(_ + 10, _ + 10)
// m.printLocation(Some("Location Updated Again"))

// ----- Multiple Parameter List -----
def testingMultiplerParamsLoop(start: Int, end: Int)(
    secondFunction: Int => Double
) = {
  for (
    i <- Range(start, end)
    if i % 3 == 0
  ) {
    var res = secondFunction(i)
    println(i + " " + res)
  }
}
// testingMultiplerParamsLoop(start = 10, end = 20) { i =>
//   math.pow((i * 2).toDouble, 0.5)
// }

// ----- Class -----
class testObj1(attr: Int) {
  def printAttr() = {
    println(s"This is the attr: $attr")
  }
}

class testObj2(val attr: Int) {
  def printAttr() = {
    println(s"This is the attr: $attr")
  }
}

class testObj3(var attr: Int) {
  val attrAgain = "Hello World"
  var attrAgain2 = "Hello World !!!"
  def printAttr() = {
    println(s"This is the attr: $attr")
  }
}

// val obj1 = new testObj1(123)
// val obj2 = new testObj2(456)
// val obj3 = new testObj3(999)
// obj1.printAttr()
// println(obj2.attr)
// println(obj3.attr)
// obj3.attr = 1000
// obj3.printAttr()
// println(obj3.attrAgain)
// println(obj3.attrAgain2)
// obj3.attrAgain2 = "No way~"
// println(obj3.attrAgain2)

// ----- Traits -----
trait Point {
  def hypo: Double
}

class Point2D(x: Double, y: Double) extends Point {
  def hypo = math.sqrt(x * x + y * y)
}

class Point3D(x: Double, y: Double, z: Double) extends Point {
  def hypo = math.sqrt(x * x + y * y + z * z)
}

// val points: Array[Point] = Array(new Point2D(4, 3), new Point3D(12, 4, 3))
// for (p <- points) {
//   println(p.hypo)
// }

// --------------- Exercise -------------------
// Exercise 3.5
def flexibleFizzBuzz(func: String => Unit) = {
  for (i <- Range.inclusive(1, 100)) {
    func(
      if (i % 3 == 0 && i % 5 == 0) "FizzBuzz"
      else if (i % 3 == 0) "Fizz"
      else if (i % 5 == 0) "Buzz"
      else i.toString()
    )
  }
}

// Example 1
// flexibleFizzBuzz(s => {})

// Example 2
// flexibleFizzBuzz(s => println(s))

// Example 3
// var i = 0
// val output = new Array[String](100)
// flexibleFizzBuzz { s =>
//   output(i) = s
//   i += 1
// }
// println(output.mkString("\n"))

// Exercise 3.6
class Msg(val id: Int, val parent: Option[Int], val txt: String)
def printMessages(messages: Array[Msg]): Unit = {
  val tab = " " * 4
  var level: Int = 0
  var lastParentId = -1
  for (msg <- messages) {
    msg.parent match {
      case Some(parent) => {
        if (lastParentId != parent) level += 1
        lastParentId = parent
        print(tab * level)
      }
      case None => level = 0
    }
    println(s"#${msg.id} ${msg.txt}")
  }
}

// Example
// printMessages(
//   Array(
//     new Msg(0, None, "Hello"),
//     new Msg(1, Some(0), "World"),
//     new Msg(2, None, "I am Cow"),
//     new Msg(3, Some(2), "Hear me moo"),
//     new Msg(4, Some(2), "Here I stand"),
//     new Msg(5, Some(2), "I am Cow"),
//     new Msg(6, Some(5), "Here me moo, moo")
//   )
// )
