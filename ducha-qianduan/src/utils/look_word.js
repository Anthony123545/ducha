//引入file-saver
import saveAs from "file-saver/dist/FileSaver";
//引入htmlDocx
import htmlDocx from "html-docx-js/dist/html-docx";

export const lookWord = (title, id, style, FX = false) => {
    let dom = document.querySelector(id);
    let str = dom.innerHTML;
    let orientation = FX;
    var orientations = ""
    if (orientation === true) {
        orientations = "landscape"
    } else {
        orientations = ""
    }
    let htmlStr = `
        <!DOCTYPE html>
        <html lang="en">
            <style>
            ${style}
            </style>
        <body style="font-family:方正仿宋_GBK;mso-ascii-font-family:'Times New Roman'">
            ${str}
        </body>
        </html>`;
    saveAs(
        htmlDocx.asBlob(htmlStr, { orientation: orientations }), title
    );
}