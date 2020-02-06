function generateDate(){
if(!document.getElementById){
    return false;
}
var generate=document.getElementById("generate");
var card_id=document.getElementById("card_id");
generate.onclick=function(){
    var now=new Date();
    var year=now.getFullYear();
    var month=now.getMonth();
    var date=now.getDate();
    var day=now.getDay();//???
    var hour=now.getHours();
    var minu=now.getMinutes();
    var sec=now.getSeconds();
    month=month+1;
    if(month<10){
        month="0"+month;
    }
    if(date<10){
        date="0"+date;
    }
    if(hour<10){
        hour="0"+hour;
    }
    if(minu<10){
        minu="0"+minu;
    }
    if(sec<10){
        sec="0"+sec;
    }
    var generateTime="";
     //????
    generateTime =""+year+month+date+hour+minu+sec;
    card_id.value=generateTime;
    }

}
function addLoadEvent(func){
    var oldonload=window.onload;
    if(typeof window.onload!='function'){
        window.onload=func;
    }
    else{
        window.onload=function(){
            oldonload();
            func();
        }
    }
}
addLoadEvent(generateDate);