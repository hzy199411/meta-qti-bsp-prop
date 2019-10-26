inherit autotools qlicense qprebuilt

DESCRIPTION = "libgpt library"

PR = "r1"

FILESEXTRAPATHS_prepend := "${THISDIR}:"

SRC_URI = "file://${PN}"
SRC_DIR = "${THISDIR}/libgpt"

S = "${WORKDIR}/libgpt"
