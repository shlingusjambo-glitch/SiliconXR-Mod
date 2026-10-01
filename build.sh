#!/bin/sh
# Builds build/siliconxr.jar: one mod jar for Fabric, Quilt, Forge and NeoForge that carries SiliconXR's
# Apple Silicon OpenVR natives (from ../SiliconXR/build, or SILICONXR_BUILD) and puts them on LWJGL's library path.
# Needs a JDK (javac, jar); Java 17 bytecode so it loads on Minecraft 1.18+.
set -e
cd "$(dirname "$0")"
NAT="${SILICONXR_BUILD:-../SiliconXR/build}"
JAVAC="${JAVAC:-javac}"; JAR="${JAR:-$(dirname "$(command -v "$JAVAC")")/jar}"
for f in libopenvr_api.dylib liblwjgl_openvr.dylib; do [ -f "$NAT/$f" ] || { echo "missing $NAT/$f (run ../SiliconXR/build.sh)"; exit 1; }; done
rm -rf build/classes build/stubs build/siliconxr.jar; mkdir -p build/classes/siliconxr/natives build/stubs
"$JAVAC" --release 17 -d build/stubs $(find stubs -name '*.java')
"$JAVAC" --release 17 -cp build/stubs -d build/classes $(find src -name '*.java')
cp -R resources/. build/classes/
cp "$NAT/libopenvr_api.dylib" "$NAT/liblwjgl_openvr.dylib" build/classes/siliconxr/natives/
"$JAR" --create --file build/siliconxr.jar -C build/classes .
echo "built build/siliconxr.jar"
