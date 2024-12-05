package com.iplonk.rpninja.utils.snapshot

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.DeviceConfig.Companion.NEXUS_4
import app.cash.paparazzi.DeviceConfig.Companion.PIXEL_5

/**
 * Represents the devices that we run our snapshot tests on
 */
enum class Device(val deviceConfig: DeviceConfig) {
	SMALL_DEVICE(NEXUS_4),
	LARGE_DEVICE(PIXEL_5),
}