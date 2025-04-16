
/* 下载文件的公共方法，参数就传blob文件流*/
export const handleExport = (data) => {
    // 动态创建iframe下载文件
    let fileName = this.selectedTabelRow[0].dirName;
    if (!data) {
        return;
    }
    let blob = new Blob([data], { type: "application/octet-stream" });
    if ("download" in document.createElement("a")) {
        // 不是IE浏览器
        let url = window.URL.createObjectURL(blob);
        let link = document.createElement("a");
        link.style.display = "none";
        link.href = url;
        link.setAttribute("download", fileName);
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link); // 下载完成移除元素
        window.URL.revokeObjectURL(url); // 释放掉blob对象
    } else {
        // IE 10+
        window.navigator.msSaveBlob(blob, fileName);
    }
}