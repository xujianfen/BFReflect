package blue.fen.demo;

import android.content.Context;
import android.os.Build;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import blue.fen.reflect.BFReflect;

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
