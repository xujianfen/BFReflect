package blue.fen.demo;

import org.junit.Test;

import blue.fen.reflect.BFReflect;
import blue.fen.reflect.BFReflectConfig;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    void test1() {
        System.out.println("\n=========  1、方法无参、可变参数、原始类型及其包装类的测试 =========\n");
        TestModel test = new TestModel();
        Object pm = BFReflect.METHOD.invokeN(test, "pm");
        Object pmN = BFReflect.METHOD.invokeN(test, "pm", null);
        Object pmi = BFReflect.METHOD.invokeN(test, "pm", 1);
        Object pmI = BFReflect.METHOD.invokeN(test, "pm", new Integer(1));
        Object pmii = BFReflect.METHOD.invokeN(test, "pm", 1, 1);
        Object pmII = BFReflect.METHOD.invokeN(test, "pm", new Integer(1), new Integer(1));
        Object pmis0 = BFReflect.METHOD.invokeN(test, "pm", new int[0]);
        Object pmIs0 = BFReflect.METHOD.invokeN(test, "pm", new Integer[0]);
        Object pmis1 = BFReflect.METHOD.invokeN(test, "pm", new int[]{1});
        Object pmIs1 = BFReflect.METHOD.invokeN(test, "pm", new Integer[]{1});
        Object pmis2 = BFReflect.METHOD.invokeN(test, "pm", new int[]{1, 1});
        Object pmIs2 = BFReflect.METHOD.invokeN(test, "pm", new Integer[]{1, 1});
        Object pmiIs = BFReflect.METHOD.invokeN(test, "pm", 1, new Integer[]{1, 1});
        Object pmiii = BFReflect.METHOD.invokeN(test, "pm", 1, 1, 1);

        System.out.println("pm()=" + pm);
        System.out.println("pm(null)=" + pmN);
        System.out.println("pm(int)=" + pmi);
        System.out.println("pm(Int)=" + pmI);
        System.out.println("pm(int,int)=" + pmii);
        System.out.println("pm(Int,Int)=" + pmII);
        System.out.println("pm(int[0])=" + pmis0);
        System.out.println("pm(Int[0])=" + pmIs0);
        System.out.println("pm({int})=" + pmis1);
        System.out.println("pm({Int})=" + pmIs1);
        System.out.println("pm({int, int})=" + pmis2);
        System.out.println("pm({Int, Int})=" + pmIs2);
        System.out.println("pm(int,{Int, Int})=" + pmiIs);
        System.out.println("pm(int, int, int)=" + pmiii);
    }

    void test2() {
        System.out.println("\n=========  2. 方法参数继承关系和可变参数的测试 =========\n");
        TestModel test = new TestModel();
        TestModel.A a = new TestModel.A();
        TestModel.B b = new TestModel.B();

        Object pm = BFReflect.METHOD.invokeN(test, "pm2");
        Object pmN = BFReflect.METHOD.invokeN(test, "pm2", null);
        Object pma = BFReflect.METHOD.invokeN(test, "pm2", a);
        Object pmb = BFReflect.METHOD.invokeN(test, "pm2", b);
        Object pmIb = BFReflect.METHOD.invokeN(test, "pm2", 01, b);
        Object pmaa = BFReflect.METHOD.invokeN(test, "pm2", a, a);
        Object pmbb = BFReflect.METHOD.invokeN(test, "pm2", b, b);
        Object pmab = BFReflect.METHOD.invokeN(test, "pm2", a, b);
        Object pmba = BFReflect.METHOD.invokeN(test, "pm2", b, a);
        Object pmbbs0 = BFReflect.METHOD.invokeN(test, "pm2", new TestModel.B[0]);
        Object pmbbs1 = BFReflect.METHOD.invokeN(test, "pm2", new TestModel.B[]{b});
        Object pmbbs1N = BFReflect.METHOD.invokeN(test, "pm2", new TestModel.B[]{null});
        Object pmbbs2 = BFReflect.METHOD.invokeN(test, "pm2", new TestModel.B[]{b, b});
        Object pmbbs2N = BFReflect.METHOD.invokeN(test, "pm2", new TestModel.B[]{b, null});
        Object pmbbs2NN = BFReflect.METHOD.invokeN(test, "pm2", new TestModel.B[]{null, null});
        Object pmbbsAAA = BFReflect.METHOD.invokeN(test, "pm2", a, b, b);
        System.out.println("pm()=" + pm);
        System.out.println("pm(null)=" + pmN);
        System.out.println("pm(a)=" + pma);
        System.out.println("pm(b)=" + pmb);
        System.out.println("pm(int, b)=" + pmIb);
        System.out.println("pm(a,a)=" + pmaa);
        System.out.println("pm(b,b)=" + pmbb);
        System.out.println("pm(a,b)=" + pmab);
        System.out.println("pm(b,a)=" + pmba);
        System.out.println("pm(B[0])=" + pmbbs0);
        System.out.println("pm({b})=" + pmbbs1);
        System.out.println("pm({null})=" + pmbbs1N);
        System.out.println("pm({b,b})=" + pmbbs2);
        System.out.println("pm({b,null})=" + pmbbs2N);
        System.out.println("pm({null,null})=" + pmbbs2NN);
        System.out.println("pm({a,b,b})=" + pmbbsAAA);
    }

    void test3() {
        System.out.println("\n========= 3. 调用父类的方法测试 =========\n");
        TestModel.A a = new TestModel.A();
        TestModel.B b = new TestModel.B();

        b.a(b);
        b.a(a);

        Object mb = BFReflect.METHOD.invokeN(b, "a", b);
        Object ma = BFReflect.METHOD.invokeN(b, "a", a);

        System.out.println("a(b)=" + mb);
        System.out.println("a(a)=" + ma);
    }

    void test4() {
        System.out.println("\n========= 4. 属性获取与修改的测试 =========\n");

        TestModel test = new TestModel();
        Object f1 = BFReflect.FIELD.getN(test, "f1");
        Object fs1 = BFReflect.FIELD.getN(TestModel.class, "fS1");
        Object fs1s = BFReflect.FIELD.getN(TestModel.class, "fS1");

        System.out.println("get f1=" + f1);
        System.out.println("get fs1=" + fs1);
        System.out.println("get fs1s=" + fs1s);

        System.out.println("\n========= set field test =========\n");

        BFReflect.FIELD.setN(test, "f1", "set f1");
        BFReflect.FIELD.setN(TestModel.class, "fS1", "set fS1");
        BFReflect.FIELD.setN(TestModel.class, "fS1", "set fS1");

        f1 = BFReflect.FIELD.getN(test, "f1");
        fs1 = BFReflect.FIELD.getN(TestModel.class, "fS1");
        fs1s = BFReflect.FIELD.getN(TestModel.class, "fS1");

        System.out.println("set f1=" + f1);
        System.out.println("set fs1=" + fs1);
        System.out.println("set fs1s=" + fs1s);
    }

    @Test
    public void reflectTest() {
        BFReflectConfig.builder().openNlog(true).complete();

        test1();
        test2();
        test3();
        test4();
    }
}