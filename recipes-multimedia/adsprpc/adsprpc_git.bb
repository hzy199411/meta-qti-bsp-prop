inherit qcommon qlicense qprebuilt update-rc.d systemd pkgconfig

SUMMARY = "adsprpc daemon"

DEPENDS += "system-core"

FILESPATH =+ "${WORKSPACE}/:"
SRC_URI   = "file://adsprpc"
SRC_URI  += "file://start_adsprpcd"
SRC_URI  += "file://start_sdsprpcd"
SRC_URI  += "file://start_mdsprpcd"
SRC_URI  += "file://start_cdsprpcd"
SRC_URI  += "file://adsprpcd.service"
SRC_URI  += "file://sdsprpcd.service"
SRC_URI  += "file://mdsprpcd.service"
SRC_URI  += "file://cdsprpcd.service"
SRC_URI  += "file://non-root_start_adsprpcd"

# Enable cdsp at boot
SRC_URI += "file://cdsp.service"
SRC_URI += "file://cdsp.sh"

SRC_DIR   = "${WORKSPACE}/adsprpc"
S  = "${WORKDIR}/adsprpc"
PR = "r1"

EXTRA_OECONF_apq8096 += "--enable-sdsprpc"
EXTRA_OECONF_apq8009 += "--enable-mdsprpc"
EXTRA_OECONF_sda845  += "--enable-sdsprpc"
EXTRA_OECONF_sda845  += "--enable-cdsprpc"

INITSCRIPT_PACKAGES         = "${PN}"
INITSCRIPT_PACKAGES_apq8096 = "${PN} ${PN}-sdsp"
INITSCRIPT_PACKAGES_sda845 = "${PN} ${PN}-sdsp"

INITSCRIPT_NAME_${PN}         = "adsprpcd"
INITSCRIPT_NAME_${PN}_apq8009 = "mdsprpcd"
INITSCRIPT_PARAMS_${PN}       = "start 70 2 3 4 5 S . stop 30 0 1 6 ."
INITSCRIPT_NAME_${PN}-sdsp    = "sdsprpcd"
INITSCRIPT_PARAMS_${PN}-sdsp  = "start 70 2 3 4 5 . stop 69 0 1 6 ."
INITSCRIPT_NAME_${PN}_sda845  = "cdsprpcd"


SYSTEMD_SERVICE_${PN}          = "${INITSCRIPT_NAME_${PN}}.service"
SYSTEMD_SERVICE_${PN}_apq8096 += "sdsprpcd.service"
SYSTEMD_SERVICE_${PN}_apq8096 += "adsprpcd.service"

SYSTEMD_SERVICE_${PN}_sda845 += "sdsprpcd.service"
SYSTEMD_SERVICE_${PN}_sda845 += "adsprpcd.service"
SYSTEMD_SERVICE_${PN}_sda845 += "cdsprpcd.service"
SYSTEMD_SERVICE_${PN}_sda845 += "cdsp.service"

do_install_append () {
    install -m 0755 ${WORKDIR}/start_${INITSCRIPT_NAME_${PN}} -D ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME_${PN}}
    # Install systemd unit files
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/${INITSCRIPT_NAME_${PN}}.service ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/sdsprpcd.service ${D}${systemd_unitdir}/system
}
do_install_append_apq8053() {
    install -m 0755 ${WORKDIR}/non-root_start_${INITSCRIPT_NAME_${PN}} -D ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME_${PN}}
    # Install systemd unit files
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/${INITSCRIPT_NAME_${PN}}.service ${D}${systemd_unitdir}/system
}

do_install_append_apq8096 () {
    install -m 0755 ${WORKDIR}/start_sdsprpcd -D ${D}${sysconfdir}/init.d/sdsprpcd
    install -m 0755 ${WORKDIR}/start_adsprpcd -D ${D}${sysconfdir}/init.d/adsprpcd

    # Install systemd unit files
    install -m 0644 ${WORKDIR}/sdsprpcd.service ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/adsprpcd.service ${D}${systemd_unitdir}/system
}

do_install_append_sda845 () {
    install -m 0755 ${WORKDIR}/start_sdsprpcd -D ${D}${sysconfdir}/init.d/sdsprpcd
    install -m 0755 ${WORKDIR}/start_adsprpcd -D ${D}${sysconfdir}/init.d/adsprpcd
    install -m 0755 ${WORKDIR}/start_cdsprpcd -D ${D}${sysconfdir}/init.d/cdsprpcd

    # Install systemd unit files
    install -m 0644 ${WORKDIR}/sdsprpcd.service ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/adsprpcd.service ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/cdsprpcd.service ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/cdsp.service     ${D}${systemd_unitdir}/system
    install -m 0744 ${WORKDIR}/cdsp.sh          ${D}${bindir}
}

PACKAGES_append_apq8096 = " ${PN}-sdsp"
PACKAGES_append_sda845 = " ${PN}-sdsp"

FILES_${PN}-sdsp-dbg  = "${libdir}/.debug/libsdsp* ${bindir}/.debug/sdsprpcd"
FILES_${PN}-sdsp = "${sysconfdir}/init.d/sdsprpcd ${bindir}/sdsprpcd ${libdir}/libsdsp*.so ${libdir}/libsdsp*.so.*"

FILES_${PN}-dbg = "${libdir}/.debug/* ${bindir}/.debug/*"
FILES_${PN}-dev = "${libdir}/*.la ${includedir}"

FILES_${PN}      = "${libdir}/libadsp*.so ${libdir}/libadsp*.so.* ${bindir}/adsprpcd"
FILES_${PN}     += "${libdir}/libsdsp*.so ${libdir}/libsdsp*.so.* ${bindir}/sdsprpcd"
FILES_${PN}     += "${libdir}/libmdsp*.so ${libdir}/libmdsp*.so.* ${bindir}/mdsprpcd"
FILES_${PN}     += "${libdir}/libcdsp*.so ${libdir}/libcdsp*.so.* ${bindir}/cdsprpcd"
FILES_${PN}     += "${bindir}/cdsp.sh"

FILES_${PN}     += "${libdir}/pkgconfig/  ${systemd_unitdir}/system/*"
FILES_${PN}     += "${sysconfdir}/* ${bindir}"
