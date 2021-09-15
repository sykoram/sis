(function() {
  'use strict';

  function scrollActiveMenuIntoView() {
  	let active = document.getElementsByClassName("menu_a")
		for (let i = 0; i < active.length; i++) {
      active[i].scrollIntoView(false)
    }
  }

  scrollActiveMenuIntoView();

  document.getElementById("login").placeholder = "Login";
  document.getElementById("heslo").placeholder = "Heslo";

})();