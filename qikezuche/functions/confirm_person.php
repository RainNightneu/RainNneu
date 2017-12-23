<?php
session_start();
include("../sendsms/sendsms.php");
header("Content-type: text/html; charset=utf-8"); 
if(isset($_SESSION['picture_id']) && !empty($_SESSION['picture_id'])&&isset($_SESSION['picture_idback']) && !empty($_SESSION['picture_idback'])&&isset($_SESSION['picture_sch']) && !empty($_SESSION['picture_sch']))
{
  $tell=$_SESSION['phone'];
  $realname=$_POST['realname'];
  $idnumber=$_POST['idnumber'];
  $stuid=$_POST['stuid'];
  $isstu=$_POST['isstu'];
  $city=$_POST['city'];
  $school=$_POST['school'];
  $filename_id=$_SESSION['picture_id'];
  $photoname_id=$_SESSION['photoname_id'];

  $filename_idback=$_SESSION['picture_idback'];
  $photoname_idback=$_SESSION['photoname_idback'];

  $filename_sch=$_SESSION['picture_sch'];
  $photoname_sch=$_SESSION['photoname_sch'];

  rename($filename_id, "../bike/cards/".$photoname_id);
  rename($filename_idback, "../bike/cards/".$photoname_idback);
  rename($filename_sch, "../bike/cards/".$photoname_sch);


  if($isstu=="是")
    $isstu=1;
  else
    $isstu=0;

  $id="../bike/cards/".$photoname_id;
  $idback="../bike/cards/".$photoname_idback;
  $stu="../bike/cards/".$photoname_sch;
  include_once('../conn/conn.php');
  $sql="update user set realname='$realname',idnumber='$idnumber',stuid='$stuid',isstu='$isstu',city='$city',school='$school',idfront='$id',idback='$idback',stucard='$stu',mark='3' where tell='$tell' ";
  $result=mysql_query($sql);
  //session_unset('picture_id');
  //session_unset('picture_sch');
  //session_unset('picture_idback');
	$_SESSION['picture_id']='';
	$_SESSION['picture_sch']='';
	$_SESSION['picture_idback']='';

  file_put_contents("e.txt", mysql_error($conn));


  mysql_close($conn);




   $phone='15117928812'; //设置发送的手机号码 
 		$sms=new SendSMS($phone); //创建对象 
		
        $sms->setTem_id('91548746');
        $renderParams = array('address' => 'qikezuche.com', 'tell' =>"0"); 
        $sms->setTemplateParams($renderParams);
        $re = $sms->send();
        if(!$re->res_code)
        {
            echo '1';
        }
        else
        {
            echo $re->res_message;
        }

 // echo '1';
}
else
{
  echo "请先上传图片";
}

?>