(function() {
  'use strict';

  function scrollActiveMenuIntoView() {
  	let active = document.getElementsByClassName("menu_a")
		for (let i = 0; i < active.length; i++) {
      active[i].scrollIntoView(false)
    }
  }

  scrollActiveMenuIntoView();

  document.querySelector("#flogin #login").placeholder = "Login";
  document.querySelector("#flogin #heslo").placeholder = "Password";

})();