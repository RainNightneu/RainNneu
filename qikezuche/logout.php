<?php
session_start();
session_destroy();

$msg="退出成功";
echo  '<script>window.location.href="index.php";</script>';
?>