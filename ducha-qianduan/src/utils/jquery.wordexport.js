import jQuery from "jquery"
if (typeof jQuery !== "undefined" && typeof saveAs !== "undefined") {
    (function ($) {
        $.fn.wordExport = function (exPortData) {

            _initData(exPortData);
            var options = {
                maxWidth: 624
            };
            // Clone selected element before manipulating it
            var markup = $(this).clone();

            // Remove hidden elements from the output
            markup.each(function () {
                var self = $(this);
                if (self.is(':hidden'))
                    self.remove();
            });

            // Embed all images using Data URLs
            var images = Array();
            var img = markup.find('img');
            for (var i = 0; i < img.length; i++) {
                // Calculate dimensions of output image
                var w = Math.min(img[i].width, options.maxWidth);
                var h = img[i].height * (w / img[i].width);
                // Create canvas for converting image to data URL
                var canvas = document.createElement("CANVAS");
                canvas.width = w;
                canvas.height = h;
                // Draw image to canvas
                var context = canvas.getContext('2d');
                context.drawImage(img[i], 0, 0, w, h);
                // Get data URL encoding of image
                var uri = canvas.toDataURL("image/png");
                $(img[i]).attr("src", img[i].src);
                img[i].width = w;
                img[i].height = h;
                // Save encoded image to array
                images[i] = {
                    type: uri.substring(uri.indexOf(":") + 1, uri.indexOf(";")),
                    encoding: uri.substring(uri.indexOf(";") + 1, uri.indexOf(",")),
                    location: $(img[i]).attr("src"),
                    data: uri.substring(uri.indexOf(",") + 1)
                };
            }

            // Prepare bottom of mhtml file with image data
            var mhtmlBottom = "\n";
            for (var i = 0; i < images.length; i++) {
                mhtmlBottom += "--NEXT.ITEM-BOUNDARY\n";
                mhtmlBottom += "Content-Location: " + images[i].location + "\n";
                mhtmlBottom += "Content-Type: " + images[i].type + "\n";
                mhtmlBottom += "Content-Transfer-Encoding: " + images[i].encoding + "\n\n";
                mhtmlBottom += images[i].data + "\n\n";
            }
            mhtmlBottom += "--NEXT.ITEM-BOUNDARY--";
            var fileContent = getModelHtml(markup.html(), mhtmlBottom, exPortData);
            var blob = new Blob([fileContent], {
                type: "application/msword;charset=utf-8"
            });
            saveAs(blob, exPortData.fileName + exPortData.fileType);
        };
        function _initData(exPortData) {
            exPortData.fileName = typeof (exPortData.fileName) !== 'undefined' ? exPortData.fileName : "jQuery-Word-Export";
            exPortData.fileType = typeof (exPortData.fileType) !== "undefined" ? exPortData.fileType : ".doc";
        }
        function getModelHtml(mhtml, mhtmlBottom, exPortData) {
            var styles, updateStyles, header, footer = "";
            if (exPortData.styles) {
                styles = exPortData.styles.html();
            }
            if (exPortData.updateExportCss) {
                updateStyles = exPortData.updateExportCss.html();
            }
            if (exPortData.isHeader) {
                header = "<div style=\"mso-element:header;padding-bottom:20px;\" id=\"h1\">\n" +
                    "<p class=\"MsoHeader\">" + exPortData.header + "</p>\n" +
                    "</div>";
            }
            if (exPortData.isFooter) {
                footer = "<div style=\"mso-element:footer;text-align: center;\" id=\"f1\">\n" +
                    "<p class=\"MsoHeader\">" + exPortData.footer + "</p>\n" +
                    "<p class=\"MsoNum\"><span class=\"num\" style=\"mso-field-code: PAGE \"></span>/<span style=\"mso-field-code:NUMPAGES\"></span>\n</p>\n" +
                    "</div>";
            }

            /*
                html标签中，和meta标签下面的判断，用来默认显示页面视图模式
             */
            return `
                <!DOCTYPE html>
                <html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" xmlns:m=\"http://schemas.microsoft.com/office/2004/12/omml\" xmlns=\"http://www.w3.org/TR/REC-html40\">
                <head>
                    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
					<!--[if gte mso 9]><xml><w:WordDocument><w:View>Print</w:View><w:TrackMoves>false</w:TrackMoves><w:TrackFormatting/><w:ValidateAgainstSchemas/><w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid><w:IgnoreMixedContent>false</w:IgnoreMixedContent><w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText><w:DoNotPromoteQF/><w:LidThemeOther>EN-US</w:LidThemeOther><w:LidThemeAsian>ZH-CN</w:LidThemeAsian><w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript><w:Compatibility><w:BreakWrappedTables/><w:SnapToGridInCell/><w:WrapTextWithPunct/><w:UseAsianBreakRules/><w:DontGrowAutofit/><w:SplitPgBreakAndParaMark/><w:DontVertAlignCellWithSp/><w:DontBreakConstrainedForcedTables/><w:DontVertAlignInTxbx/><w:Word11KerningPairs/><w:CachedColBalance/><w:UseFELayout/></w:Compatibility><w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel><m:mathPr><m:mathFont m:val=\"Cambria Math\"/><m:brkBin m:val=\"before\"/><m:brkBinSub m:val=\"--\"/><m:smallFrac m:val=\"off\"/><m:dispDef/><m:lMargin m:val=\"0\"/> <m:rMargin m:val=\"0\"/><m:defJc m:val=\"centerGroup\"/><m:wrapIndent m:val=\"1440\"/><m:intLim m:val=\"subSup\"/><m:naryLim m:val=\"undOvr\"/></m:mathPr></w:WordDocument></xml><![endif]-->
                    <style type="text/css">
                        table#hrdftrtbl {
                            width: 1px;
                            height: 1px;
                            overflow: hidden;
                        }
                
                        p.MsoHeader {
                            font-size: 20px;
                            font-weight: bold;
                            text-align: center;
                        }
                
                        p.MsoFooter, li.MsoFooter, div.MsoFooter {
                            margin: 0in;
                            mso-pagination: widow-orphan;
                            tab-stops: center 3.0in right 6.0in;
                            font-size: 20pt;
                            font-weight: bold
                        }
						
						p.MsoNum{
							text-align: center;
						}
                
                        <!-- /*FontDefinitions*/
                        @page WordSection {
							mso-title-page:yes; //首页不显示页眉页脚
							mso-page-numbers:0; //起始页码从0开始
                            mso-header-margin: 20px;
                            mso-footer-margin: 20px;
                            mso-header: h1;
                            mso-footer: f1;
                        }
                
                        div.WordSection {
                            page: WordSection;
                        }
                
                        -->
                        ${styles}
                        ${updateStyles}
                    </style>
                </head>
                <body lang=ZH-CN style="tab-interval:21.0pt">
                <div class="WordSection">
                    ${mhtml}
                    
                    <table id="hrdftrtbl" border="0" cellspacing="0" cellpadding="0">
                        <tbody>
                        <tr>
							<td>
								${header}
							</td>
							<td>
								${footer}
							</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                </body>
                </html>
    `}
    })(jQuery);
} else {
    if (typeof jQuery === "undefined") {
        console.error("jQuery Word Export: missing dependency (jQuery)");
    }
    if (typeof saveAs === "undefined") {
        console.error("jQuery Word Export: missing dependency (FileSaver.js)");
    }
}