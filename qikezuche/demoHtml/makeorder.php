<?php 
session_start();
$username='';
if(isset($_SESSION['phone']) && !empty($_SESSION['phone']))
{
	$username=$_SESSION['phone'];
	include('order.html');
}
else
{
	
}
 ?>