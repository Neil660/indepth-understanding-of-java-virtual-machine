package org.fenixsoft.jvm.chapter7;

/**
 * @author zzm
 */

import java.io.IOException;
import java.io.InputStream;

/**
 * 类加载器与instanceof关键字演示
 *
 * @author zzm
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                }
                catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = myLoader.loadClass("org.fenixsoft.jvm.chapter7.ClassLoaderTest").newInstance();
        // 输出：class org .fenixsoft .classloading .ClassLoaderTest
        System.out.println(obj.getClass());
        // 输出：false
        System.out.println(obj instanceof org.fenixsoft.jvm.chapter7.ClassLoaderTest);

        // 输出：org.fenixsoft.jvm.chapter7.ClassLoaderTest$1@49476842
        System.out.println(obj.getClass().getClassLoader());
        // 输出：sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(obj.getClass().getClassLoader().getParent());

        // 输出：sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(ClassLoaderTest.class.getClassLoader());
        // 输出：sun.misc.Launcher$ExtClassLoader@5451c3a8
        System.out.println(ClassLoaderTest.class.getClassLoader().getParent());
        // 输出：null
        System.out.println(ClassLoaderTest.class.getClassLoader().getParent().getParent());
        // 输出：sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(ClassLoader.getSystemClassLoader());
    }

}
