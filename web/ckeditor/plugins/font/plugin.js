(function()
  {
  	function addCombo( editor, comboName, styleType, lang, entries, defaultLabel, styleDefinition )
  	{
  		var config = editor.config;
  
  		// Gets the list of fonts from the settings.
  		var names = entries.split( ';' ),
  			values = [];
  
  		// Create style objects for all fonts.
  		var styles = {};
  		for ( var i = 0 ; i < names.length ; i++ )
  		{
  			var parts = names[ i ];
  
  			if ( parts )
 			{
  				parts = parts.split( '/' );
  
  				var vars = {},
  					name = names[ i ] = parts[ 0 ];
  
  				vars[ styleType ] = values[ i ] = parts[ 1 ] || name;
  
  				styles[ name ] = new CKEDITOR.style( styleDefinition, vars );
  				styles[ name ]._.definition.name = name;
  			}
  			else
  				names.splice( i--, 1 );
  		}
  
  		editor.ui.addRichCombo( comboName,
  			{
  				label : lang.label,
  				title : lang.panelTitle,
  				className : 'cke_' + ( styleType == 'size' ? 'fontSize' : 'font' ),
  				panel :
  				{
  					css : editor.skin.editor.css.concat( config.contentsCss ),
  					multiSelect : false,
  					attributes : { 'aria-label' : lang.panelTitle }
  				},
  
  				init : function()
  				{
  					this.startGroup( lang.panelTitle );
  
  					for ( var i = 0 ; i < names.length ; i++ )
  					{
  						var name = names[ i ];
  
 						// Add the tag entry to the panel list.
  						this.add( name, styles[ name ].buildPreview(), name );
  					}
  				},
  
  				onClick : function( value )
  				{
  					editor.focus();
  					editor.fire( 'saveSnapshot' );
  
  					var style = styles[ value ];
  
  					if ( this.getValue() == value )
  						style.remove( editor.document );
  					else
  						style.apply( editor.document );
  
  					editor.fire( 'saveSnapshot' );
  				},
  
  				onRender : function()
  				{
  					editor.on( 'selectionChange', function( ev )
  						{
  							var currentValue = this.getValue();
  
  							var elementPath = ev.data.path,
  								elements = elementPath.elements;
  
  							// For each element into the elements path.
  							for ( var i = 0, element ; i < elements.length ; i++ )
  							{
  								element = elements[i];
  
  								// Check if the element is removable by any of
  								// the styles.
  								for ( var value in styles )
  								{
  									if ( styles[ value ].checkElementMatch( element, true ) )
  									{
  										if ( value != currentValue )
  											this.setValue( value );
 										return;
 									}
 								}
 							}
 
 							// If no styles match, just empty it.
 							this.setValue( '', defaultLabel );
 						},
 						this); 				}
 			});
 	}
 
 	CKEDITOR.plugins.add( 'font',
 	{
 		requires : [ 'richcombo', 'styles' ],
 
 		init : function( editor )
		{
			var config = editor.config;
 
 			addCombo( editor, 'Font', 'family', editor.lang.font, config.font_names, config.font_defaultLabel, config.font_style );
 			addCombo( editor, 'FontSize', 'size', editor.lang.fontSize, config.fontSize_sizes, config.fontSize_defaultLabel, config.fontSize_style );
 		}
 	});
 })();
 
 /**
128  * The list of fonts names to be displayed in the Font combo in the toolbar.
129  * Entries are separated by semi-colons (;), while it's possible to have more
130  * than one font for each entry, in the HTML way (separated by comma).
131  *
132  * A display name may be optionally defined by prefixing the entries with the
133  * name and the slash character. For example, "Arial/Arial, Helvetica, sans-serif"
134  * will be displayed as "Arial" in the list, but will be outputted as
135  * "Arial, Helvetica, sans-serif".
136  * @type String
137  * @example
138  * config.font_names =
139  *     'Arial/Arial, Helvetica, sans-serif;' +
140  *     'Times New Roman/Times New Roman, Times, serif;' +
141  *     'Verdana';
142  * @example
143  * config.font_names = 'Arial;Times New Roman;Verdana';
144  */
 CKEDITOR.config.font_names =
 	'Arial/Arial, Helvetica, sans-serif;' +
 	'Comic Sans MS/Comic Sans MS, cursive;' +
 	'Courier New/Courier New, Courier, monospace;' +
 	'Georgia/Georgia, serif;' +
 	'Lucida Sans Unicode/Lucida Sans Unicode, Lucida Grande, sans-serif;' +
 	'Tahoma/Tahoma, Geneva, sans-serif;' +
 	'Times New Roman/Times New Roman, Times, serif;' +
 	'Trebuchet MS/Trebuchet MS, Helvetica, sans-serif;' +
 	'Verdana/Verdana, Geneva, sans-serif';
 
 /**
157  * The text to be displayed in the Font combo is none of the available values
158  * matches the current cursor position or text selection.
159  * @type String
160  * @example
161  * // If the default site font is Arial, we may making it more explicit to the end user.
162  * config.font_defaultLabel = 'Arial';
163  */
config.font_defaultLabel = 'Arial';
 
 /**
167  * The style definition to be used to apply the font in the text.
168  * @type Object
169  * @example
170  * // This is actually the default value for it.
171  * config.font_style =
172  *     {
173  *         element		: 'span',
174  *         styles		: { 'font-family' : '#(family)' },
175  *         overrides	: [ { element : 'font', attributes : { 'face' : null } } ]
176  *     };
177  */
 CKEDITOR.config.font_style =
 	{
 		element		: 'span',
 		styles		: { 'font-family' : '#(family)' },
 		overrides	: [ { element : 'font', attributes : { 'face' : null } } ]
 	};
 
 /**
186  * The list of fonts size to be displayed in the Font Size combo in the
187  * toolbar. Entries are separated by semi-colons (;).
188  *
189  * Any kind of "CSS like" size can be used, like "12px", "2.3em", "130%",
190  * "larger" or "x-small".
191  *
192  * A display name may be optionally defined by prefixing the entries with the
193  * name and the slash character. For example, "Bigger Font/14px" will be
194  * displayed as "Bigger Font" in the list, but will be outputted as "14px".
195  * @type String
196  * @default '8/8px;9/9px;10/10px;11/11px;12/12px;14/14px;16/16px;18/18px;20/20px;22/22px;24/24px;26/26px;28/28px;36/36px;48/48px;72/72px'
197  * @example
198  * config.fontSize_sizes = '16/16px;24/24px;48/48px;';
199  * @example
200  * config.fontSize_sizes = '12px;2.3em;130%;larger;x-small';
201  * @example
202  * config.fontSize_sizes = '12 Pixels/12px;Big/2.3em;30 Percent More/130%;Bigger/larger;Very Small/x-small';
203  */
 CKEDITOR.config.fontSize_sizes =
 	'8/8px;9/9px;10/10px;11/11px;12/12px;14/14px;16/16px;18/18px;20/20px;22/22px;24/24px;26/26px;28/28px;36/36px;48/48px;72/72px';
 
 /**
208  * The text to be displayed in the Font Size combo is none of the available
209  * values matches the current cursor position or text selection.
210  * @type String
211  * @example
212  * // If the default site font size is 12px, we may making it more explicit to the end user.
213  * config.fontSize_defaultLabel = '12px';
214  */
 config.fontSize_defaultLabel = '12px';
 
 /**
218  * The style definition to be used to apply the font size in the text.
219  * @type Object
220  * @example
221  * // This is actually the default value for it.
222  * config.fontSize_style =
223  *     {
224  *         element		: 'span',
225  *         styles		: { 'font-size' : '#(size)' },
226  *         overrides	: [ { element : 'font', attributes : { 'size' : null } } ]
227  *     };
228  */
 CKEDITOR.config.fontSize_style =
 	{
 		element		: 'span',
 		styles		: { 'font-size' : '#(size)' },
 		overrides	: [ { element : 'font', attributes : { 'size' : null } } ]
 	};
 