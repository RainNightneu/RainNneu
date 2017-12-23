
//星星样式设置
 jQuery(document).ready(function () {
   $('.bikecomment').rating({
   	language: 'zh-CN',
              min: 0,
              max: 5,
              step: 1,
              size: 'xs',
              showClear: false
           });
});   

$('.form_date').datetimepicker({
    format:'yyyy-mm-dd hh:ii',
    language:'zh-CN'
});



function openme(){

document.getElementById('div1').style.display='block';

document.getElementById('div2').style.display='block';

document.getElementById("div1").style.opacity = 0.8;

// document.getElementById('timeselect').style.display='none';

}


function closeme(){

document.getElementById('div1').style.display='none';

document.getElementById('div2').style.display='none';

// document.getElementById('   timeselect').style.display='block';

}

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


function addCssByLink(url){  
    var doc=document;  
    var link=doc.createElement("link");  
    link.setAttribute("rel", "stylesheet");  
    link.setAttribute("type", "text/css");  
    link.setAttribute("href", url);  
  
    var heads = doc.getElementsByTagName("head");  
    if(heads.length)  
        heads[0].appendChild(link);  
    else  
        doc.documentElement.appendChild(link);  
}  






