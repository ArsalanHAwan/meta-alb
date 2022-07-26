DESCRIPTION = "LibFCI Example: Command line tool for configuration of PFE"
HOMEPAGE = "https://source.codeaurora.org/external/autobsps32/extra/pfeng"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE-BSD3.txt;md5=6b674f4e7c2e72a1907ad7a7f03b800c"

PR = "r0"

URL ?= "git://source.codeaurora.org/external/autobsps32/extra/pfeng;protocol=https;branch=master"
SRC_URI = "${URL}"
SRCREV = "c9eea81d5026387c8f712a3565f3e41083e2f18f"

S = "${WORKDIR}/git"
MDIR = "${S}/sw/libfci_cli"

# Workaround for makefile.
# The makefile is unfortunately not properly prepared to be ran by YOCTO (no option to provide sysroot for toolchain).
# Therefore, the sysroot is prepended to "CCFLAGS:all" for compiler, and to "LDFLAGS:all" for linker.
# Those symbols are recognized by the makefile and should not collide with YOCTO symbols.
CCFLAGS:all = "--sysroot=\"${STAGING_DIR_HOST}\""
LDFLAGS:all = "--sysroot=\"${STAGING_DIR_HOST}\""
SYSROOT_WORKAROUND = "CCFLAGS:all=${CCFLAGS:all} LDFLAGS:all=${LDFLAGS:all}"

CFLAGS:prepend = "-I${S} "

PACKAGES = "${PN} ${PN}-dbg"
FILES:${PN} += "${bindir}/libfci_cli"

RDEPENDS:${PN} = "pfe"
RDEPENDS:${PN}-dbg = "pfe"

do_compile() {
	cd ${MDIR}
	${SYSROOT_WORKAROUND} ${MAKE} TARGET_OS=LINUX PLATFORM=${TARGET_SYS} all
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${MDIR}/libfci_cli ${D}${bindir}
}

COMPATIBLE_MACHINE = "s32g"
