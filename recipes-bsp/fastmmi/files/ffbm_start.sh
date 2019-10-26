#!/bin/sh
# Copyright (c) 2018 Qualcomm Technologies, Inc.
# All Rights Reserved.
# Confidential and Proprietary - Qualcomm Technologies, Inc.

ffbm_start="`cat /dev/sda3`"

if [ "${ffbm_start:0:4}" != "ffbm" ]
then
        echo "not ffbm mode" > /data/ffbm_start.log
else
        echo "ffbm mode" > /data/ffbm_start.log
        systemctl start ffbm_mmi.service
fi


