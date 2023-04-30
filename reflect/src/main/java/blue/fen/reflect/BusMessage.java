package blue.fen.reflect;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import blue.fen.reflect.utils.Singleton;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.arena <br/>
 * 创建时间：2023/4/30 13:38 （星期日｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：消息总线
 */
public class BusMessage {
    private static final Singleton<BusMessage> sInstance = new Singleton<BusMessage>() {
        @Override
        protected BusMessage create() {
            return new BusMessage();
        }
    };

    public static BusMessage getInstance() {
        return sInstance.get();
    }

    private boolean openNLog = false;

    public boolean isOpenNLog() {
        return openNLog;
    }

    public void setOpenNLog(boolean openNLog) {
        this.openNLog = openNLog;
    }

    public static final int N_EXCEPTION = 0;

    @Retention(SOURCE)
    @Target({PARAMETER, FIELD, METHOD})
    @IntDef({N_EXCEPTION})
    @interface MessageType {
    }

    public void sendMessage(@MessageType int type, Object obj) {
        switch (type) {
            case N_EXCEPTION:
                handlerNException((Exception) obj);
                break;
            default:
                throw new IllegalArgumentException("没找到" + type + "类型的消息，请选择合适的消息类型");
        }
    }

    private void handlerNException(Exception e) {
        if (isOpenNLog()) {
            e.printStackTrace();
        }
    }
}
