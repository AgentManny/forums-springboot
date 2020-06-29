const updateTimes = (function () {
	const months = 'Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec'.split`,`;

	function dateString(date, now) {
		//MM/dd/yyyy HH:mm
		return months[date.getMonth()] + ' ' + date.getDate() + (date.getFullYear() === now.getFullYear() ? '' : ' ' + date.getFullYear());
	}

	function timeString(date) {
		return date.toLocaleString(navigator.languages, {hour: 'numeric', minute: 'numeric'});
	}

	function durationString(seconds) {
		if (seconds < 60) {
			return seconds + ' second' + (seconds === 1 ? '' : 's');
		}
		const minutes = Math.floor(seconds / 60);
		if (minutes < 60) {
			return minutes + ' minute' + (minutes === 1 ? '' : 's');
		}
		const hours = Math.floor(minutes / 60);
		if (hours < 24) {
			return hours + ' hour' + (hours === 1 ? '' : 's');
		}
		const days = Math.floor(hours / 24);
		if (days < 30.5) {
			return days + ' day' + (days === 1 ? '' : 's');
		}
		const months = Math.floor(days / 30.5);
		if (days < 365.25) {
			return months + ' month' + (months === 1 ? '' : 's');
		}
		const years = Math.floor(days / 365.25);
		return years + ' year' + (years === 1 ? '' : 's');
	}

	function update() {
		const now = new Date();

		document.querySelectorAll('time[data-format]').forEach(e => {
			const format = e.dataset.format;
			const date = new Date(parseInt(e.getAttribute('datetime')));
			if (format === 'datetime') {
				e.textContent = timeString(date) + ' ' + dateString(date, now);
			} else if (format === 'date') {
				e.textContent = dateString(date, now);
			} else if (format === 'datetime-natural') {
				const oneDay = 24 * 60 * 60 * 1000;
				const oneMinute = 60 * 1000;
				const dayDiff = Math.floor((date.getTime() - date.getTimezoneOffset() * oneMinute) / oneDay) - Math.floor((now.getTime() - now.getTimezoneOffset() * oneMinute) / oneDay);
				const timeStr = timeString(date);
				if (dayDiff === 0) {
					e.textContent = timeStr + ' Today';
				} else if (dayDiff === 1) {
					e.textContent = timeStr + ' Tomorrow';
				} else if (dayDiff === -1) {
					e.textContent = timeStr + ' Yesterday';
				} else {
					e.textContent = timeStr + ' ' + dateString(date, now);
				}
			} else if (format === 'ago') {
				const seconds = Math.floor((now - date) / 1000);
				e.textContent = durationString(seconds) + ' ago';
			} else if (format === 'until') {
				const seconds = Math.floor((date - now) / 1000);
				e.textContent = 'in ' + durationString(seconds);
			}
			if (!e.dataset.originalTitle) {
				e.dataset.originalTitle = timeString(date) + ' ' + dateString(date, now);
			}
		});
	}

	update();
	setInterval(update, 1500, 10);

	return update;
})();

$ (function() {
	$('[data-toggle="tooltip"]').tooltip();
	$('[data-toggle="popover"]').popover();
});

$('#button-searchPlayer').click(function() {
	$('#modal-searchPlayer').modal('toggle');
});

$('#modal-searchPlayer').on('shown.bs.modal', function (e) {
	$('#input-searchPlayer').focus();
});

$('#input-searchPlayer').focus(function() {

	if ($(this).val().length < 2) {
		$('#dropdown-searchPlayer #dropdown-toggle').dropdown('hide');
		return;
	};

	$('#dropdown-searchPlayer #dropdown-toggle').dropdown('show');

});

$('#input-searchPlayer').focusout(function() {

	setTimeout(function() {
		$('#dropdown-searchPlayer #dropdown-toggle').dropdown('hide');
	}, 100);

});



$('#input-searchPlayer').keyup(function() {

	if (typeof playerProfileRedirect !== "undefined") {
		clearTimeout(playerProfileRedirect);
	}

	if ($(this).val().length < 2) {
		$('#dropdown-searchPlayer #dropdown-toggle').dropdown('hide');
		return;
	};

	let typed = $(this).val();

	$.getJSON('/search-autocomplete?query=' + typed, function(data) {

		let suggestions = Object.keys(data);

		if (suggestions.length < 1) return;

		let dropdownItems = '';
		suggestions.forEach(function(item) {

			let letters = item.split('');
			let username = ''
			for (let i = 0; i < letters.length; i++) {

				let typedPos = item.toLowerCase().indexOf(typed.toLowerCase());
				console.log(typedPos);
				if (i >= typedPos && i < (typedPos + typed.length)) {
					username += '<strong>' + letters[i] + '</strong>';
				} else {
					username += letters[i];
				}
			}

			dropdownItems += '<div><img src="https://crafatar.com/avatars/b519c702-1b77-4675-87f2-34bbcbdd6e20?size=24&overlay"</img>' + '<a class="dropdown-item" href="/player/' + item + '">' +
				username + '</a></div>';


		});

		if (suggestions.length == 1) {
			var playerProfileRedirect = setTimeout(function() {
				window.location = '/player/' + suggestions[0];
			}, 1500);
		};

		$('#dropdown-searchPlayer .dropdown-menu').html(dropdownItems);
		$('#dropdown-searchPlayer #dropdown-toggle').dropdown('show');

	});

});