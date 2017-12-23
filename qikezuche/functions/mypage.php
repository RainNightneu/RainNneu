<style>
body{
	font-size:15px;FONT-FAMILY:verdana;width:100%;
}
div.page{
	text-align:center;
}
div.page a{
	border:#aaaadd 1px solid;text-decoration:none;padding:2px 5px 2px 5px;margin:2px;
}
div.page span.current{
 	border:#000099 1px solid;background-color:#000099;padding:3px 6px 3px 6px;margin:2px;color:fff;
 	font-weight:bold;
}
div.page span.disable{
	border:#ccc 1px solid;padding:2px 5px 2px 5px;margin:2px;color:ddd;
}
div.page form{
	display:inline;
}

body
{
	border: 0;
	margin: 0;
}
header
    {
        width:100%;
        height:60px;
        font-size: 15px;
        border:0px;
        margin:0px;
        padding:0px;
        background:#FF9600;
    }

    .logo
    {
        align:center;
        position: relative;;
        top: 10px;
        left: 0px;
    }
    #submit
    { 
        border-width: 2px;
        border-color: #000000;
        border-style: dotted double;
        font-size: 18px;
        line-height: 30px;
        background: #FF9600;
        float: right;
        margin-right: 50px;
    }

    #login
    {
         border:0px;
        background: #FF9600;
        line-height: 35px;

        font-size: 20px;
        float: right;
        margin-left: 20px
    }

    #regist
    {
         border:0px;
        background: #FF9600;
        line-height: 35px;

        font-size: 20px;
        float: right;
         margin-right: 20px;
    }

    #selfcenter
    {
      border:0px;
        background: #FF9600;
        line-height: 35px;

        font-size: 20px;
        float: right;
        margin-left: 20px
    }
    #logout
    {
        border:0px;
        background: #FF9600;
        line-height: 35px;

        font-size: 20px;
        float: right;
        margin-left: 10px
        margin-right:20px;
    }
#map
{
	width:40%;
	background: #FFFFFF;
	height:100%;
	margin-left: 10%;
	float: left;
}
#allmap
{
	width: 95%;
	height: 800px;
	margin: auto;
}
#search
{
	width: 100%;
	height:20%;
}
#content
{
	width:40%;
	overflow:scroll;
	float: left;
}
.btn-group
{
	font-weight: 600;
}
.btn
{
	border-radius: 0px;
	background: #FFFFFF;
	width: 136px;
}
table
{
	margin-left: 100px;
	border: 0;
}
td
{
	width:280px;
}
#mainer
{
	float: none;
}
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=YOOj78k6VSa1CzmQXrKS9gUm"></script>
<link href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<?php 
    session_start();


    $username='';
	if(isset($_SESSION['username']) && !empty($_SESSION['username']))
	{
		$username=$_SESSION['username'];
		
	}



	/*$db="qdm16542388_db";
	//数据库连接
	$ID= mysql_connect("localhost","root","");	
	//数据库选择
	$ok=mysql_select_db($db,$ID);
	//设置数据库编码格式
	mysql_query("SET NAMES UTF8");*/
	include_once('../conn/conn.php');
	//传入页码



    if(isset($_GET["page"]))
		$page=$_GET["page"];
	else $page=1;
	$perNum = 12;  //每页显示的项数
	$showPage = 5;//页码条显示的页数
	$perow = 2;   //每行显示的条数
	

	echo '<header>';
	echo <<<EOT
        <div>
        <div class="logo">&nbsp;&nbsp;
        <img id="logo" src="../image/logo.png" onclick="window.location.href='./index.php'">
EOT;
if($username=='')
{
echo <<<EOT
<button id="submit" onclick="window.location.href='submit.php' ">发布车辆</button> 
<button id="regist" onclick="window.location.href='../regist.html' ">注册</button>
<button id="login" onclick="window.location.href='../login.html'">登录</button>
EOT;
}
else
{

echo '<button id="submit" onclick="click1();" >发布车辆</button> ';
echo "<button id='logout' onclick='click2();'>点击注销</button>";
echo "<button id='selfcenter' onclick='click3();'>".$username."</button>";
//echo '';
}


          
      echo   ' </div>';
      echo   ' </div>';
    

	echo '</header>';

echo <<<EOT
<div id="mainer">

	<div id="search">

	   <center>
		<button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
		  展开详细搜索
		</button>
		<div class="collapse" id="collapseExample">
		  <div class="well">
		    <p><span>单车类型：<button class="searcharea">山地车</button> <button class="searcharea">公路车</button><button class="searcharea">死飞</button><button class="searcharea">折叠</button><button class="searcharea">其他</button></span></p>
		    <p><span>新旧程度：<button class="searcharea">七成新及以下</button> <button class="searcharea">八成新</button><button class="searcharea">九成新及以上</button></span></p>
		    <p><span>车轮尺寸：</span></p>
		    <p><span>变速类型：</span></p>
		    <p><span>车把类型：</span></p>
		    <p><span>避震类型：</span></p>
		    <p><span>是否快拆：</span></p>

		   
		  </div>
	</div>
	</center>

     <div id="map">
		<div id="allmap">
		</div>
  </div>
EOT;

  
if(stripos($_SERVER['HTTP_USER_AGENT'],"android")!=false || stripos($_SERVER['HTTP_USER_AGENT'],"iphone")!=false ||stripos($_SERVER['HTTP_USER_AGENT'],"windows phone")!=false)
{
  echo '移动端访问';
}
else
{
    	//根据页码取出数据
	//编写sql获取分页数据 SELECT *FROM 表明 LIMIT起始位置，显示条数
	$sql="SELECT * FROM bike LIMIT ".($page-1)*$perNum.",$perNum";
	$result=mysql_query($sql,$conn);
	//处理数据
	echo "<div class='content'>";
echo <<<EOT
<div class="btn-group" role="group" aria-label="...">
  <button type="button" class="btn btn-default" disable="disable">排序&#8595; &#8593;</button>
  <button type="button" class="btn btn-default">价格&#8595; </button>
  <button type="button" class="btn btn-default">距离 &#8593;</button>
  <button type="button" class="btn btn-default">接单率 &#8593;</button>
</div>
EOT;

	echo "<table  align=center>";
    $i=0;
	$bikearr=array();
	while ($row=mysql_fetch_array($result )) 
	{
		if($i % $perow == 0 ){
			$bikearr[] = $row[24].'+'.$row[25];
			echo "<tr>";
			echo "<td>";
			echo '<a href=" detail.php?bid='.$row[0].' " target="_blank">';
			echo '<img src="'.$row[15].'" style="width:260px;height:195px; onclick= " /><br>';

	
			echo "编号：$row[0]<br>";
			echo "车名：$row[1]<br>";
			echo "$row[13]<br>";
			//"<img $row[15]";  //显示数据库的内容
			echo "</td>";
		}
		else 
		{
			$bikearr[] = $row[24].'+'.$row[25];
			echo "<td>";
			echo '<a href=" detail.php?bid='.$row[0].' " target="_blank">';
			echo '<img src="'.$row[15].'" style="width:260px;height:195px;" /><br>'; 
			echo "编号：$row[0]<br>";
			echo "车名：$row[1]<br>";
			echo "$row[13]<br>";
			 //显示数据库的内容
			echo "</td>";
			echo "</tr>";
		}
		$i=$i+1;
	}
	echo "</table></div>";
	echo "<br>";
	

echo '<script>';



// 百度地图API功能
echo 'var map = new BMap.Map("allmap");';
$arr=explode('+', $bikearr[0]);
echo "var point = new BMap.Point(" .$arr[0] .','.$arr[1].");";
echo "map.centerAndZoom(point, 15);";
echo <<<EOT
function addMarker(point)
{
	  var marker = new BMap.Marker(point);
	  map.addOverlay(marker);
}
    var bounds = map.getBounds();
	var sw = bounds.getSouthWest();
	var ne = bounds.getNorthEast();
	var lngSpan = Math.abs(sw.lng - ne.lng);
	var latSpan = Math.abs(ne.lat - sw.lat);
	for (var i = 0; i <= 10; i ++) {
		var point = new BMap.Point(sw.lng + lngSpan * (Math.random() * 0.7), ne.lat - latSpan * (Math.random() * 0.7));
		addMarker(point);
}
EOT;


echo '</script>';


	$total_sql="SELECT COUNT(*) FROM bike ";
	$total_result=mysql_fetch_array(mysql_query($total_sql));
	$total=$total_result[0];
	$total_pages=ceil($total/$perNum);
	
	mysql_free_result($result);
	mysql_close($conn);

	//显示数据，分页条
	$page_banner="<div class='page'>";
	//计算偏移量
	$pageoffset=($showPage-1)/2;
	//初始化数据
	$start=1;
	$end=$total_pages;
	
	if($page > 1){
		$page_banner.= "<a href='".$_SERVER['PHP_SELF']."?page=1'>首页</a>";
		$page_banner.= "<a href='".$_SERVER['PHP_SELF']."?page=".($page-1)."'><上一页</a>";
	}else{
		$page_banner.="<span class='disable'>首页</a></span>";
		$page_banner.="<span class='disable'><上一页</a></span>";
	}
	
	if($total_pages > $showPage){
		if($page > $pageoffset+1)
			$page_banner.="...";
		if($page > $pageoffset){
			$start = $page-$pageoffset;
			$end = $total_pages>$page+$pageoffset?$page+$pageoffset:$total_pages;
		}else{
			$start = 1;
			$end = $total_pages > $showPage?$showPage:$total_pages;
		}
		if($page + $pageoffset > $total_pages){
			$start =$start -($page + $pageoffset -$end);
		}
	}
	for($j = $start;$j<=$end;$j++){
		if($page == $j){
			$page_banner.="<span class='current'>".$j."</span>";
		}else{
			$page_banner.="<a href='".$_SERVER['PHP_SELF']."?page=".$j."'>$j</a>";
		}
		
	}
	if($total_pages > $showPage && $total_pages > $page + $pageoffset){
		$page_banner.="...";
	}
	if($page < $total_pages){
		$page_banner.="<a href='".$_SERVER['PHP_SELF']."?page=".($page+1)."'>下一页></a>";
		$page_banner.="<a href='".$_SERVER['PHP_SELF']."?page=".$total_pages."'>尾页</a>";
	}else{
		$page_banner.="<span class='disable'>尾页</a></span>";
		$page_banner.="<span class='disable'>下一页></a></span>";
	}
	
	$page_banner.="共{$total_pages}页，";
	$page_banner.="<form action='mypage.php'>";
	$page_banner.="到第<input type='test' size='2' name='page'>页";
	$page_banner.="<input type='submit' value='确定' >";
	$page_banner.="</form></div></div>";	
	echo $page_banner;
}


	
?>
<script type="text/javascript">

function click1()
{
    window.location.href="submit.php";
}
function click2()
{
    window.location.href="../logout.php";
}
function click3()
{
    window.location.href="selfcenter.php";
}

</script>