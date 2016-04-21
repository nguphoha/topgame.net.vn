/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.toolbar = [
		{ name: 'mode', items: [ 'Source' ]},
		{ name: 'clipboard', items : [ 'PasteFromWord', '-', 'Undo', 'Redo' ] },
		{ name: 'links', items: [ 'Link', 'Unlink'] },
		{ name: 'insert', items: [ 'Image', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'Iframe' ] },
		{ name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ]  },
		{ name: 'colors',      items : [ 'TextColor','BGColor' ] },
		'/',
		{ name: 'paragraph',   items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock']  },
		{ name: 'styles',      items : [ 'Styles','Format','Font','FontSize' ]  },
		{ name: 'tools', items: [ 'Maximize'] },
	];
        //config.skin = 'kama';
        config.extraPlugins = 'justify';
};
