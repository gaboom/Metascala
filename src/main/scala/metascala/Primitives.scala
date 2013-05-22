package metascala

import scala.collection.mutable
import scala.reflect.ClassTag

object Prim extends {

  val all: Map[Char, Prim[_]] = Map(
    'V' -> (V: Prim[_]),
    'Z' -> (Z: Prim[_]),
    'B' -> (B: Prim[_]),
    'C' -> (C: Prim[_]),
    'S' -> (S: Prim[_]),
    'I' -> (I: Prim[_]),
    'F' -> (F: Prim[_]),
    'J' -> (J: Prim[_]),
    'D' -> (D: Prim[_])
  )
  def unapply(p: Prim[_]) = Some(p.size)
}
abstract class Prim[T: ClassTag](val size: Int){
  def read(x: () => Val): T
  def write(x: T, out: Val => Unit): Unit
  def boxedClass: Class[_]
  val primClass: Class[_] = implicitly[ClassTag[T]].runtimeClass
}

case object V extends Prim[Unit](0){
  def apply(x: Val) = ???
  def read(x: () => Val) = ()
  def write(x: Unit, out: Val => Unit) = ()
  def boxedClass = classOf[java.lang.Void]

}

case object Z extends Prim[Boolean](1){
  def apply(x: Val) = x != 0
  def read(x: () => Val) = this(x())
  def write(x: Boolean, out: Val => Unit) = out(if (x) 1 else 0)
  def boxedClass = classOf[java.lang.Boolean]
}

case object B extends Prim[Byte](1){
  def apply(x: Val) = x.toByte
  def read(x: () => Val) = this(x())
  def write(x: Byte, out: Val => Unit) = out(x)
  def boxedClass = classOf[java.lang.Byte]
}

case object C extends Prim[Char](1){
  def apply(x: Val) = x.toChar
  def read(x: () => Val) = this(x())
  def write(x: Char, out: Val => Unit) = out(x)
  def boxedClass = classOf[java.lang.Character]
}

case object S extends Prim[Short](1){
  def apply(x: Val) = x.toShort
  def read(x: () => Val) = this(x())
  def write(x: Short, out: Val => Unit) = out(x)
  def boxedClass = classOf[java.lang.Short]
}

case object I extends Prim[Int](1){
  def apply(x: Val) = x
  def read(x: () => Val) = this(x())
  def write(x: Int, out: Val => Unit) = out(x)
  def boxedClass = classOf[java.lang.Integer]
}

case object F extends Prim[Float](1){
  def apply(x: Val) = java.lang.Float.intBitsToFloat(x)
  def read(x: () => Val) = this(x())
  def write(x: Float, out: Val => Unit) = out(java.lang.Float.floatToRawIntBits(x))
  def boxedClass = classOf[java.lang.Float]
}

case object J extends Prim[Long](2){
  def apply(v1: Val, v2: Val) = v1.toLong << 32 | v2 & 0xFFFFFFFFL
  def read(x: () => Val) = {
    this(x(), x())
  }
  def write(x: Long, out: Val => Unit) = {
    out((x >> 32).toInt)
    out(x.toInt)
  }
  def boxedClass = classOf[java.lang.Long]
}

case object D extends Prim[Double](2){
  def apply(v1: Val, v2: Val) = java.lang.Double.longBitsToDouble(J(v1, v2))
  def read(x: () => Val) = java.lang.Double.longBitsToDouble(J.read(x))
  def write(x: Double, out: Val => Unit) = J.write(java.lang.Double.doubleToRawLongBits(x), out)
  def boxedClass = classOf[java.lang.Double]
}