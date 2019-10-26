inherit autotools-brokensep pkgconfig update-rc.d qprebuilt systemd

DESCRIPTION = "PD mapper"
LICENSE = "Qualcomm-Technologies-Inc.-Proprietary"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta-qti-bsp-prop/files/qcom-licenses/\
${LICENSE};md5=92b1d0ceea78229551577d4284669bb8"

PR = "r1"
PACKAGES = "${PN}"
FILESPATH =+ "${WORKSPACE}:"

SRC_URI = "file://ss-services"
SRC_URI += "file://pdmapper.service"
SRC_DIR = "${WORKSPACE}/ss-services/"
S = "${WORKDIR}/ss-services"

DEPENDS = "glib-2.0 json-c system-core qmi qmi-framework libcutils"

CPPFLAGS += "-I${WORKSPACE}/qmi-framework/inc"
CPPFLAGS += "-I${WORKSPACE}/system-core/"

INITSCRIPT_NAME = "start_pdmappersvc"
INITSCRIPT_PARAMS = "start 90 2 3 4 5 . stop 10 0 1 6 ."
INITSCRIPT_SERVICE = "pdmapper.service"

EXTRA_OEMAKE = "DEFAULT_INCLUDES= CFLAGS="-I. -I${WORKSPACE}/ss-services/pd-mapper/pd-mapper-idl/ -I${STAGING_INCDIR}/cutils/ -I${STAGING_INCDIR}/qmi/ -I${WORKDIR}/ss-services/pd-mapper/pd-mapper-svc -I${WORKDIR}/ss-services/pd-notifier/pd-notifier-idl/ -I${WORKSPACE}/ss-services/pd-mapper/libpdmapper/ -I${WORKSPACE}/ss-services/pd-notifier/libpdnotifier/ -I${STAGING_KERNEL_BUILDDIR}/usr/include""
EXTRA_OEMAKE += "DEFAULT_INCLUDES= CPPFLAGS="-I. -I${WORKSPACE}/ss-services/pd-mapper/pd-mapper-idl/ -I${STAGING_INCDIR}/qmi-framework/ -I${STAGING_INCDIR}/qmi/ -I${STAGING_INCDIR}/json-c/ -I${STAGING_INCDIR}/cutils/ -I${WORKSPACE}/pd-notifier/pd-notifier-idl/ -I${WORKSPACE}/ss-services/pd-mapper/libpdmapper/ -I${WORKSPACE}/ss-services/pd-notifier/libpdnotifier/ -I${WORKSPACE}/system/core/include/""

do_install_append() {
	if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
		install -m 0755 ${S}/pd-mapper/${INITSCRIPT_NAME} -D ${D}${sysconfdir}/initscripts/${INITSCRIPT_NAME}
		install -d ${D}${sysconfdir}/systemd/system/
		install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants/
		install -m 0644 ${WORKDIR}/${INITSCRIPT_SERVICE} -D ${D}${sysconfdir}/systemd/system/${INITSCRIPT_SERVICE}
		ln -sf ${sysconfdir}/systemd/system/${INITSCRIPT_SERVICE} \
		${D}${sysconfdir}/systemd/system/multi-user.target.wants/${INITSCRIPT_SERVICE}
	else
		install -m 0755 ${S}/pd-mapper/${INITSCRIPT_NAME} -D ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
	fi
}

