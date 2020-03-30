# Android App

[Download NateCast App](https://github.com/nateg5/Android/raw/master/NateCast/app/app-release.apk)

# Install Widevine and User Agent Switcher

1. Ensure your RPi is on the latest Raspbian distribution (Buster).

2. Download and extract libwidevinecdm

```
wget https://github.com/nateg5/Android/releases/download/NateCast/widevine-flash_armhf.sh
chmod 777 widevine-flash_armhf.sh
./widevine-flash_armhf.sh
sudo tar Cfx / widevine-flash-*_armhf.tgz
cd /usr/lib/chromium-browser
sudo cp /opt/WidevineCdm/_platform_specific/linux_arm/libwidevinecdm.so libwidevinecdm.so
sudo chmod 777 libwidevinecdm.so
```

3. Open Chromium

5. Go to https://chrome.google.com/webstore/detail/user-agent-switcher-for-c/djflhoibgkdhkhhcedjiklpkjnoahfmg

6. Install User-Agent Switcher for Chrome

7. Right-click User-Agent Switcher icon then select Options

8. Add a new user agent user the following information

    **Name:** Netflix

    **User-Agent String:** Mozilla/5.0 (X11; CrOS x86_64 11895.95.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.125 Safari/537.36

    **Group:** Chrome

    **Append:** Replace

    **Indicator Flag:** NFX

9. Left-click User-Agent Switcher icon then select Chrome > Netflix

* References
    * https://blog.vpetkov.net/2019/07/12/netflix-and-spotify-on-a-raspberry-pi-4-with-latest-default-chromium/
    * https://www.novaspirit.com/2017/09/14/watch-netflix-raspberry-pi/
    * https://raspberryparanovatos.com/tutoriales/como-ver-netflix-en-raspberry-pi-usando-el-navegador-web-vivaldi/
    * https://help.vivaldi.com/article/raspberry-pi/
    * https://gist.github.com/ruario/19a28d98d29d34ec9b184c42e5f8bf29

# Install Apache, PHP and XDOTool on RPi

```
sudo apt-get install apache2 -y
sudo apt-get install php libapache2-mod-php -y
sudo mv /var/www/html/index.html /var/www/html/index.html.bak
sudo apt-get install xdotool -y
```

Reference: https://www.raspberrypi.org/documentation/remote-access/web-server/apache.md

# Update RPi Apache Install

Copy the files from https://github.com/nateg5/Android/tree/master/NateCast/RPiFiles to the apache server on the RPi at

```
cd /var/www/html
sudo wget https://raw.githubusercontent.com/nateg5/Android/master/NateCast/RPiFiles/full.txt
sudo wget https://raw.githubusercontent.com/nateg5/Android/master/NateCast/RPiFiles/index.php
sudo wget https://raw.githubusercontent.com/nateg5/Android/master/NateCast/RPiFiles/index.sh
sudo wget https://raw.githubusercontent.com/nateg5/Android/master/NateCast/RPiFiles/key.txt
sudo wget https://raw.githubusercontent.com/nateg5/Android/master/NateCast/RPiFiles/loading.php
sudo wget https://raw.githubusercontent.com/nateg5/Android/master/NateCast/RPiFiles/url.txt
sudo chmod 777 *
```

# Update RPi Autostart

Add the following line to the end of ~/.config/lxsession/LXDE-pi/autostart (or /etc/xdg/lxsession/LXDE-pi/autostart)

```
@/var/www/html/index.sh
```

# Update Chrome Sound Settings

Go to

```
chrome://settings/content/sound 
```

and add Netflix and Amazon to the Allow section.

```
https://www.netflix.com:443
https://www.amazon.com:443
```
