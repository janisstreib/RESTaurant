<?php
include '../lang/en.php';
$lang = substr ( $_SERVER ['HTTP_ACCEPT_LANGUAGE'], 0, 2 );
if (isset ( $_COOKIE ['lang'] ))
	$langCoo = $_COOKIE ['lang'];
else
	$langCoo = $lang;
if ($langCoo == "de" || $langCoo == "en") {
	$lang = $langCoo;
}
switch ($lang) {
	case "de" :
		include '../lang/de.php';
		break;
}
?>
Date.prototype.format = function(format) // author: meizz
{
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getUTCHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}

	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}
var AudioPlayer = (function() {
	var playing = false;
	var active = false;
	var enabled = false;
	var player;
	var bufferw;
	var audioElement;
	var seekbar;
	var playerroot;
	var longerThanHour = false;
	var nav = document.getElementById("navbar");
	function seekTo(value) {
		audioElement.currentTime = value;
	}
	function init(mime) {
		playerroot = document.createElement("table");
		var root = document.createElement("tr");
		playerroot.setAttribute("class", "playercontainer");
		var playsection = document.createElement("td"); 
		bufferw = document.createElement("img");
		bufferw.src = "img/load.gif";
		bufferw.setAttribute("id", "playerbuffer");
		audioElement = new Audio("");
		var canPlayType = audioElement.canPlayType(mime);
		if (canPlayType.match(/maybe|probably/i)) {
			enabled = true;
			player = document.createElement("form");
			player.setAttribute("class", "navbar-form navbar-left");
			player.setAttribute("class", "currentplaying");
			seekbar = document.createElement("form");
			seekbar.setAttribute("class", "navbar-form navbar-left");
			seekbar.setAttribute("id", "aplayerseek");	
			var time1 = document.createElement("p");
			var time2 = document.createElement("p");
			time1.setAttribute("class", "currentplaying");
			time2.setAttribute("class", "currentplaying");
			time1.innerHTML = '0:00';
			time2.innerHTML = '0:00';
			playsection.appendChild(player);
			root.appendChild(playsection);
			playsection = document.createElement("td");
			playsection.appendChild(time1);
			root.appendChild(playsection);
			var seekInput = document.createElement("input");
			seekInput.setAttribute("step", "any");
			seekInput.setAttribute("type", "range");
			seekInput.setAttribute("value", "0");
			if(isIE())
			seekInput.oninput = function(){seekTo(seekInput.value)}; 
			else
			seekInput.onchange= function(){seekTo(seekInput.value)};
			audioElement.addEventListener('timeupdate', function(){ seekInput.value = audioElement.currentTime; var currentPlay = new Date(audioElement.currentTime*1000);  if(!longerThanHour) {time1.innerHTML = currentPlay.format("mm:ss");}else { time1.innerHTML = currentPlay.format("hh:mm:ss");}}, false);
			seekbar.appendChild(seekInput);
			playsection = document.createElement("td");
			playsection.appendChild(seekbar);
			root.appendChild(playsection);
			playsection = document.createElement("td");
			playsection.appendChild(time2);
			root.appendChild(playsection);
			audioElement.addEventListener('canplay',function() { seekInput.max = audioElement.duration; var maxplay = new Date(audioElement.duration*1000); if(audioElement.duration/60 < 59) {time2.innerHTML = maxplay.format("mm:ss"); longerThanHour = false;}else { time2.innerHTML = maxplay.format("hh:mm:ss"); longerThanHour = true;}},false); 
			var container = document.createElement("div");
			container.setAttribute("class", "form-group");
			var tmp = document.createElement("button");
			tmp.setAttribute("type", "button");
			tmp.setAttribute("class", "btn");
			tmp.setAttribute("id", "playerplay");
			$(tmp)
					.click(
							function() {
								if (playing) {
									audioElement.pause();
									tmp.innerHTML = '<span class="glyphicon glyphicon-play"></span>';
									playing = false;
								} else {
									audioElement.play();
									tmp.innerHTML = '<span class="glyphicon glyphicon-pause"></span>';
									playing = true;
								}
							});
			tmp.innerHTML = '<span class="glyphicon glyphicon-play"></span>';
			container.appendChild(tmp);
			var tmp2 = document.createElement("span");
			tmp2.setAttribute("id", "currentplaying");
			tmp2.setAttribute("class", "pull-left currentplaying");
			container.appendChild(tmp2);
			player.appendChild(container);
			$(".downloadc")
					.each(
							function(i, obj) {
								var link = obj.firstChild.getAttribute("href");
								var title = obj.firstChild.getAttribute("data-atitle");
								var playButton = document.createElement("a");
								playButton.setAttribute("class",
										"btn btn-default btn-default");
								playButton.setAttribute("data-toggle",
										"tooltip");
								playButton.setAttribute("data-placement",
										"left");
								playButton.setAttribute("title",
										"<?=$lng_play ?>");
								playButton.innerHTML = '<span class="glyphicon glyphicon-play"></span>';
								obj.appendChild(playButton);
								$(playButton).click(function() {
									playAudio(link, title);
								});
							});
			audioElement.addEventListener('playing',function() { document.getElementById("playerplay").innerHTML = '<span class="glyphicon glyphicon-pause"></span>'; if(document.contains(bufferw))player.removeChild(bufferw); },false); 
			audioElement.addEventListener('ended', function(){ document.getElementById("playerplay").innerHTML = '<span class="glyphicon glyphicon-play"></span>'; }, false);
			playerroot.appendChild(root);
		}
	}
	function playAudio(url, title) {
		if (!active) {
			nav.appendChild(playerroot);
		}
		audioElement.src = url;
		
		audioElement.play();
		player.appendChild(bufferw);
		playing = true;
		document.getElementById("currentplaying").innerHTML = title;
	}
	function isIE() {
            var ua = window.navigator.userAgent;
            var msie = ua.indexOf("MSIE ");

            if (msie > 0)      // If Internet Explorer, return version number
                return true;
            else                 // If another browser, return 0
	            return false;
        }
	return{init: init}
})();