// JavaScript Document
$(function()
{
	autoHeight();
	
	$(window).resize(function()
	{
		autoHeight();
	});
	
	$(".siderbar p").click(function()
	{
		if($(this).next("div").css("display")!="block")
		{
			$(".siderbar div").slideUp();
			$(this).next("div").slideDown();
		}		
	});
	
	$(".siderbar a").click(function()
	{
		$(".siderbar a").css({"color":"#333","font-weight":"normal","background":"none"});
		$(this).css({"color":"#fff","font-weight":"bold","background":"#368D81"});
	});
	//滑动效果
	$(".siderbar>li>ul").slideUp();
	$(".siderbar>li>span").on("click",function(){
		$(".siderbar>li>ul").slideUp();
		$(this).next().slideDown();
	});
	$(".siderbar>li>span").eq(0).trigger("click");
});

function autoHeight()
{
	var lessHeight= $(window).height()-$(".header").outerHeight()-$(".footer").outerHeight();
	$(".siderbar,.content").height(lessHeight-8);
}