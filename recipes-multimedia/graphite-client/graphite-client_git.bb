inherit qcommon qprebuilt qlicense

DESCRIPTION = "Graphite-client Library"
PR = "r0"

DEPENDS = "liblog libcutils expat"

SRC_DIR = "${WORKSPACE}/audio/mm-audio/graphite-client/"
S = "${WORKDIR}/audio/mm-audio/graphite-client/"

EXTRA_OECONF += "--with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include"

PACKAGES = "${PN}"
FILES_${PN} = "${libdir}/*.so ${libdir}/*.so.* ${libdir}/pkgconfig/*"

#Skips check for .so symlinks
INSANE_SKIP_${PN} = "dev-so"
