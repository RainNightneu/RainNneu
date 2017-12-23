<?php
session_start();
header("Content-type: text/html; charset=utf-8"); 
header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 
header("Last-Modified: ".gmdate("D, d M Y H:i:s")." GMT"); 
header("Cache-Control: no-cache, must-revalidate"); 
header("Pramga: no-cache");

$name='';
if(isset($_SESSION['phone']) && !empty($_SESSION['phone']))
{
	$username=$_SESSION['phone'];
	$name=$_SESSION['name'];
}
include('html/home.html');

?>
