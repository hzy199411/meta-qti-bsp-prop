inherit qcommon qprebuilt qlicense pkgconfig autotools

DESCRIPTION = "Audio Calibration Library"
PR = "r11"

TARGETNAME = "${@d.getVar('BASEMACHINE', True).replace('mdm','').replace('apq','')}"
SRC_DIR = "${WORKSPACE}/audio/mm-audio-cal/audcal/"

S = "${WORKDIR}/audio/mm-audio-cal/audcal/"

DEPENDS = "glib-2.0 audioalsa diag common"

EXTRA_OECONF += "--with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include \
                 --with-glib \
                 --enable-target=${BASEMACHINE}"

do_install_append() {
    if [ -d ${S}/acdbdata/${TARGETNAME}/MTP/ ];then
        mkdir -p ${D}${sysconfdir}/
        install -m 0755 ${S}/acdbdata/${TARGETNAME}/MTP/*  -D ${D}${sysconfdir}
    fi
}

do_install_append_apq8053() {
    mkdir -p -m 0755 ${D}${sysconfdir}/acdbdata/MTP
    if [ "${MACHINE}" == "apq8053-compact" ]; then
        cp -r ${S}/acdbdata/8953/MTP/compact-concam/* ${D}${sysconfdir}/acdbdata/MTP/
    else
        cp -r ${S}/acdbdata/8953/MTP/* ${D}${sysconfdir}/acdbdata/MTP/
    fi
}

do_install_append_apq8098() {
    mkdir -p -m 0755 ${D}${sysconfdir}/acdbdata/MTP
    cp -r ${S}/acdbdata/8998/MTP/* ${D}${sysconfdir}/acdbdata/MTP/
}

do_install_append_apq8096() {
    mkdir -p -m 0755 ${D}${sysconfdir}/acdbdata/MTP
    cp -r ${S}/acdbdata/8996/MTP/* ${D}${sysconfdir}/acdbdata/MTP/
}

do_install_append_sda845() {
    mkdir -p -m 0755 ${D}${sysconfdir}/acdbdata/MTP
    cp -r ${S}/acdbdata/sdm845/MTP/* ${D}${sysconfdir}/acdbdata/MTP/
}
