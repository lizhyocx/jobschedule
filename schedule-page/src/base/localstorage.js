class LocalStorageClient {
    //添加键值对
    put(key, value) {
        localStorage.setItem(key, value);
    }

    //添加json对象
    putJSON(key, json) {
        localStorage.setItem(key, JSON.stringify(json));
    }

    //根据key查找JSON对象
    getJSON(key) {
        if (localStorage.getItem(key) == 'undefined')
            return	null;
        return JSON.parse(localStorage.getItem(key));
    }

    //根据key查找value
    get(key) {
        if(localStorage.getItem(key) == 'undefined')
            return	null;
        return localStorage.getItem(key)
    }

    //移除key对应的元素
    remove(key) {
        localStorage.removeItem(key);
    }

    //清空localStorage
    clear () {
        localStorage.clear();
    }

    //根据索引获得item，结果为一个json对象 {key,value}
    indexOf(index) {
        var item = {};
        if (localStorage.length <= index){
            return null;
        }

        var key = localStorage.key(i);
        var value = localStorage.getItem(key);
        item.key = key;
        item.value = value;
        return item;
    }

    /**
     * localStorage缓存数据方法
     * key: localStorage存储的key值，不以“cache_”开头的key，一律自动加上"cache_"前缀
     * data: 存储的数据
     * flag: 是否和用户相关 true or false
     */
    save(key, data, flag) {
        if (!key  || !data || !cacheEnable) {
            return false;
        }
        key = this._buildCacheKey(key);
        var d = {
            data: data,
            timestamp: Date.now()
        };
        if (flag) {
            var user = this._getUser();
            d.user = user;
        }
        try {
            this.putJSON(key, d);
        } catch (e) {
            // 异常，超出容量限制，清空缓存
            this.clearCache();
            return false;
        }
        return true;
    }

    /**
     * localStorage缓存数据方法
     * preKey: 存储的key值前缀，不以“cache_”开头的key，一律自动加上"cache_"前缀
     * variableKey： key值可变部分，如id
     * count： 相同前缀key下最多可以存储多少条
     * data: 存储的数据
     * flag: 是否和用户相关 true or false
     */
    saveAssignCount(preKey, variableKey, count, data, flag) {
        if (!preKey  || !data || !cacheEnable) {
            return false;
        }
        preKey = this._buildCacheKey(preKey);
        var d = {
            data: data,
            timestamp: Date.now()
        };
        if (flag) {
            var user = this._getUser();
            d.user = user;
        }
        if (this.getJSON(preKey + variableKey) == null) {
            // 不是同键值，需要检测是否需要删除老数据
            var cacheData = new Array();
            for (var i = 0; i < localStorage.length; i++ ) {
                var storageKey = localStorage.key(i);
                if (storageKey.indexOf(preKey) == 0) {
                    // 取相同前缀key的缓存对象的key和时间，以便数量超过时删除
                    cacheData.push({key:storageKey, time: this.getJSON(storageKey).timestamp});
                }
            }
            if (cacheData.length > count) {
                // 按时间从小到达排列
                cacheData.sort(function(a, b) {
                    return (b.time - a.time);
                })
                for (var j = cacheData.length - 1; j >= count; j--) {
                    this.remove(cacheData[j].key);
                }
            }
        }
        try {
            this.putJSON(preKey + variableKey, d);
        } catch (e) {
            // 异常，超出容量限制，清空缓存
            this.clearCache();
            return false;
        }
        return true;
    }

    /**
     * 从localStorage读取缓存数据方法
     * key: localStorage存储的key值
     * overtime: 超时时间（分钟）,默认60分钟
     */
    load(key, overtime) {
        if (!key || !cacheEnable) {
            return null;
        }
        key = this._buildCacheKey(key);
        var d = this.getJSON(key);
        if (d == null) {
            return null;
        }
        if (isNaN(overtime) || overtime < 0) {
            overtime = 60;
        }
        var user = this._getUser();
        if (d.timestamp + overtime * 60 * 1000 > Date.now()) {
            // 时间在有效期内
            if (!d.user || d.user === user) {
                // 存储的数据和用户无关或者同一用户
                return d.data;
            } else {
                // 不同用户，清除缓存
                this.remove(key);
                return null;
            }
        } else {
            // 超过有效期，清除缓存
            this.remove(key);
            return null;
        }
    }

    // 读写缓存信息时的用户(这里取商户的cookie)
    _getUser() {
        if (document.cookie.length > 0) {
            var c_name = "YZ_KB_SUPPLIER_ID";
    		c_start = document.cookie.indexOf(c_name + "=")
    		if (c_start != -1) {
    			c_start = c_start + c_name.length + 1
    			c_end = document.cookie.indexOf(";", c_start)
    			if (c_end == -1) c_end = document.cookie.length
    			return unescape(document.cookie.substring(c_start, c_end));
    		}
    	}
        return "unknown";
    }

    // 不以“cache_”开头的key，一律自动加上"cache_"前缀
    _buildCacheKey(key) {
        if (key.indexOf("cache_") !== 0) {
            return "cache_" + key;
        }
        return key;
    }

    /**
     * 删除缓存数据"cache_"开头的
     */
    clearCache() {
        for (var i = 0; i < localStorage.length; i++ ) {
            var storageKey = localStorage.key(i);
            if (storageKey.indexOf("cache_") === 0) {
                localStorage.removeItem(storageKey);
            }
        }
    }

}

window.localStorageClient = new LocalStorageClient();
