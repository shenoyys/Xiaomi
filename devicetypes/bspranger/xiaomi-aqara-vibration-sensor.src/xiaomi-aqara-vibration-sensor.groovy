/**
 *  Xiaomi Aqara Vibration Sensor
 *  Model DJT11LM
 *  Version 0.65b
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Original device handler code by a4refillpad, adapted for use with Aqara model by bspranger
 *  Additional contributions to code by alecm, alixjg, bspranger, gn0st1c, foz333, jmagnuson, rinkek, ronvandegraaf, snalee, tmleafs, twonk, & veeceeoh
 *
 *  Known issues:
 *  Xiaomi sensors do not seem to respond to refresh requests
 *  Inconsistent rendering of user interface text/graphics between iOS and Android devices - This is due to SmartThings, not this device handler
 *  Pairing Xiaomi sensors can be difficult as they were not designed to use with a SmartThings hub. See
 *
 */
metadata {
	definition (name: "Xiaomi Aqara Vibration Sensor", namespace: "bspranger", author: "bspranger") {
		capability "Acceleration Sensor"
		capability "Battery"
		capability "Button"
		capability "Configuration"
		capability "Health Check"
		capability "Sensor"
		capability "Three Axis"

		attribute "accelSensitivity", "String"
		attribute "batteryRuntime", "String"
		attribute "lastCheckin", "String"
		attribute "lastCheckinCoRE", "Date"
		attribute "lastDrop", "String"
		attribute "lastDropCoRE", "Date"
		attribute "lastStationary", "String"
		attribute "lastStationaryCoRE", "Date"
		attribute "lastTilt", "String"
		attribute "lastTiltCoRE", "Date"
		attribute "lastVibration", "String"
		attribute "lastVibrationCoRE", "Date"
		attribute "tiltAngle", "String"
		attribute "sensorStatus", "enum", ["vibrating", "tilted", "dropped", "-"]
		attribute "vibrationLevel", "String"

		fingerprint endpointId: "01", profileId: "0104", deviceId: "000A", inClusters: "0000,0003,0019,0101", outClusters: "0000,0004,0003,0005,0019,0101", manufacturer: "LUMI", model: "lumi.vibration.aq1", deviceJoinName: "Xiaomi Aqara Vibration Sensor"

		command "resetBatteryRuntime"
	}

	simulator {
	}

	tiles(scale: 2) {
		// Motion used for sensor vibration/shake events
		multiAttributeTile(name:"sensorStatus", type: "generic", width: 6, height: 4) {
			tileAttribute("device.sensorStatus", key: "PRIMARY_CONTROL") {
				attributeState "default", label:'Vibration Detected', icon:"st.motion.motion.active", backgroundColor:"#00a0dc"
				attributeState "vibrating", label:'Vibration Detected', icon:"st.motion.motion.active", backgroundColor:"#00a0dc"
				attributeState "tilted", label:'Tilt Detected', icon:"st.motion.motion.active", backgroundColor:"#00a0dc"
				attributeState "dropped", label:'Drop Detected', icon:"st.motion.motion.active", backgroundColor:"#00a0dc"
				attributeState "-", label:'', icon:"st.motion.motion.inactive", backgroundColor:"#ffffff"
			}
			tileAttribute("device.vibrationLevel", key: "SECONDARY_CONTROL") {
				attributeState("vibrationLevel", label:'Last Vibration Level: ${currentValue}')
			}
		}
		valueTile("battery", "device.battery", decoration: "flat", inactiveLabel: false, width: 2, height: 2) {
			state "battery", label:'${currentValue}%', unit:"%", icon:"https://raw.githubusercontent.com/bspranger/Xiaomi/master/images/XiaomiBattery.png",
			backgroundColors:[
				[value: 10, color: "#bc2323"],
				[value: 26, color: "#f1d801"],
				[value: 51, color: "#44b621"]
			]
		}
		// Motion for vibration events (NOT USED)
		standardTile("motion", "device.motion", width: 2, height: 2) {
			state("active", label: 'Vibration Detected', icon: "st.motion.motion.active", backgroundColor: "#00a0dc")
			state("inactive", label: 'No Vibration', icon: "st.motion.motion.inactive", backgroundColor: "#ffffff")
		}
		// Acceleration for tilt events (NOT USED)
		standardTile("acceleration", "device.acceleration", width: 2, height: 2) {
			state("active", label: '-', icon: "st.motion.acceleration.active", backgroundColor: "#00a0dc")
			state("inactive", label: 'Tilt Detected', icon: "st.motion.acceleration.inactive", backgroundColor: "#ffffff")
		}
		// Button Capability used for drop events (NOT USED)
		standardTile("button", "device.button", width: 2, height: 2) {
			state("default", label: '-', backgroundColor: "#ffffff")
			state("pushed", label: 'Drop Detected', backgroundColor: "#00a0dc")
		}
		valueTile("tiltAngle", "device.tiltAngle", decoration: "flat", inactiveLabel: false, width: 2, height: 2) {
			state "default", label:'Last Angle Change:\n${currentValue}°'
		}
		valueTile("threeAxis", "device.threeAxis", decoration: "flat", inactiveLabel: false, width: 2, height: 2) {
			state "default", label:'3-Axis:\n${currentValue}'
		}
		valueTile("accelSensitivity", "device.accelSensitivity", decoration: "flat", inactiveLabel: false, width: 2, height: 2) {
			state "default", label:'Sensitivity\nLevel:\n${currentValue}'
		}
		valueTile("lastVibration", "device.lastVibration", decoration: "flat", inactiveLabel: false, width: 4, height: 1) {
			state "default", label:'Last Vibration Event:\n${currentValue}'
		}
		valueTile("lastTilt", "device.lastTilt", decoration: "flat", inactiveLabel: false, width: 4, height: 1) {
			state "default", label:'Last Tilt Event:\n${currentValue}'
		}
		valueTile("lastDrop", "device.lastDrop", decoration: "flat", inactiveLabel: false, width: 4, height: 1) {
			state "default", label:'Last Drop Event:\n${currentValue}'
		}
		valueTile("batteryRuntime", "device.batteryRuntime", decoration: "flat", inactiveLabel: false, width: 4, height: 1) {
			state "batteryRuntime", label:'Battery Changed:\n ${currentValue}'
		}
		valueTile("spacer", "spacer", decoration: "flat", inactiveLabel: false, width: 1, height: 1) {
			state "default", label:''
		}
		valueTile("spacerlg", "spacerlg", decoration: "flat", inactiveLabel: false, width: 2, height: 2) {
			state "default", label:''
		}

	main (["sensorStatus"])
	details(["sensorStatus","tiltAngle","threeAxis","battery","accelSensitivity","lastVibration","lastTilt","lastDropped","batteryRuntime"])
	}

	preferences {
		// Accelerometer sensitivity level Config
		input description: "", type: "paragraph", element: "paragraph", title: "DEVICE SETTINGS"
		input name: "sensLevel", type: "enum", title: "Set sensitivity level", description: "Select Level", options:["1": "High", "2": "Medium", "3": "Low"]
		// Date & Time Config
		input description: "", type: "paragraph", element: "paragraph", title: "DATE & CLOCK"
		input name: "dateformat", type: "enum", title: "Set Date Format\nUS (MDY) - UK (DMY) - Other (YMD)", description: "Date Format", options:["US","UK","Other"]
		input name: "clockformat", type: "bool", title: "Use 24 hour clock?"
		// Battery Reset Config
		input description: "If you have installed a new battery, the toggle below will reset the Changed Battery date to help remember when it was changed.", type: "paragraph", element: "paragraph", title: "CHANGED BATTERY DATE RESET"
		input name: "battReset", type: "bool", title: "Battery Changed?"
		// Advanced Settings
		input description: "Only change the settings below if you know what you're doing.", type: "paragraph", element: "paragraph", title: "ADVANCED SETTINGS"
		// Battery Voltage Range
		input description: "", type: "paragraph", element: "paragraph", title: "BATTERY VOLTAGE RANGE"
		input name: "voltsmax", type: "decimal", title: "Max Volts\nA battery is at 100% at __ volts\nRange 2.8 to 3.4", range: "2.8..3.4", defaultValue: 3
		input name: "voltsmin", type: "decimal", title: "Min Volts\nA battery is at 0% (needs replacing) at __ volts\nRange 2.0 to 2.7", range: "2..2.7", defaultValue: 2.5
		// Live Logging Message Display Config
		input description: "These settings affect the display of messages in the Live Logging tab of the SmartThings IDE.", type: "paragraph", element: "paragraph", title: "LIVE LOGGING"
		input name: "infoLogging", type: "bool", title: "Display info log messages?", defaultValue: true
		input name: "debugLogging", type: "bool", title: "Display debug log messages?"
	}
}

// Parse incoming device messages to generate events
def parse(String description) {
	displayDebugLog(": Parsing '${description}'")
	def result = [:]

	// Any report - button press & Battery - results in a lastCheckin event and update to Last Checkin tile
	sendEvent(name: "lastCheckin", value: formatDate(), displayed: false)
	sendEvent(name: "lastCheckinCoRE", value: now(), displayed: false)

	// Send message data to appropriate parsing function based on the type of report
	if (description?.startsWith("read attr - raw: ")) {
		result = parseReadAttrMessage(description)
	}  else if (description?.startsWith('catchall:')) {
		result = parseCatchAllMessage(description)
	}
	if (result != [:]) {
		displayDebugLog(": Creating event $result")
		return createEvent(result)
	} else {
		displayDebugLog(": Unable to parse unrecognized message")
		return [:]
	}
}

// Check catchall for battery voltage data to pass to getBatteryResult for conversion to percentage report
private Map parseCatchAllMessage(String description) {
	Map resultMap = [:]
	def catchall = zigbee.parse(description)
	displayDebugLog(": $catchall")

	if (catchall.clusterId == 0x0000) {
		def MsgLength = catchall.data.size()
		// Xiaomi CatchAll does not have identifiers, first UINT16 is Battery
		if ((catchall.data.get(0) == 0x01 || catchall.data.get(0) == 0x02) && (catchall.data.get(1) == 0xFF)) {
			for (int i = 4; i < (MsgLength-3); i++) {
				if (catchall.data.get(i) == 0x21) { // check the data ID and data type
					// next two bytes are the battery voltage
					resultMap = getBatteryResult((catchall.data.get(i+2)<<8) + catchall.data.get(i+1))
					break
				}
			}
		}
	}
	return resultMap
}

// Parse read attr - raw messages (includes all sensor event messages and reset button press, and )
private Map parseReadAttrMessage(String description) {
		def cluster = description.split(",").find {it.split(":")[0].trim() == "cluster"}?.split(":")[1].trim()
		def attrId = description.split(",").find {it.split(":")[0].trim() == "attrId"}?.split(":")[1].trim()
		def value = description.split(",").find {it.split(":")[0].trim() == "value"}?.split(":")[1].trim()
		def eventType
		Map resultMap = [:]

		if (cluster == "0101") {
			// Handles vibration (value 01), tilt (value 02), and drop (value 03) event messages
			if (attrId == "0055") {
				if (value?.endsWith('0002')) {
					eventType = 2
					parseTiltAngle(value[0..3])
				} else {
					eventType = Integer.parseInt(value,16)
				}
				resultMap = mapSensorEvent(eventType)
			}
			// Handles XYZ Accelerometer value messages (NEEDS FURTHER DEVELOPMENT)
			else if (attrId == "0508") {
				int x = Integer.valueOf(value[8..11],16).shortValue()
				int y = Integer.valueOf(value[4..7],16).shortValue()
				int z = Integer.valueOf(value[0..3],16).shortValue()
				def axisValues = "${x},${y},${z}"
				def descText = ": Accelerometer axis values = $axisValues"
				displayInfoLog(descText)
				resultMap = [
					name: 'threeAxis',
					value: axisValues,
					linkText: getLinkText(device),
					handlerName: name,
					isStateChange: isStateChange(device, "threeAxis", axisValues),
					descriptionText: "$device.displayName$descText",
				]
			}
			// Handles Vibration level messages (NEEDS TESTING TO VERIFY)
			else if (attrId == "0505") {
				def level = Integer.parseInt(value[0..3],16)
				def descText = ": Vibration level reported at $level"
				displayInfoLog(descText)
				resultMap = [
					name: 'vibrationLevel',
					value: level,
					descriptionText: "$device.displayName$descText",
				]
			}
		}
		else if (cluster == "0000" && attrId == "0005")	{
				displayDebugLog(": reset button press detected")
				def model = value.split("01FF")[0]
				def data = value.split("01FF")[1]
				def modelName = ""
				// Parsing the model
				for (int i = 0; i < model.length(); i+=2) {
						def str = model.substring(i, i+2);
						def NextChar = (char)Integer.parseInt(str, 16);
						modelName = modelName + NextChar
				}
				displayDebugLog(" reported: cluster: ${cluster}, attrId: ${attrId}, value: ${value}, model:${modelName}, data:${data}")
				if (data[4..7] == "0121") {
						resultMap = getBatteryResult(Integer.parseInt((data[10..11] + data[8..9]),16))
				}
		}
		return resultMap
}

// Create map of values to be used for vibration, tilt, or drop event
private Map mapSensorEvent(value) {
	def eventName = ["", "motion", "acceleration", "button"]
	def eventType = ["", "active", "active", "pushed"]
	def lastEvent = ["Stationary", "Vibration", "Tilt", "Drop"]
	def messageType = ["is stationary", "was vibrating or moving", "was tilted", "was dropped"]
	updateLastMovementEvent(lastEvent[value])
	sendEvent(name: "sensorStatus", value: messageType[value], isStateChange: true, displayed: false)
	displayInfoLog(messageType[value])
	if (value == 0) {
		return
	} else {
		runIn(61, clearsensorStatus)
		return [
			name: eventName[value],
			value: eventType[value],
			// data: [buttonNumber: 1],  (Need to check if this is actually needed if only 1 button)
			descriptionText: "${device.displayName} ${messageType[value]}",
			isStateChange: true
		]
	}
}

// Handles tilt angle change message and posts event to update UI tile display
private parseTiltAngle(value) {
	def angle = Integer.parseInt(value,16)
	def descText = ": tilt angle changed by $angle°"
	sendEvent(
		name: 'tiltAngle',
		value: angle,
		// unit: "°",  // Need to check whether this works or is needed at all
		isStateChange:true,
		descriptionText : "$device.displayName$descText"
	)
	displayInfoLog(descText)
}

// This function adapted from SmartSense Multisensor device handler by SmartThings
// NOT YET USED
private List<Map> parseAxis(List<Map> attrData) {
	def results = []
	def x = hexToSignedInt(attrData.find { it.attrInt == 0x0012 }?.value)
	def y = hexToSignedInt(attrData.find { it.attrInt == 0x0013 }?.value)
	def z = hexToSignedInt(attrData.find { it.attrInt == 0x0014 }?.value)

	if ([x, y ,z].any { it == null }) {
		return []
	}

	def xyzResults = [:]
		// The axises reported by the Device Handler differ from the axises reported by the sensor
		xyzResults.x = z
		xyzResults.y = x
		xyzResults.z = y

	log.debug "${device.displayName}: parseAxis -- ${xyzResults}"

	def value = "${xyzResults.x},${xyzResults.y},${xyzResults.z}"
	results << [
			name: "threeAxis",
			value: value,
			linkText: getLinkText(device),
			descriptionText: "${getLinkText(device)} was ${value}",
			handlerName: name,
			isStateChange: isStateChange(device, "threeAxis", value),
			displayed: false
	]
	results
}

// Convert raw 4 digit integer voltage value into percentage based on minVolts/maxVolts range
private Map getBatteryResult(rawValue) {
		// raw voltage is normally supplied as a 4 digit integer that needs to be divided by 1000
		// but in the case the final zero is dropped then divide by 100 to get actual voltage value
		def rawVolts = rawValue / 1000

		def minVolts
		def maxVolts

		if (voltsmin == null || voltsmin == "")
			minVolts = 2.5
		else
		 minVolts = voltsmin

		if (voltsmax == null || voltsmax == "")
			maxVolts = 3.0
		else
	maxVolts = voltsmax

		def pct = (rawVolts - minVolts) / (maxVolts - minVolts)
		def roundedPct = Math.min(100, Math.round(pct * 100))

		def result = [
				name: 'battery',
				value: roundedPct,
				unit: "%",
				isStateChange:true,
				descriptionText : "${device.displayName} Battery at ${roundedPct}% (${rawVolts} Volts)"
		]

		displayDebugLog(": ${result}")
		return result
}

private def displayDebugLog(message) {
	if (debugLogging)
		log.debug "${device.displayName}${message}"
}

private def displayInfoLog(message) {
	if (infoLogging || state.prefsSetCount < 3)
		log.info "${device.displayName}${message}"
}

//Reset the date displayed in Battery Changed tile to current date
def resetBatteryRuntime(paired) {
	def newlyPaired = paired ? " for newly paired sensor" : ""
	sendEvent(name: "batteryRuntime", value: formatDate(true))
	displayInfoLog(": Setting Battery Changed to current date${newlyPaired}")
}

// installed() runs just after a sensor is paired using the "Add a Thing" method in the SmartThings mobile app
def installed() {
	state.prefsSetCount = 0
	displayInfoLog(": Installing")
	init(0)
	checkIntervalEvent("")
}

// configure() runs after installed() when a sensor is paired
def configure() {
	displayInfoLog(": Configuring")
	init(1)
	checkIntervalEvent("configured")
	return
}

// updated() will run twice every time user presses save in preference settings page
def updated() {
	displayInfoLog(": Updating preference settings")
	if (!state.prefsSetCount)
		state.prefsSetCount = 1
	else if (state.prefsSetCount < 3)
		state.prefsSetCount = state.prefsSetCount + 1
	init(0)
	if (battReset) {
		resetBatteryRuntime()
		device.updateSetting("battReset", false)
	}
	if (!sensLevel)
		state.sensitivity = 0
	else if (sensLevel != state.sensitivity)
		changeSensitivity(sensLevel)
	checkIntervalEvent("preferences updated")
	displayInfoLog(": Info message logging enabled")
	displayDebugLog(": Debug message logging enabled")
}

def init(displayLog) {
	clearsensorStatus()
	if (!device.currentState('batteryRuntime')?.value)
		resetBatteryRuntime(true)
	sendEvent(name: "numberOfButtons", value: 1)
}

// update lastStationary, lastVibration, lastTilt, or lastDrop to current date/time
def updateLastMovementEvent(pressType) {
	displayDebugLog(": Setting Last $pressType to current date/time")
	sendEvent(name: "last${pressType}", value: formatDate(), displayed: false)
	sendEvent(name: "last${pressType}CoRE", value: now(), displayed: false)
}

def clearsensorStatus() {
	mapSensorEvent(0)
}

private checkIntervalEvent(text) {
	// Device wakes up every 1 hours, this interval allows us to miss one wakeup notification before marking offline
	if (text)
		displayInfoLog(": Set health checkInterval when ${text}")
	sendEvent(name: "checkInterval", value: 2 * 60 * 60 + 2 * 60, displayed: false, data: [protocol: "zigbee", hubHardwareId: device.hub.hardwareID])
}

def changeSensitivity(value) {
	state.sensitivity = value
	def level = Integer.parseInt(value)
	def attrValue = ["", "0x01", "0x0B", "0x15"]
	def levelText = ["", "High", "Medium", "Low"]
	def descText = ": Sensitivity level set to ${levelText[level]}"
	sendHubCommand(zigbee.writeAttribute(0x0000, 0xFF0D, 0x20, attrValue[level], [mfgCode: 0x115F]).collect { new physicalgraph.device.HubAction(it) }, 0)
	sendEvent(name: "accelSensitivity", value: levelText[level], isStateChange: true, descriptionText: descText)
	displayInfoLog(descText)
}

def formatDate(batteryReset) {
		def correctedTimezone = ""
		def timeString = clockformat ? "HH:mm:ss" : "h:mm:ss aa"

	// If user's hub timezone is not set, display error messages in log and events log, and set timezone to GMT to avoid errors
		if (!(location.timeZone)) {
				correctedTimezone = TimeZone.getTimeZone("GMT")
				log.error "${device.displayName}: Time Zone not set, so GMT was used. Please set up your location in the SmartThings mobile app."
				sendEvent(name: "error", value: "", descriptionText: "ERROR: Time Zone not set, so GMT was used. Please set up your location in the SmartThings mobile app.")
		}
		else {
				correctedTimezone = location.timeZone
		}
		if (dateformat == "US" || dateformat == "" || dateformat == null) {
				if (batteryReset)
						return new Date().format("MMM dd yyyy", correctedTimezone)
				else
						return new Date().format("EEE MMM dd yyyy ${timeString}", correctedTimezone)
		}
		else if (dateformat == "UK") {
				if (batteryReset)
						return new Date().format("dd MMM yyyy", correctedTimezone)
				else
						return new Date().format("EEE dd MMM yyyy ${timeString}", correctedTimezone)
				}
		else {
				if (batteryReset)
						return new Date().format("yyyy MMM dd", correctedTimezone)
				else
						return new Date().format("EEE yyyy MMM dd ${timeString}", correctedTimezone)
		}
}
