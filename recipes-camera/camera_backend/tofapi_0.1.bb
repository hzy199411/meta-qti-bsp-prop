inherit cmake qlicense qprebuilt

DESCRIPTION = "Camx"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/proprietary/:"
SRC_URI  =+ "file://chi-cdk/"
SRC_URI  =+ "file://TOF_API/"

SRC_DIR  = "${WORKSPACE}/vendor/qcom/proprietary/TOF_API"
S = "${WORKDIR}/TOF_API/build/cmake/"
DEPENDS += "camx-header"
DEPENDS += "system-core"
DEPENDS += "libhardware"
DEPENDS += "camera-metadata"
DEPENDS += "glib-2.0"
DEPENDS += "libui"

INSANE_SKIP = "1"

CHI_CDK_PATH = "${WORKDIR}/chi-cdk/cdk/"
CHI_VENDOR_PATH = "${WORKDIR}/TOF_API/"

EXTRA_OECMAKE = "\
                -DCAMXDEBUG:STRING=True \
                -DCMAKE_CROSSCOMPILING:BOOL=True \
                -DKERNEL_INCDIR=${STAGING_KERNEL_BUILDDIR} \
                -DCMAKE_LIBRARY_PATH:PATH=${STAGING_LIBDIR} \
                -DCMAKE_INCLUDE_PATH:PATH=${STAGING_INCDIR} \
                -DCMAKE_SYSROOT:PATH=${STAGING_DIR_HOST} \
                -DCAMX_PATH:PATH=${WORKDIR}/camx/ \
                -DCAMX_CDK_PATH:PATH=${WORKDIR}/chi-cdk/cdk/ \
                -DCAMX_VENDOR_PATH:PATH=${WORKDIR}/TOF_API/ \
"
do_configure_prepend() {
    find ${CHI_CDK_PATH} ${CHI_VENDOR_PATH} -iname *.cpp -exec sed -i 's/\/vendor\/lib/\/usr\/lib/g' {} +
    find ${CHI_CDK_PATH} ${CHI_VENDOR_PATH} -iname *.h -exec sed -i 's/\/vendor\/lib/\/usr\/lib/g' {} +
    find ${CHI_CDK_PATH} ${CHI_VENDOR_PATH} -iname *.c -exec sed -i 's/\/vendor\/lib/\/usr\/lib/g' {} +
}

FILES_${PN} = "\
    /usr/lib/* \
    /usr/bin/* \
    /usr/include/* \
    /usr/share/*"

FILES_${PN}-dev = ""
#Skips check for .so symlinks
INSANE_SKIP_${PN} = "dev-so"
