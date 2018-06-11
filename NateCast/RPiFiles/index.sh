#!/bin/bash

while true; do
    url=$(head -1 /var/www/html/url.txt)
    url_length=${#url}
    if [ $url_length -gt 0 ]
    then
        echo "" > /var/www/html/url.txt
    fi

    full=$(head -1 /var/www/html/full.txt)
    full_length=${#full}
    if [ $full_length -gt 0 ]
    then
        echo "" > /var/www/html/full.txt
    fi
    
    key=$(head -1 /var/www/html/key.txt)
    key_length=${#key}
    if [ $key_length -gt 0 ]
    then
        echo "" > /var/www/html/key.txt
    fi

    if [ "$url" == "close" ]
    then
        echo "pkill chromium"
        pkill chromium
    elif [ "$url" == "reboot" ]
    then
        echo "reboot"
        reboot
    elif [ "$url" == "mousemove" ]
    then
        echo "mousemove"
        xdotool mousemove 1280 720
    elif [ $url_length -gt 0 ] 
    then
        chromium-browser $url & 
        if [ "$full" == "true" ]
        then
            sleep 5
            xdotool key F11 & 
        fi
    fi
    
    if [ $key_length -gt 0 ]
    then
        xdotool key $key &
    fi
    sleep 1
done
