/*!
 * jQuery Waterfall v1.2
 * 
 * Author		: LeoLai
 * Blog			: http://leolai.cnblogs.com/
 * Mail 		: leolai.mail@qq.com
 * QQ 			: 657448678 
 * Date 		: 2013-4-19 
 * Last Update 	: 2013-5-23
 *
 **************************************************************
 * 1. 根据页面大小自动排列
 * 2. 自定义异步请求函数（返回JSON，json格式与html模板对应即可，默认格式请看demo json.js）
 * 3. 自定义html模板
 * 4. 图片自动按比例缩放
 * 5. 是否显示分页(未完成)
 * usage: url必填，其它不传将使用默认配置
	$('#id').waterfall({
		itemClass: 'wf_item',	// 砖块类名
		imgClass: 'thumb_img',	// 图片类名
		colWidth: 235,			// 列宽
		marginLeft: 15,			// 每列的左间宽
		marginTop: 15,			// 每列的上间宽
		perNum: 'auto',			// 每次下拉时显示多少个(默认是列数)
		isAnimation: true,		// 是否使用动画效果
		ajaxTimes: 'infinite',	// 限制加载的次数(int) 字符串'infinite'表示无限加载 
		url: null,				// 数据来源(ajax加载，返回json格式)，传入了ajaxFunc参数，此参数将无效
		ajaxFunc: null,			// 自定义异步函数, 第一个参数为成功回调函数，第二个参数为失败回调函数
								// 当执行成功回调函数时，传入返回的JSON数据作为参数
		createHtml: null		// 自定义生成html字符串函数,参数为一个信息集合，返回一个html字符串
	});
 *
 */
 
;(function($, window, document){
	$.fn.waterfall = function(options){
		var // 配置信息
			opts = $.extend({}, $.fn.waterfall.defaults, options), 
			
			isIE6 = !-[1,] && !window.XMLHttpRequest,
		
			ajaxTimes = 0,		// 已向服务器请求的次数
			isLoading = false,	// 是否正在加载数据
			isFinish = false,	// true时不再向服务器发送请求
			
			colsHeight = [],	// 用于存储每列的高度
			minColsIndex = 0,	// 最低那列的下标
			
			jsonCache = [],		// 服务器返回的JSON缓存数据
			
			wf_box_top = 0,		// $wf_box 的相对视图的位置高度
			wf_item_top = 0,	// 瀑布流块的top, left值
			wf_item_left = 0,
			
			// 一些jQ对象
			$wf_box, $wf_col, 
			$wf_col_temp, $wf_col_items,
			$wf_result, $backTop,
			
			// 异步请求函数
			$num = 1;
			ajaxFunc = $.isFunction(opts.ajaxFunc) ?
						opts.ajaxFunc :
						function(success, error){
							$.ajax({
								type: 'POST',				
								url: opts.url,
								cache: false,
								data:"num="+$num++,
								dataType:'json',
								timeout: 60000,
								success: success,
								error: error
							});
						},
			// 生成html字符串函数
			createHtml = $.isFunction(opts.createHtml) ?
					opts.createHtml:
					function(data){
                        var cur = data.imgSrc;
                        images.push(cur);
                        return '<div class="wf_item_inner">' +
                            '<a href="#" onclick="preViewImg(\''+cur+'\');"  class="thumb fresco" data-fresco-group="shared_options" data-fresco-caption="'+ data.title +'">' +
                            '<img class="'+opts.imgClass+'"  src="'+ data.imgSrc +'" />' +
                            '<div class="entry-overlay"><span class="fangda"></span></div></a>' +
                            '<h3 class="title">'+ data.title +'</h3>' +
                            '</div>';
					};
		
		
		
		// usage:
		// fixedPosition(elem, {top:0, left:0});
		// fixedPosition(elem, {bottom:0, right:0});
		var fixedPosition = function(){
			var html = document.getElementsByTagName('html')[0],
				dd = document.documentElement,
				db = document.body,
				doc = dd || db;
			
			// 给IE6 fixed 提供一个"不抖动的环境"
			// 只需要 html 与 body 标签其一使用背景静止定位即可让IE6下滚动条拖动元素也不会抖动
			// 注意：IE6如果 body 已经设置了背景图像静止定位后还给 html 标签设置会让 body 设置的背景静止(fixed)失效
			if (isIE6 && db.currentStyle.backgroundAttachment !== 'fixed') {
				html.style.backgroundImage = 'url(about:blank)';
				html.style.backgroundAttachment = 'fixed';
			};
			
			// pos = {top:0, right:0, bottom:0, left:0}
			return isIE6 ? 
				function(elem, pos){
					var style = elem.style,
						dom = '(document.documentElement || document.body)'; 
					
					if(typeof pos.left !== 'number'){
						pos.left = doc.clientWidth - pos.right - elem.offsetWidth; 
					}
					if(typeof pos.top !== 'number'){
						pos.top = doc.clientHeight - pos.bottom - elem.offsetHeight; 
					}
					
					elem.style.position = 'absolute';
					style.removeExpression('left');
					style.removeExpression('top');
					style.setExpression('left', 'eval(' + dom + '.scrollLeft + ' + pos.left + ') + "px"');
					style.setExpression('top', 'eval(' + dom + '.scrollTop + ' + pos.top + ') + "px"');
				} : 
				function(elem, pos){
					var style = elem.style;
						
					style.position = 'fixed';
					
					if(typeof pos.left === 'number'){
						style.left = pos.left + 'px';
					}else{
						style.left = 'auto'; 
						style.right = pos.right + 'px';
					}
					
					if(typeof pos.top === 'number'){
						style.top = pos.top + 'px';
					}else{
						style.top = 'auto'; 
						style.bottom = pos.bottom + 'px';
					}
				 
				};
		}();
		
		
		// 异步获取数据
		function getJSONData(){
			if(!(isFinish || isLoading)){ // 确保上一次加载完毕才发送新的请求
				// 滚动条下拉时判断是否需要向服务器请求数据或者是处理缓存数据
				if(colsHeight.minHeight + wf_box_top < $(window).height() + $(window).scrollTop()){
					// 如果缓存还有数据，直接处理数据
					if(jsonCache.length > 0){
						dealData();
					}else{
						if(opts.ajaxTimes === 'infinite' || ajaxTimes < opts.ajaxTimes){
							showMsg('loading');
							// 传参给服务器
							opts.params.ajax = ++ajaxTimes;
							ajaxFunc(
								function(jsonData){
									try{
										if(typeof jsonData === 'string') jsonData = $.parseJSON(jsonData);
										if($.isEmptyObject(jsonData) || typeof jsonData === 'string'){
											showMsg('finish');
										}else{
											jsonCache = jsonCache.concat(jsonData).reverse();
											dealData();
										}
									}
									catch(e){
										showMsg('error');
									}
								}, 
								function(){
									showMsg('error');
								}
							);
									
						}else{
							showMsg('finish');
						}
					}
					
				}
			}
		}
		
		// 处理返回的数据
		function dealData(){
			var perNum = typeof opts.perNum === 'number' ? opts.perNum : opts.colNum,
				data = null,
				wf_col_height = $wf_col.height(),
				$wf_item, $wf_img, htmlStr;
			// 确保所有图片都已知宽高
			loadImg(jsonCache, opts.imgUrlName, function(){
				while(perNum-- > 0 && (data = jsonCache.pop())){
					
					minColsIndex = getColsIndex(colsHeight)[0];
					
					wf_item_left = minColsIndex * (opts.colWidth + opts.marginLeft);
					wf_item_top = colsHeight[minColsIndex] + opts.marginTop;
					
					htmlStr = createHtml(data);
					
					$wf_item = $('<div>').addClass(opts.itemClass).html(htmlStr)
								.css({width:opts.colWidth, left: wf_item_left, top: wf_item_top})
								.appendTo($wf_col);
					$wf_img = $wf_item.find('.'+opts.imgClass);
					$wf_img.height($wf_img.width() / data.width * data.height);
					
					if(opts.isAnimation){
						$wf_item.css({opacity:0}).animate({
													opacity: 1
												}, 800);
					}
					
					// 更新每列的高度
					colsHeight[minColsIndex] = wf_item_top + $wf_item.outerHeight();
					if( colsHeight[minColsIndex] > colsHeight.maxHeight ){
						colsHeight.maxHeight = colsHeight[minColsIndex];
					}
					$wf_col.height(colsHeight.maxHeight);
				}
				
				isLoading = false;
				$wf_result.hide();
				
				// 保证浏览器有效滚动
				getJSONData();
				
			});
		}
		
		// 排列瀑布流的块
		function realign(){
			var colNum = 0,
				i = 0,
				backTop_left =  0,
				speed = 0;
			
			// 计算出当前屏幕可以排多少列
			colNum = Math.floor(($wf_box.width() + opts.marginLeft) / (opts.colWidth + opts.marginLeft));
			
			if(colNum > 0 && colNum !== opts.colNum){
				opts.colNum = colNum;
				$wf_col.width((opts.colWidth+opts.marginLeft) * opts.colNum - opts.marginLeft);
				
				// 重新调整存储列
				for(i=0; i<opts.colNum; i++){
					colsHeight[i] = 0;
				}
				colsHeight.length = opts.colNum;
				
				$wf_col_items = $wf_col.children('.wf_item');
				$wf_col_items.each(function(num, value){
					minColsIndex = getColsIndex(colsHeight)[0];
					wf_item_top = colsHeight[minColsIndex] + opts.marginTop;
					wf_item_left = minColsIndex * (opts.colWidth + opts.marginLeft);
					
					
					if(opts.isAnimation) speed = 300;
					$(this).width(opts.colWidth).animate({
													left:wf_item_left, 
													top:wf_item_top
												}, speed);
					
					
					colsHeight[minColsIndex] = wf_item_top + $(this).outerHeight();
				});
				
				getColsIndex(colsHeight);
				$wf_col.height(colsHeight.maxHeight);
				
				getJSONData();
			}
			
			// 返回顶部按钮位置
			backTop_left = $wf_col.offset().left + ($wf_col.width() + $wf_box.width()) / 2 - $backTop.width(); 
			
			fixedPosition($backTop[0], {
				left: backTop_left,
				bottom: 0
			});
			
		}
		
		// 显示结果信息
		function showMsg(type){
			switch(type){
				case 'loading':
					isLoading = true;
					$wf_result.html('').addClass('wf_loading').show();
					break;
				case 'error':
					$wf_result.removeClass('wf_loading').show().html('数据格式错误，请返回标准的Json数据或Json格式字符串！');
					isFinish =  true;
					break;
				case 'finish':
					$wf_result.removeClass('wf_loading').show().html('已加载完毕，没有更多了！');
					isFinish = true;
					break;
			}
		}
		
		return this.each(function(){
			if($(this).data('_wf_is_done_')) return true;
			
			$wf_box = $(this).addClass('waterfall').data('_wf_is_done_', true);
			wf_box_top = $wf_box.offset().top;	// 保存 $wf_box 的相对视图的位置高度
			
			$wf_col = $wf_box.children('.wf_col');
			$wf_col.length === 0 && ($wf_col = $('<div>').addClass('wf_col').appendTo($wf_box));
			$wf_result = $('<div>').addClass('wf_result').appendTo($wf_box);
			
			// 增加返回顶部按钮
			$backTop = $('<a></a>').attr('id', 'backTop').attr('title', '返回顶部').appendTo(document.body);
			$backTop.css('opacity', 0).bind('click', function(){
				$('body,html').stop(true).animate({
					scrollTop: wf_box_top
				}, 500);
			});
			
			$(document.body).css('overflow', 'scroll');
			// 排列已经存在的瀑布流块
			realign();
			$(document.body).css('overflow', 'auto');
			
			// 第一次拉取图片时，保证图片能填满窗出现滚动
			getJSONData();
			
			// 注册滚动条事件
			$(window).bind('scroll', function(){
				if($(window).scrollTop() > wf_box_top){
					$backTop.stop(true).animate({opacity: 1}, 500);
				}else{
					$backTop.stop(true).animate({opacity: 0}, 500);
				}
				getJSONData();
				
			// 注册窗口改变大小事件
			}).bind('resize', function(){
				throttle(realign);
			});
		});
	};
	
	// 默认配置
	$.fn.waterfall.defaults = {
		itemClass: 'wf_item',	// 砖块类名
		imgClass: 'thumb_img',	// 图片类名
		colWidth: 90,			// 列宽(int)
		marginLeft: 10,			// 每列的左间宽(int)
		marginTop: 15,			// 每列的上间宽(int)
		perNum: 'auto',			// 每次下拉时显示多少个(默认是列数)
		isAnimation: true,		// 是否使用动画效果
		ajaxTimes: 'infinite',	// 限制异步请求的次数(int) 字符串'infinite'表示无限加载
		imgUrlName: 'imgSrc',	// 在json里表示图片路径的属性名称(用于预加载图片获取高宽)
		params: {},				// 键值对，发送到服务器的数据。将自动转换为请求字符串格式。
								// 如 {foo:["bar1", "bar2"]} 转换为 "&foo=bar1&foo=bar2"。
		url: '',				// 数据来源(ajax加载，返回json格式)，传入了ajaxFunc参数，此参数可省略(string)
		// 自定义异步函数, 第一个参数为成功回调函数，第二个参数为失败回调函数
		// 当执行成功回调函数时，传入返回的JSON数据作为参数
		ajaxFunc: null,		// (function)
		createHtml: null	// 自定义生成html字符串函数,参数为一个信息集合，返回一个html字符串(function)
		
	};
	
	
	/*****************一些全局函数*********************/
	/**
	 * 图片头数据加载就绪事件
	 * @参考 	http://www.planeart.cn/?p=1121
	 * @param	{String}	图片路径
	 * @param	{Function}	尺寸就绪 (参数1接收width; 参数2接收height)
	 * @param	{Function}	加载完毕 (可选. 参数1接收width; 参数2接收height)
	 * @param	{Function}	加载错误 (可选)
	 */
	var imgReady = (function(){
		var list = [], intervalId = null,
		
		// 用来执行队列
		tick = function () {
			var i = 0;
			for (; i < list.length; i++) {
				list[i].end ? list.splice(i--, 1) : list[i]();
			};
			!list.length && stop();
		},

		// 停止所有定时器队列
		stop = function () {
			clearInterval(intervalId);
			intervalId = null;
		};

		return function (url, ready, load, error) {
			var check, width, height, newWidth, newHeight,
				img = new Image();
			
			
			if(!url){
				error && error();
				return;
			}
			
			img.src = url;

			// 如果图片被缓存，则直接返回缓存数据
			if (img.complete) {
				ready(img.width, img.height);
				load && load(img.width, img.height);
				return;
			};
			
			// 检测图片大小的改变
			width = img.width;
			height = img.height;
			check = function () {
				newWidth = img.width;
				newHeight = img.height;
				if (newWidth !== width || newHeight !== height ||
					// 如果图片已经在其他地方加载可使用面积检测
					newWidth * newHeight > 1024
				) {
					ready(newWidth, newHeight);
					check.end = true;
				};
			};
			check();
			
			// 加载错误后的事件
			img.onerror = function () {
				error && error();
				check.end = true;
				img = img.onload = img.onerror = null;
			};
			
			// 完全加载完毕的事件
			img.onload = function () {
				load && load(img.width, img.height);
				!check.end && check();
				// IE gif动画会循环执行onload，置空onload即可
				img = img.onload = img.onerror = null;
			};

			// 加入队列中定期执行
			if (!check.end) {
				list.push(check);
				// 无论何时只允许出现一个定时器，减少浏览器性能损耗
				if (intervalId === null) intervalId = setInterval(tick, 40);
			};
		};
	})();
	
	// 快速获取图片头数据，加载就绪后执行回调函数
	function loadImg(jsonData, imgUrlName, callback){
		var count = 0,
			i = 0,
			intervalId = null,
			data = null,
			imgSrc = 
			done = function(){
				 if(count === jsonData.length) {
					 clearInterval(intervalId);
					 callback && callback();
				 }
			};
		for(; i<jsonData.length; i++){
			data = jsonData[i];
			data.height = parseInt(data.height);
			data.width = parseInt(data.width);
			
			// 如果已知图片的高度，则跳过
			if(data.height >= 0 && data.width >= 0){
				++count;
			}else{
				(function(data){
					imgReady(data[imgUrlName], function(width,height){
						// 图片头数据加载就绪，保存宽高
						data.width = width;
						data.height = height;
						++count;
					}, null, function(){
						// 图片加载失败，替换成默认图片
						data.width = 308;
						data.height = 385;
						data.imgSrc = 'images/default.jpg';
						++count;
					});
				})(data);
			}
		}
		
		intervalId = setInterval(done, 40);
	}
	
	/*
	 * 函数节流：避免因为高频率的更改导致浏览器挂起或崩溃，如onresize事件处理程序尝试复杂的DOM操作
	 * 思路：在一定时间内重复执行某操作只执行一次。
	 */
	function throttle(method, context){
		clearTimeout(method.tid);
		context = context || null;
		method.tid = setTimeout(function(){
			method.call(context);				
		},100);
	}
	
	// 返回从小到大排序的数组的下标的数组
	// e.g. 传入数组[300,200,250,400] 返回[1,2,0,3]
	function getColsIndex(arr){
		var clone = arr.slice(),	// 数组副本，避免改变原数组
			ret = [], 	// 对应下标数组
			len = arr.length,
			i, j, temp;
			
		for(i=0;i<len;i++){
			ret[i] = i;
		}
		
		//外层循环(冒泡排序法：从小到大)
		for(i=0;i<len;i++){
			//内层循环
			for(j=i;j<len;j++){
				if(clone[j] < clone[i]){
					//交换两个元素的位置
					temp=clone[i];
					clone[i]=clone[j];
					clone[j]=temp;
					
					temp=ret[i];
					ret[i]=ret[j];
					ret[j]=temp;
				}
			}
		}
		arr.minHeight = arr[ret[0]];
		arr.maxHeight = arr[ret[ret.length -1]];
		return ret;
	}

})(jQuery, window, document);