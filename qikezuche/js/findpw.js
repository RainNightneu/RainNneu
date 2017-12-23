

function click1()

{

    window.location.href="../functions/submit.php";

}

function click2()

{

    window.location.href="../logout.php";

}

function click3()

{

    window.location.href="../functions/selfcenter.php";

}





 function createXMLHttpRequest()

 {

  var xmlHttp=null;

    if (window.XMLHttpRequest) 

    {

       xmlHttp = new XMLHttpRequest(); //FireFox、Opera等浏览器支持的创建方式

    } else 

    {

       xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");//IE浏览器支持的创建方式

    }

    return xmlHttp;

  }





function addCookie(name,value,days,path){   /**添加设置cookie**/  

    var name = escape(name);  

    var value = escape(value);  

    var expires = new Date();  

    expires.setTime(expires.getTime() + days * 3600000 * 24);  

    //path=/，表示cookie能在整个网站下使用，path=/temp，表示cookie只能在temp目录下使用  

    path = path == "" ? "" : ";path=" + path;  

    //GMT(Greenwich Mean Time)是格林尼治平时，现在的标准时间，协调世界时是UTC  

    //参数days只能是数字型  

    var _expires = (typeof days) == "string" ? "" : ";expires=" + expires.toUTCString();  

    document.cookie = name + "=" + value + _expires + path;  

}  

function getCookieValue(name){  /**获取cookie的值，根据cookie的键获取值**/  

    //用处理字符串的方式查找到key对应value  

    var name = escape(name);  

    //读cookie属性，这将返回文档的所有cookie  

    var allcookies = document.cookie;         

    //查找名为name的cookie的开始位置  

    name += "=";  

    var pos = allcookies.indexOf(name);      

    //如果找到了具有该名字的cookie，那么提取并使用它的值  

    if (pos != -1){                                             //如果pos值为-1则说明搜索"version="失败  

        var start = pos + name.length;                  //cookie值开始的位置  

        var end = allcookies.indexOf(";",start);        //从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置  

        if (end == -1) end = allcookies.length;        //如果end值为-1说明cookie列表里只有一个cookie  

        var value = allcookies.substring(start,end); //提取cookie的值  

        return (value);                           //对它解码        

    }else{  //搜索失败，返回空字符串  

        return "";  

    }  

}  

function deleteCookie(name,path){   /**根据cookie的键，删除cookie，其实就是设置其失效**/  

    var name = escape(name);  

    var expires = new Date(0);  

    path = path == "" ? "" : ";path=" + path;  

    document.cookie = name + "="+ ";expires=" + expires.toUTCString() + path;  

}  

  



function sendsms()

  {

    var xml=createXMLHttpRequest();

    var phone=eval(document.getElementById("findphone")).value;

    var randcode=eval(document.getElementById("findvcode1")).value;

    randcode=randcode.toLowerCase();

    //alert(randcode);

    if(phone == "" || isNaN(phone))

    {

      alert("请输入正确的手机号");

      return false;

    }

      if(phone.length !=11)

      {

         alert("请输入正确的手机号");

         return false;

      }



        var post_method="number="+phone+"&vcode="+randcode;

        xml.open("POST","../sendsms/sendvcode_pass.php",true);

        xml.setRequestHeader("Content-Type","application/x-www-form-urlencoded;");

       // alert(xml.responseText);

        xml.onreadystatechange=function()

        {

          if (xml.readyState==4 && xml.status==200)

          {

           // alert(xml.responseText);

              if(xml.responseText == -1)

              {

                  alert("请填写正确的验证码再发送短信验证码");

              }

            else

            {

              var p1=document.getElementById("phonemsg");

            //   p1.style.color="red";

              if(xml.responseText == 0)

              {

             alert("该手机号并没有注册!");

              }

              else  

              {

                if(xml.responseText == 1)      

                {

            //      p1.innerHTML="";

                  var cObj = document.getElementById("tell");

                  cObj.setAttribute("readOnly",'true');

                  alert("发送成功");

                }

                else 

                {

                  alert(xml.responseText);

                }

              }

            }

            

          }

        }

      

        xml.send(post_method);

    

  }



function changepassword()

{  

    var userName = document.getElementById("findphone").value;  

    var userPass = document.getElementById("pass1").value;  

    var userPass1 = document.getElementById("pass2").value;  

    var vcode=document.getElementById("phonecode").value;      

    if(userPass != userPass1)

     {

        alert("两次密码不一致！");

        return false;

     }

    else

    {

        if(vcode =='' || vcode.length !=4)

        {

         alert("请输入正确的手机验证码");

         return false;

        }

       else

       {

            var xml=createXMLHttpRequest();

            var post_method="phone="+userName+"&password="+userPass+"&vcode="+vcode;

            xml.open("POST","../changepassword.php",true);

            xml.setRequestHeader("Content-Type","application/x-www-form-urlencoded;");

            xml.onreadystatechange=function()                                                

            {



                if (xml.readyState==4 && xml.status==200)

                {

                   

                    if(xml.responseText != 1)

                    {

                     alert(xml.responseText);

                    }

                    else

                      {                                                     

                        var objChk = document.getElementById("chkRememberPass");  

                        if(objChk.checked)

                        {  

                         addCookie("userName",userName,7,"/");  

                         addCookie("userPass",userPass,7,"/");  





                        }

                         window.location.href="../index.php";

                                                                              

                    }

                    

                }

                                                        

            }

           xml.send(post_method);                                         

        }

    }                     

}



function chv(vcode)

{

  vcode.src=vcode.src+'?_s='+Math.random();

}









function submittime()

{

var id1=document.getElementById('starttimecal');

var id2=document.getElementById('endtimecal');

var starttime=id1.value;

var endtime=id2.value;

 window.location.href="../functions/search.php?starttime="+starttime+"&endtime="+endtime;

}

