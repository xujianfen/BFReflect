package blue.fen.demo;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect <br/>
 * 创建时间：2023/2/6 00:22 （星期一｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：
 */

public class TestModel {
    // ================== 基础功能测试用 ====================
    private String f1 = "f1";
    private static String fS1 = "fS1";

    private Object m1(String msg) {
        return msg;
    }

    private static Object mS1(String msg) {
        return msg;
    }


    // ================== 方法参数测试用 ====================
    // 1、无参、可变参数、原始类型及其包装类的测试
    private Object pm() {
        return "no param";
    }

    private Object pm(int i) {
        return "int";
    }

    private Object pm(Integer i) {
        return "Integer";
    }

    private Object pm(int... i) {
        return "int...";
    }

    private Object pm(int i, Integer... is) {
        return "int Integer...";
    }

    private Object pm(Integer[] i) {
        return "Integer...";
    }

    // ================== 方法参数测试用 ====================
    // 2. 继承关系和可变参数的测试
    private Object pm2() {
        return "param";
    }

    private Object pm2(A a) {
        return "a";
    }

    private Object pm2(B b) {
        return "B";
    }

    private Object pm2(int i, Bi b) {
        return "int Bi";
    }

    private Object pm2(int i, Bi2 b) {
        return "int Bi2";
    }

    private Object pm2(A a, Ai... as) {
        return "ai...";
    }

    private Object pm2(A a, B b, B b2, A... as) {
        return "abb a...";
    }

    private Object pm2(Ai2... as) {
        return "ai2...";
    }

    private Object pm2(B[] bs) {
        return "B[]";
    }

    private Object pm2(A a, B b) {
        return "a B";
    }

    private Object pm2(B b, A a) {
        return "B A";
    }

    private Object pm2(Bi bi, Bi2 bi2) {
        return "Bi Bi2";
    }


    interface Ai {
    }

    interface Ai2 {
    }

    interface Bi {
    }

    interface Bi2 {
    }

    public static class A extends B implements Ai, Ai2 {

    }

    public static class B extends C implements Bi, Bi2 {
        String a(B b) {
            return "B b";
        }
    }

    public static class C {
        String a(B b) {
            return "B b";
        }

        String a(A a) {
            return "C a";
        }
    }
}
