/**
 * Created by Maikel Rivero Dorta mriverodorta@gmail.com on 7/08/14.
 */
"use strict";
angular.module('lopl')
    .factory('RequestInterceptor', ['Sign', 'UserService', function(Sign, UserService) {

        /**
         * Configure the Request Interceptor here
         */          
        var requestInterceptor = {
            request: function(config) {

                var userData = UserService.getCurrent() || UserService.getLoginRequestUserData();

                if (userData) {

                    var date = new Date().toString();
                    var method = config.method;
                    var data = JSON.stringify(config.data);

                    var signedMessage = Sign.sign(method, userData, data, date);

                    config.headers['Authorization'] = userData.email+':'+signedMessage;
                    config.headers['X-LOPL-Date'] = date;
                }

                return config;
            }
        };
        return requestInterceptor;
    }]);
