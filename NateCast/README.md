# Android App

[Download NateCast App](https://github.com/nateg5/Android/raw/master/NateCast/app/app-release.apk)

# Install Chromium with Widevine

Use the instructions at https://www.novaspirit.com/2017/09/14/watch-netflix-raspberry-pi/ to install a compatible version of the Chromium browser and use the following settings for the User-Agent Switcher

**Name:** Netflix

**User-Agent String:** Mozilla/5.0 (X11; CrOS armv7l 6946.63.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36

**Group:** Replace

**Append:** IE

# Install Apache on RPi

```
sudo apt-get install apache2 -y
```

Reference: https://www.raspberrypi.org/documentation/remote-access/web-server/apache.md

# Update RPi Apache Install

Cope the files from https://github.com/nateg5/Android/tree/master/NateCast/RPiFiles to the apache server on the RPi at

```
/var/www/html
```

# Update RPi Autostart

Add the following line to the end of ~/.config/lxsession/LXDE-pi/autostart

```
@/var/www/html/index.sh
```

# Create Static IP Address on RPi

Add the following lines to the end of /etc/dhcpcd.conf. You can get the routers/domain_name_servers address by running **route** from the command line. You can also use a different ip_address.

```
interface wlan0

static ip_address=192.168.1.120/24
static routers=192.168.1.1
static domain_name_servers=192.168.1.1
```
