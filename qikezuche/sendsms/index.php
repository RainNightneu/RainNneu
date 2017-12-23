<?php
session_start();
$name='';
if(isset($_SESSION['phone']) && !empty($_SESSION['phone']))
{
	$username=$_SESSION['phone'];
	$name=$_SESSION['name'];
}
include('html/home.html');

?>