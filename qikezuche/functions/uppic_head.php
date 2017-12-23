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
                if (! array_key_exists ( $_FILES ["pic_head"] ["type"], $tp )) 
                {  
                  $char=$_FILES['pic_head']['name'].'|'.$_FILES['pic_head']['tmp_name'].'|'.$_FILES['pic_head']['type'].'|'.$_FILES['pic_head']['size'];
                  

                     file_put_contents('1.txt', $char);
                     echo $_FILES['pic_head'];
                }  
                else
                {
                  $char=$_FILES['pic_head']['name'].'|'.$_FILES['pic_head']['tmp_name'].'|'.$_FILES['pic_head']['type'].'|'.$_FILES['pic_head']['size'];
                  file_put_contents('1.txt', $char);
                  $tail=$tp[$_FILES ["pic_head"] ["type"]];
                  $str=substr(str_shuffle('ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789'),0,6);
                  $str=$str.$username.'head';
                  $photoname=$str.$tail;
                  move_uploaded_file($_FILES['pic_head']['tmp_name'], "../bike/tmp/".$photoname);
                  $_SESSION['picture_head']="../bike/tmp/".$photoname;
                  $_SESSION['photoname_head']=$photoname;
                  echo '2';
                }
}
