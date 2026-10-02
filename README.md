# SiliconXR Mod

Vivecraft on Apple Silicon Macs, with no SteamVR. Minecraft's VR goes through
[MacVR](https://github.com/shlingusjambo-glitch/MacVR) to your Quest.

Vivecraft talks OpenVR through LWJGL, and LWJGL has no Apple Silicon OpenVR natives. This mod carries them from
[SiliconXR](https://github.com/shlingusjambo-glitch/SiliconXR) and puts them on LWJGL's library path at startup.
One jar works on **Fabric, Quilt, Forge and NeoForge**. It does nothing on other platforms.

## Use

MacVR installs this mod automatically next to Vivecraft. To install it by hand, drop `siliconxr.jar` into your instance's
`mods/` folder next to Vivecraft. Start MacVR, connect your headset, then enable VR in game.

## Build

```sh
SILICONXR_BUILD=/path/to/SiliconXR/build ./build.sh   # -> build/siliconxr.jar (needs a JDK 17+)
```

The default `SILICONXR_BUILD` is `../SiliconXR/build`. Loader APIs are stubbed at compile time (`stubs/`), so no
Minecraft or loader dependencies are needed.

`test/run.sh` loads the jar with LWJGL already initialized, as it is in game, and runs SiliconXR's OpenVR test through it. It uses a temporary MacVR shared memory, so MacVR does not need to be running.

## What you get in game

- Frames go to MacVR without stalling Minecraft's render thread on the GPU.
- Vivecraft skips drawing the lens corners you can't see, using the hidden area mesh.
- Haptics reach both hands, including when both buzz at once.
- Vivecraft re-reads the controller aim when you switch between controllers and hand tracking.
- Skeletal hand data is available to mods that read OpenVR skeletal input. Vivecraft 1.3.x does not read it.
