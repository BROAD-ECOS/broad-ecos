angular.module('lopl')
.factory('Hashing', [function () {
    return {
        hmac : function(message, secret) {
            return CryptoJS.HmacSHA512(message, secret);
        },
        md5 : function(message) {
            return CryptoJS.MD5(message);
        },
        sha512 : function(message) {
            return CryptoJS.SHA512(message);
        }
    };
}]);