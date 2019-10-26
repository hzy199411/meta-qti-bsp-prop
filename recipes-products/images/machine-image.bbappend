# Additional non-open source packages to be put to the root filesystem.
# If product is specified try to include product inc otherwise include base inc.
def get_qti_img_inc_file(d):
    product     = d.getVar('PRODUCT', True)
    basemachine = d.getVar('BASEMACHINE', True)
    if product != 'base' or '':
        inc_file_name = basemachine + "-" + product + "-qti-image.inc"
    else:
        inc_file_name = basemachine + "-" + "base-qti-image.inc"
    img_inc_file_path = os.path.join(d.getVar('THISDIR'), basemachine, inc_file_name)
    if os.path.exists(img_inc_file_path):
        img_inc_file = inc_file_name
    else:
        img_inc_file = basemachine + "-base-qti-image.inc"
    return img_inc_file

require ${BASEMACHINE}/${@get_qti_img_inc_file(d)}

inherit qimage

require internal-image.inc
# Set up for handling the generation of the /usr image
# partition...
require mdm-usr-image.inc

# Set up for handling the generation of the /cache image
# partition...
require mdm-cache-image.inc

# Set up for handling the generation of the /persist image
# partition only for APQ Targets
require apq-persist-image.inc

do_rootfs[nostamp] = "1"
do_build[nostamp]  = "1"

