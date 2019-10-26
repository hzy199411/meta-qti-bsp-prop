inherit autotools-brokensep pkgconfig qcommon qlicense qprebuilt
require ${COREBASE}/meta-qti-bsp-prop/recipes-qti/include/common-location-prop-gps-defines.inc

DESCRIPTION = "GPS LBS Core"
PR = "r1"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://gps/framework/native/core/"
SRC_DIR = "${WORKSPACE}/gps/framework/native/core/"
S = "${WORKDIR}/gps/framework/native/core"

DEPENDS = "qmi-framework gps-utils loc-core loc-api-v02 loc-base-util loc-flp-hdr izat-core"
DEPENDS += "lbs-core-hdr loc-net-iface"
EXTRA_OECONF = "--with-core-includes=${WORKSPACE}/system/core/include \
                --with-locpla-includes=${STAGING_INCDIR}/loc-pla \
                --with-locflp-includes=${STAGING_INCDIR}/loc-flp-hdr \
                --with-glib"

