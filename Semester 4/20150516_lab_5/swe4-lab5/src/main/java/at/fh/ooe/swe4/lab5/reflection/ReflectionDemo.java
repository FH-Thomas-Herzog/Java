package at.fh.ooe.swe4.lab5.reflection;

import java.lang.reflect.*;

public class ReflectionDemo {

  public static void callMethod() {
    System.out.println("\n************* call method add *************");
    try {
      Class<?> cls = Class.forName("at.fh.ooe.swe4.lab5.reflection.Calc");
      ICalc calc = (ICalc)cls.newInstance();
      System.out.println("3+5=" + calc.add(3, 5));
    }
    catch (Throwable e) {
      System.err.println(e);
    }
  }

  public static void showParameters(Class<?>[] params) {
    for (Class<?> param : params)
      System.out.println("   " + param.getTypeName());
  }

  public static void showMethods(Class<?> clazz) {
    System.out.println("info for class " + clazz.getName());

    System.out.println("******** constructor(s) ********");
    int i=1;
    for (Constructor<?> ctor : clazz.getConstructors()) {
      System.out.printf("Constructor %d%n", i++);
      showParameters(ctor.getParameterTypes());
      System.out.println("---------------------------------------");
    }

    System.out.println("******** method(s) ********");
    for (Method method : clazz.getMethods()) {
      System.out.printf("%s: returns %s%n", method.getName(), method.getReturnType().getName());
      showParameters(method.getParameterTypes());
      System.out.println("---------------------------------------");
    }
  }

  public static void main(String[] args) {    
    try {
      // String str = "abc";
      // version 1:
      // Class<?> c = str.getClass();
      // version 2:
      // Class<?> c = String.class;
      // version 3:
      Class<?> c = Class.forName("java.lang.String");
      
      showMethods(c);
      callMethod();
    }
    catch (Exception e) {
      System.out.println("No such class: " + e);
    }
  }
}