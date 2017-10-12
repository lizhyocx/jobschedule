class SessionStorageClient {
    //添加键值对
    put(key, value) {
        sessionStorage.setItem(key, value);
    }

    //添加json对象
    putJSON(key, json) {
        sessionStorage.setItem(key, JSON.stringify(json));
    }

    //根据key查找JSON对象
    getJSON(key) {
        if (sessionStorage.getItem(key) == 'undefined')
            return null;
        return JSON.parse(sessionStorage.getItem(key));
    }

    //根据key查找value
    get(key) {
        if (sessionStorage.getItem(key) == 'undefined')
            return null;
        return sessionStorage.getItem(key)
    }

    //移除key对应的元素
    remove(key) {
        sessionStorage.removeItem(key);
    }

    //清空localStorage
    clear() {
        sessionStorage.clear();
    }

    //根据索引获得item，结果为一个json对象 {key,value}
    indexOf(index) {
        var item = {};
        if (sessionStorage.length <= index) {
            return null;
        }

        var key = sessionStorage.key(i);
        var value = sessionStorage.getItem(key);
        item.key = key;
        item.value = value;
        return item;
    }

    /**
     * 注入页面之间跳转需要传递的参数
     * @paramObj 一个JSON对象，用来传递参数，其格式如下:
     *             {"articleId":"123456"}
     */
    injectParamObject (paramObj) {
        this.putJSON("paramObj", paramObj);
    }

    /**
     * 取出存在sessionStorage中的参数对象
     * @return 一个JSON对象，其格式如下:
     *  {"articleId":"123456"}
     */
    takeOutParamObject() {
        return this.getJSON("paramObj");
    }

}

window.sessionStorageClient = new SessionStorageClient();
