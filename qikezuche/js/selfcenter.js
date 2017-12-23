// <!-- 车主和租客的订单评价框控制 -->
// <script type="text/javascript">
  function openownerreview(a){
    if (document.getElementById("ownercomments").style.display !="block") {
        // document.getElementById("ownerreviewcontents").getElementsByTagName("span").innerHTML = 0;
        document.getElementById("ownercommenttextarea").value = "";
        document.getElementById("ownercommentsbackdiv").style.display = "block";
        document.getElementById("ownercomments").style.display = "block";
		    var str=a.previousElementSibling;
        var oid=str.innerHTML;
        document.getElementById("think_twice_before_change_this").innerHTML =oid;
    };
  }
  function closeownercommentsdiv(){
    if (document.getElementById("ownercomments").style.display !== "none") {
      document.getElementById("ownercommentsbackdiv").style.display = "none";
      document.getElementById("ownercomments").style.display = "none";
    };
  }
  function openrenterreview(a){
    if (document.getElementById("rentercomments").style.display != "block") {
      document.getElementById("rentercommentsbackdiv").style.display = "block";
      document.getElementById("rentercomments").style.display = "block";
      var str=a.previousElementSibling;
      var oid=str.innerHTML;
      document.getElementById("think_twice_before_change_this").innerHTML =oid;

    };
  }
  function closerentercommentsdiv(){
    if (document.getElementById("rentercomments").style.display != "none") {
      document.getElementById("rentercommentsbackdiv").style.display = "none";
      document.getElementById("rentercomments").style.display = "none";
    };
  }
    jQuery(document).ready(function () {
     $('#ownercommentinput').rating({
                // language: 'zh-CN',
                min: 0,
                max: 5,
                step: 1,
                size: 'xs',
                showClear: false
             });
       $('#rentercommentinput').rating({
                min: 0,
                max: 5,
                step: 1,
                size: 'xs',
                showClear: false
             });
    });
// </script>
// <!-- end 车主和租客的订单评价控制 -->
