/**
@ Autor :@yonax73 | yonax73@gmail.com
@ Version: 0.1
@ Date : 5/25/2015
@ Date update: 5/25/2015
@ Update by: @yonax73  | yonax73@gmail.com
@ Description: utils
**/
var Utils = (function () {
    
    function Utils() {}

    Utils.serializeObject = function (object) {      
        var serialized = [];
        var i = 0;
        var n = object.length;
        for (i = 0; i < n; i++) {
           var obj = object[i];  
           serialized.push(obj.name + '=' + obj.value);
        }
        return serialized.join('&');
    };

    return Utils;
})();
