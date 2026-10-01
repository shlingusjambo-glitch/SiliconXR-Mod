package siliconxr.mod;

/** NeoForge entry point: runs at mod construction, before Minecraft's first tick (when Vivecraft starts VR). */
@net.neoforged.fml.common.Mod("siliconxr")
public class SiliconXRNeoForge {
    public SiliconXRNeoForge() { SiliconXRMod.setup(); }
}
