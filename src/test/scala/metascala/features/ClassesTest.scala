package metascala.features

import org.scalatest.FreeSpec
import metascala.Util

class ClassesTest extends FreeSpec with Util{

  "classes" - {
    val tester = new Tester("metascala.features.classes.ClassStuff")
    "customClass" in tester.run("customClass")
    "stringConcat" in tester.run("stringConcat")
    "inheritance" in tester.run("inheritance")
    "constructor" in tester.run("constructor")
    "superConstructor" in tester.run("superConstructor")
    "override" in tester.run("override")
    "innerClass" in tester.run("innerClass")

  }
  "inheritance" - {
    val tester = new Tester("metascala.features.classes.Inheritance")
    "implement" in tester.run("implement", 10)
    "abstractClass" in tester.run("abstractClass")
    "shadowedInheritedGet" in tester.run("shadowedInheritedGet")
    "shadowedInheritedSet" in tester.run("shadowedInheritedSet")
    "superMethod" in tester.run("superMethod")
    "staticInheritance" in tester.run("staticInheritance")
  }
}
