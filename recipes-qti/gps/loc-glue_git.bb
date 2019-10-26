inherit autotools-brokensep pkgconfig qcommon qlicense qprebuilt
require ${COREBASE}/meta-qti-bsp-prop/recipes-qti/include/common-location-prop-gps-defines.inc

DESCRIPTION = "GPS Location Service library"
PR = "r1"

FILESPATH =+ "${WORKSPACE}/gps/framework/native:"
SRC_URI = "file://framework-glue"
SRC_DIR = "${WORKSPACE}/gps/framework/native/framework-glue"
S = "${WORKDIR}/framework-glue"

DEPENDS = "loc-net-iface location-geofence location-flp location-service"
DEPENDS += "lbs-core"
EXTRA_OECONF = "--with-core-includes=${WORKSPACE}/system/core/include \
                --with-locpla-includes=${STAGING_INCDIR}/loc-pla \
                --with-glib"
