inherit autotools qcommon qlicense qprebuilt
require ${COREBASE}/meta-qti-bsp-prop/recipes-qti/include/common-location-prop-gps-defines.inc

DESCRIPTION = "xtra daemon application "
PR = "r1"

SRC_URI = "file://gps-noship/xtra-daemon/"
SRC_DIR = "${WORKSPACE}/gps-noship/xtra-daemon/"
S = "${WORKDIR}/gps-noship/xtra-daemon/"

DEPENDS = "izat-core"
DEPENDS += "lbs-core-hdr loc-api-v02 loc-net-iface"
EXTRA_OECONF = "--with-core-includes=${WORKSPACE}/system/core/include \
                --with-locpla-includes=${STAGING_INCDIR}/loc-pla \
                --with-glib"
