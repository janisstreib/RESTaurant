var offset = 9;
function loadNext(action, offset) {
	$.getJSON(pathprefix + "api/list.php?action=" + action + "&limit=5&offset="
			+ offset, function(data) {
		var doc = document.getElementById("list");
		var lda = document.getElementById("loadingArea");
		var load = document.createElement("img");
		load.setAttribute("src", "img/load.gif");
		lda.appendChild(load);
		var timeout = 40;
		$.each(data, function(key, val) {
			setTimeout(function() {
				var a; 
				if (h4add) {
					a = document.createElement("div");
					a.setAttribute("id", "pdcno" + val[0]);
				} else {
					a = document.createElement("a");
					a.setAttribute("href", "?/index/podcast/" + val[0]);
				}
				a
						.setAttribute("class",
								"list-group-item clear-left loadeffect");
				var tmp = document.createElement("h4");
				tmp.setAttribute("class", "list-group-item-heading");
				tmp.innerHTML = val[1];
				if (h4add) {
					if (action == "recommended") {
						var button = document.createElement("button");
						button.setAttribute("class", "close");
						button.setAttribute("aria-hidden", "true");
						button.innerHTML = '<span class="glyphicon glyphicon-trash">';
						$(button).click(function() {
							deletePDC(val[0]);
						});
						tmp.appendChild(button);
					} else if (action == "popularexcluderecom") {
						var button = document.createElement("button");
						button.setAttribute("class", "close");
						button.setAttribute("aria-hidden", "true");
						button.innerHTML = '<span class="glyphicon glyphicon-plus">';
						$(button).click(function() {
							addPDC(val[0]);
						});
						tmp.appendChild(button);
					}
				}
				a.appendChild(tmp);
				tmp = document.createElement("span");
				var type = val[5];
				if (type === "audio") {
					tmp.setAttribute("class",
							"glyphicon glyphicon-music pull-right");
					a.appendChild(tmp);
				} else if (type === "video") {
					tmp.setAttribute("class",
							"glyphicon glyphicon-film pull-right");
					a.appendChild(tmp);
				} else if (type === "mixed") {
					tmp.setAttribute("class",
							"glyphicon glyphicon-music pull-right");
					a.appendChild(tmp);
					tmp = document.createElement("span");
					tmp.setAttribute("class", "pull-right");
					tmp.innerHTML = "&nbsp;/";
					a.appendChild(tmp);
					tmp = document.createElement("span");
					tmp.setAttribute("class",
							"glyphicon glyphicon-film pull-right");
					a.appendChild(tmp);
				}
				tmp = document.createElement("div");
				tmp.setAttribute("class", "list-group-item-text");
				var img = document.createElement("img");
				img.setAttribute("data-original", val[4]);
				img.setAttribute("alt", val[1]);
				img.setAttribute("class",
						"thumbnail img-responsive podcast-thumb lazy");
				tmp.appendChild(img);
				var p = document.createElement("p");
				p.setAttribute("class", "podcast-descr");
				p.innerHTML = val[3];
				tmp.appendChild(p);
				a.appendChild(tmp);
				tmp = document.createElement("p");
				tmp.setAttribute("class", "clear-left");
				a.appendChild(tmp);
				doc.appendChild(a);
				$(function() {
					$("img.lazy").lazyload();
				});
			}, timeout);
			timeout += 100;
		});
		lda.removeChild(load);
	});
}
$(document).ready(
		function() {
			$(window).scroll(
					function() {
						if ($(window).scrollTop() == $(document).height()
								- $(window).height()) {
							offset += 5;
							loadNext(mode, offset);
						}
					});
		});
