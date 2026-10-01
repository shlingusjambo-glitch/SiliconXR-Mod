package siliconxr.mod;

/** Fabric / Quilt entry point (fabric.mod.json "preLaunch"). */
public class SiliconXRFabric implements net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() { SiliconXRMod.setup(); }
}
