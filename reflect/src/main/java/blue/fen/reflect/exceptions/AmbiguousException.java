package blue.fen.reflect.exceptions;

import java.lang.reflect.InvocationTargetException;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.exceptions <br/>
 * 创建时间：2023/4/29 19:51 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：当（方法/构造函数）无法确定匹配对象时，抛出该异常。
 */
public class AmbiguousException extends InvocationTargetException {
    public AmbiguousException(String message) {
        super(new RuntimeException(), message);
    }
}
