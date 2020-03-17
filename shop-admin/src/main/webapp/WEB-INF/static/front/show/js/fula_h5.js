window.onload = function() {
	var wHgt = $(window).height();
	$('.body_inner').css('height', wHgt + 'px');
	var arrSec = ['.sec_1', '.sec_2', '.sec_3', '.sec_4', '.sec_5', '.sec_6'];
	var dots = document.querySelector('#dots').querySelectorAll('span');
	for(var i = 0; i < 6; i++) {
		document.querySelectorAll('.sec')[i].style.height = wHgt + 'px';
	}

	var oSecs = document.querySelectorAll('.sec');
	var oImg = document.querySelector('.sec_1').getElementsByTagName('img')[0];
	var num = parseInt(getStyle(oImg, 'height'));
	var del_hgt = wHgt - num;
	for(var i = 0; i < oSecs.length; i++) {
		oSecs[i].index = i;
		adaptImg(oSecs[i]);
	}

	function adaptImg(obj) {
		obj.style.height = wHgt + 'px';
		var oImg2 = obj.getElementsByTagName('img')[0];
		oImg2.style.transform = 'translateY(' + del_hgt + 'px)';
	}
	
	//阻止默认滑动
	$('body').bind("touchmove", function(ev) {
		var oEvent=ev||event;
		oEvent.preventDefault();
	});

	$('.body_inner').swipeUp(function() {
		var tran = document.querySelector('.body_inner');
		var n = Math.abs(getTranNum(tran)) / wHgt;
		if(n < 5) {
			$(this).css('transform', 'translateY(' + (getTranNum(tran) - wHgt) + 'px)');
			$(this).css('webkitTransform', 'translateY(' + (getTranNum(tran) - wHgt) + 'px)');
			upSwipe(n);
		} else {
			$(this).css('transform', 'translateY(' + (getTranNum(tran)) + 'px)');
			$(this).css('webkitTransform', 'translateY(' + (getTranNum(tran)) + 'px)');
		}
//		console.log(n);
	});
	$('.body_inner').swipeDown(function() {
		var tran = document.querySelector('.body_inner');
		var n = Math.abs(getTranNum(tran)) / wHgt;
		if(n > 0) {
			$(this).css('transform', 'translateY(' + (getTranNum(tran) + wHgt) + 'px)');
			$(this).css('webkitTransform', 'translateY(' + (getTranNum(tran) + wHgt) + 'px)');
			downSwipe(n);
		} else {
			$(this).css('transform', 'translateY(' + (getTranNum(tran)) + 'px)');
			$(this).css('webkitTransform', 'translateY(' + (getTranNum(tran)) + 'px)');
		}

	})

	function upSwipe(n) {
		var oNow = arrSec[n];
		var oNext = arrSec[n + 1];
		dotActive(n + 1);
		$('.download').css('display', 'block');
		$('.download').removeClass('bounceInDown').addClass('animated bounceOutUp');
		$(oNow + ' h4').removeClass('fadeInLeftBig').addClass('animated fadeOutLeftBig');
		$(oNow + ' p').removeClass('fadeInRightBig').addClass('animated fadeOutRightBig');
		setTimeout(function() {
			$(oNext + ' h4').removeClass('fadeOutLeftBig').addClass('animated fadeInLeftBig');
		}, 200)
		setTimeout(function() {
			$(oNext + ' p').removeClass('fadeOutRightBig').addClass('animated fadeInRightBig');
		}, 200)
		setTimeout(function() {
			$('.download').removeClass('bounceOutUp').addClass('animated bounceInDown');
		}, 300)
	};

	function downSwipe(n) {
		var oNow = arrSec[n];
		var oNext = arrSec[n + 1];
		var oPre = arrSec[n - 1];
		dotActive(n - 1);
		if(n == 1) {
			$('.download').css('display', 'none');
		}
		$('.download').removeClass('bounceInDown').addClass('animated bounceOutUp');
		$(oNow + ' h4').removeClass('fadeInLeftBig').addClass('animated fadeOutLeftBig');
		$(oNow + ' p').removeClass('fadeInRightBig').addClass('animated fadeOutRightBig');
		setTimeout(function() {
			$(oPre + ' h4').removeClass('fadeOutLeftBig').addClass('animated fadeInLeftBig');
		}, 200)
		setTimeout(function() {
			$(oPre + ' p').removeClass('fadeOutRightBig').addClass('animated fadeInRightBig');
		}, 200)
		setTimeout(function() {
			$('.download').removeClass('bounceOutUp').addClass('animated bounceInDown');
		}, 300)
	};
	// 获得transform值
	function getTranNum(obj) {
		var str = obj.style.webkitTransform;
		var re = /\-?\d+/ig;
		var str1 = str.match(re);
		return Number(str1);
	};
	// 获得元素属性
	function getStyle(obj, attr) {
		return getComputedStyle(obj) ? getComputedStyle(obj)[attr] : obj.currentStyle[attr];
	};

	function dotActive(n) {
		for(var i = 0; i < dots.length; i++) {
			dots[i].className = '';
		}
		dots[n].className = 'actived';
	}
}