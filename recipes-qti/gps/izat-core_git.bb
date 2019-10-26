inherit autotools qcommon qlicense qprebuilt
require ${COREBASE}/meta-qti-bsp-prop/recipes-qti/include/common-location-prop-gps-defines.inc

DESCRIPTION = "Izat Core"
PR = "r1"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://gps-noship/framework/native/core/"
SRC_DIR = "${WORKSPACE}/gps-noship/framework/native/core/"
S = "${WORKDIR}/gps-noship/framework/native/core"

DEPENDS = "lbs-core-hdr loc-api-v02 loc-net-iface"
EXTRA_OECONF ="--with-glib \
               --with-locpla-includes=${STAGING_INCDIR}/loc-pla \
               --with-lbscore-includes=${STAGING_INCDIR}/lbs-core-hdr"

PACKAGES = "${PN}"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
FILES_${PN} = "${libdir}/*"
FILES_${PN} += "/usr/include/*"
FILES_${PN} += "/etc/*"

# The izat-core package contains symlinks that trip up insane
INSANE_SKIP_${PN} = "dev-so"
