inherit autotools qcommon qlicense qprebuilt
require ${COREBASE}/meta-qti-bsp-prop/recipes-qti/include/common-location-prop-gps-defines.inc

DESCRIPTION = "GPS Garden test shippable app for LOC API HAL"
PR = "r5"

SRC_URI = "file://gps/garden-app/"
SRC_DIR = "${WORKSPACE}/gps/garden-app/"
S = "${WORKDIR}/gps/garden-app"

DEPENDS = "location-api loc-glue location-client-api location-hal-daemon"
EXTRA_OECONF = "--with-core-includes=${WORKSPACE}/system/core/include \
                --with-locationapi-includes=${STAGING_INCDIR}/location-api-iface \
                --with-locpla-includes=${STAGING_INCDIR}/loc-pla \
                --with-glib"

PACKAGES = "${PN}"
FILES_${PN} += "${libdir}/*"
FILES_${PN} += "/usr/include/*"

INSANE_SKIP_${PN} = "dev-deps"

do_install_append () {
    chmod 6755 ${D}${bindir}/garden_app
}