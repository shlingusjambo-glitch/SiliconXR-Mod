package siliconxr.mod;

/** Forge entry point: runs at mod construction, before Minecraft's first tick (when Vivecraft starts VR). */
@net.minecraftforge.fml.common.Mod("siliconxr")
public class SiliconXRForge {
    public SiliconXRForge() { SiliconXRMod.setup(); }
}
