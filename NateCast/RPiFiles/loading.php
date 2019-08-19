<html>
<body>
<h1>Redirecting to <br>
<?php
echo $_GET["url"];
?>
<br>in <span id="seconds"></span> seconds ...</h1>
<script>
function updateTime(time) {
    document.getElementById("seconds").innerHTML = time;
    if(time === 0) {
        location.href = "<?php echo $_GET["url"]; ?>";
        return;
    }
    time = time - 1;
    setTimeout(updateTime, 1000, time);
}
setTimeout(updateTime, 1000, <?php echo $_GET["time"]; ?>);
</script>
</body>

