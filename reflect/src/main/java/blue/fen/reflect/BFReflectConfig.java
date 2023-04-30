package blue.fen.reflect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import blue.fen.reflect.param.matching.priority.IParamPriority;
import blue.fen.reflect.param.matching.priority.PriorityProvider;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect <br/>
 * 创建时间：2023/4/30 12:36 （星期日｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：配置类
 */
public class BFReflectConfig {
    public static Builder builder() {
        return new Builder();
    }

    private static void handlerComplete(Builder builder) {
        BusMessage.getInstance().setOpenNLog(builder.openNLog);

        if (builder.isRecovery) {
            PriorityProvider.get().recoveryPriority();
        } else if (builder.priority != null) {
            PriorityProvider.get().setPriority(builder.priority);
        }
    }

    public static class Builder {
        private boolean openNLog = false;
        private boolean isRecovery = false;

        @Nullable
        private IParamPriority priority;

        private Builder() {
        }

        /**
         * 打开N类型调用错误消息的打印
         */
        public Builder openNlog(boolean open) {
            this.openNLog = open;
            return this;
        }

        /**
         * 设置参数优先级处理逻辑
         */
        public Builder setPriority(@NonNull IParamPriority priority) {
            this.priority = priority;
            return this;
        }

        /**
         * 恢复参数优先级处理逻辑
         */
        public Builder recoveryPriority() {
            isRecovery = true;
            return this;
        }

        /**
         * 提交配置信息
         */
        public void complete() {
            handlerComplete(this);
        }
    }
}
