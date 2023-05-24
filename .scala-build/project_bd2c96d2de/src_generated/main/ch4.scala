



object ch4 {
/*<script>*/// ======== 4.1 Operation ========
// ----- 4.1.1 Builders -----
// Builders let you efficiently construct a collection of unknown length,
// "freezing" it into the collection you want at the end.
// This is most useful for constructing Arrays or immutable collections
// where you cannot add or remove elements once the collection has been constructed.

// val b = Array.newBuilder[Int]
// b += 1
// b += 2
// println(b)
// println(b.result().mkString(" "))

// ----- 4.1.2 Factory -----
val arrFill = Array.fill(5)("hello")
// println(arrFill.toList)

val arrTabllate = Array.tabulate(5)(n => s"hello -> $n")
// println(arrTabllate.toList)

val arrConcatenate = Array(1, 2, 3) ++ Array(4, 5, 6)
// println(arrConcatenate.toList)

// ----- 4.1.3 Transforms -----
val arrMap = Array(1, 2, 3, 4, 5).map(i => i * 3)
// println(arrMap.toList)

val arrFilter = Array(1, 2, 3, 4, 5).filter(i => i % 2 == 1)
// println(arrFilter.toList)

val arrTake = Array(1, 2, 3, 4, 5).take(2) // Keep first two elements
// println(arrTake.toList)

val arrDrop = Array(1, 2, 3, 4, 5).drop(3) // Keep first three elements
// println(arrDrop.toList)

val arrSlice = Array(1, 2, 3, 4, 5).slice(1, 3) // Keep elements from index 1-4
// println(arrSlice.toList)

val arrDistinct =
  Array(1, 2, 3, 4, 5, 4, 3, 2, 1, 2, 3, 4, 5, 6, 7,
    8).distinct // Removes all duplicates
// println(arrDistinct.toList)

// ----- 4.1.4 Queries -----
val arrFind1 =
  Array(2, 3, 4, 5, 6, 7, 8, 9).find(i =>
    i % 3 == 1
  ) // Return first element of None
// println(arrFind1)

val arrExists1 =
  Array(2, 3, 4, 5, 6, 7, 8, 9).exists(i =>
    i % 3 == 1 && i > 6
  ) // Return first element of None
// println(arrExists1)

// ----- 4.1.5 Aggregation -----
// mkString
// println(Array(2, 3, 6, 7, 8, 10).mkString(","))
// println(Array(2, 3, 6, 7, 8, 10).mkString("[", ",", "]"))

// foldLeft
// println(
//   Array(1, 2, 3, 4, 5, 6, 7).foldLeft(0)((x, y) => x + y)
// ) // sum of all elements
// println(
//   Array(1, 2, 3, 4, 5, 6, 7).foldLeft(1)((x, y) => x * y)
// ) // product of all elements

// groupBy
val grouped = Array(1, 2, 3, 4, 5, 6, 7).groupBy(_ % 2)
// println(grouped)
// println(grouped(0).toList)
// println(grouped(1).toList)

// ----- 4.1.6 Combining Operations -----
def stdDev(arr: Array[Double]): Double = {
  val mean = arr.sum / arr.length
  val squareError = arr.map(_ - mean).map(x => x * x)
  math.sqrt(squareError.sum / arr.length)
}
// println(stdDev(Array(1, 2, 3, 4, 5)))

// ----- 4.1.7 Converters -----
val t1 = Array(1, 2, 3).to(Vector)
// println(t1)

val t2 = Vector(1, 2, 3).to(Array)
// println(t2.toList)

val t3 = Array(1, 2, 3).to(Set)
// println(t3)

val t4 = Vector(1, 2, 3).to(Set)
// println(t4)

// ----- 4.1.8 Views -----
val myArray = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)
val myNewArray1 = myArray.map(x => x + 1).filter(x => x % 2 == 0).slice(1, 3)
// println(myNewArray1.toList)

val myNewArray2 =
  myArray.view.map(_ + 1).filter(_ % 2 == 0).slice(1, 3).to(Array)
// println(myNewArray2.toList)

// ======== 4.2 Immutable Collections ========
// ----- 4.2.1 Immutable Vector -----
val v = Vector(1, 2, 3, 4, 5)
val v2 = v.updated(2, 10)
// println(s"v(0) = ${v(0)}")
// println(s"v2 = ${v2.mkString(" ")}")
// println(s"v = ${v.mkString(" ")}")

val v3 = v :+ 99
// println(s"v3 = ${v3.mkString(" ")}")
val v4 = 999 +: v
// println(s"v4 = ${v4.mkString(" ")}")
val v5 = 9009 +: v :+ 9119
// println(s"v5 = ${v5.mkString(" ")}")

// ----- 4.2.3 Immutable Set -----
val s = Set(1, 2, 3)
// println(s.contains(2))
// println(s.contains(4))
val s1 = Set(1, 2, 3) + 4 + 5
// println(s1)
val s2 = Set(1, 2, 3) - 2
// println(s2)
val s3 = Set(1, 2, 3) ++ Set(2, 3, 4)
// println(s3)
// for (i <- s3) println(i)

// ----- Immutable Maps -----
val m1 = Map("one" -> 1, "two" -> 2, "three" -> 3)
// println(m1)
// println(m1.contains("two"))

val m2 = Map(1 -> 0.5, 2 -> 1.9, 4 -> 99.0)
// println(m2)
// println(m2.contains(3))

// println(m2.get(2))
// println(m2.get(6))

// ----- Immutable List -----
val myList01 = List(1, 2, 3, 4, 5)
// println(myList01)
// println(myList01.head)
val myTail01 = myList01.tail
// println(myTail01)
val myOtherList01 = 0 :: myList01
// println(myOtherList01)

val myList02 = myList01.updated(3, 9)
// println(myList02)

// ======== 4.3 Mutable Collections ========
// ----- 4.3.1 Mutable ArrayDeque -----
val myArrayDeque = collection.mutable.ArrayDeque(1, 2, 3, 4, 5)
// println(myArrayDeque.removeHead())
// println(myArrayDeque.append(6))

// ----- 4.3.2 Mutable Set -----
val this_s = collection.mutable.Set(1, 2, 3)
// println(this_s.contains(2))
// println(this_s.contains(4))
this_s.add(4)
// println(this_s)

// ----- 4.3.3 Mutable Maps -----
val m = collection.mutable.Map("one" -> 1, "two" -> 2, "three" -> 3)
m.remove("two")
m("five") = 5
// println(m)

m.getOrElseUpdate("three", -1) // Get the value as it exists
// println(m)
m.getOrElseUpdate("four", -1) // Insert the value as it does not
// println(m)

// ----- 4.3.4 In-Place Operations
val iparr = collection.mutable.ArrayDeque(1, 2, 3, 4)
// println(iparr)

iparr.mapInPlace(_ + 1)
// println(iparr)

iparr.filterInPlace(_ % 2 == 0)
// println(iparr)

iparr.append(0)
// println(iparr)

iparr.sortInPlace()
// println(iparr)

iparr.dropInPlace(1)
// println(iparr)

// ======== 4.4 Common Iterfaces ========
def iterateOverSomething[T](items: Seq[T]) = {
  for (i <- items) println(i)
}
// iterateOverSomething(Vector(1, 2, 3, 4, 5))
// iterateOverSomething(Array(1, 2, 3, 4, 5))

def getIndexTwoAndFour[T](items: IndexedSeq[T]) = (items(2), items(4))

// println(getIndexTwoAndFour(Vector(1, 2, 3, 4, 5)))
// println(getIndexTwoAndFour(Array(2, 4, 6, 8, 10)))
/*</script>*/ /*<generated>*/
def args = ch4_sc.args$
  /*</generated>*/
}

object ch4_sc {
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
    ch4.hashCode() // hasCode to clear scalac warning about pure expression in statement position
  }
}

