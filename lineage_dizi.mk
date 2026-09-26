#
# Copyright (C) 2024 The LineageOS Project
#
# SPDX-License-Identifier: Apache-2.0
#

# Inherit from those products. Most specific first.
$(call inherit-product, $(SRC_TARGET_DIR)/product/core_64_bit.mk)
TARGET_SUPPORTS_OMX_SERVICE := false
$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base.mk)

# Inherit some common Evolution X stuff.
$(call inherit-product, vendor/lineage/config/common_full_tablet_wifionly.mk)

# Inherit from dizi device
$(call inherit-product, device/xiaomi/dizi/device.mk)

# EvolutionX Config
TARGET_BOOT_ANIMATION_RES := 1600
TARGET_BUILD_APERTURE_CAMERA := true
TARGET_DISABLE_EPPE := true

PRODUCT_NAME := lineage_dizi
PRODUCT_DEVICE := dizi
PRODUCT_MANUFACTURER := Xiaomi
PRODUCT_BRAND := Redmi
PRODUCT_MODEL := 2405CRPFDG

PRODUCT_SYSTEM_NAME := dizi_eea
PRODUCT_SYSTEM_DEVICE := dizi

PRODUCT_BUILD_PROP_OVERRIDES += \
    BuildDesc="dizi_eea-user 16 BP2A.250605.031.A3 OS3.0.303.0.WNSEUXM release-keys" \
    BuildFingerprint=Redmi/dizi_eea/dizi:16/BP2A.250605.031.A3/OS3.0.303.0.WNSEUXM:user/release-keys \
    DeviceName=$(PRODUCT_SYSTEM_DEVICE) \
    DeviceProduct=$(PRODUCT_SYSTEM_NAME)

PRODUCT_GMS_CLIENTID_BASE := android-xiaomi
