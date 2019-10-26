inherit cmake qlicense qprebuilt

DESCRIPTION = "Camx"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/proprietary/:"
SRC_URI  = "file://chi-cdk/"

SRC_DIR  = "${WORKSPACE}/vendor/qcom/proprietary/chi-cdk"
S = "${WORKDIR}/chi-cdk/"

DEPENDS += "camx-header"
DEPENDS += "system-core"
DEPENDS += "libhardware"
DEPENDS += "camera-metadata"
DEPENDS += "glib-2.0"
DEPENDS += "libui"

INSANE_SKIP = "1"

CHI_CDK_PATH = "${WORKDIR}/chi-cdk/cdk/"
CHI_VENDOR_PATH = "${WORKDIR}/chi-cdk/vendor/"

EXTRA_OECMAKE = "\
                -DCAMXDEBUG:STRING=True \
                -DCMAKE_CROSSCOMPILING:BOOL=True \
                -DKERNEL_INCDIR=${STAGING_KERNEL_BUILDDIR} \
                -DCMAKE_LIBRARY_PATH:PATH=${STAGING_LIBDIR} \
                -DCMAKE_INCLUDE_PATH:PATH=${STAGING_INCDIR} \
                -DCMAKE_SYSROOT:PATH=${STAGING_DIR_HOST} \
                -DCAMX_PATH:PATH=${WORKDIR}/camx/ \
                -DCAMX_CDK_PATH:PATH=${WORKDIR}/chi-cdk/cdk/ \
                -DCAMX_VENDOR_PATH:PATH=${WORKDIR}/chi-cdk/vendor/ \
"

do_configure_prepend() {
    python ${CHI_CDK_PATH}/tools/buildbins.py --bin-path=${CHI_CDK_PATH}/generated/lib/camera/
    find ${CHI_CDK_PATH} ${CHI_VENDOR_PATH} -iname *.cpp -exec sed -i 's/\/vendor\/lib/\/usr\/lib/g' {} +
    find ${CHI_CDK_PATH} ${CHI_VENDOR_PATH} -iname *.h -exec sed -i 's/\/vendor\/lib/\/usr\/lib/g' {} +
    find ${CHI_CDK_PATH} ${CHI_VENDOR_PATH} -iname *.c -exec sed -i 's/\/vendor\/lib/\/usr\/lib/g' {} +
}
do_install_append() {
    mkdir -p ${D}${libdir}/camera/
    cp -rf ${CHI_CDK_PATH}/generated/lib/camera/* ${D}${libdir}/camera/
}

FILES_${PN} = "\
    /usr/lib/* \
    /usr/bin/* \
    /usr/include/* \
    /usr/share/*"

FILES_${PN}-dev = ""
#Skips check for .so symlinks
INSANE_SKIP_${PN} = "dev-so"
