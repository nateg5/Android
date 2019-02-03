# Android App

[Download NateCast App](https://github.com/nateg5/Android/raw/master/NateCast/app/app-release.apk)

# Install Chromium with Widevine

1. Download and Install Chromium with Widevine

```
wget https://github.com/nateg5/Android/releases/download/NateCast/chromium-browser_56.0.2924.84-0ubuntu0.14.04.1.1011.deb
sudo dpkg -i chromium-browser_56.0.2924.84-0ubuntu0.14.04.1.1011.deb
```

2. Download and extract DRM zip

```
wget https://github.com/nateg5/Android/releases/download/NateCast/drm.zip
unzip drm.zip
```

3. Copy libwidevinecdm.so

```
sudo cp libwidevinecdm.so /usr/lib/chromium-browser/libwidevinecdm.so
```

4. Open Chromium

5. Go to https://chrome.google.com/webstore/detail/user-agent-switcher-for-c/djflhoibgkdhkhhcedjiklpkjnoahfmg

6. Install User-Agent Switcher for Chrome

7. Right-click User-Agent Switcher icon then select Options

8. Add a new user agent user the following information

    **Name:** Netflix

    **User-Agent String:** Mozilla/5.0 (X11; CrOS armv7l 9901.77.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.81 Safari/537.36

    **Group:** Chrome

    **Append:** Replace

    **Idicator Flag:** NFX

9. Left-click User-Agent Switcher icon then select Chrome > Netflix

* References
    * https://www.novaspirit.com/2017/09/14/watch-netflix-raspberry-pi/
    * https://raspberryparanovatos.com/tutoriales/como-ver-netflix-en-raspberry-pi-usando-el-navegador-web-vivaldi/

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
