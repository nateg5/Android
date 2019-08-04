<?php
    $ping = $_GET["ping"];

    $url = $_GET["url"];
    if(strlen($url) > 0) {
        $file = fopen("url.txt", "w");
        fwrite($file, $url);
        fclose($file);
    }

    $full = $_GET["full"];
    if(strlen($full) > 0) {
        $file = fopen("full.txt", "w");
        fwrite($file, $full);
        fclose($file);
    }
    
    $key = $_GET["key"];
    if(strlen($key) > 0) {
        $file = fopen("key.txt", "w");
        fwrite($file, $key);
        fclose($file);
    }

    if(strlen($ping) > 0) {
        echo "ping";
    }

    if(strlen($url) > 0) {
        $message = "Launching $url";
        if(strcmp($full, "true") == 0) {
            $message .= " in full screen mode";
        }
        $message .= "!";
        if(strcmp($url, "close") == 0) {
            $message = "Closing Chrome";
        }
        if(strcmp($url, "reboot") == 0) {
            $message = "Rebooting";
        }
        if(strcmp($url, "mousemove") == 0) {
            $message = "Moving Mouse";
        }
        
        echo $message;
    }
?>
