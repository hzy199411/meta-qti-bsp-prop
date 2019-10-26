inherit pkgconfig qlicense qprebuilt

DESCRIPTION = "Camx"

FILESPATH =+ "${WORKSPACE}/vendor/qcom/proprietary/:"
SRC_URI  = "file://camx/"

SRC_DIR  = "${WORKSPACE}/vendor/qcom/proprietary/camx"
#S = "${WORKDIR}/camx/build/infrastructure/cmake/"

INSANE_SKIP = "1"

CAMX_PATH = "${WORKDIR}/camx/"

do_install_append() {
        install -d ${D}/usr/include/
        install -m 0644 ${WORKDIR}/camx/src/utils/camxtypes.h ${D}/usr/include/camxtypes.h
        install -m 0644 ${WORKDIR}/camx/src/osutils/camxmem.h ${D}/usr/include/camxmem.h
        install -m 0644 ${WORKDIR}/camx/src/core/hal/camxcommontypes.h ${D}/usr/include/camxcommontypes.h
        install -m 0644 ${WORKDIR}/camx/src/utils/camxdefs.h ${D}/usr/include/camxdefs.h
        install -m 0644 ${WORKDIR}/camx/src/utils/camxutils.h ${D}/usr/include/camxutils.h
        install -m 0644 ${WORKDIR}/camx/src/core/camxtuningdatamanager.h ${D}/usr/include/camxtuningdatamanager.h
        install -m 0644 ${WORKDIR}/camx/src/osutils/camxosutils.h ${D}/usr/include/camxosutils.h
        install -m 0644 ${WORKDIR}/camx/src/utils/camxdebug.h ${D}/usr/include/camxdebug.h
        install -m 0644 ${WORKDIR}/camx/src/utils/camxdebugprint.h ${D}/usr/include/camxdebugprint.h
        install -m 0644 ${WORKDIR}/camx/src/utils/camxformats.h ${D}/usr/include/camxformats.h
        install -m 0644 ${WORKDIR}/camx/src/core/hal/camxhal3types.h ${D}/usr/include/camxhal3types.h
        install -m 0644 ${WORKDIR}/camx/src/csl/camxcsl.h ${D}/usr/include/camxcsl.h
        install -m 0644 ${WORKDIR}/camx/src/utils/camxincs.h ${D}/usr/include/camxincs.h
        install -m 0644 ${WORKDIR}/camx/src/utils/camxatomic.h ${D}/usr/include/camxatomic.h
        install -m 0644 ${WORKDIR}/camx/src/utils/camxtrace.h ${D}/usr/include/camxtrace.h
}

do_configure_prepend() {
        find ${CAMX_PATH} -iname *.h -exec sed -i 's/\/vendor\/lib/\/usr\/lib/g' {} +
}

FILES_${PN} = "\
    /usr/include/*"

FILES_${PN}-dev = ""
#Skips check for .so symlinks
INSANE_SKIP_${PN} = "dev-so"
