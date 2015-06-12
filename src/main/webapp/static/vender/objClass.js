// JavaScript Document

//删减行控制类
var num = 1;
function actRow(){
	this.boxid = "";
	this.content = "";
	this.par = 0;
	this.a = function(){
		var _boxid = this.boxid;
		//alert(_boxid);
		var _content = this.content;
		num++;
		$(function(){
			$("#"+_boxid).append(_content);
			rr(_boxid);
		});
		var text = document.getElementsByTagName("textarea");
		for(var i=0;i<text.length;i++){
			autoTextarea(text[i]);// 调用
		}
	}
	
	this.d = function(){
		var _boxid = this.boxid;
		var _par = this.par;
		$(function(){
			$("#"+_boxid+" .deltr").click(function(){
				if(_par==1){
					$(this).parent().parent().parent().parent().parent().parent().remove();
				}else{
					$(this).parent().parent().remove();
				}
				rr(_boxid);
			});
		});
		var text = document.getElementsByTagName("textarea");
		for(var i=0;i<text.length;i++){
			autoTextarea(text[i]);// 调用
		}
	}
}

function rr(id){
	$(function(){
		var ids = $("#"+id+" tr");
		ids.each(function(){
			var nos = $(this).index()+1;
			$(this).find(".nos").html(nos);
		});
	});
}