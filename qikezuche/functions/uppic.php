<?php
session_start();
header("Content-Type: text/html; charset=utf-8");
$username=$_SESSION['phone'];
if(empty($_FILES)) 
{
    exit('图片过大或者未选择');
}
else
{
    $tp = array (  
                    "image/gif" => '.gif',  
                    "image/pjpeg" => '.jpeg',  
                    "image/jpg" => '.jpg',  
                    "image/jpeg" => '.jpeg',
                    "image/png"=>'.png'
                );  
                if (! array_key_exists ( $_FILES ["pic"] ["type"], $tp )) 
                {  
                  $char=$_FILES['pic']['name'].'|'.$_FILES['pic']['tmp_name'].'|'.$_FILES['pic']['type'].'|'.$_FILES['pic']['size'];
                  

                    // file_put_contents('1.txt', $char);
                     echo $_FILES['pic'];
                }  
                else
                {
                  $char=$_FILES['pic']['name'].'|'.$_FILES['pic']['tmp_name'].'|'.$_FILES['pic']['type'].'|'.$_FILES['pic']['size'];
                 // file_put_contents('1.txt', $char);
                  $tail=$tp[$_FILES ["pic"] ["type"]];
                  $str=substr(str_shuffle('ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789'),0,6);
                  $str=$str.$username;
                  $photoname=$str.$tail;
                  move_uploaded_file($_FILES['pic']['tmp_name'], "../bike/tmp/".$photoname);
                  $_SESSION['picture']="../bike/tmp/".$photoname;
                  $_SESSION['photoname']=$photoname;
                  echo '2';
                }
}
