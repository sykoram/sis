(function () {
    'use strict';

    let cssReset = `html {box-sizing: border-box; font-size: 16px !important;}
    *, *:before, *:after {box-sizing: inherit;}
    body, h1, h2, h3, h4, h5, h6, p, ol, ul, form {margin: 0; padding: 0;}`;

    // colors
    const c = {
        blue: "#2A77B5",
        blue2: "#82b5df",
        blue3: "#abcee9",
        green: "#BAD683",
        lightgray: "#ccc",
        lightorange: "#FFC38E",
    };

    let main = `
    body {
        font-family: arial, helvetica, verdana, tahoma;
    }
	h1 {
		font-size: 1.2rem;
	}
    /* HEADER */
	#stev_header_bkg {
        background-color: ${c.blue};
        color: white;
		margin: 0;
		display: flex;
        width: 100%;
    }
	#stev_header {
		height: auto;
		min-width: 100%;
		display: block;
		padding: 0;
		margin: 0;
		overflow-x: auto;
		overflow-y: hidden;
		white-space: nowrap;
	}
    #stev_module_ico {display: none}
    #verze_modulu {display: none}
    #stev_nazev {
        display: inline-block;
        margin: .25rem;
    }
    #stev_nazev_modulu {
        font-weight: bold;
    }
    #stev_podtitul_modulu {
        font-size: .9rem;
    }
    #stev_nazev.stev_empty_subtitle #stev_podtitul_modulu {display: none}
    #stev_logo {display: none}
    #stev_role_icons {
        display: block;
        margin-bottom: .25rem;
		float: none;
		white-space: break-spaces;
    }
	#stev_role_icons.anonym {
        top: 0;
    }
    #flogin {
        font-size: 0;
		top: 0 !important;
    }
    #login, #heslo {
        width: 8rem;
        background-color: white;
        border: none;
        border-radius: .25rem;
        height: 1.2rem;
        margin: 0 0 .25rem .25rem;
		font-size: .9rem;
		padding: .25rem;
    }
    #flogin input[type="submit"] {
        color: ${c.blue};
        background-color: #d8e9f6;
        border: none;
        border-radius: .25rem;
        height: 1.2rem;
        margin: 0 0 .25rem .25rem;
		font-size: .9rem;
    }
    label[for="login"], label[for="heslo"], #chkAutologin, #flogin label[for="chkAutologin"] {display: none}
	#stev_user_roles {
		display: inline-block;
	}
	#stev_role {
		padding: .25rem .25rem .25rem .5rem !important;
	}
	#stev_jmeno {
		font-size: 0;
		padding: 0 !important;
	}
	#stev_jmeno > b {
		font-size: .75rem;
	}
	/*#stev_user_roles {display: none}*/
	#stev_role_obj {
		font-size: 0;
		background: none !important;
		padding: 0 !important;
	}
	#stev_role_obj::after {
		content: "▼";
        font-size: 1rem;
        line-height: 1rem;
	}
	#stev_role_menu {
		position: static;
	}
	#stev_role_list {
		left: 0;
		top: auto !important;
		right: auto !important;
		width: 100%;
	}
	#stev_role_list .tab2 {
		display: flex;
        width: 100%;
		margin: 0 !important;
	}
	#stev_role_list .tab2 tbody {
		overflow-x: auto;
		overflow-y: hidden;
		white-space: nowrap;
	}
	#stev_user_roles_fak {display: none}
	#stev_icons {
		margin-bottom: .5rem;
		top: .3rem !important;
	}
	.stev_ico {
		background: none !important;
		color: white;
		width: auto !important;
		height: auto !important;
		margin-left: .25rem;
		padding: 0 .25rem;
	}
	#stev_home::after {
		content: "⌂";
        font-size: 2rem;
        line-height: 1rem;
	}
	#stev_login::after {
		/*content: "⎆";*/
		content: "⚿";
        font-size: 1.5rem;
        line-height: 1rem;
	}
	#stev_logoff::after {
		content: "⚿";
        font-size: 1.5rem;
        line-height: 1rem;
	}
	#stev_settings::after {
		content: "⚙";
        font-size: 1.5rem;
        line-height: 1rem;
	}
	#stev_lang_en::after {
		content: "🇬🇧";
        font-size: 1.5rem;
        line-height: 1rem;
	}
	#stev_lang_cz::after {
		content: "🇨🇿";
        font-size: 1.5rem;
        line-height: 1rem;
	}
	#stev_help::after {
		content: "?";
        font-size: 1.5rem;
        line-height: 1rem;
	}
    /* MENU */
    body > table, body > table > tbody, body > table > tbody > tr, body > table > tbody > tr > td {
        display: block;
    }
    body > table > tbody > tr > td {
        width: 100%;
        overflow: auto;
    }
    table.menu {
        display: flex;
        width: 100%;
        margin: 0 !important;
    }
	table.menu tbody {
		width: 100%;
	}
    table.menu .menu1, table.menu .menu2 {
        display: flex;
        width: 100% !important;
        overflow-x: auto;
		overflow-y: hidden;
    }
    table.menu .menu1 td, table.menu .menu2 td {
        display: flex;
        border: none !important;
    }
    table.menu td > * {
        padding: .5rem !important;
        color: black !important;
        text-decoration: none;
		border: 0 !important;
    }
    .menu1 .session, .menu_empty {display: none !important}
	.menu .ico {
		background-color: transparent !important;
	}
	.menu .ico > a {
		background: none;
		width: auto;
		height: auto;
	}
	.menu .ico .home::after {
        content: "⌂";
        font-size: 1.6rem;
        line-height: .8rem;
    }
	.menu .ico .bookmarks::after {
        content: "☆";
        font-size: 1.4rem;
        line-height: 1rem;
    }
	.menu .ico .print {display: none}
	/*.menu .ico .print::after {
        content: "🗎";
        font-size: 1.2rem;
        line-height: 1rem;
    }*/
    .menu .ico .modul {display: none}
    /*.menu .ico .modul::after {
        content: "⊞";
        font-size: 1.5rem;
        line-height: 1rem;
    }
    #stev_modul_list {
        position: absolute;
        left: 0;
        z-index: 100;
        background-color: ${c.blue2};
        border: 1px solid ${c.blue};
    }*/
    .menu1 {
        background-color: ${c.blue2} !important;
    }
    .menu2 {
        background-color: ${c.blue3} !important;
    }
    .menu .menu_a {
        background-color: white;
        font-weight: bold;
    }
    .menu2 td:first-child {
        display: none !important;
    }
	.menu .shadow, .menu .shadow_a {display: none;}
	/* MESSAGES */

	/* FILTER */
	.filtr_div {
		border: 1px solid ${c.blue} !important;
		background-color: #eee !important;
		padding: 0 !important;
		margin: .25rem 0 !important;
	}
	.filtr_div > div:first-child {
		background-color: ${c.blue3} !important;
		padding: .25rem !important;
		border: none !important;
	}
	.filtr_div > div:first-child > a {
		background: none !important;
		padding: 0 !important;
	}
	.filtr_div > div:first-child > a.filtr_on::before {
		content: "▲";
	}
	.filtr_div > div:first-child > a.filtr_off::before {
		content: "▼";
	}
	.filtr_div > div:nth-child(2) {
		display: flex;
        width: 100%;
        /*margin: 0 !important;*/
	}
	.filtr_div > div:nth-child(2) > form {
		display: flex;
        width: 100% !important;
        overflow-x: auto;
		/*overflow-y: hidden;*/
	}
	.filtr_div > div:nth-child(2) > form .tab1 {
		display: flex;
		width: 100%;
	}
	.filtr_div > div:nth-child(2) > form td {
		white-space: nowrap;
	}
	.filtr_div + div[id^="filtr_hr"] {display: none}
	#akce_div {
		border-top-width: 1px !important;
	}
	#akce_hr {display: none !important}
	/* CONTENT */
	#content {
		overflow: hidden;
	}
	#content .tab1 {
        display: flex;
        width: 100%;
        margin: .25rem 0 !important;
		/*border: 1px solid ${c.lightgray} !important;*/
    }
	#content .tab1 > colgroup {
		display: none;
	}
	#content .tab1 > tbody {
        overflow-x: auto;
		overflow-y: hidden;
		white-space: nowrap;
    }

	.head1, .head1 td, .head2, .head2 td {
		border-top: none !important;
		border-bottom: none !important;
	}
	.head1, .head1 td {
		background-color: ${c.blue} !important;
	}
	.head2, .head2 td {
		background-color: ${c.blue2} !important;
	}
	.head3, .head3 td {
		border-top-width: 1px !important;
	}
	.form_div {
		margin: .25rem 0 !important;
	}
	.form_div > table {
        display: flex;
        width: 100%;
    }
	.form_div > table > tbody {
        overflow-x: auto;
		overflow-y: hidden;
		white-space: nowrap;
    }
	.tab1 *[id^="pamela"] {
		white-space: break-spaces;
	}
	.tab1 *[id^="pamela"] > div:first-child {display: none !important}
	#roztab {
        display: flex;
        width: 100%;
    }
	#roztab > tbody {
        overflow-x: auto;
		overflow-y: hidden;
		white-space: nowrap;
    }
	.link5 {
		line-height: .8em;
	}
    /* FOOTER */
	#foot {
        display: flex;
        width: 100%;
        margin: 1rem 0 0 0 !important;
		border: 0 !important;
    }
	#foot tbody {
		width: 100%;
	}
    #foot tbody tr {
		display: flex;
		width: 100% !important;
        overflow-x: auto;
        overflow-y: hidden;
		background-color: ${c.blue2} !important;
    }
	#foot td > * {
		padding: .5rem !important;
        color: black;
        text-decoration: none;
		border: 0 !important;
	}
	#foot td > a:hover {
		background-color: ${c.blue3} !important;
	}
	#foot .top, #foot .tisk {display: none !important}
	#paticka2 {
		margin: .5rem 0 !important;
	}
    `;

    /*
    HORIZONTAL SCROLL:
    outer {
        display: flex;
        width: 100%;
        margin: 0 !important;
    }
    inner {
        display: flex;
        width: 100vw !important;
        overflow-x: auto;
                overflow-y: hidden;
    }
    inner item {
        display: flex;
        border: none !important;
    }
    */

    function addViewportMeta() {
        let meta = document.createElement("meta");
        meta.setAttribute("name", "viewport");
        meta.setAttribute("content", "width=device-width, initial-scale=1, minimum-scale=1");
        document.head.appendChild(meta);
    }

    function addCustomStyle() {
        let styleEl = document.createElement("style");
        styleEl.setAttribute("type", "text/css");
        styleEl.setAttribute("class", "custom");
        styleEl.appendChild(document.createTextNode(cssReset + main));
        document.head.appendChild(styleEl);
    }

    addViewportMeta();
    addCustomStyle();

})();