## BFReflect

  BFReflect是用于简化反射流程的工具，它可以帮助你绕过Android的hide代码，同时你不需要再去关注反射类型的层级，反射的访问权限，实参和形参的类型匹配等这些不应该关注的细节，BFReflect会自动帮你找到合适的反射目标并处理掉让你不愉快的细节，让你能像写普通的代码一样愉快的进行反射。

PS：由于BFReflect还处于测试阶段并且部分功能尚未完善，若想用到实际项目中，请谨慎判断可行性。



## 如何获取

最新版本可在[Maven Central](https://central.sonatype.com/artifact/io.github.xujianfen.pram/reflect/1.0.0/versions)上获得。

```gradle
implementation 'io.github.xujianfen.pram:reflect:1.0.0'
```

依赖成功后，可以在代码中导入BFReflect。

```java
import blue.fen.reflect.BFReflect;
```



## 功能介绍

### 1. 三大反射API

| 调用模式 | 是否支持 |  进度  |                           支持程度                           |
| :------: | :------: | :----: | :----------------------------------------------------------: |
| 属性反射 |    是    | 已完成 |               基本可支持普通属性反射的所有情况               |
| 方法反射 |    是    | 待完善 | 方法查找功能基本可支持普通方法反射的所有情况。 <br/>方法调用功能，支持自动匹配四种类型：相同类型、<br/> 继承类型、基础类型和封装类型的转化、可变参数类<br/>型 ，同时可校正实参，筛选重写方法，对模棱两可的<br/>方法进行异常提示；暂不支持泛型类型的匹配和多元<br/>数组的匹配。 |
| 类型反射 |    否    | 未开始 |                            不支持                            |

属性调用范例

```java
Test test = new Test();

//属性查找
Field field = BFReflect.FIELD.find(Test.class, "f1");

//调用普通属性
Object result = BFReflect.FIELD.get(test, "test");
BFReflect.FIELD.set(test, "test", "set test");

//调用静态属性
Object result = BFReflect.FIELD.get(Test.class, "testStatic");
BFReflect.FIELD.set(Test.class, "testStatic", "set testStatic");
```

方法调用范例

```java
//方法查找
Method method = BFReflect.METHOD.find(Test.clss, "setTest", new Class[]{String.class});
    
//调用普通方法
Object result = BFReflect.METHOD.invoke(test, "setTest", "set test");

//调用静态方法
Object result = BFReflect.METHOD.invoke(Test.class, "setTestStatic", "set test");
```



### 2. 三种调用模式


| 调用模式     | 描述                                                     |
| ------------ | -------------------------------------------------------- |
| 普通模式调用 | 调用方需要接收可能出现的异常                             |
| N模式调用    | 调用方无需接收异常消息，异常消息被屏蔽，改为返回默认数据 |
| X模式调用    | 返回包装好的结果对象，调用方可根据需要这里结果。         |

普通模式调用，需要使用try-catch语句包裹代码。

```java
try {
    Object result = BFReflect.METHOD.invoke(test, "setTest", "set test");
} catch (Exception e) {
    e.printStackTrace();
}
```

N模式调用，虽然不需要使用try-catch语句包裹代码，但是最好对结果进行一下处理。

```java
Object result = BFReflect.METHOD.invokeN(test, "setTest", "set test");

if(result != null) {
    ...
}
```

X模式调用，调用方可根据不同场景选择合适的方式处理结果。

```java
//场景1 （类似N模式调用）
IReflectResult<Object> rResult = BFReflect.METHOD.invokeX(test, "setTest", "set test");

if(rResult.isSuccess()) {
	Object result = rResult.get();   
}

//场景2
try {
    IReflectResult<Object> rResult = BFReflect.METHOD.invokeX(test, "setTest", "set test");

    rResult.tryThrow(); //抛出异常
    
    Object result = rResult.get();
} catch (Exception e) {
    e.printStackTrace();
}

//场景3 （类似普通模式调用）
try {
    Object result = BFReflect.METHOD.invokeX(test, "setTest", "set test").getOrThrow();;
} catch (Exception e) {
    e.printStackTrace();
}

//场景4 
IReflectResult<Object> rResult = BFReflect.METHOD.invokeX(test, "setTest", "set test");
Exception e = rResult.getThrow(); 
Object result = rResult.get();
```



### 3. 自定义配置

| 配置方法         | 描述                      |
| ---------------- | ------------------------- |
| openNLog         | 是否开启N模式异常日志打印 |
| setPriority      | 设置优先级处理器          |
| recoveryPriority | 重置为默认优先级处理器    |

可根据以下方式进行配置。

```java
BFReflectConfig.builder().openNlog(true).recoveryPriority().complete();
```

  

## 优先级介绍

为了兼容特殊场景，调用方可根据需要自行设计优先级计算逻辑，在考虑这一步之前，有必要了解一下默认的优先级是如何计算的。

### 1. 数据来源

介绍优先级之前，需要先了解一下数据来源。

BFReflect的参数匹配流程由Arena控制，它会先筛选掉不合适的方法，接着筛选出的方法会进行比赛，每轮比赛出1个参数，第i轮比赛，就是匹配第i个参数，

接下来，参数需要经过ParameterMatchingCenter，进行参数过滤，然后会由某个过滤节点得到过滤当前的过滤分数，这时候分数不行的方法会直接淘汰，分数匹配的方法会交给优先级处理器继续计算，最终得到该轮比赛的真正分数。

优先级会从过滤器得到三个数据，分别是前面的参数积累下来的分数，匹配出方法的过滤器，过滤器给出的分数，

你可以从MatchingSpec得到过滤器的类型，目前有以下5种过滤器类型，你需要根据不同的过滤类型计算优先级。

| 过滤器                | 匹配优先级 | 描述                             |
| --------------------- | ---------- | -------------------------------- |
| SAME_FILTER           | 0          | 匹配相同类型                     |
| SUPER_FILTER          | 1          | 匹配继承关系                     |
| WRAPPER_FILTER        | 2          | 匹配基本类型和封装类型           |
| ARRAY_VARIABLE_FILTER | 3          | 匹配继承关系的可变参数           |
| ARRAY_WRAPPER_FILTER  | 4          | 匹配基本类型和封装类型的可变参数 |

### 2. 优先级计算

BFReflect的优先级以较小值优先来判断，优先级数组高的最终都会被Arena淘汰，只留下优先级最低的结果，若最终匹配出结果不为1，说明匹配失败，Arena将抛出异常。

在自定义优先级处理器之前，先了解一下默认优先级是如何得到的。

默认优先级处理器根据五个过滤器，将优先级分为5个空间，每个过滤器都会得到一个只属于它们的空间，它们只可以对自己的空间范围产生影响。

| 过滤器                | 有效范围掩码 | 基数       | 长度 |
| --------------------- | ------------ | ---------- | ---- |
| SAME_FILTER           | 0            | 0          | 0    |
| SUPER_FILTER          | 0x000000FF   | 0x00000001 | 255  |
| WRAPPER_FILTER        | 0x0000FF00   | 0x00000100 | 255  |
| ARRAY_VARIABLE_FILTER | 0x00FF0000   | 0x00010000 | 255  |
| ARRAY_WRAPPER_FILTER  | 0x7F000000   | 0x01000000 | 127  |

每场比赛的最终优先级，符合这条公式：优先级 = 旧数据 + 过滤器基数 * 过滤器分数。

这里可以看出，每种过滤器都有一个最大的分数值，一旦超过这个值，只能将它当成最大分数值处理，也就是超过这个范围的重载方法，默认过滤器是无法辨别的，这也是需要自定义优先级的原因。当然值得庆幸的是，这种情况一般比较极端，即使出现了，在不定义优先级处理器的情况下，我们还可以直接使用find方法找到目标（如Method），然后再进行调用，我们损失的只是自动根据对象匹配类型的过程。



## 实战

虽然巴拉巴拉介绍了一堆，但如果不能投入使用，这个工具就显得有点多余，于是写了个小Demo来增加工具的说服力。

我们来简单写段热加载的代码，详细代码可在项目Demo中查看。

首先我们使用BFReflect完成加载dex文件的逻辑，可以看到繁琐的反射语句都简化成了一句简简单单的调用代码。

```java
public class Hotfix {
    public static void loadDex(Context context, File patchFile) throws Exception {
        ClassLoader classLoader = context.getClassLoader();
        Object pathList = BFReflect.FIELD.get(classLoader, "pathList");

        if (pathList != null) {
            Object[] dexElements = (Object[]) BFReflect.FIELD.get(pathList, "dexElements");

            if (dexElements == null) return;

            ArrayList<IOException> suppressedExceptions = new ArrayList<>();

            List<File> files = new ArrayList<>();

            files.add(patchFile);

            File optimizedDirectory;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                optimizedDirectory = context.getCodeCacheDir();
            } else {
                optimizedDirectory = context.getCacheDir();
            }

            Object[] patchElements = (Object[]) BFReflect.METHOD.invoke(pathList.getClass(), "makeDexElements",
                    files, optimizedDirectory, suppressedExceptions, classLoader, false);

            Object[] newElements = (Object[]) Array.newInstance(patchElements.getClass().getComponentType(),
                    dexElements.length + patchElements.length);

            System.arraycopy(
                    patchElements, 0, newElements, 0, patchElements.length);
            System.arraycopy(
                    dexElements, 0, newElements, patchElements.length, dexElements.length);

            BFReflect.FIELD.set(pathList, "dexElements", newElements);
        }
    }
}

```

再来看看我们要热加载的类

```java
public class A {
    public String demo() {
        return "success";
    }
}
```

再来看看我们的调用代码。

```java
try {
    Hotfix.loadDex(getApplicationContext(), dexFile);
    Object a = Class.forName("blue.fen.demo.a.A").newInstance();
    Method method = a.getClass().getDeclaredMethod("demo");
    Object result = method.invoke(a);
    toast(result.toString());
} catch (Exception e) {
    toast("加载失败");
    e.printStackTrace();
}
```

运行结果。

![demo](https://img-blog.csdnimg.cn/db7eec1f75f94e18bd7fcb2d613d11f1.gif#pic_center)

接下来就可以愉快的进行开发了，希望可以带给使用者一段不错的体验旅程。

