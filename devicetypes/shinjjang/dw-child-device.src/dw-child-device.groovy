/**
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
 */
metadata {
	definition (name: "DW Child Device", namespace: "ShinJjang", author: "ShinJjang", mnmn: "SmartThings", runLocally: true, minHubCoreVersion: '000.017.0012', executeCommandsLocally: false, vid: "generic-switch") {
		capability "Switch"
		capability "Refresh"
		capability "Actuator"
		capability "Sensor"
		capability "Health Check"
	}

	tiles {
		multiAttributeTile(name:"switch", type: "lighting", width: 3, height: 4, canChangeIcon: true){
			tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
				attributeState "off", label: '${name}', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff", nextState:"turningOn"
				attributeState "on", label: '${name}', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#00A0DC", nextState:"turningOff"
				attributeState "turningOn", label:'${name}', action:"switch.off", icon:"st.switches.switch.on", backgroundColor:"#00A0DC", nextState:"turningOff"
				attributeState "turningOff", label:'${name}', action:"switch.on", icon:"st.switches.switch.off", backgroundColor:"#ffffff", nextState:"turningOn"
			}
		}
		standardTile("refresh", "device.switch", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh"
		}
	}
}

def installed() {
	configure()
}

def updated() {
	configure()
}

def configure() {
	parent.configureChild()
	refresh()
}
def on() {
	log.info "on"
	parent.childOnOff(device.deviceNetworkId, 0xFF)
}

def off() {
	log.info "off"
    parent.childOnOff(device.deviceNetworkId, 0x00)
}

def refresh() {
	log.info "refresh"
    parent.childRefresh(device.deviceNetworkId)
}

def ping() {
	refresh()
}
