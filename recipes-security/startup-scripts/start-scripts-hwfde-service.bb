inherit qlicense
DESCRIPTION = "Start up script for securemsm fde daemon"

SRC_URI :="file://start_hwfde-daemon"
SRC_URI +="file://hwfde.service"
SRC_URI +="file://hwfde.path"

PR = "r3"

INITSCRIPT_NAME = "hwfde_service"
INITSCRIPT_PARAMS = "start 8 2 3 4 5 . stop 20 0 1 6 ."

inherit update-rc.d systemd pkgconfig

FILES_${PN} += "${systemd_unitdir}/system/"

do_install_append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -m 0755 ${WORKDIR}/start_hwfde-daemon -D ${D}${sysconfdir}/initscripts/${INITSCRIPT_NAME}
        install -d ${D}/etc/systemd/system/
        install -m 0644 ${WORKDIR}/hwfde.service -D ${D}/etc/systemd/system/hwfde.service
        install -m 0644 ${WORKDIR}/hwfde.path -D ${D}/etc/systemd/system/hwfde.path
        install -d ${D}/etc/systemd/system/multi-user.target.wants/
        # enable the service for multi-user.target
        ln -sf /etc/systemd/system/hwfde.path ${D}/etc/systemd/system/multi-user.target.wants/hwfde.path
    else
        install -m 0755 ${WORKDIR}/start_hwfde-daemon -D ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
    fi
}
