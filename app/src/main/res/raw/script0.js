(function () {
    'use strict';

    const cssReset = `html {box-sizing: border-box; font-size: 16px !important;}
    *, *:before, *:after {box-sizing: inherit;}
    body, h1, h2, h3, h4, h5, h6, p, ol, ul, form {margin: 0; padding: 0;}`;

    // colors
    const c = {
        blue: "#276aaa",
        blue2: "#82b5df",
        blue3: "#abcee9",
		blue4: "#d3e6f4",
    };

    const main = `
	html, body {
		height: 100%;
	}
    body {
        font-family: arial, helvetica, verdana, tahoma;
		display: flex;
		flex-direction: column;
    }
	body > * {
		flex: 1 0 auto;
	}

	h1 {
		font-size: 1.2rem;
	}
    
	/* HEADER */
	a[name="stev_top"] {
		display: none;
	}
	#stev_header_bkg {
        background-color: #2A77B5;
		background-image: linear-gradient(to right, #1f4789, #2a77b5);
        color: white;
		margin: 0;
		display: flex;
        width: 100%;
		flex-grow: 0;
    }
	#stev_header {
		height: auto;
		min-width: 100%;
		display: block;
		padding: 0;
		margin: 0;
		border: none;
		overflow-x: auto;
		overflow-y: hidden;
		white-space: nowrap;
	}
    #stev_module_ico {display: none}
    #verze_modulu {display: none}
    #stev_nazev {
        display: inline-block;
        margin: .5rem;
    }
    #stev_nazev_modulu {
        font-weight: bold;
		padding: 0;
    }
    #stev_podtitul_modulu {
        font-size: .9rem;
		margin: 0;
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
    #flogin #login, #flogin #heslo {
        width: 6rem;
        background-color: white;
        border: none;
        border-radius: .25rem;
        height: 1.3rem;
        margin: 0 .2rem .2rem 0;
		font-size: .9rem;
		padding: .25rem;
    }
	#flogin #heslo {
		width: 8rem;
	}
    #flogin input[type="submit"] {
        color: ${c.blue};
        background-color: #d8e9f6;
        border: none;
        border-radius: .25rem;
        height: 1.3rem;
        margin: 0;
		font-size: .9rem;
    }
    #flogin label[for="login"], #flogin label[for="heslo"], #chkAutologin, #flogin label[for="chkAutologin"] {display: none}
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
	#stev_role_obj:hover {
		color: white !important;
		text-decoration: none !important;
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
	#stev_role_list .link2 {
		color: black !important;
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
        font-size: 1.8rem;
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

	#loading .head1 {
		background-color: ${c.blue} !important;
		color: white !important;
	}
	#loading .head1 + div {
		border: 1px solid ${c.blue} !important;
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
        padding: .75rem .5rem !important;
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
		padding: .75rem !important;
	}
	.menu .ico .home::after {
        content: "⌂";
        font-size: 1.4rem;
        line-height: .9rem;
    }
	.menu .ico .bookmarks::after {
        content: "☆";
        font-size: 1.4rem;
        line-height: 1rem;
    }
	.menu .ico .print {display: none}
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
	.menu1 td > a:hover {
		background-color: ${c.blue3} !important;
	}
    .menu2 {
        background-color: ${c.blue3} !important;
    }
	.menu2 td > a:hover {
		background-color: ${c.blue4} !important;
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
	td.info_ico, td.warning_ico, td.error_ico {
		display: none;
	}
	td.info_text, td.warning_text, td.error_text {
		border: none !important;
	}
	.pozn1 {
		border-top-width: 1px;
	}
	.div_legend {
		border-top-width: 1px;
	}

	/* FILTER */
	.filtr_div {
		border: 1px solid ${c.blue} !important;
		background-color: #eee !important;
		padding: 0 !important;
		margin: .5rem .25rem !important;
	}
	.filtr_div > div:first-child {
		background-color: ${c.blue3} !important;
		border: none !important;
	}
	.filtr_div > div:first-child > a {
		background: none !important;
		display: block;
		padding: .25rem;
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
		flex-direction: column;
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
	.filtr_div .tab1 th {
		vertical-align: middle;
	}
	.filtr_div .inp1 {
		font-size: 1.2em;
		margin: 0;
		background-color: white;
		border: none;
		border-bottom: 1px solid ${c.blue};
	}
	.filtr_div .inp1[type="text"] {
		padding-left: .25rem;
	}
	.filtr_div input[type="checkbox"], .filtr_div input[type="radio"] {
		margin-left: .25rem;
	}
	.filtr_div + div[id^="filtr_hr"] {display: none}
	.but_find, .but_next, .but_save, .but1, .but_akce, .but_akce2, .but_delete {
		background: ${c.blue} !important;
		color: white !important;
		padding: .25rem .75rem !important;
		border: 0;
		font-size: 1.2em;
	}
	.but_find > a, .but_next > a, .but_save > a, .but1 > a, .but_akce2 > a, .but_delete > a {
		color: white !important;
	}
	.but_filtr, img[src="../img/ico_n_arrow_refresh_small_grey.png"] {
		display: none;
	}
	#akce_div {
		border-top-width: 1px !important;
		background-color: #eee !important;
		padding: 0 !important;
		margin: .5rem .25rem !important;
	}
	#akce_div > div:first-child {
		background-color: #ebc5c5 !important;
		border: none !important;
		padding: .25rem !important;
	}
	#akce_div > div:first-child > img {
		display: none;
	}
	#akce_hr {display: none !important}
	.inp2 {
		background-color: white !important;
	}
	
	/* CONTENT */
	#content {
		overflow: hidden;
	}
	#content .tab1 {
        display: flex;
        width: 100%;
        margin: .25rem 0 !important;
    }
	#content .tab1 > colgroup {
		display: none;
	}
	#content .tab1 > tbody {
        overflow-x: auto;
		overflow-y: hidden;
		white-space: nowrap;
    }
	#content .tab1 .row,
	#content .tab1 .row td,
	#content .tab1 .row1,
	#content .tab1 .row1 td,
	#content .tab1 .row2,
	#content .tab1 .row2 td {
		border: none;
		border-right: 1px dotted #ddd;
	}

	#content .row_tab, #content .row_tab_left {
		display: none;
	}

	.head1, .head1 td, .head2, .head2 td {
		border-top: none !important;
		border-bottom: none !important;
	}
	.head1, .head1 td, .head10, .head10 td {
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
		border-color: ${c.blue};
	}
	.form_div_title {
		background-color: ${c.blue};
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
	td img[src$="order_down.gif"], td img[src$="order_up.gif"],
	td img[src$="order_down_a.gif"], td img[src$="order_up_a.gif"] {
		display: none;
	}
	.tab1 *[id^="pamela"] {
		white-space: normal;
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
		border: none !important;
	}
	.link5:hover {
		background-color: transparent !important;
		text-decoration: underline;
	}
	.seznam1 .akt {
		border: none !important;
		background-color: ${c.blue} !important;
		padding: .25rem;
	}
	.seznam1 .link1 {
		padding: .25rem;
		border: none;
	}
	.seznam1 .link1:hover {
		border: none;
		text-decoration: none;
	}

	.link2, .link3, .link8 {
		color: ${c.blue} !important;
	}
	.link8 {
		background: none !important;
		padding: 0 !important;
	}

    /* FOOTER */
	#foot, #foot + .shadow {
        display: none;
    }
	#paticka2 {
		margin: 0 !important;
		padding: .5rem 0 !important;
		flex-grow: 0;
	}
    `;

    const pageSpecific = `
	/* INDEX */
	body.index .menu1 + tr {display: none}
	body.index #content {
		padding-top: 0;
	}
	body.index #content > div:nth-child(2n) {
		display: block !important;
		background-image: linear-gradient(to right, #1f4789, #2a77b5) !important;
		padding: .5rem !important;
	}
	body.index #content > div:nth-child(2n+1) {
		background-color: white !important;
		border: none !important;
		padding: .5rem !important;
		margin-bottom: 0 !important;
	}
	body.index #content .hr1 {
		border: none;
		border-bottom: 1px solid #ccc;
	}
	body.index #content .link5 {
		width: 16rem !important;
		line-height: .9em;
		border: none !important;
	}
	body.index #content .link5:hover {
		background-color: transparent !important;
		text-decoration: underline;
	}
	body.index #content .link5 > img:last-child {
		height: 32px !important;
	}
	body.index #hint {display: none}

	/* ANKETA */
	body.anketa #content > .tab1 > tbody {
		white-space: normal;
	}

	/* KDOJEKDO */
	body.kdojekdo #hledani span.form_select ~ a,
	body.kdojekdo #hledani span.form_select ~ span,
	body.kdojekdo #hledani span.form_select_multiple ~ a,
	body.kdojekdo #hledani span.form_select_multiple ~ span {
		position: relative !important;
		top: 0 !important;
		left: 0 !important;
	}
	`;

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
        styleEl.appendChild(document.createTextNode(cssReset + main + pageSpecific));
        document.head.appendChild(styleEl);
    }

    addViewportMeta();
    addCustomStyle();

})();