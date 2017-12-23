<?php
session_start();
//header("Content-type: text/html; charset=utf-8");  
//$_SESSION['phone']='13121918158';

$tellcode=$_POST['tellcode'];
$username=$_POST['username'];
$password=$_POST['password'];
$tell=$_SESSION['phone'];
//$tell=$_POST['tell'];

if($tellcode != $_SESSION['tellcode'] || $tell != $_POST['phone'])
{
	echo '0';
}
else
{
	include_once("conn/conn.php");
    $sql="select uid from user where tell = '$tell' ";
	  $rs=mysql_query($sql,$conn);
	  if(mysql_fetch_row($rs))
	  {
	      echo '3';

	  }

		  else
		  {
		    

				$sql="select uid from user where username = '$username' ";
				$rs=mysql_query($sql,$conn);
				if(mysql_fetch_row($rs))
				{
				    echo '1';
				}

				else
				{
					    $sql="insert into user (username,password,tell,mark) values ('$username','$password','$tell','1')";
						if($rs=mysql_query($sql,$conn))
						   {
									$_SESSION['phone']=$tell;
									$_SESSION['name']=$username;
	
							   echo '2';
						   }	   
					//}
			       
				}

		  }
	mysql_close($conn);
}

?>