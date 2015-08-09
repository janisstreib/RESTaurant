var AudioPlayer = (function() {
	var enabled = false;
	var audioElement;
	var audioTag;
	function init(mime, playtext) {
		audioElement = new Audio("");
		var canPlayType = audioElement.canPlayType(mime);
		if (canPlayType.match(/maybe|probably/i)) {
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
										playtext);
								playButton.innerHTML = '<span class="glyphicon glyphicon-play"></span>';
								obj.appendChild(playButton);
								$(playButton).click(function() {
									playAudio(link, title);
								});
							});
			audioTag = document.createElement("audio");
			audioTag.setAttribute("controls", "y");
		}
		
	}
	function playAudio(url, title) {
		if(!enabled) {
			enabled = true;
			$(window).on('resize load', function() {
			    $('body').css({"padding-top": ($(".navbar").height() +$('#pdcheadcontent').height())+ "px"});
			});
		} else {
			audioTag.pause();
			document.getElementById("pdcheadcontent").removeChild(document.getElementById("currentplaying"));
			var element = document.getElementById("player");
			element.parentNode.removeChild(element);
		}
		var currplay = document.createElement("p");
		currplay.setAttribute("id", "currentplaying");
		document.getElementById("pdcheadcontent").appendChild(currplay);
		document.getElementById("pdcheadcontent").appendChild(audioTag);
		var src = document.createElement("source");
		src.setAttribute("src",url);
		audioTag.innerHTML = '';
		audioTag.appendChild(src);
		$( 'audio' ).audioPlayer();
		currplay.innerHTML = title;
		$('body').css({"padding-top": ($(".navbar").height() +$('#pdcheadcontent').height() + 10)+ "px"});
		$('#pdcheadcontent').css({"visibility": "visible"});
	}
	return{init: init}
})();