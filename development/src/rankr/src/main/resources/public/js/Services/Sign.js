angular.module('lopl')
            .factory('Sign', ['Hashing', function (Hashing) {
                return {
                    sign : function(method, user, message, date) {
                        var message = [method, user.email, user.secret, Hashing.md5(message), date];
                        var hmac = Hashing.hmac(message.join('\n'), user.secret).toString();
                        return Hashing.sha512(hmac).toString();
                    }
                };
            }]);