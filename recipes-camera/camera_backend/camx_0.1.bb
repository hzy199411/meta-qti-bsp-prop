inherit cmake pkgconfig qlicense qprebuilt

DESCRIPTION = "Camx"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/proprietary/:"
SRC_URI  = "file://camx/"
SRC_URI  =+ "file://camx-lib/"
SRC_URI  =+ "file://camx-lib-3a/"
SRC_URI  =+ "file://camx-lib-stats/"
SRC_URI  =+ "file://chi-cdk/"

SRC_DIR  = "${WORKSPACE}/vendor/qcom/proprietary/camx"
S = "${WORKDIR}/camx/build/infrastructure/cmake/"

DEPENDS += "av-frameworks"
DEPENDS += "system-core"
DEPENDS += "libui"
DEPENDS += "adreno200"
DEPENDS += "fastcv-noship"
DEPENDS += "system-media"
DEPENDS += "libhardware"
DEPENDS += "virtual/kernel"
DEPENDS += "camera-metadata"
DEPENDS += "sensors-see"
DEPENDS += "chicdkgenerator"
DEPENDS += "camera-metadata"
DEPENDS += "media-headers"
DEPENDS += "media"
RDEPENDS_${PN} += "media"

INSANE_SKIP = "1"

CAMX_PATH = "${WORKDIR}/camx/"
CHI_CDK_PATH = "${WORKDIR}/chi-cdk/"
CAMX_LIB_PATH = "${WORKDIR}/camx-lib/"
CAMX_LIB_3A_PATH = "${WORKDIR}/camx-lib-3a/"
CAMX_LIB_STATS_PATH = "${WORKDIR}/camx-lib-stats/"
CAMX_TOOLS_PATH = "${WORKDIR}/camx/build/infrastructure/tools/"
CMAKE_OUTPUT_DIR = "${CAMX_PATH}/build/built/cmake"
CAMX_SYSTEM_PATH = "${WORKDIR}/camx-lib/system"

EXTRA_OECMAKE = "\
                -DCAMXDEBUG:STRING=True \
                -DCMAKE_CROSSCOMPILING:BOOL=True \
                -DKERNEL_INCDIR=${STAGING_KERNEL_BUILDDIR} \
                -DCMAKE_LIBRARY_PATH:PATH=${STAGING_LIBDIR} \
                -DCMAKE_INCLUDE_PATH:PATH=${STAGING_INCDIR} \
                -DCMAKE_SYSROOT:PATH=${STAGING_DIR_HOST} \
                -DCAMX_PATH:PATH=${WORKDIR}/camx/ \
                -DCAMX_EXT_INCLUDE:PATH=${WORKDIR}/ext_header \
                -DLINUX_LE:BOOL=True \
"

do_configure_prepend() {
        perl ${CAMX_TOOLS_PATH}/settingsgenerator/settingsgenerator.pl \
             ${CAMX_PATH}/src/core/camxsettings.xml \
             ${CAMX_PATH}/src/hwl/titan17x/camxtitan17xsettings.xml \
             ${CAMX_PATH}/build/built/settings/camxoverridesettings.txt

        perl ${CAMX_TOOLS_PATH}/version.pl \
             ${CAMX_PATH}/src/core/g_camxversion.h

        perl ${CAMX_TOOLS_PATH}/props.pl \
             ${CAMX_PATH}/src/core/camxproperties.xml \
             ${CAMX_PATH}/src/core/g_camxproperties

        sed -i '/${CAMX_CDK_PATH}\/generated\/build\/cmake/d' ${CAMX_PATH}/build/infrastructure/cmake/CMakeLists.txt
        find ${CAMX_PATH} ${CHI_CDK_PATH} ${CAMX_LIB_PATH} ${CAMX_LIB_3A_PATH} ${CAMX_LIB_STATS_PATH} -iname *.cpp -exec sed -i 's/\/vendor\/lib/\/usr\/lib/g' {} +
        find ${CAMX_PATH} ${CHI_CDK_PATH} ${CAMX_LIB_PATH} ${CAMX_LIB_3A_PATH} ${CAMX_LIB_STATS_PATH} -iname *.h -exec sed -i 's/\/vendor\/lib/\/usr\/lib/g' {} +
        find ${CAMX_PATH} ${CHI_CDK_PATH} ${CAMX_LIB_PATH} ${CAMX_LIB_3A_PATH} ${CAMX_LIB_STATS_PATH} -iname *.c -exec sed -i 's/\/vendor\/lib/\/usr\/lib/g' {} +
}



do_install_append() {
        install -d ${D}/lib/firmware/
        install -d ${D}/usr/lib/rfsa/adsp/
        install -m 0755 ${CAMX_PATH}/src/hwl/dspinterfaces/libdsp_streamer_skel.so ${D}/usr/lib/rfsa/adsp/libdsp_streamer_skel.so
#       install -m 0755 ${CAMX_SYSTEM_PATH}/firmware/CAMERA_ICP_AAAAAA.elf ${D}/lib/firmware/CAMERA_ICP.elf
#       compatible lib64 compile
        cp ${CAMX_SYSTEM_PATH}/firmware/CAMERA_ICP_AAAAAA.elf ${D}/lib/firmware/CAMERA_ICP.elf
        chmod 0755 ${D}/lib/firmware/CAMERA_ICP.elf
}

FILES_${PN} = "\
    /usr/lib/* \
    /usr/bin/* \
    /usr/include/* \
    /data/* \
    /lib/firmware/* \
    /vendor/*"

FILES_${PN}-dev = ""
#Skips check for .so symlinks
INSANE_SKIP_${PN} = "dev-so"
INSANE_SKIP_${PN} += "arch"
