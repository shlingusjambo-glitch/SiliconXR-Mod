package siliconxr.mod;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Puts SiliconXR's Apple Silicon OpenVR natives (libopenvr_api.dylib, liblwjgl_openvr.dylib) on LWJGL's library
 * path, before Vivecraft starts VR. Vivecraft then talks to MacVR instead of SteamVR. Does nothing off macOS.
 */
public final class SiliconXRMod {
    private static final String[] NATIVES = {"libopenvr_api.dylib", "liblwjgl_openvr.dylib"};
    private static boolean done;

    public static synchronized void setup() {
        if (done || !System.getProperty("os.name", "").startsWith("Mac")) return;
        done = true;
        try {
            Path dir = Paths.get(System.getProperty("user.home"), "Library", "Caches", "SiliconXR", "natives");
            Files.createDirectories(dir);
            for (String name : NATIVES) {
                try (InputStream in = SiliconXRMod.class.getResourceAsStream("/siliconxr/natives/" + name)) {
                    if (in == null) throw new IOException("missing " + name + " in the SiliconXR jar");
                    Path tmp = Files.createTempFile(dir, name, ".tmp");
                    Files.copy(in, tmp, StandardCopyOption.REPLACE_EXISTING);
                    Files.move(tmp, dir.resolve(name), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
                }
            }
            addLibraryPath(dir.toString());
            System.out.println("[SiliconXR] OpenVR natives ready in " + dir);
        } catch (Exception e) {
            System.err.println("[SiliconXR] setup failed: " + e);
        }
    }

    /** LWJGL reads org.lwjgl.librarypath once, into Configuration.LIBRARY_PATH; update both. */
    private static void addLibraryPath(String dir) throws ReflectiveOperationException {
        String prop = System.getProperty("org.lwjgl.librarypath");
        System.setProperty("org.lwjgl.librarypath", prepend(dir, prop));
        Object cfg = Class.forName("org.lwjgl.system.Configuration").getField("LIBRARY_PATH").get(null);
        Object cur = cfg.getClass().getMethod("get").invoke(cfg);
        if (cur == null || !cur.toString().contains(dir)) cfg.getClass().getMethod("set", Object.class).invoke(cfg, prepend(dir, (String) cur));
    }

    private static String prepend(String dir, String path) {
        if (path == null || path.isEmpty()) return dir;
        return path.contains(dir) ? path : dir + File.pathSeparator + path;
    }
}
