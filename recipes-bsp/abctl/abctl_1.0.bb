inherit autotools qlicense qprebuilt

DESCRIPTION = "abctl library and utility."

PR = "r1"

DEPENDS += "libgpt oem-recovery libsparse libcutils"

FILESEXTRAPATHS_prepend := "${THISDIR}:"

SRC_URI = "file://${PN}"
SRC_DIR = "${THISDIR}/abctl"

S = "${WORKDIR}/abctl"
