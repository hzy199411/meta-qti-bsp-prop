inherit cmake qlicense qprebuilt

DESCRIPTION = "Camx"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/proprietary/:"
SRC_URI  = "file://chi-cdk/"
SRC_URI  =+ "file://TOF_API/"
#SRC_URI  =+ "file://ext_header/"

SRC_DIR  = "${WORKSPACE}/vendor/qcom/proprietary/chi-cdk"
S = "${WORKDIR}/chi-cdk/build/cmake/"

DEPENDS += "av-frameworks"
DEPENDS += "system-core"
DEPENDS += "libui"
DEPENDS += "adreno200"
DEPENDS += "fastcv-noship"
DEPENDS += "system-media"
DEPENDS += "libhardware"
DEPENDS += "virtual/kernel"
DEPENDS += "camera-metadata"
DEPENDS += "camx-header"
DEPENDS += "chicdkgenerator"
DEPENDS += "camx"
INSANE_SKIP = "1"

CHI_CDK_PATH = "${WORKDIR}/chi-cdk/"
CHI_VENDOR_PATH = "${WORKDIR}/chi-cdk/vendor/"
CMAKE_OUTPUT_DIR = "${CAMX_PATH}/build/built/cmake"

EXTRA_OECMAKE = "\
    -DCAMXDEBUG:STRING=True \
    -DCMAKE_CROSSCOMPILING:BOOL=True \
    -DKERNEL_INCDIR=${STAGING_KERNEL_BUILDDIR} \
    -DCMAKE_LIBRARY_PATH:PATH=${STAGING_LIBDIR} \
    -DCMAKE_INCLUDE_PATH:PATH=${STAGING_INCDIR} \
    -DCMAKE_SYSROOT:PATH=${STAGING_DIR_HOST} \
    -DCAMX_CDK_PATH:PATH=${WORKDIR}/chi-cdk/cdk/ \
    -DCAMX_INCLUDE_PATH:PATH=${STAGING_INCDIR}/camx/ \
    -DCAMX_VENDOR_PATH:PATH=${WORKDIR}/chi-cdk/vendor/ \
"
do_configure_prepend() {
    find ${CHI_CDK_PATH} ${CHI_VENDOR_PATH} -iname *.cpp -exec sed -i 's/\/vendor\/lib/\/usr\/lib/g' {} +
    find ${CHI_CDK_PATH} ${CHI_VENDOR_PATH} -iname *.h -exec sed -i 's/\/vendor\/lib/\/usr\/lib/g' {} +
    find ${CHI_CDK_PATH} ${CHI_VENDOR_PATH} -iname *.c -exec sed -i 's/\/vendor\/lib/\/usr\/lib/g' {} +
}

do_install_append() {
    install -d ${D}/data/misc/camera/
    install -d ${D}/vendor/etc/camera/
    install -d ${D}/usr/lib/rfsa/adsp/
    install -m 0644 ${CHI_CDK_PATH}/vendor/node/hvx/addconstant/libdsp_streamer_add_constant.so ${D}/usr/lib/rfsa/adsp/libdsp_streamer_add_constant.so
    install -m 0644 ${CHI_CDK_PATH}/vendor/node/hvx/binning/libdsp_streamer_binning.so ${D}/usr/lib/rfsa/adsp/libdsp_streamer_binning.so
    install -d ${D}/data/firmware2/
    cp -rf ${CHI_CDK_PATH}/vendor/node/altekdepthshow/AL6100_FW/* ${D}/data/firmware2/
    install -d ${D}/usr/bin/
}

FILES_${PN} = "\
    /usr/lib/* \
    /usr/bin/* \
    /usr/include/* \
    /data/* \
    /vendor/*"

FILES_${PN}-dev = ""
#Skips check for .so symlinks
INSANE_SKIP_${PN} = "dev-so"

INSANE_SKIP_${PN} += "arch"
