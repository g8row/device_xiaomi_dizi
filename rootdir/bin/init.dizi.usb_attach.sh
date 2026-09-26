#!/vendor/bin/sh
# The dwc3 controller can miss the cable attach at boot and stay
# "not attached" until a replug. Cycling its mode re-runs the attach.
udc=/sys/class/udc/a600000.dwc3/state
mode=/sys/devices/platform/soc/a600000.ssusb/mode

sleep 5
if [ "$(cat $udc)" != configured ]; then
    echo none > $mode
    sleep 2
    echo peripheral > $mode
fi
