// Mod setup on a JVM where LWJGL's Configuration is already initialized (as in game): setup() must still put the
// natives on LWJGL's path, then SiliconXR's OpenVRTest runs. Usage: test/run.sh
public class SetupTest {
    public static void main(String[] a) throws Exception {
        Class.forName("org.lwjgl.system.Configuration");   // LWJGL already read org.lwjgl.librarypath (unset)
        siliconxr.mod.SiliconXRMod.setup();
        OpenVRTest.main(a);
    }
}
