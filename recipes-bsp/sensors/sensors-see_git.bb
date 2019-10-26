inherit qcommon qprebuilt qlicense autotools-brokensep

DESCRIPTION = "Sensors-see Library"
LICENSE = "Qualcomm-Technologies-Inc.-Proprietary"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta-qti-bsp-prop/files/qcom-licenses/\
Qualcomm-Technologies-Inc.-Proprietary;md5=92b1d0ceea78229551577d4284669bb8"

DEPENDS = "liblog"
DEPENDS += "libcutils"
DEPENDS += "libutils"
DEPENDS += "system-core"
DEPENDS += "common-headers"
DEPENDS += "glib-2.0"
DEPENDS += "qmi-framework"
DEPENDS += "diag"
DEPENDS += "libhardware"
DEPENDS += "protobuf"
DEPENDS += "protobuf-native"
DEPENDS += "sensors-see-commonsys-intf"

SRC_DIR = "${WORKSPACE}/vendor/qcom/proprietary/sensors-see"
S = "${WORKDIR}/vendor/qcom/proprietary/sensors-see"
SRC_URI += "file://sensors-see-daemon.service"

CFLAGS += "-I${STAGING_INCDIR}/cutils"
CFLAGS += "-I${STAGING_INCDIR}/utils"
CFLAGS += "-I${STAGING_LIBDIR}/glib-2.0/include"
CFLAGS += "-I${WORKSPACE}/system/core/include"
CPPFLAGS += "-DGOOGLE_PROTOBUF_NO_RTTI"

EXTRA_OECONF += " --with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include"
EXTRA_OECONF += " --with-glib"
EXTRA_OECONF += " --with-common-includes=${STAGING_INCDIR}"
EXTRA_OECONF += " --enable-sns-le-qcs605"

EXTRA_OEMAKE += " 'CC=${CC}' 'CFLAGS=${CFLAGS}'"

do_install_append() {
    install -d ${D}/vendor/etc/sensors/config
    install -m 0644 ${S}/ssc/registry/config/*.json -D ${D}/vendor/etc/sensors/config
    install -m 0644 ${S}/ssc/registry/sns_reg_config_le -D ${D}/vendor/etc/sensors/sns_reg_config

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_unitdir}/system/
        install -m 0644 ${WORKDIR}/sensors-see-daemon.service -D ${D}${systemd_unitdir}/system/sensors-see-daemon.service
        install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/
        # enable the service for multi-user.target
        ln -sf ${systemd_unitdir}/system/sensors-see-daemon.service \
             ${D}${systemd_unitdir}/system/multi-user.target.wants/sensors-see-daemon.service
    fi
}

#Disable the split of debug information into -dbg files
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

#Skips check for .so symlinks
INSANE_SKIP_${PN} = "dev-so"

# need to export these variables for python-config to work
FILES_${PN} += "${includedir}/*"
FILES_${PN} += "/usr/lib/*"
FILES_${PN} += "/usr/lib64/*"
FILES_${PN} += "/vendor/etc/sensors/*"
FILES_${PN} += "/persist/sensors/*"
FILES_${PN} += "${systemd_unitdir}/system/"
FILES_${PN}-dev  = "${libdir}/*.la ${includedir}"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
