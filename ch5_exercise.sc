sealed trait Experssion
case class BinOp(left: Experssion, operator: String, right: Experssion)
    extends Experssion
case class Literal(value: Int) extends Experssion
case class Variable(name: String) extends Experssion

def stringify(expr: Experssion): String = expr match {
  case BinOp(left, operator, right) =>
    s"(${stringify(left)} ${operator} ${stringify(right)})"
  case Literal(value) => value.toString
  case Variable(name) => name
}

def evaluate(expr: Experssion): Experssion = expr match {

  case BinOp(Literal(left), "+", Literal(right)) => Literal(left + right)
  case BinOp(Literal(left), "-", Literal(right)) => Literal(left - right)
  case BinOp(Literal(left), "*", Literal(right)) => Literal(left * right)

  case BinOp(Variable(left), "+", Literal(0)) => Variable(left)
  case BinOp(Variable(left), "-", Literal(0)) => Variable(left)
  case BinOp(Variable(left), "*", Literal(1)) => Variable(left)

  case BinOp(Literal(0), "+", Variable(right)) => Variable(right)
  case BinOp(Literal(1), "*", Variable(right)) => Variable(right)

}
