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

	$.getJSON('http://158.69.23.6:8080/player/search?value=' + typed, function(data) {

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

			dropdownItems += '<a class="dropdown-item" href="' + siteURL + 'player/' + item + '">' + username + '</a>';


		});

		if (suggestions.length == 1) {
			var playerProfileRedirect = setTimeout(function() {
				window.location.replace(siteURL + 'player/' + suggestions[0]);
			}, 1500);
		};

		$('#dropdown-searchPlayer .dropdown-menu').html(dropdownItems);
		$('#dropdown-searchPlayer #dropdown-toggle').dropdown('show');

	});

});
