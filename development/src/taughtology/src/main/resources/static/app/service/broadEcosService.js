'use strict';

(function() {
    var app = angular.module('taughtology')
    app.factory('broadEcosService', ['localStorageService', function (localStorageService) {

        var _broadEcos = {};

        return {

            'init': function (token, platform) {
                var initialized = true;
                if (token) {
                    var currentToken = localStorageService.get('token');

                    if (!currentToken || token != currentToken) {
                        localStorageService.set('token', token);
                        localStorageService.set('platform', platform);
                        initialized = true;
                    }
                }

                return initialized;
            },

            'context': function(){
                var context = false;
                var token = localStorageService.get('token');
                var platform = localStorageService.get('platform');

                if (token && platform) {
                    context = {
                        'token': token,
                        'platform': platform
                    }
                }

                return context;
            }
        };

    }]);
})();